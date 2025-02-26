package real;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Period;

import javax.swing.JOptionPane;

public class User {
    private String name;
    private String dob;
    private int age;
    private int id;
    private String address;
    private String gender;
    private int pointAmount;
    
    public User(){}

    public User(String name,String dob,String address,String gender,int pointAmount,int id){
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.gender = gender;
        this.pointAmount = pointAmount;
        this.id = id;
        this.age = ageCAlc(dob);//will fix with an actual calc eventually
    }

    public String getName(){return name;} 
    public int getId(){return id;} 
    public String getAddress(){return address;} 
    public int getAge(){return age;} 
    public String getDob(){return dob;} 
    public String getGender(){return gender;} 
    public int getPointAmount(){return pointAmount;} 



    public static int ageCAlc(String dob){
        int years = Integer.parseInt(dob.substring(0, dob.indexOf("-")));
        int months = Integer.parseInt(dob.substring(5, 7));
        int days = Integer.parseInt(dob.substring(8, 10));
        LocalDate today = LocalDate.now();
        LocalDate bday= LocalDate.of(years,months,days);
        Period age2= (Period.between(bday,today));
        int age3=age2.getYears();
        //System.out.println(age3);
        
        return age3;
    }
    public User getUser(String nameInput) throws Exception{
        /* 
        String userDob = null;
        String userAddress = null;
        String userGender = null;
        int userPoints = 0;
        int userId=0;
        boolean canLeave=false;
        */
    
        try {
            Connection con = DBHelper.getConnection();
            String query = "SELECT * FROM users WHERE name = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, nameInput);  
            ResultSet resultSet = pstmt.executeQuery();
    
            if (resultSet.next()) { 
                User user = new User(resultSet.getString("name"), 
                    resultSet.getString("dob"), 
                    resultSet.getString("address"), 
                    resultSet.getString("gender"), 
                    resultSet.getInt("pointsAmount"), 
                    resultSet.getInt("userId")
                    );
                name = resultSet.getString("name");
                dob = resultSet.getString("dob");
                address = resultSet.getString("address");
                gender = resultSet.getString("gender");
                pointAmount = resultSet.getInt("pointsAmount");
                id = resultSet.getInt("userId");

                JOptionPane.showMessageDialog(null, "Welcome back, " + user.getName() + "!", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                return user;
            } 
            else {
                JOptionPane.showMessageDialog(null, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
                resultSet.close();
                pstmt.close();
                con.close();    
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;

        //System.out.println(currentUser.toString());
    }

    
    public String toString(){
        return ("name: "+name + "\n address: "+address+ "\n Gender: "+gender+ "\n Points:: "+pointAmount );
        
    }
     
    
}
