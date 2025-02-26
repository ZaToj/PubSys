package real;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class OrderMenu {
    public static void show(User user){
        JFrame frame = new JFrame("Order Menu");
        frame.setSize(800,600);
        frame.setLayout(new GridLayout(0,3,10,10));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation ( 150, 150 );
        menuItem item = new menuItem();

        JPanel itemPanel = new JPanel();
        for(int i=1;i<10;i++){
            item=item.getMenuItem(i);
            itemPanel= createMenuItem(item.getItemName(),item.getItemCost(),item.getImgFilePath());
            frame.add(itemPanel);
        }
        frame.setVisible(true);
    }
        
    
    private static JPanel createMenuItem(String name, int price, String imagePath){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(150, 150));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        String priceText="";
        priceText = priceText+price;

        ImageIcon icon = new ImageIcon(imagePath); 
        Image img = icon.getImage().getScaledInstance(100, 80, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(img));

        JLabel nameLabel = new JLabel(name, SwingConstants.CENTER);
        JLabel priceLabel = new JLabel(priceText, SwingConstants.CENTER);

        JButton button = new JButton("Select");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "You selected: " + name);
            }
        });

        // Panel for text
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.add(nameLabel);
        textPanel.add(priceLabel);

        // Add components
        panel.add(imageLabel, BorderLayout.CENTER);
        panel.add(textPanel, BorderLayout.SOUTH);
        panel.add(button, BorderLayout.NORTH);

        return panel;
    }
    
}
