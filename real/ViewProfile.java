package real;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.*;

public class ViewProfile {
    private static JPanel contentPanel = new JPanel();
    private static JPanel sidebarPanel = new JPanel();

    public static void show(User user) {
        Font buttonFont = LanguageManager.getInstance().getFont(); 
        contentPanel = new JPanel();
        sidebarPanel = new JPanel();
        JFrame frame = new JFrame(LanguageManager.getInstance().getMessages().getString("viewProfile.title"));
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout(10, 10));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(150, 150);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Create the details panel with GridLayout
        JPanel detailsPanel = new JPanel(new GridLayout(9, 2, 10, 10)); // 7 rows, 2 columns

        // Labels (non-editable text)
        JLabel idLabel = new JLabel(LanguageManager.getInstance().getMessages().getString("viewProfile.userId"));
        JLabel idValueLabel = new JLabel(""+user.getId());
        idLabel.setFont(buttonFont);
        idValueLabel.setFont(buttonFont);
        
        JLabel favDLabel = new JLabel(LanguageManager.getInstance().getMessages().getString("viewProfile.favDrink"));
        JLabel favDValueLabel = new JLabel(user.getFav(2));
        favDLabel.setFont(buttonFont);
        favDValueLabel.setFont(buttonFont);
        
        JLabel favFLabel = new JLabel(LanguageManager.getInstance().getMessages().getString("viewProfile.favFood"));
        JLabel favFValueLabel = new JLabel(user.getFav(1));
        favFLabel.setFont(buttonFont);
        favFValueLabel.setFont(buttonFont);

        JLabel pointsLabel = new JLabel(LanguageManager.getInstance().getMessages().getString("viewProfile.points"));
        JLabel pointsValueLabel = new JLabel(String.valueOf(user.getPointAmount()));
        pointsLabel.setFont(buttonFont);
        pointsValueLabel.setFont(buttonFont);

        JLabel nameLabel = new JLabel(LanguageManager.getInstance().getMessages().getString("viewProfile.name"));
        JTextField nameField = new JTextField(user.getName());
        nameField.setEnabled(false);
        nameLabel.setFont(buttonFont);
        nameField.setFont(buttonFont);

        JLabel ageLabel = new JLabel(LanguageManager.getInstance().getMessages().getString("viewProfile.age"));
        JLabel ageValuLabel = new JLabel(String.valueOf(user.getAge()));
        ageLabel.setFont(buttonFont);
        ageValuLabel.setFont(buttonFont);

        JLabel addressLabel = new JLabel(LanguageManager.getInstance().getMessages().getString("viewProfile.address"));
        JTextField addressField = new JTextField(user.getAddress());
        addressField.setEnabled(false);
        addressLabel.setFont(buttonFont);
        addressField.setFont(buttonFont);

        JLabel genderLabel = new JLabel(LanguageManager.getInstance().getMessages().getString("viewProfile.gender"));
        JTextField genderField = new JTextField(user.getGender());
        genderField.setEnabled(false);
        genderLabel.setFont(buttonFont);
        genderField.setFont(buttonFont);

        // Add elements to the details panel
        detailsPanel.add(idLabel);
        detailsPanel.add(idValueLabel);

        detailsPanel.add(nameLabel);
        detailsPanel.add(nameField);

        detailsPanel.add(ageLabel);
        detailsPanel.add(ageValuLabel);

        detailsPanel.add(addressLabel);
        detailsPanel.add(addressField);

        detailsPanel.add(genderLabel);
        detailsPanel.add(genderField);

        detailsPanel.add(pointsLabel);
        detailsPanel.add(pointsValueLabel);
        
        detailsPanel.add(favDLabel);
        detailsPanel.add(favDValueLabel);
        
        detailsPanel.add(favFLabel);
        detailsPanel.add(favFValueLabel);

        // Sidebar (Control Panel)
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setPreferredSize(new Dimension(200, frame.getHeight()));
        sidebarPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JButton returnButton = new JButton(LanguageManager.getInstance().getMessages().getString("viewProfile.return"));
        returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                sidebarPanel = null;
                contentPanel = null;
                mainMenu.show(user);
            }
        });

        // Toggle Edit Button
        JToggleButton enableEdit = new JToggleButton(LanguageManager.getInstance().getMessages().getString("viewProfile.enableEdit"));
        enableEdit.setAlignmentX(Component.CENTER_ALIGNMENT);
        enableEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean editable = enableEdit.isSelected();
                enableEdit.setText(editable ? LanguageManager.getInstance().getMessages().getString("viewProfile.disableEdit") : LanguageManager.getInstance().getMessages().getString("viewProfile.enableEdit"));
                nameField.setEnabled(editable);
                addressField.setEnabled(editable);
                genderField.setEnabled(editable);
            }
        });
        //save chnages
        JButton saveButton = new JButton(LanguageManager.getInstance().getMessages().getString("viewProfile.saveChanges"));
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(genderField.getText().equalsIgnoreCase("male")||genderField.getText().equalsIgnoreCase("female")){

                
                    if(JOptionPane.showConfirmDialog(null, LanguageManager.getInstance().getMessages().getString("viewProfile.confirmChanges"), null, 1)==0){
                        try {
                            String sql = "UPDATE users SET name = ? , gender=?, address=? WHERE userId = ?";
                            Connection con = DBHelper.getConnection();
                            PreparedStatement pstmt = con.prepareStatement(sql);
                            pstmt.setString(1, nameField.getText());
                            pstmt.setString(2, genderField.getText());
                            pstmt.setString(3, addressField.getText());
                            pstmt.setInt(4, user.getId());
                            pstmt.executeUpdate();
                            JOptionPane.showMessageDialog(null, LanguageManager.getInstance().getMessages().getString("viewProfile.successfulUpdate"));
                            frame.dispose();
                            mainMenu.show(user);
    
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null,LanguageManager.getInstance().getMessages().getString("viewProfile.errorUpdating") );
                        }
                    }else{}
                }
                else{
                    JOptionPane.showMessageDialog(null, "Please enter a valid gender. Male/Female");
                }
            }
        });
        
        


        // Add buttons to sidebar
        //sidebarPanel.add(new );
        enableEdit.setFont(buttonFont);
        saveButton.setFont(buttonFont);
        returnButton.setFont(buttonFont);

        sidebarPanel.add(Box.createVerticalStrut(20)); // Spacing
        sidebarPanel.add(enableEdit);
        sidebarPanel.add(Box.createVerticalStrut(20)); // Spacing
        sidebarPanel.add(saveButton);
        sidebarPanel.add(Box.createVerticalGlue()); // Push everything up
        sidebarPanel.add(Box.createVerticalStrut(20));
        sidebarPanel.add(returnButton);

        // Content Panel Layout
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.add(detailsPanel, BorderLayout.CENTER);

        // Add panels to frame
        frame.add(sidebarPanel, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
