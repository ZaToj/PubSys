package real;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ViewProfile {
    private static JPanel contentPanel = new JPanel();
    private static JPanel sidebarPanel = new JPanel();

    public static void show(User user) {
        contentPanel = new JPanel();
        sidebarPanel = new JPanel();
        JFrame frame = new JFrame("View User Info");
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout(10, 10));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(150, 150);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Create the details panel with GridLayout
        JPanel detailsPanel = new JPanel(new GridLayout(6, 2, 10, 10)); // 6 rows, 2 columns

        // Labels (non-editable text)
        JLabel idLabel = new JLabel("User ID:");
        JLabel idValueLabel = new JLabel(""+user.getId());

        JLabel pointsLabel = new JLabel("Points:");
        JLabel pointsValueLabel = new JLabel(String.valueOf(user.getPointAmount()));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(user.getName());
        nameField.setEnabled(false);

        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField(String.valueOf(user.getAge()));
        ageField.setEnabled(false);

        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField(user.getAddress());
        addressField.setEnabled(false);

        JLabel genderLabel = new JLabel("Gender:");
        JTextField genderField = new JTextField(user.getGender());
        genderField.setEnabled(false);

        // Add elements to the details panel
        detailsPanel.add(idLabel);
        detailsPanel.add(idValueLabel);

        detailsPanel.add(nameLabel);
        detailsPanel.add(nameField);

        detailsPanel.add(ageLabel);
        detailsPanel.add(ageField);

        detailsPanel.add(addressLabel);
        detailsPanel.add(addressField);

        detailsPanel.add(genderLabel);
        detailsPanel.add(genderField);

        detailsPanel.add(pointsLabel);
        detailsPanel.add(pointsValueLabel);

        // Sidebar (Control Panel)
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setPreferredSize(new Dimension(200, frame.getHeight()));
        sidebarPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JButton returnButton = new JButton("Return");
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
        JToggleButton enableEdit = new JToggleButton("Enable Edit");
        enableEdit.setAlignmentX(Component.CENTER_ALIGNMENT);
        enableEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean editable = enableEdit.isSelected();
                enableEdit.setText(editable ? "Disable Edit" : "Enable Edit");
                nameField.setEnabled(editable);
                ageField.setEnabled(editable);
                addressField.setEnabled(editable);
                genderField.setEnabled(editable);
            }
        });

        // Add buttons to sidebar
        //sidebarPanel.add(new );
        sidebarPanel.add(Box.createVerticalStrut(20)); // Spacing
        sidebarPanel.add(enableEdit);
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
