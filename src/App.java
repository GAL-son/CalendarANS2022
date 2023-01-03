public class App {
    public static void main(String[] args) throws Exception {
        Date curr = new Date(9,1,2023);

        for(int i = 1; i < 250; i++) {            
            int next = 1;            
            Date nextDate = new Date(curr.getDay(), curr.getMonth(), curr.getYear());
            nextDate.daysForward(next);

            System.out.print(curr + "-" + nextDate);
            System.out.println(" " + curr.dayOfWeek() + "-" + nextDate.dayOfWeek());

            int testDayWeek = (curr.dayOfWeek() + (next%7));
            if(testDayWeek > 7 ) testDayWeek %= 7;

            if(nextDate.dayOfWeek() != testDayWeek) {
                System.out.println("ERROR" + nextDate.dayOfWeek() + "VS" + (curr.dayOfWeek() + (next%7)) + "\n");
            }

            curr.daysForward(next);
        }    

        
    }
}
