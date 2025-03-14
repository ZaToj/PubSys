package real;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;
import java.sql.*;


public class viewUserInfo {
    private static JPanel controlsPanel = new JPanel();
    private static JPanel optionsPanel = new JPanel();
    private static Boolean del=false;
    private static User user2;


    public static void show(User user){
        user2=user;
        optionsPanel = new JPanel();
        controlsPanel = new JPanel();
        JFrame frame = new JFrame("View User info");
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout(10, 10));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(150, 150);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 


        // Sidebar (Control Panel)
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));
        controlsPanel.setPreferredSize(new Dimension(200, frame.getHeight()));
        controlsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JButton returnButton = new JButton("Return");
        returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnButton.setSize(4000,40000);
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                controlsPanel=null;
                optionsPanel=null;
                AdminOptions.show(user);
            }
        });
        JButton enableDelete = new JButton("Enable Delete");
        enableDelete.setAlignmentX(Component.CENTER_ALIGNMENT);
        enableDelete.setSize(4000,40000);
        enableDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(del==false){
                    del=true;
                    enableDelete.setText("Disable Delete");
                    System.out.println("IF WORKER");

                }
                else{
                    System.out.println("ELSE WORKER");
                    del=false;
                    enableDelete.setText("Enable Delete");

                }
            }
        });


        controlsPanel.add(Box.createVerticalStrut(20)); // Spacing
        controlsPanel.add(enableDelete);
        controlsPanel.add(Box.createVerticalGlue()); // Push everything to the top
        controlsPanel.add(Box.createVerticalStrut(20));
        controlsPanel.add(returnButton);
        // controlsPanel.add(Box.createGlue()); // Push everything to the top

        // Main Options Panel
        optionsPanel.setLayout(new GridLayout(3, 0, 10, 10));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        frame.add(controlsPanel, BorderLayout.WEST); // Sidebar
        frame.add(optionsPanel, BorderLayout.CENTER); // Main menu items


        getUsers();
        //frame.add(new JScrollPane(), BorderLayout.CENTER);
        frame.setVisible(true);
    }
    

    public static void getUsers(){
        String name;
            try {
                String query="SELECT * FROM users ";
                Connection con = DBHelper.getConnection();
                PreparedStatement pstmt = con.prepareStatement(query);
                //pstmt.setInt(1,i);  
                ResultSet res = pstmt.executeQuery();
                while(res.next()){
                    name = res.getString(2);
                    String name2 = res.getString(2);
                    String out="UserId: "+res.getInt(1) + " \nName: "+res.getString(2) + " \nDOB: "+res.getString(3) + " \nAddress: "+res.getString(4) + " \nGender: "+res.getString(5) + " \nPoints: "+res.getInt(6)+" ";
                    JButton personButton = new JButton(name);
                    personButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if(del){
                                    int choice =JOptionPane.showConfirmDialog(null, "Are you sure you would like to delete" + name2+"?");
                                    if(choice==0){
                                        deleteUser(name2,user2);
                                    }
                                    else{
                                        JOptionPane.showMessageDialog(null, "Succesfully Aborted");
                                    }
                            }
                            else{
                                JOptionPane.showMessageDialog(null, out);
                            }
                        }
                    });
                    optionsPanel.add(personButton);
                }
            } catch (SQLException e) {
                try{
                    File f = new File("error.log.txt");
                    PrintWriter W = new PrintWriter(f);
                    e.printStackTrace(W);        
                }
                catch(Exception u){
                    u.printStackTrace();
                }
            }
    
        }
        public static void deleteUser(String name,User user){
            if(name.equals(user.getName())){
                JOptionPane.showMessageDialog(null, "Cant delete Self", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else{
            try {
                Connection con = DBHelper.getConnection();
                String query = "DELETE FROM users WHERE name = ?";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setString(1, name);  
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, name+" has been erradicated");
        
        
                // Close resources
                pstmt.close();
                con.close();
            } catch (Exception excep) {
                excep.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database Error!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    
            
            
        }
        }
    

