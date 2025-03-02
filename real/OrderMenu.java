package real;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderMenu {
    private static menuItem order[] = new menuItem[25];
    private static int i = 0;
    private static JLabel orderedItem = new JLabel();
    private static JPanel controlsPanel = new JPanel();


    public static void show(User user) {
        JFrame frame = new JFrame("Order Menu");
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout(10, 10));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(150, 150);

        // Sidebar (Control Panel)
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));
        controlsPanel.setPreferredSize(new Dimension(200, frame.getHeight()));
        controlsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JButton returnButton = new JButton("Return");
        returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                mainMenu.show(user);
            }
        });

        controlsPanel.add(Box.createVerticalStrut(20)); // Spacing
        controlsPanel.add(returnButton);
        controlsPanel.add(Box.createVerticalGlue()); // Push everything to the top

        // Main Options Panel
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(3, 0, 10, 10));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Load menu items
        for (int i = 1; i < 10; i++) {
            menuItem item = menuItem.getMenuItem(i);
            if (item != null) {
                JPanel itemPanel = createMenuItem(item.getItemName(), item.getItemCost(), item.getImgFilePath(), item.getItemId());
                optionsPanel.add(itemPanel);
            }
        }

        // Add panels to frame
        frame.add(controlsPanel, BorderLayout.WEST); // Sidebar
        frame.add(optionsPanel, BorderLayout.CENTER); // Main menu items

        frame.setVisible(true);
    }

    private static JPanel createMenuItem(String name, int price, String imagePath, int itemId) {
        JPanel panel = new JPanel();
        menuItem tempItem = new menuItem(itemId, price, name, imagePath);
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(150, 150));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        String priceText = "£" + price;

        // Load image
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage().getScaledInstance(100, 80, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(img));

        JLabel nameLabel = new JLabel(name, SwingConstants.CENTER);
        JLabel priceLabel = new JLabel(priceText, SwingConstants.CENTER);

        JButton button = new JButton("Select");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (i < order.length) {
                    order[i] = tempItem;
                    i++;
                    orderedItem.setText(name);
                    controlsPanel.add(orderedItem);
                    
                    JOptionPane.showMessageDialog(null, "You selected: " + name);
                } else {
                    JOptionPane.showMessageDialog(null, "Order limit reached!");
                }
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
