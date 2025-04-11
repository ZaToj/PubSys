package real;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.*;

public class mainMenu {
    private static User user;

    public static void show(User useri) {
        user = useri;
        JFrame frame = new JFrame("Main Menu");
        JButton button = new JButton(LanguageManager.getInstance().getMessages().getString("mainMenu.order"));
        JButton button2 = new JButton(LanguageManager.getInstance().getMessages().getString("mainMenu.viewProfile"));
        JButton button3 = new JButton(LanguageManager.getInstance().getMessages().getString("mainMenu.orderHistory"));
        JButton adminMenuButton = new JButton(LanguageManager.getInstance().getMessages().getString("mainMenu.admin"));
        user = updateUser(user);
       
        // Increase font size (buttons will grow to accommodate text)
        Font buttonFont = LanguageManager.getInstance().getFont(30);
        button.setFont(buttonFont);
        button2.setFont(buttonFont);
        button3.setFont(buttonFont);
        adminMenuButton.setFont(buttonFont);

        JLabel logoLabel; //try get logo
        try {
            ImageIcon logo = new ImageIcon("Imgs/logo.png");
            logoLabel = new JLabel(logo);
            logoLabel.setHorizontalAlignment(JLabel.CENTER);
        } catch (Exception e) {
            logoLabel = new JLabel("Logo not found");
            logoLabel.setHorizontalAlignment(JLabel.CENTER);
            e.printStackTrace();
        }
        // Create a panel with BoxLayout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        
        // Add horizontal glue for centering
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(button);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Space between buttons
        buttonPanel.add(button2);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(button3);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        if(user.isAdmin()) {
            buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            buttonPanel.add(adminMenuButton);
        }
        buttonPanel.add(Box.createHorizontalGlue());
    
        frame.setLayout(new BorderLayout());
        frame.add(logoLabel, BorderLayout.NORTH);    
        frame.add(buttonPanel, BorderLayout.CENTER); 

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 900);
        frame.setLocation(150, 150);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        button.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.dispose();
                OrderMenu.show(user);
            }
          
        });
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
