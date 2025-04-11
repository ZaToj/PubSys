package real;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminOptions {
    public static void show(User user){
        JFrame frame = new JFrame(LanguageManager.getInstance().getMessages().getString("admin.title"));
        frame.setSize(400,300);
        frame.setLayout(new GridLayout(0,2,40,40));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        JButton returnButton = new JButton(LanguageManager.getInstance().getMessages().getString("admin.return"));
        JButton deleteUser = new JButton(LanguageManager.getInstance().getMessages().getString("admin.makeAdmin"));
        JButton viewButton = new JButton(LanguageManager.getInstance().getMessages().getString("admin.updateUsers"));
        JButton button4 = new JButton(LanguageManager.getInstance().getMessages().getString("mainMenu.bookTable"));

        Font buttonFont = LanguageManager.getInstance().getFont(30);
        returnButton.setFont(buttonFont);
        deleteUser.setFont(buttonFont);
        viewButton.setFont(buttonFont);
        button4.setFont(buttonFont);

        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(Box.createRigidArea(new Dimension(40, 0)));
        buttonPanel.add(viewButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(deleteUser);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(returnButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(button4);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(Box.createHorizontalGlue());

        frame.setLayout(new BorderLayout());
        frame.add(buttonPanel, BorderLayout.CENTER); 

        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.dispose();
                mainMenu.show(user);
            }
          
        });
        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.dispose();
                viewUserInfo.show(user);
            }
          
        });
        deleteUser.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.dispose();
                adminMaker.show(user);
            }
          
        });

        button4.addActionListener(new ActionListener() {
 
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.dispose();
                bookTable.show(user);
            }
        });
        
        frame.setVisible(true);

    }

}