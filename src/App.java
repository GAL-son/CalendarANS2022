import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class App {
    public static void main(String[] args) throws Exception {
        
        String d1s = "1/1/12";
        Date d1 = new Date(d1s);

        System.out.println(d1.format(Date.FORMAT_LONG));

        Date d2 = Date.parse("02/02/2020");
        System.out.println(d2.format(Date.FORMAT_ROMAN));

        Date d3 = new Date("12/12");
    }
}
