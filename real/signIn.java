package real;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLDataException;
import java.sql.SQLException;

public class signIn {
    public static void show(){
        User user = new User();
        JTextField name,passwordText;
        JFrame frame = new JFrame("Sign in");
        JPanel username = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel password = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton loginButton = new JButton("LOG IN MAN PLEASEEEEEEEE");

        frame.setSize(400,300);
        frame.setLayout(new GridLayout(6,2,10,10));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation ( 150, 150 );


        username.add(new JLabel("Name: "));
        name= new JTextField(10);
        username.add(name);
        frame.add(username);

        password.add(new JLabel("Password: "));
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
                    } catch (SQLException excep) {
                        excep.printStackTrace();
                        JOptionPane.showMessageDialog(null, "No Account found!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    catch(Exception excep){
                        excep.printStackTrace();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Please fill in all data", "", 0);
                }
                
            }
        });
        frame.setVisible(true);

    }
    
}
