import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuGrid extends JFrame {
    public MenuGrid() {
        setTitle("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 3, 10, 10)); // 3 columns, unlimited rows
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Sample items (replace with database data)
        String[][] menuItems = {
            {"Burger", "$5.99", "path_to_image.jpg"},
            {"Pizza", "$8.99", "path_to_image.jpg"},
            {"Pasta", "$7.49", "path_to_image.jpg"},
            {"Salad", "$4.99", "path_to_image.jpg"},
            {"Fries", "$3.49", "path_to_image.jpg"},
            {"Soda", "$1.99", "path_to_image.jpg"}
        };

        for (String[] item : menuItems) {
            mainPanel.add(createMenuItem(item[0], item[1], item[2]));
        }

        add(new JScrollPane(mainPanel)); // Scroll if too many items
        setVisible(true);
    }

    private JPanel createMenuItem(String name, String price, String imagePath) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(150, 150));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Load Image
        ImageIcon icon = new ImageIcon(imagePath); // Replace with actual image loading
        Image img = icon.getImage().getScaledInstance(100, 80, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(img));

        // Labels for Name & Price
        JLabel nameLabel = new JLabel(name, SwingConstants.CENTER);
        JLabel priceLabel = new JLabel(price, SwingConstants.CENTER);

        // Clickable Button
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuGrid::new);
    }
}
