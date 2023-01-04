import java.util.ArrayList;
import java.util.Collections;

public class App {
    public static void main(String[] args) throws Exception {
        Date d1 = new Date(1, 1, 1);

        d1.setFromFile("src/test.txt");
        System.out.println(d1);      
        d1.daysForward(12);
        d1.saveToFile("src/text.txt");

        Date d2 = new Date(2, 2, 2);
        d2.setFromFile("src/text.txt");
        System.out.println(d2);  

        




        
    }
}
