package real;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdminOptions {
    public static void show(User user){
        JFrame frame = new JFrame("Admin");
        frame.setSize(400,300);
        frame.setLayout(new GridLayout(0,2,40,40));
        JButton returnButton = new JButton("Return");
        returnButton.setSize(10,10);
        frame.add(returnButton);
        JButton deleteUser = new JButton("RID O THAT MAN");
        deleteUser.setSize(10,10);

        frame.add(deleteUser);

        frame.setVisible(true);

        //return button
        frame.add(returnButton);
        returnButton.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.dispose();
                mainMenu.show(user);
            }
          
        });
        //return button
        deleteUser.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.dispose();
                deleteUser(user);
            }
          
        });
        
    }

    public static void deleteUser(User user){
        JFrame frame = new JFrame("Delete User");
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton returnButton = new JButton("Return");
        JButton submit = new JButton("Submit");
        JTextField name;

        frame.setSize(400,300);
        frame.setLayout(new GridLayout(6,2,10,10));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation ( 150, 150 );
        frame.setVisible(true);
        
        //names
        namePanel.add(new JLabel("Name: "));
        name= new JTextField(10);
        namePanel.add(name);
        frame.add(namePanel);
    
        //submit
        submit = new JButton("Submit");
        frame.add(submit);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e){
                if(name.getText().equals(user.getName())){
                    JOptionPane.showMessageDialog(null, "Cant delete Self", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                else{
                try {
                    Connection con = DBHelper.getConnection();
                    String query = "DELETE FROM users WHERE name = ?";
                    PreparedStatement pstmt = con.prepareStatement(query);
                    pstmt.setString(1, name.getText());  
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, name.getText()+" has been erradicated");
            
            
                    // Close resources
                    pstmt.close();
                    con.close();
                } catch (Exception excep) {
                    excep.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database Error!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        
                
                
            }        
        });

        //return button
        frame.add(returnButton);
        returnButton.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.dispose();
                AdminOptions.show(user);                
            }
          
        });
    }
}
