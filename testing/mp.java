import javax.swing.*;

//import com.mysql.cj.protocol.Resultset;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.Date;
import java.util.*;
//import java.util.concurrent.Flow;
public class mp extends JFrame
{ 

    private static JTextField name, dob, address;
    private static JRadioButton male, female, tojian;
    private static JButton submit;
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/pub_sys";
    private static final String DB_USER = "Toj";  // Change this to your MySQL username
    private static final String DB_PASS = "KeelJameTojMjoan";      // Change this to your MySQL password


    private static User currentUser = new User();
    
	public static void main(String args[]) 
	{ 
        Scanner scan = new Scanner(System.in);
        landingPage();
        scan.close();   
	} 

    public static void landingPage(){
        JFrame frame = new JFrame("Landing Page");
        JButton login = new JButton("Login");
        JButton createAcc = new JButton("Create Account");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        frame.setSize(400,300);
        frame.setLayout(new GridLayout(6,2,10,10));
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocation ( 150, 150 );
        frame.setVisible(true);
        buttonPanel.add(login);
        buttonPanel.add(createAcc);
        frame.add(buttonPanel);

        //Logib
        login.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.setVisible(false);
                signIn();
            }
          
        });

        
        //Create Account
        createAcc.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.setVisible(false);
                NewUser();
            }
          
        });




    }


    public static void mainMenu(){
        JFrame frame = new JFrame("Main Menu");
        JButton button = new JButton("ORDER MENUE");
        JButton button2 = new JButton("NEW USER");
        //Label button1Label = new Label("Order");
        
        frame.setLayout( new FlowLayout() ); // set frame layout
        frame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        frame.setSize ( 900, 900) ;
        frame.setVisible ( true ) ;
        frame.setLocation ( 150, 150 );
        frame.add(button);
        frame.add(button2);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //frame.add(button1Label);
        button.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.setVisible(false);
                orderMenue();
            }
          
        });
        button2.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.setVisible(false);
                NewUser();
            }
          
        });
    }
    public static void signIn(){
        JFrame frame = new JFrame("Sign in");
        JPanel username = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton loginButton = new JButton("LOG IN MAN PLEASEEEEEEEE");

        frame.setSize(400,300);
        frame.setLayout(new GridLayout(6,2,10,10));
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocation ( 150, 150 );
        username.add(new JLabel("Name: "));
        name= new JTextField(10);
        username.add(name);
        frame.add(username);

        frame.add(loginButton);
        loginButton.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                boolean con=grabAcc(name.getText());
                if(con){mainMenu();}
                frame.setVisible(false);
            }
          
        });
        frame.setVisible(true);

    }
    public static boolean grabAcc(String nameInput) {
        String userName = null;
        String userDob = null;
        String userAddress = null;
        String userGender = null;
        int userPoints = 0;
        boolean canLeave=false;
    
        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            String query = "SELECT * FROM user WHERE name = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, nameInput);  
            ResultSet resultSet = pstmt.executeQuery();
    
            if (resultSet.next()) { 
                userName = resultSet.getString("name");
                userDob = resultSet.getString("dob");
                userAddress = resultSet.getString("address");
                userGender = resultSet.getString("gender");
                userPoints = resultSet.getInt("pointsAmount");
    
                /*
                System.out.println("User Found: " + userName);
                System.out.println("DOB: " + userDob);
                System.out.println("Address: " + userAddress);
                System.out.println("Gender: " + userGender);
                System.out.println("Points: " + userPoints);
                */
    
                JOptionPane.showMessageDialog(null, "Welcome back, " + userName + "!", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                canLeave=true;
            } 
            else {
                JOptionPane.showMessageDialog(null, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
    
            // Close resources
            resultSet.close();
            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Error!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        currentUser = new User(userName, userDob, userAddress, userGender, userPoints);
        System.out.println(currentUser.toString());
        return canLeave;
    }
    


    public static void orderMenue(){
        JFrame frame = new JFrame("Order Menu");
        Label label = new Label("How many beers would you like?");
        JButton yButton = new JButton("Yes!!");
        JButton submitButton = new JButton("Submit!");
        JButton nButton = new JButton("No!!(??)");
        Label button1Label = new Label("Would you like a beer?\n");
        TextField textField = new TextField(15);
        JButton returnButton = new JButton("Return");


        frame.setLayout( new FlowLayout() ); // set frame layout
        frame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        frame.setSize ( 900, 900) ;
        frame.setVisible ( true ) ;
        frame.setLocation ( 150, 150 );
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.add(button1Label);
        button1Label.setBounds(50, 50, 300, 30);
        frame.add(yButton);
        frame.add(nButton);
        yButton.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                yButton.setVisible(false);
                nButton.setVisible(false);
                button1Label.setVisible(false);
                frame.add(label);
                frame.add(textField);
                frame.add(submitButton);
                frame.setVisible(true);
            }
          
        });        
        nButton.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                button1Label.setText("WHAT!");        
            }
          
        });
        submitButton.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                try {
                    int numBeer = Integer.parseInt(textField.getText());
                    if(numBeer >= 100){
                        label.setText("Legend");
                        textField.setVisible(false);
                        submitButton.setVisible(false);
                        frame.setVisible(true);
                    }
                    else{
                        label.setText("pussy =( ");
                        frame.setVisible(true);
                    }
                } 
                catch (Exception ex) {
                    label.setText("Enter a number numb nuts");
                }
            }
          
        });  
        frame.add(returnButton);
        returnButton.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.setVisible(false);
                mainMenu();
            }
          
        });
      

    

    }
    public static void NewUser(){
        JFrame frame = new JFrame("New User");
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel dobPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel addressPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton returnButton = new JButton("Return");
        frame.setSize(400,300);
        frame.setLayout(new GridLayout(6,2,10,10));
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
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
                addUser();
            }
        });

        //return button
        frame.add(returnButton);
        returnButton.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.setVisible(false);
                mainMenu();
            }
          
        });


        
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
            Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO user (name, dob, address, gender, pointsAmount) VALUES (?, ?, ?, ?, ?)");
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
