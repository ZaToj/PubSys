import java.time.LocalDate;
import java.time.Period;

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
        this.age = ageCAlc(dob);//will fix with an actual calc eventually
    }

    public String getName(){return name;} 
    public int getId(){return id;} 
    public String getAddress(){return address;} 
    public int getAge(){return age;} 
    public String getDob(){return dob;} 
    public String getGender(){return gender;} 
    public int getPointAmount(){return pointAmount;} 



    public static int ageCAlc(String dob){
        int years = Integer.parseInt(dob.substring(0, dob.indexOf("-")));
        int months = Integer.parseInt(dob.substring(5, 7));
        int days = Integer.parseInt(dob.substring(8, 10));
        LocalDate today = LocalDate.now();
        LocalDate bday= LocalDate.of(years,months,days);
        Period age2= (Period.between(bday,today));
        int age3=age2.getYears();
        System.out.println(age3);
        
        return age3;
    }
    
    public String toString(){
        return ("name: "+name + "\n address: "+address+ "\n Gender: "+gender+ "\n Points:: "+pointAmount );
        
    }
     
    
}
