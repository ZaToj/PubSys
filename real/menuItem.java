package real;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;

public class menuItem  {
    private int itemId;
    private int itemCost;
    private String itemName;
    private Icon myPicture;
    private String imgFilePath;
    
    public menuItem(){}
    public menuItem(int itemId,int cost, String itemName,String imgFilePath){
        this.itemId = itemId;
        this.itemCost = cost;
        this.itemName = itemName;
        this.imgFilePath = imgFilePath;
        myPicture= new ImageIcon(imgFilePath);
    }
    public int getItemId(){return itemId;}
    public int getItemCost(){return itemCost;}
    public String getItemName(){return itemName;}
    public String getImgFilePath(){return imgFilePath;}
    public Icon getIcon(){return myPicture;}
    
    public void setImgFilePath(String imgFilePath){
        this.imgFilePath=imgFilePath;
        myPicture= new ImageIcon(imgFilePath);
    }


    public static menuItem getMenuItem(int searchId) {
        String itemName = null;
        int itemCost = 0;
        String imgFilePath = null;
        int itemId=0;
        String searchIdString="";
        searchIdString = searchIdString+ searchId;
    
        try {
            Connection con = DBHelper.getConnection();
            String query = "SELECT * FROM menuItems WHERE itemId = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, searchIdString);  
            ResultSet resultSet = pstmt.executeQuery();
    
            if (resultSet.next()) { 
                itemName = resultSet.getString("itemName");
                itemCost = Integer.parseInt(resultSet.getString("itemCost"));
                String relativePath = resultSet.getString("imgFilePath");
                itemId = resultSet.getInt("itemId");
                String basePath = System.getProperty("user.dir"); 
                imgFilePath = basePath + File.separator + relativePath;
    
            //JOptionPane.showMessageDialog(null, "Welcome back, " + userName + "!", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
            } 
            else {
                JOptionPane.showMessageDialog(null, "Item not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
    
            // Close resources
            resultSet.close();
            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        menuItem ren = new menuItem(itemId, itemCost, itemName, imgFilePath);

        System.out.println(imgFilePath);
        return ren;
    }    
}
