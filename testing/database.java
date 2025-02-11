import javax.swing.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class database {
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/pub_sys";
    private static final String DB_USER = "Toj";  // Change this to your MySQL username
    private static final String DB_PASS = "KeelJameTojMjoan";      // Change this to your MySQL password

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int op=1;
        System.out.println("WOULD YOU LIKE TO ADD (1) OR READ (2)");
        op = scan.nextInt();

        if(op==1){
            String userName = null;
            String userDob = null;
            String userAddress = null;
            String userGender = null;
            int userPoints = 0;
            String nameInput;
            System.out.println("WHO YOU LOOKIN FO FOO???:");
            nameInput=scan.nextLine();
            try{
                Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                String query = "SELECT * FROM user WHERE name = ?";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setString(1, nameInput);  
                ResultSet resultSet = pstmt.executeQuery();
                if (resultSet.next()) { 
                    userName = resultSet.getString("name");
                    userDob = resultSet.getString("dob");
                    userAddress = resultSet.getString("address");
                    userGender = resultSet.getString("gender");
                    userPoints = resultSet.getInt("pointsAmount");
                }

                System.out.println(userName);
                System.out.println(userDob);
                System.out.println(userAddress);
                System.out.println(userGender);
                System.out.println(userPoints);
            }
            catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database Error!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            finally{
                scan.close();
            }
        }
        else{
            scan.nextLine();
            System.out.print("ENTER THE NAME: ");
            String name = scan.nextLine();
            System.out.print("ENTER THE DOB: ");
            String dob= scan.nextLine();
            System.out.print("ENTER THE ADDRESS: ");
            String address= scan.nextLine();
            System.out.print("ENTER THE GENDER: ");
            String gender= scan.nextLine();

            try {
                Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
                PreparedStatement pstmt = con.prepareStatement("INSERT INTO user (name, dob, address, gender, pointsAmount) VALUES (?, ?, ?, ?, ?)");
                pstmt.setString(1,name);
                pstmt.setDate(2,Date.valueOf(dob));
                pstmt.setString(3,address);
                pstmt.setString(4,gender);
                pstmt.setInt(5,0);
                pstmt.executeUpdate();
    
    
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog( null,"NO WORK!","!", JOptionPane.ERROR_MESSAGE);
            }
    
    
        }
    
    }
}