package real;

public class drinkItem extends menuItem {
    private boolean isAlco;
    private String type="drink";

    public drinkItem(){}
    public drinkItem(int itemId,int cost, String itemName,String imgFilePath,boolean isAlco,int pointAmount){
        super(itemId,cost,itemName,imgFilePath,pointAmount);

    }   
    public String getType(){return type;}    
    public boolean hasAlco(){return isAlco;}     
}