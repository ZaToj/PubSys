import java.time.LocalDate;
import java.time.Period;

public class dateTest {
    public static void main(String[] args) {
        String dob="2003-11-01";
        int years = Integer.parseInt(dob.substring(0, dob.indexOf("-")));
        int months = Integer.parseInt(dob.substring(5, 7));
        int days = Integer.parseInt(dob.substring(8, 10));
        System.out.println(years);
        System.out.println(months);
        System.out.println(days);

        int age =((years*365)+(months*30)+(days))/365;
        System.out.println(age);
        LocalDate today = LocalDate.now();
        System.out.println(today);
        LocalDate bday= LocalDate.of(years,months,days);
        System.out.println(bday);

        Period age2= (Period.between(bday,today));
        int age3=age2.getYears();
        System.out.println(age3);

    }
    
}
