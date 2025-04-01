package real;

public class foodItem extends menuItem {
    private boolean isAlco;
    private String type="food";

    public foodItem(){}
    public foodItem(int itemId,int cost, String itemName,String imgFilePath,int pointAmount){
        super(itemId,cost,itemName,imgFilePath,pointAmount);

    }
    public String getType(){return type;}    
    public boolean hasAlco(){return isAlco;}    
}