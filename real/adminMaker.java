package real;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashMap;

public class adminMaker {
    private static JPanel controlsPanel = new JPanel();
    private static JPanel optionsPanel = new JPanel();
    private static User user2;
    
    // HashMap to track each user's admin status
    private static HashMap<String, Boolean> adminStatus = new HashMap<>();

    public static void show(User user) {
        user2 = user;
        optionsPanel = new JPanel();
        controlsPanel = new JPanel();
        JFrame frame = new JFrame(LanguageManager.getInstance().getMessages().getString("adminMaker.title"));
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout(10, 10));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(150, 150);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Sidebar (Control Panel)
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));
        controlsPanel.setPreferredSize(new Dimension(200, frame.getHeight()));
        controlsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JButton returnButton = new JButton(LanguageManager.getInstance().getMessages().getString("adminMaker.return"));
        returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnButton.setSize(4000, 40000);
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                controlsPanel = null;
                optionsPanel = null;
                AdminOptions.show(user);
            }
        });

        controlsPanel.add(Box.createVerticalStrut(20));
        controlsPanel.add(Box.createVerticalGlue());
        controlsPanel.add(Box.createVerticalStrut(20));
        controlsPanel.add(returnButton);
        controlsPanel.add(Box.createGlue());

        // Main Options Panel
        optionsPanel.setLayout(new GridLayout(3, 0, 10, 10));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        frame.add(controlsPanel, BorderLayout.WEST);
        frame.add(optionsPanel, BorderLayout.CENTER);

        getUsers();
        frame.setVisible(true);
    }

    public static void getUsers() {
        try {
            String query = "SELECT userId, name, isAdmin FROM users";
            Connection con = DBHelper.getConnection();
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet res = pstmt.executeQuery();

            while (res.next()) {
                String name = res.getString("name");
                boolean isAdmin = res.getBoolean("isAdmin");
                
                // Store admin status in HashMap
                adminStatus.put(name, isAdmin);

                JButton personButton = new JButton(name);
                personButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        boolean currentStatus = adminStatus.get(name); 

                        if (currentStatus) { 
                            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove " + name + " as an admin?");
                            if (choice == 0) {
                                deCrown(name, user2);
                                adminStatus.put(name, false); 
                            } else {
                                JOptionPane.showMessageDialog(null, LanguageManager.getInstance().getMessages().getString("adminMaker.abortion"));
                            }
                        } else {
                            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to make " + name + " an admin?");
                            if (choice == 0) {
                                crown(name, user2);
                                adminStatus.put(name, true); 
                            } else {
                                JOptionPane.showMessageDialog(null, LanguageManager.getInstance().getMessages().getString("adminMaker.abortion"));
                            }
                        }
                    }
                });

                optionsPanel.add(personButton);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void crown(String name, User user) {
        if (name.equals(user.getName())) {
            JOptionPane.showMessageDialog(null, LanguageManager.getInstance().getMessages().getString("adminMaker.alreadyAdmin"), "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                Connection con = DBHelper.getConnection();
                String query = "UPDATE users SET isAdmin=1 WHERE name = ?";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setString(1, name);
                pstmt.executeUpdate();
                
                adminStatus.put(name, true); // Update local admin status

                JOptionPane.showMessageDialog(null, name + LanguageManager.getInstance().getMessages().getString("adminMaker.madeAdmin"));

                pstmt.close();
                con.close();
            } catch (Exception excep) {
                excep.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database Error!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void deCrown(String name, User user) {
        if (name.equals(user.getName())) {
            JOptionPane.showMessageDialog(null, "You cannot unAdmin yourself", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                Connection con = DBHelper.getConnection();
                String query = "UPDATE users SET isAdmin=0 WHERE name = ?";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setString(1, name);
                pstmt.executeUpdate();
                
                adminStatus.put(name, false); // Update local admin status

                JOptionPane.showMessageDialog(null, name + LanguageManager.getInstance().getMessages().getString("adminMaker.unAdmin"));

                pstmt.close();
                con.close();
            } catch (Exception excep) {
                excep.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database Error!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}