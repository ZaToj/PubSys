import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class admin{
    
    public static void show(){
        JFrame frame = new JFrame("Admin");
        frame.setSize(400,300);
        frame.setLayout(new GridLayout(0,2,40,40));
        JButton returnButton = new JButton("Return");
        returnButton.setSize(10,10);
        frame.add(returnButton);
        JButton deleteUser = new JButton("RID O THAT MAN");
        deleteUser.setSize(10,10);

        frame.add(deleteUser);

        frame.setVisible(true);

        //return button
        frame.add(returnButton);
        returnButton.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.dispose();
                mp.mainMenu();
            }
          
        });
        //return button
        deleteUser.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.dispose();
                mp.deleteUser();
            }
        });
    }
}