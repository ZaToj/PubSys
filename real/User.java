package real;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private String password;
    private int pointAmount;
    private boolean isAdmin;
    
    public User(){}

    public User(String name,String password, String dob,String address,String gender,int pointAmount,int id,boolean isAdmin){
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.gender = gender;
        this.pointAmount = pointAmount;
        this.id = id;
        this.password=password;
        this.age = ageCAlc(dob);//will fix with an actual calc eventually
        this.isAdmin=isAdmin;
    }

    public String getName(){return name;} 
    public int getId(){return id;} 
    public String getAddress(){return address;} 
    public int getAge(){return age;} 
    public String getDob(){return dob;} 
    public String getGender(){return gender;} 
    public int getPointAmount(){return pointAmount;} 
    public boolean isAdmin(){return isAdmin;} 
    public String getPass(){return password;} 
    
    public void setPass(String password){password=this.password;} 
    public void setPointsAmount(int pointAmount){pointAmount=this.pointAmount;} 



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

    public  String passHasher(String passIn){
        //
        //takes in string and gets the acscii value of each char, concatanates them to a string and takes the frist 6 which is then turned to binar (20 digits)
        //
        String out="";
        String concat="";
        for (int i = 0; i < passIn.length(); i++){
            int preBinary=passIn.charAt(i);
            concat+=preBinary;
        }
        String concat1=concat.substring(0,3);
        String concat2=concat.substring(concat.length()-3);
        concat=concat1+concat2;
        int input=Integer.parseInt(concat);
        String temp="";
        while (input!=0){
            if (input%2==0){
                temp+=0;
            }
            else{
                temp+=1;
            }
            input =input/2;
        }
        for(int i=temp.length()-1; i !=-1
        ;i--){
            out += temp.charAt(i);
        }
        setPass(out);
        return out;
    }
    public User getUser(String nameInput,String inPass) throws Exception{
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
            String query = "SELECT * FROM users WHERE name = ? AND password = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, nameInput);  
            pstmt.setString(2, passHasher(inPass));  
            ResultSet resultSet = pstmt.executeQuery();
    
            if (resultSet.next()) { 
                User user = new User(
                    resultSet.getString("name"),
                    resultSet.getString("password"), 
                    resultSet.getString("dob"), 
                    resultSet.getString("address"), 
                    resultSet.getString("gender"), 
                    resultSet.getInt("pointsAmount"), 
                    resultSet.getInt("userId"),
                    resultSet.getBoolean("isAdmin")
                    );
                     
                name = resultSet.getString("name");
                password = resultSet.getString("password");
                dob = resultSet.getString("dob");
                address = resultSet.getString("address");
                gender = resultSet.getString("gender");
                pointAmount = resultSet.getInt("pointsAmount");
                id = resultSet.getInt("userId");
                isAdmin = resultSet.getBoolean("isAdmin");
                LanguageManager.getInstance().getMessages().getString("welcome.message").replace("${0}", user.getName());

                JOptionPane.showMessageDialog(null,LanguageManager.getInstance().getMessages().getString("welcome.message").replace("${0}", user.getName()), "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                return user;
            } 
            else {
                JOptionPane.showMessageDialog(null, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
                resultSet.close();
                pstmt.close();
                con.close();    
                return null;
            }
        } catch (SQLException e) {
            try (PrintWriter out = new PrintWriter(new FileWriter("error.log", true))) {
                out.println("Error: " + e.getMessage());
            }catch (IOException ioException) {
                ioException.printStackTrace(); // fallback in case writing to file fails
            }
            JOptionPane.showMessageDialog(null, "Database Error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
                return null;
    }

    
    public String toString(){
        return ("name: "+name + "\n address: "+address+ "\n Gender: "+gender+ "\n Points:: "+pointAmount );
    }
     
    
}
