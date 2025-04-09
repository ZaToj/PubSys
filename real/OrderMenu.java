package real;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ResourceBundle;

@SuppressWarnings("unused")
public class OrderMenu {
    private static menuItem order[] = new menuItem[25];
    private static int i = 0;
    private static int oc = 0;
    private static int subTotal = 0;
    private static JLabel subTotalLabel;
    private static JPanel controlsPanel;
    private static DefaultListModel<String> model;
    private static JList<String> list;
    private static JFrame frame;
    private static JButton returnButton, orderButton;
    private static JComboBox<String> categoryFilter; // Added JComboBox
    private static JPanel optionsPanel; 

    public static void show(User user) {
        frame = new JFrame();
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout(10, 10));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(150, 150);
    
        // Sidebar (Control Panel)
        controlsPanel = new JPanel();
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));
        controlsPanel.setPreferredSize(new Dimension(200, frame.getHeight()));
        controlsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    
        // Add category filter
        String[] categories = {"Both", "Food", "Drinks"};
        categoryFilter = new JComboBox<>(categories);
        categoryFilter.setMaximumSize(new Dimension(150, 30));
        categoryFilter.setAlignmentX(Component.CENTER_ALIGNMENT);
        categoryFilter.addActionListener(e -> refreshMenuItems(user));
    
        returnButton = new JButton();
        returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnButton.addActionListener(e -> {
            clear(user);
            frame.dispose();
        });
    
        orderButton = new JButton();
        orderButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        orderButton.addActionListener(e -> {
            int pointsGained = 0;
            boolean worked = processsOrder(user);
            if (worked) {
                for (int j = 0; j < oc; j++) {
                    pointsGained += order[j].getPointAmount();
                }
                givePoints(user, pointsGained);
                JOptionPane.showMessageDialog(null, LanguageManager.getInstance().getMessages().getString("ordermenu.orderSuccess"));
                clear(user);
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(null, LanguageManager.getInstance().getMessages().getString("ordermenu.orderFail"));
            }
        });
    
        model = new DefaultListModel<>();
        list = new JList<>(model);
        subTotalLabel = new JLabel();
        Font buttonFont = LanguageManager.getInstance().getFont(16);
        orderButton.setFont(buttonFont);
        returnButton.setFont(buttonFont);
        list.setFont(buttonFont);
        subTotalLabel.setFont(buttonFont);
        categoryFilter.setFont(buttonFont);
    
        controlsPanel.add(Box.createVerticalStrut(20));
        controlsPanel.add(categoryFilter);
        controlsPanel.add(Box.createVerticalStrut(20));
        controlsPanel.add(orderButton);
        controlsPanel.add(Box.createVerticalGlue());
        controlsPanel.add(Box.createVerticalStrut(20));
        controlsPanel.add(list);
        controlsPanel.add(Box.createVerticalStrut(20));
        controlsPanel.add(subTotalLabel);
        controlsPanel.add(returnButton);
        orderButton.setMaximumSize(new Dimension(150, 30));
        returnButton.setMaximumSize(new Dimension(150, 30));
        list.setMaximumSize(new Dimension(150, Integer.MAX_VALUE));
        subTotalLabel.setMaximumSize(new Dimension(150, 30));
    
        // Main Options Panel Container
        JPanel optionsContainer = new JPanel();
        optionsContainer.setLayout(new BoxLayout(optionsContainer, BoxLayout.Y_AXIS)); // Stack grids vertically
    
        // Create the first 3x3 grid
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(3, 3, 10, 10)); // 3x3 grid
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        // Add the first optionsPanel to the container
        optionsContainer.add(optionsPanel);
    
        // Wrap the container in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(optionsContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smooth scrolling
    
        // Initial load of menu items
        refreshMenuItems(user);
    
        // Add panels to frame
        frame.add(controlsPanel, BorderLayout.WEST);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    
        updateLanguage();
        frame.setVisible(true);
    }
    
    private static void refreshMenuItems(User user) {
        // Clear the current options container
        JPanel optionsContainer = (JPanel) optionsPanel.getParent();
        optionsContainer.removeAll();
    
        String selectedCategory = (String) categoryFilter.getSelectedItem();
        int itemCount = 0;
    
        try {
            Connection con = DBHelper.getConnection();
            String sql = "SELECT itemId FROM menuItems WHERE hasAlco =0";
            if(user.getAge()>=18){
                sql = "SELECT itemId FROM menuItems";
            }

            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
    
            optionsPanel = new JPanel(new GridLayout(3, 3, 20, 20)); 
            optionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            optionsPanel.setMaximumSize(new Dimension(1050, 1050)); 
    
            while (rs.next()) {
                int itemId = rs.getInt("itemId");
                menuItem item = menuItem.getMenuItem(itemId);
    
                if (item != null) {
                    String itemType = item instanceof foodItem ? "food" :
                                      item instanceof drinkItem ? "drink" : "";
    
                    if (selectedCategory.equals("Both") ||
                        (selectedCategory.equals("Food") && itemType.equals("food")) ||
                        (selectedCategory.equals("Drinks") && itemType.equals("drink"))) {
    
                        if (itemCount > 0 && itemCount % 9 == 0) {
                            optionsContainer.add(optionsPanel);
                            optionsPanel = new JPanel(new GridLayout(3, 3, 20, 20));
                            optionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                            optionsPanel.setMaximumSize(new Dimension(1050, 1050)); 
                        }
    
                        JPanel itemPanel = createMenuItem(
                            item.getItemName(),
                            item.getItemCost(),
                            item.getImgFilePath(),
                            item.getItemId(),
                            item.getPointAmount()
                        );
                        optionsPanel.add(itemPanel);
                        itemCount++;
                    }
                }
            }
            if (itemCount > 0) {
                while (itemCount % 9 != 0) {
                    optionsPanel.add(new JPanel());
                    itemCount++;
                }
                optionsContainer.add(optionsPanel);
            }
    
            rs.close();
            stmt.close();
            con.close();
    
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading menu items from database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    
        optionsContainer.revalidate();
        optionsContainer.repaint();
    }    
    private static void updateLanguage() {
        ResourceBundle messages = LanguageManager.getInstance().getMessages();
        frame.setTitle(messages.getString("ordermenu.title"));
        returnButton.setText(messages.getString("ordermenu.return"));
        orderButton.setText(messages.getString("ordermenu.order"));
        subTotalLabel.setText(messages.getString("ordermenu.subtotal").replace("${0}", String.valueOf(subTotal)));
    }

    private static void givePoints(User user, int in) {
        int points = user.getPointAmount() + in;
        user.setPointsAmount(points);
        try {
            Connection con = DBHelper.getConnection();
            String sql = "UPDATE users SET pointsAmount = ? WHERE userId = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, points);
            pstmt.setInt(2, user.getId());
            pstmt.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, LanguageManager.getInstance().getMessages().getString("ordermenu.pointsError"), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private static void clear(User user) {
        oc = 0;
        i = 0;
        subTotal = 0;
        subTotalLabel = new JLabel();
        controlsPanel = new JPanel();
        model = new DefaultListModel<>();
        list = new JList<>(model);
        mainMenu.show(user);
    }

    private static JPanel createMenuItem(String name, int price, String imagePath, int itemId, int pointAmount) {
        JPanel panel = new JPanel();
        menuItem tempItem = new menuItem(itemId, price, name, imagePath, pointAmount);
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(350, 350)); 
        panel.setMaximumSize(new Dimension(350, 350)); 
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    
        String priceText = "$" + price;
    
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage().getScaledInstance(250, 200, Image.SCALE_SMOOTH); 
        JLabel imageLabel = new JLabel(new ImageIcon(img));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
    
        JLabel nameLabel = new JLabel(name, SwingConstants.CENTER);
        JLabel priceLabel = new JLabel(priceText, SwingConstants.CENTER);
        Font font = LanguageManager.getInstance().getFont(18); 
        nameLabel.setFont(font);
        priceLabel.setFont(font);
    
        JButton button = new JButton(LanguageManager.getInstance().getMessages().getString("ordermenu.select"));
        button.setFont(font);
        button.addActionListener(e -> {
            if (i < order.length) {
                order[i] = tempItem;
                model.addElement(order[i].getItemName());
                subTotal += order[i].getItemCost();
                subTotalLabel.setText(LanguageManager.getInstance().getMessages().getString("ordermenu.subtotal").replace("${0}", String.valueOf(subTotal)));
                i++;
                oc++;
            } else {
                JOptionPane.showMessageDialog(null, LanguageManager.getInstance().getMessages().getString("ordermenu.orderLimit"));
            }
        });
    
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.add(nameLabel);
        textPanel.add(priceLabel);
    
        panel.add(button, BorderLayout.NORTH);
        panel.add(imageLabel, BorderLayout.CENTER);
        panel.add(textPanel, BorderLayout.SOUTH);
    
        return panel;
    }
    public static boolean processsOrder(User user) {
        if (oc == 0) {
            JOptionPane.showMessageDialog(null, LanguageManager.getInstance().getMessages().getString("ordermenu.noItems"));
            return false;
        }

        Connection con = null;
        PreparedStatement orderStmt = null;
        PreparedStatement itemStmt = null;
        ResultSet rs = null;
        boolean success = false;
        int orderId = -1;

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

            for (int j = 0; j < oc; j++) {
                itemStmt.setInt(1, orderId);
                itemStmt.setInt(2, order[j].getItemId());
                itemStmt.setInt(3, 1);
                itemStmt.addBatch();
            }

            itemStmt.executeBatch();
            con.commit();
            success = true;
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            JOptionPane.showMessageDialog(null, LanguageManager.getInstance().getMessages().getString("ordermenu.orderFail"));
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