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

        controlsPanel.add(Box.createVerticalStrut(20));
        controlsPanel.add(orderButton);
        controlsPanel.add(Box.createVerticalGlue());
        controlsPanel.add(Box.createVerticalStrut(20));
        controlsPanel.add(list);
        controlsPanel.add(Box.createVerticalStrut(20));
        controlsPanel.add(subTotalLabel);
        controlsPanel.add(returnButton);

        // Main Options Panel
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(3, 0, 10, 10));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Load menu items
        for (int j = 1; j < 10; j++) {
            menuItem item = menuItem.getMenuItem(j);
            if (item != null) {
                JPanel itemPanel = createMenuItem(item.getItemName(), item.getItemCost(), item.getImgFilePath(), item.getItemId(), item.getPointAmount());
                optionsPanel.add(itemPanel);
            }
        }

        // Add panels to frame
        frame.add(controlsPanel, BorderLayout.WEST);
        frame.add(optionsPanel, BorderLayout.CENTER);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        updateLanguage(); // Set initial text
        frame.setVisible(true);
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
        panel.setPreferredSize(new Dimension(150, 150));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        String priceText = "$" + price;

        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage().getScaledInstance(100, 80, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(img));

        JLabel nameLabel = new JLabel(name, SwingConstants.CENTER);
        JLabel priceLabel = new JLabel(priceText, SwingConstants.CENTER);

        JButton button = new JButton(LanguageManager.getInstance().getMessages().getString("ordermenu.select"));
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

        panel.add(imageLabel, BorderLayout.CENTER);
        panel.add(textPanel, BorderLayout.SOUTH);
        panel.add(button, BorderLayout.NORTH);

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