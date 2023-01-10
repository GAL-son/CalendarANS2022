import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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

        Date[] dateArray = {d0, d1, d2};

        System.out.println("\nUnsorted list");
        for (Date date : list) {
            System.out.println(date.format(Date.FORMAT_ROMAN));
        }

        System.out.println("\nUnsorted array");
        for (Date date : dateArray) {
            System.out.println(date.format(Date.FORMAT_ROMAN));
        }

        Collections.sort(list);
        Arrays.sort(dateArray);

        System.out.println("\nSorted list");
        for (Date date : list) {
            System.out.println(date.format(Date.FORMAT_LONG));
        }

        System.out.println("\nSorted array");
        for (Date date : dateArray) {
            System.out.println(date.format(Date.FORMAT_LONG));
        }
        
    }
}
