package real;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class landing {
        public static void show(){
        JFrame frame = new JFrame("Landing Page");
        JButton login = new JButton("Login");
        JButton createAcc = new JButton("Create Account");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        frame.setSize(400,300);
        frame.setLayout(new GridLayout(6,2,10,10));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation ( 150, 150 );
        buttonPanel.add(login);
        buttonPanel.add(createAcc);
        frame.add(buttonPanel);
        
        //Login
        login.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.dispose();
                signIn.show();
            }
          
        });
        //Create Account
        createAcc.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.dispose();
                createAccount.show();
            }
          
        });
        frame.setVisible(true);
    }

    
}
