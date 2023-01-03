public class App {
    public static void main(String[] args) throws Exception {
        Date d1 = new Date(29,12,2022);

        System.out.println("Week Forward / backward");
        System.out.println(d1);
        d1.weekForward();
        System.out.println(d1);
        d1.weekBackward();
        System.out.println(d1);

        System.out.println("Test Leap Year");
        Date d2 = new Date(22, 2, 2020);
        System.out.println(d2);
        d2.weekForward();
        System.out.println(d2);

        System.out.println("Day Forward / backward");
        Date d3 = new Date(12, 5, 2001);
        System.out.println(d3);
        d3.daysForward(15);
        System.out.println(d3);
        d3.daysBackward(4);
        System.out.println(d3);
    }
}
