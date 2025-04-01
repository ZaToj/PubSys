package real;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class OrderMenu {
    private static menuItem order[] = new menuItem[25];
    private static int i = 0;
    private static int oc = 0;
    private static int subTotal = 0;    
    private static JLabel subTotalLabel = new JLabel("Subtotal: $0");
    private static JPanel controlsPanel = new JPanel();
    private static DefaultListModel<String> model = new DefaultListModel<>();
    private static JList<String> list = new JList<String>(model);


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
        returnButton.setSize(4000,40000);
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clear(user);
                frame.dispose();
        }
        });

        JButton orderButton = new JButton("Order");
        orderButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        orderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int pointsGained=0;
                boolean worked=false;
                worked=processsOrder(user);
                if(worked){
                    for(int i=0;i<oc;i++){
                        pointsGained+=order[i].getPointAmount();
                    }
                    givePoints(user,pointsGained);
                    JOptionPane.showMessageDialog(null, "IT WORKED ORDER PLACED");
                    clear(user);
                    frame.dispose();
                }
                else{
                    JOptionPane.showMessageDialog(null, "IT NOT WORKED ORDER NOT PLACED");
                }
                
            }
        });

        controlsPanel.add(Box.createVerticalStrut(20)); // Spacing
        controlsPanel.add(orderButton);
        controlsPanel.add(Box.createVerticalGlue()); // Push everything to the top
        controlsPanel.add(Box.createVerticalStrut(20));
        controlsPanel.add(list);
        controlsPanel.add(Box.createVerticalStrut(20));
        controlsPanel.add(subTotalLabel);
        controlsPanel.add(returnButton);
        // controlsPanel.add(Box.createGlue()); // Push everything to the top

        // Main Options Panel
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(3, 0, 10, 10));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Load menu items
        for (int i = 1; i < 10; i++) {
            menuItem item = menuItem.getMenuItem(i);
            if (item != null) {
                JPanel itemPanel = createMenuItem(item.getItemName(), item.getItemCost(), item.getImgFilePath(), item.getItemId(),item.getPointAmount());
                optionsPanel.add(itemPanel);
            }
        }

        // Add panels to frame
        frame.add(controlsPanel, BorderLayout.WEST); // Sidebar
        frame.add(optionsPanel, BorderLayout.CENTER); // Main menu items
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 


        frame.setVisible(true);
    }

    private static void givePoints(User user, int in){
        int points=user.getPointAmount()+in;
        user.setPointsAmount(points);
        try {

            Connection con = DBHelper.getConnection();
            String sql = "UPDATE users SET pointsAmount = ? WHERE userId = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, points);
            pstmt.setInt(2, user.getId());
            pstmt.executeUpdate();


        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error in the adding points!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private static void clear(User user){

        oc=0;
        i=0;
        subTotal=0;
        subTotalLabel = new JLabel("Subtotal: $0");
        controlsPanel = new JPanel();
        model = new DefaultListModel<>();
        list = new JList<>(model);
        mainMenu.show(user);
        
    }

    private static JPanel createMenuItem(String name, int price, String imagePath, int itemId,int pointAmount) {
        JPanel panel = new JPanel();
        menuItem tempItem = new menuItem(itemId, price, name, imagePath,pointAmount);
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(150, 150));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        

        String priceText = "$" + price;

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
                    model.addElement(order[i].getItemName());
                    subTotal=subTotal+order[i].getItemCost();
                    subTotalLabel.setText("SubTotal: $"+(subTotal));
                    i++;
                    oc++;
                    
                    //JOptionPane.showMessageDialog(null, "You selected: " + name);
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

    public static boolean processsOrder(User user) {
    if (oc == 0) {
        JOptionPane.showMessageDialog(null, "No items in the order!");
        return false;
    } 

    Connection con = null;
    PreparedStatement orderStmt = null;
    PreparedStatement itemStmt = null;
    ResultSet rs = null;
    boolean success = false;
    int orderId = -1;
    int pointValue=0;

    try {
        con = DBHelper.getConnection();
        con.setAutoCommit(false);  

        String orderQuery = "INSERT INTO orders (userid, totalcost) VALUES (?, ?)";
        orderStmt = con.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS);
        orderStmt.setInt(1, user.getId());
        orderStmt.setDouble(2, subTotal);
        orderStmt.executeUpdate();

        rs = orderStmt.getGeneratedKeys();
        if (rs.next()) {
            orderId = rs.getInt(1);
        } else {
            throw new SQLException("Failed to retrieve order ID.");
        }

        
        String itemQuery = "INSERT INTO orderitems (orderid, itemid, quantity) VALUES (?, ?, ?)";
        itemStmt = con.prepareStatement(itemQuery);
        
        for (int i = 0; i < oc; i++) {
            itemStmt.setInt(1, orderId);
            itemStmt.setInt(2, order[i].getItemId());
            itemStmt.setInt(3, 1); // Assuming quantity is always 1 for now
            itemStmt.addBatch();  // Add to batch
        }

        itemStmt.executeBatch();  
        con.commit();  
        success = true;

        JOptionPane.showMessageDialog(null, "Order placed successfully!");
    } catch (SQLException e) {
        if (con != null) {
            try {
                con.rollback();  // Rollback transaction on error
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }
        JOptionPane.showMessageDialog(null, "Error placing order.");
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (orderStmt != null) orderStmt.close();
            if (itemStmt != null) itemStmt.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return success;
}   
}