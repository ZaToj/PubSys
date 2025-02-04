public class User {
    private String name;
    private String dob;
    private int age;
    private String address;
    private String gender;
    private int pointAmount;
    
    public User(){}

    public User(String name,String dob,String address,String gender,int pointAmount){
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.gender = gender;
        this.pointAmount = pointAmount;
        this.age = 100;//will fix with an actual calc eventually
    }
     
    
}
