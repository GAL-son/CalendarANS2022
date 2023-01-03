public class App {
    public static void main(String[] args) throws Exception {
        Date d1 = new Date(9,1,2023);
        for(int i = 1; i < 7; i++) {
            System.out.println(d1 + "DAY OF WEEK: " + d1.dayOfWeek());
            d1.daysForward(1);
        }
        

        
    }
}
