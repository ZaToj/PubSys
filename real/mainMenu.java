package real;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class mainMenu {
    public static void show(User user){
        JFrame frame = new JFrame("Main Menu");
        JButton button = new JButton("Order");
        JButton button2 = new JButton("View Profile");
        JButton button3 = new JButton("View order History");
        JButton adminMenuButton = new JButton("Admin");
        //Label button1Label = new Label("Order");
        
        frame.setLayout( new GridLayout(1,4) ); // set frame layout
        frame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        frame.setSize ( 900, 900) ;
        frame.setVisible ( true ) ;
        frame.setLocation ( 150, 150 );
        frame.add(button);
        frame.add(button2);
        frame.add(button3);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.add(button1Label);
        button.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.dispose();
                OrderMenu.show(user);
            }
          
        });
        if(user.getName().equalsIgnoreCase("toj")){
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
                //ViewOrderHistory();
                //to do
            }
          
        });
        adminMenuButton.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.dispose();
                AdminOptions.show(user);
            }
          
        });

    }
    
}
