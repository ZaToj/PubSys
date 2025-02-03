import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.Flow;
public class mp extends JFrame
{ 

    private static JTextField name, dob, address;
    private static JRadioButton male, female, tojian;
    private static JButton submit;
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/pub_sys";
    private static final String DB_USER = "Toj";  // Change this to your MySQL username
    private static final String DB_PASS = "KeelJameTojMjoan";      // Change this to your MySQL password

    
	public static void main(String args[]) 
	{ 
        Scanner scan = new Scanner(System.in);
        mainMenu();
        scan.close();   
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
    public static void orderMenue(){
        JFrame frame = new JFrame("Order Menu");
        Label label = new Label("How many beers would you like?");
        JButton yButton = new JButton("Yes!!");
        JButton submitButton = new JButton("Submit!");
        JButton nButton = new JButton("No!!(??)");
        Label button1Label = new Label("Would you like a beer?\n");
        TextField textField = new TextField(15);

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

    

    }
    public static void NewUser(){
        JFrame frame = new JFrame("New User");
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel dobPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel addressPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        frame.setSize(400,300);
        frame.setLayout(new GridLayout(5,2,10,10));
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
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO user (name, dob, address, gender) VALUES (?, ?, ?, ?)");
            pstmt.setString(1,sqlName);
            pstmt.setDate(2,Date.valueOf(sqlDob));
            pstmt.setString(3,sqlAddress);
            pstmt.setString(4,sqlGender);
            pstmt.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog( null,"NO WORK!","!", JOptionPane.ERROR_MESSAGE);
        }
    }

} 
