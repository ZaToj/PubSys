package real;

import javax.swing.*;

//import com.mysql.cj.protocol.Resultset;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class createAccount {

    private static JTextField name, dob, address,password,conPassword;
    private static JRadioButton male, female, tojian;
    private static JButton submit,returnButton;
    private static ResourceBundle messages = LanguageManager.getInstance().getMessages();
    private static JFrame frame = new JFrame();

        public static void show(){
        frame = new JFrame("New User");
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel passPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel conPassPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel dobPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel addressPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        returnButton = new JButton("Return");
        frame.setSize(400,300);
        frame.setLayout(new GridLayout(8,2,10,10));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation ( 150, 150 );
        frame.setVisible(true);
        
        //names
        namePanel.add(new JLabel(messages.getString("createaccount.name")));
        name= new JTextField(10);
        namePanel.add(name);
        frame.add(namePanel);
        
        //Password
        passPanel.add(new JLabel(messages.getString("createaccount.password")));
        password= new JPasswordField(10);
        passPanel.add(password);
        frame.add(passPanel);

        //ConPassword
        conPassPanel.add(new JLabel(messages.getString("createaccount.confirmPassword")));
        conPassword= new JPasswordField(10);
        conPassPanel.add(conPassword);
        frame.add(conPassPanel);

        
        //birth
        dobPanel.add(new JLabel(messages.getString("createaccount.dob")));
        dob= new JTextField(10);
        dobPanel.add(dob);
        frame.add(dobPanel);        
        
        //address
        addressPanel.add(new JLabel(messages.getString("createaccount.address")));
        address= new JTextField(10);
        addressPanel.add(address);
        frame.add(addressPanel);  

        //gender

        male = new JRadioButton(messages.getString("createaccount.male"));
        female = new JRadioButton(messages.getString("createaccount.female"));
        tojian = new JRadioButton(messages.getString("createaccount.tojian"));
        ButtonGroup genders= new ButtonGroup();
        genders.add(male);
        genders.add(female);
        genders.add(tojian);
        genderPanel.add(new JLabel(messages.getString("createaccount.gender")));
        genderPanel.add(male);
        genderPanel.add(female);
        genderPanel.add(tojian);
        frame.add(genderPanel);

        //submit
        submit = new JButton(messages.getString("createaccount.submit"));
        frame.add(submit);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    validatePass(password.getText());
                    validateDate(dob.getText());
                    if(conPassword.getText().equals(password.getText())){
                        if(!name.getText().equals("")&&!dob.getText().equals("")&&!address.getText().equals("")&&(male.isSelected()||female.isSelected()||tojian.isSelected())){
                            addUser();
                            frame.dispose();
                            landing.show();
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Please make sure to fill all the fields", "Error!", 0);
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Passwords do not match!", "Passwords must match", 0);
                        conPassword.setText("");
                        password.setText("");
                    }
                }
                catch(InvalidDateFormatException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid Date Format", JOptionPane.ERROR_MESSAGE);
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid password  ", JOptionPane.ERROR_MESSAGE);
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
        updateLanguage();
    }
    private static void updateLanguage() {
        ResourceBundle messages = LanguageManager.getInstance().getMessages();
        frame.setTitle(messages.getString("createaccount.title"));
        submit.setText(messages.getString("createaccount.submit"));
        returnButton.setText(messages.getString("createaccount.return"));
    }
    public static void validateDate(String date) throws InvalidDateFormatException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
        } catch (ParseException e) {
            throw new InvalidDateFormatException(messages.getString("createaccount.InvalidDateFormatException"));
        }
    }    
    public static void validatePass(String pass) throws Exception {
        if(pass.length()>6){
            throw new Exception("Ensure password is longer than 6 chaacetrs");
        }
    }

    public static void addUser(){
        String sqlName = name.getText();
        String sqlDob = dob.getText();
        String sqlAddress = address.getText();
        String sqlGender="";
        String sqlpassword = passHasher(password.getText());

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
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO users (name,password, dob, address, gender, pointsAmount,isAdmin) VALUES (?, ?, ?, ?, ?, ?,0)");
            pstmt.setString(1,sqlName);
            pstmt.setString(2,sqlpassword);            
            pstmt.setDate(3,Date.valueOf(sqlDob));
            pstmt.setString(4,sqlAddress);
            pstmt.setString(5,sqlGender);
            pstmt.setInt(6,0);
            pstmt.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog( null,"NO WORK!","!", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static String passHasher(String passIn){
        //
        //takes in string and gets the acscii value of each char, concatanates them to a string and takes the frist 6 which is then turned to binar (20 digits)
        //
        String out="";
        String concat="";
        for (int i = 0; i < passIn.length(); i++){
            int preBinary=passIn.charAt(i);
            concat+=preBinary;
        }
        String concat1=concat.substring(0,3);
        String concat2=concat.substring(concat.length()-3);
        concat=concat1+concat2;
        int input=Integer.parseInt(concat);
        String temp="";
        while (input!=0){
            if (input%2==0){
                temp+=0;
            }
            else{
                temp+=1;
            }
            input =input/2;
        }
        System.out.println(temp);
        for(int i=temp.length()-1; i !=-1
        ;i--){
            out += temp.charAt(i);
        }
        return out;
    }

    
}
