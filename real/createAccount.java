package real;

import javax.swing.*;

//import com.mysql.cj.protocol.Resultset;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class createAccount {

    private static JTextField name, dob, address;
    private static JRadioButton male, female, tojian;
    private static JButton submit;

        public static void show(){
        JFrame frame = new JFrame("New User");
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel dobPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel addressPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton returnButton = new JButton("Return");
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
        
        //birth
        dobPanel.add(new JLabel("Date of birth: (YYYY-MM-DD)"));
        dob= new JTextField(10);
        dobPanel.add(dob);
        frame.add(dobPanel);        
        
        //address
        addressPanel.add(new JLabel("Address: "));
        address= new JTextField(10);
        addressPanel.add(address);
        frame.add(addressPanel);  

        //gender

        male = new JRadioButton("Male");
        female = new JRadioButton("female");
        tojian = new JRadioButton("tojian");
        ButtonGroup genders= new ButtonGroup();
        genders.add(male);
        genders.add(female);
        genders.add(tojian);
        genderPanel.add(new JLabel("Gender: "));
        genderPanel.add(male);
        genderPanel.add(female);
        genderPanel.add(tojian);
        frame.add(genderPanel);

        //submit
        submit = new JButton("Submit");
        frame.add(submit);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    validateDate(dob.getText());
                    if(!name.getText().equals("")&&!dob.getText().equals("")&&!address.getText().equals("")&&(male.isSelected()||female.isSelected()||tojian.isSelected())){
                        addUser();
                        frame.dispose();
                        landing.show();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Please make sure to fill all the fields", "Error!", 0);
                    }
                    }
                    catch(InvalidDateFormatException ex){
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid Date Format", JOptionPane.ERROR_MESSAGE);
                    }
        }
        });

        //return button
        frame.add(returnButton);
        returnButton.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.dispose();
                landing.show();
                
            }
          
        });
        
    }
    public static void validateDate(String date) throws InvalidDateFormatException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
        } catch (ParseException e) {
            throw new InvalidDateFormatException("Please enter the date in YYYY-MM-DD format.");
        }
    }

    public static void addUser(){
        // ill do this later 
        //sql stuff/
        //sql i love you
        String sqlName = name.getText();
        String sqlDob = dob.getText();
        String sqlAddress = address.getText();
        String sqlGender="";
        if(male.isSelected()){
            sqlGender="Male";
        }     
        else if(female.isSelected()){
            sqlGender="Female";
        }     
        else if(tojian.isSelected()){
            sqlGender="Tojian";
        }
        try {
            Connection con = DBHelper.getConnection();
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO users (name, dob, address, gender, pointsAmount) VALUES (?, ?, ?, ?, ?)");
            pstmt.setString(1,sqlName);
            pstmt.setDate(2,Date.valueOf(sqlDob));
            pstmt.setString(3,sqlAddress);
            pstmt.setString(4,sqlGender);
            pstmt.setInt(5,0);
            pstmt.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog( null,"NO WORK!","!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
