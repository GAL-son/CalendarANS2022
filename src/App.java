import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class App {
    public static void main(String[] args) throws Exception {
        Date d0 = new Date(2,1,2023);
        Date d1 = new Date(1, 1, 1000);
        Date d2 = new Date(19, 3, 666);

        ArrayList<Date> list = new ArrayList<>();
        list.add(d2);
        list.add(d1);
        list.add(d0);

        System.out.println(list);

        Collections.sort(list);

        System.out.println(list);

        

        




        
    }
}
