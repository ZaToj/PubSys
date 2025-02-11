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

    


    
}
