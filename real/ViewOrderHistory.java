package real;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewOrderHistory {
    public static void show(User user) {
        JFrame frame = new JFrame("Order History");
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout(10, 10));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocation(150, 150);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 


        Object[][] orderData = fetchUserOrders(user.getId());
        String[] orderColumnNames = {"Order ID", "Total Cost", "Order Date"};

        JTable orderTable = new JTable(orderData, orderColumnNames);
        JScrollPane orderScrollPane = new JScrollPane(orderTable);

        JPanel detailsPanel = new JPanel(new BorderLayout());
        JLabel detailsLabel = new JLabel("Select an order to view details", SwingConstants.CENTER);
        detailsPanel.add(detailsLabel, BorderLayout.NORTH);

        JTable itemTable = new JTable();
        JScrollPane itemScrollPane = new JScrollPane(itemTable);
        detailsPanel.add(itemScrollPane, BorderLayout.CENTER);
        
        JButton returnButton = new JButton(LanguageManager.getInstance().getMessages().getString("orderHistory.return"));
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();  // Close this window
                mainMenu.show(user);  // Open Main Menu
            }
        });
        detailsPanel.add(returnButton, BorderLayout.SOUTH);


        orderTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && orderTable.getSelectedRow() != -1) {
                    int orderId = (int) orderTable.getValueAt(orderTable.getSelectedRow(), 0);
                    Object[][] orderDetails = fetchOrderDetails(orderId);
                    String[] itemColumnNames = {"Item Name", "Quantity", "Item Cost", "Total Cost"};

                    itemTable.setModel(new javax.swing.table.DefaultTableModel(orderDetails, itemColumnNames));
                }
            }
        });

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, orderScrollPane, detailsPanel);
        splitPane.setDividerLocation(200);
        frame.add(splitPane, BorderLayout.CENTER); 
        frame.setVisible(true); 
    }

    private static Object[][] fetchUserOrders(int userId) {
        String query = "SELECT orderid, totalcost, orderdate FROM orders WHERE userid = ?";
        List<Object[]> orders = new ArrayList<>();

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                orders.add(new Object[]{
                        rs.getInt("orderid"),
                        rs.getDouble("totalcost"),
                        rs.getDate("orderdate")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders.toArray(new Object[0][0]);
    }

    private static Object[][] fetchOrderDetails(int orderId) {
        String query = "SELECT m.itemName, oi.quantity, m.itemCost, (oi.quantity * m.itemCost) AS totalCost " +
                "FROM orderitems oi " +
                "JOIN menuitems m ON oi.itemid = m.itemId " +
                "WHERE oi.orderid = ?";
        List<Object[]> items = new ArrayList<>();

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                items.add(new Object[]{
                        rs.getString("itemName"),
                        rs.getInt("quantity"),
                        rs.getDouble("itemCost"),
                        rs.getDouble("totalCost")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items.toArray(new Object[0][0]);
    }
}
