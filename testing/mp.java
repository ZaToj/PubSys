import java.util.*;
import javax.swing.JFrame;


import java.awt.Button;
import java .awt.FlowLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionListener;
public class mp extends JFrame
{ 
	public static void main(String args[]) 
	{ 
        Scanner scan = new Scanner(System.in);
        mainMenu();
        scan.close();   
	} 

    public static void mainMenu(){
        JFrame frame = new JFrame("Main Menu");
        Button button = new Button("ORDER MENUE");
        //Label button1Label = new Label("Order");
        
        frame.setLayout( new FlowLayout() ); // set frame layout
        frame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        frame.setSize ( 900, 900) ;
        frame.setVisible ( true ) ;
        frame.setLocation ( 150, 150 );
        frame.add(button);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //frame.add(button1Label);
        button.addActionListener(new ActionListener() {
          
            public void actionPerformed(java.awt.event.ActionEvent e){
                frame.setVisible(false);
                orderMenue();
            }
          
        });
    }
    public static void orderMenue(){
        JFrame frame = new JFrame("Order Menu");
        Label label = new Label("How many beers would you like?");
        Button yButton = new Button("Yes!!");
        Button submitButton = new Button("Submit!");
        Button nButton = new Button("No!!(??)");
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

} 
