package real;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;

public class menuItem  {
    private int itemId;
    private int itemCost;
    private int pointAmount;
    private String itemName;
    private Icon myPicture;
    private String imgFilePath;
    
    public menuItem(){}
    public menuItem(int itemId,int cost, String itemName,String imgFilePath,int pointAmount){
        this.itemId = itemId;
        this.itemCost = cost;
        this.itemName = itemName;
        this.imgFilePath = imgFilePath;
        this.pointAmount=pointAmount;
        myPicture= new ImageIcon(imgFilePath);
    }
    public int getItemId(){return itemId;}
    public int getItemCost(){return itemCost;}
    public int getPointAmount(){return pointAmount;}
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
        int itemId = 0;
        int pointAmount = 0;
        String type = "";
        boolean hasAlco = false;

        try {
            Connection con = DBHelper.getConnection();

            // Query the base menu item details
            String baseQuery = "SELECT itemId, itemCost, imgFilePath, type, hasAlco, pointsAmount FROM menuItems WHERE itemId = ?";
            PreparedStatement baseStmt = con.prepareStatement(baseQuery);
            baseStmt.setInt(1, searchId);
            ResultSet baseResult = baseStmt.executeQuery();

            if (baseResult.next()) {
                itemId = baseResult.getInt("itemId");
                itemCost = baseResult.getInt("itemCost");
                String relativePath = baseResult.getString("imgFilePath");
                type = baseResult.getString("type");
                hasAlco = baseResult.getBoolean("hasAlco");
                pointAmount = baseResult.getInt("pointsAmount");
                String basePath = System.getProperty("user.dir");
                imgFilePath = basePath + File.separator + relativePath;

                // Query the translated name
                String locale = LanguageManager.getInstance().getLocale().getLanguage(); // 'en' or 'ja'
                String transQuery = "SELECT itemName FROM menuItemTranslations WHERE itemId = ? AND locale = ?";
                PreparedStatement transStmt = con.prepareStatement(transQuery);
                transStmt.setInt(1, searchId);
                transStmt.setString(2, locale);
                ResultSet transResult = transStmt.executeQuery();

                if (transResult.next()) {
                    itemName = transResult.getString("itemName");
                } else {
                    // Fallback: use English if translation not found
                    transStmt.setString(2, "en");
                    transResult = transStmt.executeQuery();
                    if (transResult.next()) {
                        itemName = transResult.getString("itemName");
                    } else {
                        itemName = "Unknown Item";
                    }
                }

                transResult.close();
                transStmt.close();
            } else {
                JOptionPane.showMessageDialog(null, "Item not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            baseResult.close();
            baseStmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Error!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (type.equals("drink")) {
            return new drinkItem(itemId, itemCost, itemName, imgFilePath, hasAlco, pointAmount);
        } else {
            return new foodItem(itemId, itemCost, itemName, imgFilePath, pointAmount);
        }
    }
}
