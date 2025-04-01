package tempPasswordChanger;
public class maker{
    public static void main(String[] args) {
    String conName="chelskeel24!";
    conName=passHasher(conName);
    System.out.println(conName);
}
public static  String passHasher(String passIn){
    //
    //takes in string and gets the acscii value of each char, concatanates them to a string and takes the frist 6 which is then turned to binar (20 digits)
    //
    String out="";
    String concat="";
    for (int i = 0; i < passIn.length(); i++){
        int preBinary=passIn.charAt(i);
        concat+=preBinary;
    }
    concat=concat.substring(0,6);
    int input=Integer.parseInt(concat);
    String temp="";
    while (input!=0){
        if (input%2==0){
            temp+=0;
        }
        else{
            temp+=1;
        }
        input =input/2;
    }
    //System.out.println(temp);
    for(int i=temp.length()-1; i !=-1
    ;i--){
        out += temp.charAt(i);
    }
    return out;
}
}
