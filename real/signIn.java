package real;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//import java.sql.SQLDataException;
import java.sql.SQLException;

public class signIn {
    public static void show(){
        User user = new User();
        JTextField name,passwordText;
        JFrame frame = new JFrame(LanguageManager.getInstance().getMessages().getString("signin.title"));
        JPanel username = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel password = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton loginButton = new JButton(LanguageManager.getInstance().getMessages().getString("signin.loginButton"));

        frame.setSize(400,300);
        frame.setLayout(new GridLayout(6,2,10,10));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation ( 150, 150 );


        username.add(new JLabel(LanguageManager.getInstance().getMessages().getString("signin.name")));
        name= new JTextField(10);
        username.add(name);
        frame.add(username);

        password.add(new JLabel(LanguageManager.getInstance().getMessages().getString("signin.password")));
        passwordText= new JPasswordField(10);
        password.add(passwordText);
        frame.add(password);

        frame.add(loginButton);
        loginButton.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                if(!name.getText().equals("")&&!passwordText.getText().equals("")){
                    try {
                        user.getUser(name.getText(),passwordText.getText());
                        if(user.getName()!=null){
                            mainMenu.show(user);
                            frame.dispose();
                        }
                    }catch (SQLException excep) {
                        try (PrintWriter out = new PrintWriter(new FileWriter("error.log", true))) {
                            out.println("Error: " + excep.getMessage());
                        }catch (IOException ioException) {
                            ioException.printStackTrace(); // fallback in case writing to file fails
                        }
                        JOptionPane.showMessageDialog(null, "No Account found!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    catch(Exception excep){
                        excep.printStackTrace();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, LanguageManager.getInstance().getMessages().getString("signin.dataError"), "", 0);
                }
                
            }
        });
        frame.getRootPane().setDefaultButton(loginButton);
        frame.setVisible(true);
    }
    
}
