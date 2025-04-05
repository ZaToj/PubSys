package real;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.*;

public class mainMenu {
    private static User user;

    public static void show(User useri){
        user=useri;
        //add in user update for everytime they come back here
        JFrame frame = new JFrame("Main Menu");
        JButton button = new JButton(LanguageManager.getInstance().getMessages().getString("mainMenu.order"));
        JButton button2 = new JButton(LanguageManager.getInstance().getMessages().getString("mainMenu.viewProfile"));
        JButton button3 = new JButton(LanguageManager.getInstance().getMessages().getString("mainMenu.orderHistory"));
        JButton button4 = new JButton(LanguageManager.getInstance().getMessages().getString("mainMenu.bookTable"));
        JButton adminMenuButton = new JButton(LanguageManager.getInstance().getMessages().getString("mainMenu.admin"));
        //Label button1Label = new Label("Order");
        user = updateUser(user);
        
        frame.setLayout( new GridLayout(0,1) ); // set frame layout
        frame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        frame.setSize ( 900, 900) ;
        frame.setVisible ( true ) ;
        frame.setLocation ( 150, 150 );
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.add(button);
        frame.add(button2);
        frame.add(button3);
        frame.add(button4);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.add(button1Label);
        button.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.dispose();
                OrderMenu.show(user);
            }
        });
        if(user.isAdmin()){
            frame.add(adminMenuButton);
        }
        button2.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.dispose();
                ViewProfile.show(user);
            }
          
        });
        button3.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.dispose();
                ViewOrderHistory.show(user);
            }
          
        });
        adminMenuButton.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.dispose();
                AdminOptions.show(user);
            }
          
        });

        button4.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.dispose();
                bookTable.show(user);
            }
        });
    }
    private static User updateUser(User user){
        try {
            Connection con = DBHelper.getConnection();
            String query = "SELECT * FROM users WHERE userId=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, user.getId());  
            ResultSet resultSet = pstmt.executeQuery();
    
            if (resultSet.next()) { 
                user = new User(
                    resultSet.getString("name"),
                    resultSet.getString("password"), 
                    resultSet.getString("dob"), 
                    resultSet.getString("address"), 
                    resultSet.getString("gender"), 
                    resultSet.getInt("pointsAmount"), 
                    resultSet.getInt("userId"),
                    resultSet.getBoolean("isAdmin")
                    );
            }
            
        }catch(Exception ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,LanguageManager.getInstance().getMessages().getString("mainMenu.updateError") );
            }
        return user;
        
    }
}
