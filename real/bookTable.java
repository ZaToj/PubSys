package real;


    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.util.HashMap;

    public class bookTable {

        public static void show(User user) {
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
    
            // Load background image
            ImageIcon restaurantImage = new ImageIcon("TableLayout.jpg");
            backgroundLabel = new JLabel(restaurantImage);
            backgroundLabel.setBounds(0, 0, 800, 600);
            
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
                {100, 150}, {200, 200}, {300, 250}, {400, 300}, {500, 350} // test will get rid of later
            };
    
            for (int i = 0; i < tablePositions.length; i++) {
                int x = tablePositions[i][0];
                int y = tablePositions[i][1];
    
                JButton tableButton = new JButton("Table " + (i + 1));
                tableButton.setBounds(x, y, 80, 40);
                tableButton.setBackground(Color.GREEN);
    
                final int tableId = i;
                tableButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        toggleTableStatus(tableButton, tableId);
                    }
                });
    
                tablePanel.add(tableButton);
                tableStatus.put(i, false); 
            }
        }
    
        private void toggleTableStatus(JButton tableButton, int tableId) {
            boolean isBooked = tableStatus.get(tableId);
            tableStatus.put(tableId, !isBooked);
    
            if (isBooked) {
                tableButton.setBackground(Color.GREEN);
                tableButton.setText("Table " + (tableId + 1));
            } else {
                tableButton.setBackground(Color.RED);
                tableButton.setText("Booked");
            }
        }
    
        }
    }
    

