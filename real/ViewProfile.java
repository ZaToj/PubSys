package real;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ViewProfile {
    public static void show(User user){
        JFrame frame = new JFrame("Profile");
        JButton returnButton = new JButton("Return");
        JLabel idLabel = new JLabel("UserID: " +user.getId());
        JLabel namLabel = new JLabel("Name: " +user.getName());
        JLabel ageLabel = new JLabel("Age: " +user.getAge());
        JLabel addressLabel = new JLabel("Address: " +user.getAddress());
        JLabel genderLabel = new JLabel("Gender: " +user.getGender());
        JLabel pointsLabel = new JLabel("Points: " +user.getPointAmount());

        frame.add(idLabel);
        frame.add(namLabel);   
        frame.add(ageLabel);
        frame.add(addressLabel);
        frame.add(genderLabel);
        frame.add(pointsLabel);
        frame.setSize(400,300);
        frame.setLayout(new GridLayout(6,2,10,10));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation ( 150, 150 );

        frame.add(returnButton);


        frame.setVisible(true);

        //return button
        frame.add(returnButton);
        returnButton.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.dispose();
                mainMenu.show(user);
            }
          
        });


    }
    
}
