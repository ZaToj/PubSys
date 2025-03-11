package real;

public class order {
    private int arraySize;
    private menuItem items[];
    private double cost;
    private int userId;

    public order(){

    }
    
    public order(int arraySize,menuItem items[],double cost,int userId){
        setArraySize(arraySize);
        setCost(cost);
        setItems(items);
        setUserId(userId);
    }

    //setters
    public void setArraySize(int arraySize){this.arraySize=arraySize;}
    public void setItems(menuItem items[]){this.items=items;}
    public void setCost(double cost){this.cost=cost;}    
    public void setUserId(int userId){this.userId=userId;}    
    
    //getters
    public int setArraySize(){return arraySize;}
    public menuItem[] getItems(){return items;}
    public double getCost(){return cost;}
    public int getUserId(){return userId;}    

    
    
}
