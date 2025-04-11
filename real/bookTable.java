package real;
    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

    public class bookTable {
        public static User user ;

        public static void show(User useri) {
            user = useri;
            SwingUtilities.invokeLater((new Runnable() {
                @Override
                public void run(){
                    RestaurantTableBooking frame = new RestaurantTableBooking();
                    frame.setVisible(true);
                }
            }));
        }
        
    
    public static class RestaurantTableBooking extends JFrame {
        private JLabel backgroundLabel;
        private JPanel tablePanel;
        private HashMap<Integer, Boolean> tableStatus = new HashMap<>();
    
        public RestaurantTableBooking() {
            setTitle("Restaurant Table Booking");
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(null);
            setExtendedState(JFrame.MAXIMIZED_BOTH);

            JButton returnb = new JButton("Return");
            returnb.addActionListener(new ActionListener() {
 
                public void actionPerformed(java.awt.event.ActionEvent e){
                    dispose();
                    mainMenu.show(user);
                }
            });
            add(returnb);
    
            // Load background image
            ImageIcon logo = new ImageIcon("Imgs/floorplan.jpg");
            backgroundLabel = new JLabel(logo);
            backgroundLabel.setBounds(0, 0, 800, 600);  
            backgroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
            
            // Create panel for tables
            tablePanel = new JPanel();
            tablePanel.setLayout(null);
            tablePanel.setBounds(0, 0, 800, 600);
            tablePanel.setOpaque(false);
    
            addTables();
    
            // Add components
            add(tablePanel);
            add(backgroundLabel);
        }
    
        private void addTables() {
            int[][] tablePositions = {
                {1, 160}, {240, 435}, {350, 435}, {459, 435},{705, 150},{575,150},{13,388},{110,388} // test might now work??
            };
            
            JButton returnb = new JButton("Return");
            returnb.addActionListener(new ActionListener() {
 
                public void actionPerformed(java.awt.event.ActionEvent e){
                    dispose();
                    mainMenu.show(user);
                }
            });
            returnb.setBounds(5,459,190,80);

            add(returnb);

            for (int i = 1; i < tablePositions.length; i++) {
                int x = tablePositions[i][0];
                int y = tablePositions[i][1];
    
    

                try {
                    String sql = "SELECT tablestatus FROM tablestatus WHERE tableid = ? ";
                    Connection con = DBHelper.getConnection();
                    PreparedStatement pstmt = con.prepareStatement(sql);
                    pstmt.setInt(1, i);
                    ResultSet rs = pstmt.executeQuery();
                
                    if(rs.next()){
                    tableStatus.put(i, rs.getBoolean("tablestatus"));
                    JButton tableButton = new JButton();
                    if(!rs.getBoolean("tablestatus")){

                    tableButton.setText("Table " + (i + 1));
                    tableButton.setBounds(x, y, 80, 40);
                    tableButton.setBackground(Color.GREEN);
                    tablePanel.add(tableButton);
                    
                    }
                
                else{
                    tableButton.setText("BOOKED " + (i + 1));
                    tableButton.setBounds(x, y, 80, 40);
                    tableButton.setBackground(Color.RED); 
                    tablePanel.add(tableButton); 
                }

                final int tableId = i;
                tableButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        toggleTableStatus(tableButton, tableId);
                    }
                });

            }  
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error!");
                    e.printStackTrace();
                }

                
            }
        }
    
        private void toggleTableStatus(JButton tableButton, int tableId) {
            boolean isBooked = tableStatus.get(tableId);
            tableStatus.put(tableId, !isBooked);
    
            if (isBooked) {
                tableButton.setBackground(Color.GREEN);
                tableButton.setText("Table " + (tableId + 1));
                try {
                    String sql = "UPDATE tablestatus SET tablestatus = 0 WHERE tableid = ? ";
                    Connection con = DBHelper.getConnection();
                    PreparedStatement pstmt = con.prepareStatement(sql);
                    pstmt.setInt(1, tableId);
                    pstmt.executeUpdate();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "error updating!");
                    e.printStackTrace();
                }
            } else {
                tableButton.setBackground(Color.RED);
                tableButton.setText("Booked");

                try {
                    String sql = "UPDATE tablestatus SET tablestatus = 1 WHERE tableid = ? ";
                    Connection con = DBHelper.getConnection();
                    PreparedStatement pstmt = con.prepareStatement(sql);
                    pstmt.setInt(1, tableId);
                    pstmt.executeUpdate();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "error updating!");
                    e.printStackTrace();
                }
            }
        }
    
        }
    }
    

