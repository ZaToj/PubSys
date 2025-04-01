package real;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class mainMenu {
    public static void show(User user){

        //add in user update for everytime they come back here
        JFrame frame = new JFrame("Main Menu");
        JButton button = new JButton("Order");
        JButton button2 = new JButton("View Profile");
        JButton button3 = new JButton("View order History");
        JButton button4 = new JButton("Book a Table");
        JButton adminMenuButton = new JButton("Admin");
        //Label button1Label = new Label("Order");
        
        frame.setLayout( new GridLayout(0,1) ); // set frame layout
        frame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        frame.setSize ( 900, 900) ;
        frame.setVisible ( true ) ;
        frame.setLocation ( 150, 150 );
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.add(button);
        frame.add(button2);
        frame.add(button3);
        frame.add(button4);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.add(button1Label);
        button.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.dispose();
                OrderMenu.show(user);
            }
        });
        if(user.isAdmin()){
            frame.add(adminMenuButton);
        }
        button2.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.dispose();
                ViewProfile.show(user);
            }
          
        });
        button3.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.dispose();
                ViewOrderHistory.show(user);
                //to do
            }
          
        });
        adminMenuButton.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.dispose();
                AdminOptions.show(user);
            }
          
        });

        button4.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.dispose();
                bookTable.show(user);
            }
        });
    }
    
}
