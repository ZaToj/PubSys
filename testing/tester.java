import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class tester extends JFrame {
    private JTextField nameField, dobField, addressField;
    private JRadioButton maleButton, femaleButton, otherButton;
    private JButton submitButton;

    // Database Credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/UserDB";
    private static final String DB_USER = "root";  // Change this to your MySQL username
    private static final String DB_PASS = "";      // Change this to your MySQL password

    public tester() {
        setTitle("User Registration");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));

        // Name
        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        // Date of Birth
        add(new JLabel("Date of Birth (YYYY-MM-DD):"));
        dobField = new JTextField();
        add(dobField);

        // Address
        add(new JLabel("Address:"));
        addressField = new JTextField();
        add(addressField);

        // Gender
        add(new JLabel("Gender:"));
        JPanel genderPanel = new JPanel();
        maleButton = new JRadioButton("Male");
        femaleButton = new JRadioButton("Female");
        otherButton = new JRadioButton("Other");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        genderGroup.add(otherButton);
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        genderPanel.add(otherButton);
        add(genderPanel);

        // Submit Button
        submitButton = new JButton("Submit");
        add(submitButton);

        // Action Listener for Submit Button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertData();
            }
        });

        setVisible(true);
    }

    private void insertData() {
        String name = nameField.getText();
        String dob = dobField.getText();
        String address = addressField.getText();
        String gender = "";

        if (maleButton.isSelected()) gender = "Male";
        else if (femaleButton.isSelected()) gender = "Female";
        else if (otherButton.isSelected()) gender = "Other";

        if (name.isEmpty() || dob.isEmpty() || address.isEmpty() || gender.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Users (name, dob, address, gender) VALUES (?, ?, ?, ?)")) {

            pstmt.setString(1, name);
            pstmt.setDate(2, Date.valueOf(dob));
            pstmt.setString(3, address);
            pstmt.setString(4, gender);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "User data saved successfully!");
            clearFields();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving data!", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        nameField.setText("");
        dobField.setText("");
        addressField.setText("");
        maleButton.setSelected(false);
        femaleButton.setSelected(false);
        otherButton.setSelected(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new tester());
    }
}
