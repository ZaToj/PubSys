public class User {
    private String name;
    private String dob;
    private int age;
    private int id;
    private String address;
    private String gender;
    private int pointAmount;
    
    public User(){}

    public User(String name,String dob,String address,String gender,int pointAmount,int id){
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.gender = gender;
        this.pointAmount = pointAmount;
        this.id = id;
        this.age = 100;//will fix with an actual calc eventually
    }

    public String getName(){return name;} 
    public int getId(){return id;} 
    public String getAddress(){return address;} 
    public String getAge(){return "unavailble rn";} 
    public String getDob(){return dob;} 
    public String getGender(){return gender;} 
    public int getPointAmount(){return pointAmount;} 

    
    public String toString(){
        return ("name: "+name + "\n address: "+address+ "\n Gender: "+gender+ "\n Points:: "+pointAmount );
        
    }
     
    
}
