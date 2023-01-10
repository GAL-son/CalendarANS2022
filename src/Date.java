import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Date implements Comparable<Date> {
    private int day;
    private Month month;
    private int year;

    // Finals
    
    static final int FORMAT_SHORT = 123;
    static final int FORMAT_LONG = 124;
    static final int FORMAT_ROMAN = 125;
    static final int FORMAT_ABRR = 126;  

    static final String[] daysOfWeek = {
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday", 
        "Friday",
        "Saturday",
        "Sunday"
    };

    // Constructors

    /**
     * Base constructors tahes three parameters:
     * @param _day month day number
     * @param _month month number (1-12)
     * @param _year year number
     * @throws RuntimeException when _day is larger than max possible days in month
     */
    public Date(int _day, int _month, int _year) throws RuntimeException
    {
        year = _year;
        month = new Month(_month);

        if(_day > month.getNumberOfDays(isLeapYear())) throw new RuntimeException();
        else day = _day;
        
        logToFile("Date Created");
    }

    /**
     * Constructor that creates form a string in format: dd/mm/yyyy or d/m/y.
     * @param date String in fomrat <b>dd/mm/yyyy</b> or <b>d/m/y</b>.
     * @throws RuntimeException
     * @throws IOException
     */
    public Date(String date) throws RuntimeException, IOException {
        Date tmp = parse(date);
        this.day = tmp.day;
        this.month = tmp.month;
        this.year = tmp.year;
    }

    // Displaying 

    public String toString()
    {
        return day + "/" + month.getMonth() + "/" + year;
    }

    public String format(int format) {
        String result = "";

        switch (format) {
            case FORMAT_SHORT:
                result = day + " " + month.toString() + " " + year;
                break;
            case FORMAT_LONG:
                result = daysOfWeek[dayOfWeek()] + ", " + day + " " + month.toString() + " " + year;
                break;
            case FORMAT_ROMAN:
                result = ((day<10)?"0"+day:day) + "." + month.getRoman() + "." + year;
                break;
            case FORMAT_ABRR:
                result = daysOfWeek[dayOfWeek()].substring(0,3) + ", " + day + "-" + month.toString().substring(0,3) + "-" + year;
                break;
            default:
                result = toString();
        }

        return result;
    }

    public String nameDayOfWeek()
    {
        return daysOfWeek[dayOfWeek()-1];
    }

    // Modifying date
    static public Date parse(String date) throws RuntimeException, IOException {

        IOException eFormat = new IOException("Invalid format");

        String[] frag = date.split("/", 3);
        if(frag.length < 3) throw eFormat;
        for (String string : frag) {
            for(int i = 0; i < string.length(); i++) {
                if(!Character.isDigit(string.charAt(i))) throw eFormat;
            }
        }

        int _d = Integer.parseInt(frag[0]);
        int _m = Integer.parseInt(frag[1]);
        int _y = Integer.parseInt(frag[2]);

        return new Date(_d, _m, _y);
    }

    public void daysForward(int numOfDaysForward)
    {   
        logToFile("Date moved forward by " + numOfDaysForward + " days");
        int moveDays = numOfDaysForward;
        while (moveDays > 0) {
            int add;
            if(moveDays > month.getNumberOfDays(isLeapYear())) {
                add = month.getNumberOfDays(isLeapYear());
            } else {
                add = moveDays;
            }
        
            day += add;

            int numDays = month.getNumberOfDays(isLeapYear());
            if(day > numDays) {
                day -= numDays;
                try {
                    month.nextMonth();
                } catch(Exception e)
                {
                    nextYear();
                }
            }

            moveDays -= add;
        }
    }

    public void daysBackward(int numOfDaysBackward)
    {
        logToFile("Date moved backward by " + numOfDaysBackward + " days");
        int moveDays = numOfDaysBackward;
        while (moveDays > 0) {
            int sub;
            if(moveDays > month.getNumberOfDays(isLeapYear())) {
                sub = month.getNumberOfDays(isLeapYear());
            } else {
                sub = moveDays;
            }
        
            day -= sub;
            if (day < 1)
            {
                try {
                    month.previousMonth();
                } catch (Exception e)
                {
                    PreviousYear();
                }

                day = month.getNumberOfDays(isLeapYear()) + day;
            }        

            moveDays -= sub;
        }
    }

    public void weekForward()
    {
        daysForward(7);
    }

    public void weekBackward()
    {
        daysBackward(7);
    }

    public void nextYear()
    {
        year++;
    }

    public void PreviousYear()
    {
        year--;
    }

    // date info getters
    public boolean isLeapYear()
    {
        if(year%400 == 0) return true;
        if(year%4 == 0 && year%100 != 0) return true;
        return false;
    }

    public static boolean isLeapYear(int year) {
        
        if(year%400 == 0) return true;
        if(year%4 == 0 && year%100 != 0) return true;
        return false;
    }

    public int getYear()
    {
        return year;
    }

    public int getMonth() {
        return month.getMonth();
    }

    public int getDay() {
        return day;
    }   

    public int dayOfWeek() {
        int dayOfWeek = 0;
        int q = day;
        int m = month.getMonth();
        m = (m<3) ? m+12 : m;
        int K = year%100;
        int J = year/100;
        //System.out.println("DEBUG/ Day: " + q + " Month: " + m + " YearCentury: " + K + " Century: " + J);

         dayOfWeek = (q + ((13 * (m+1))/5) + K + (K/4) + (J/4) - (2*J))%7;

        return  ((dayOfWeek+5)%7)+1;
    }

    private int getDays()
    {
        int days = 0;
        int baseYear = 1900;
        Boolean moveUp = baseYear <= year;
        int direction = (moveUp) ? 1:-1;

        for(int i = baseYear + ((moveUp)? 0 : -1); i != year; i += direction) {
            if(Date.isLeapYear(i)) days += direction * 366;
                else days += direction * 365;
        }

        int startMonth = (moveUp) ? 1 : 12;
        for(int i = startMonth; i != month.getMonth(); i += direction) {
            days += Month.getNumberOfDays(i, isLeapYear());
        }

        if(moveUp) days+=day;
        else days += month.getNumberOfDays(isLeapYear()) - day;

        return days -1;     
    }

    // Comparing dates
    public static int daysBetween(Date d1, Date d2) {
        int days1 = d1.getDays();
        int days2 = d2.getDays();
        if(days1*days2 > 0) return Math.abs(days1-days2);
        else return Math.abs(days1+days2);        
    }

    public boolean isEalier(Date compare) {
        if(year != compare.getYear()) return year < compare.getYear();
        if(month.getMonth() != compare.getMonth()) return month.getMonth() < compare.getMonth();
        if(day != compare.getDay()) return day < compare.getDay();
        return false;
    }

    @Override
    public int compareTo(Date o) {
        int multipier = (this.isEalier(o)) ? 1 : -1;
        return multipier * daysBetween(this, o);
    }

    // Files 
    public void setFromFile(String filePath) throws IOException {
        String date = null;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            date = br.readLine();
            br.close();
        }

        if(date.isBlank()) throw new IOException("File empty!");
        //if(date.length() != 10 || date.charAt(2) != '/' || date.charAt(5) != '/') throw new IOException("Invalid format!");

        int firstSlash = -1;
        int secondSlash = -1;

        for(int i = 0; i < date.length(); i++) {
            char curr = date.charAt(i);
            if(!Character.isDigit(curr) && curr != '/') throw new IOException("Invalid Format!");

            if(curr == '/') {
                if(firstSlash == -1) firstSlash = i;
                else secondSlash = i;
            }
        }

        if(firstSlash == secondSlash) throw new IOException("Invalid Format");        
        
        String day = date.substring(0, firstSlash);
        String month = date.substring(firstSlash+1, secondSlash);
        String year = date.substring(secondSlash+1);

        logToFile("Date was set from file " + filePath);

        this.day = Integer.parseInt(day);
        this.month.mon = Integer.parseInt(month);
        this.year = Integer.parseInt(year);
    }

    public void saveToFile(String filePath) throws IOException {
        String date = this.toString();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.append(date);
            bw.newLine();
            bw.close();
        }

        logToFile("Date was saved to file " + filePath);
    }

    private void logToFile(String message){
        String logPath = "src/log.txt";
        message = this.toString() + " - " +  message;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(logPath, true))) {
            bw.append(message + "\n");
            bw.close();
        } catch (IOException e) {

        }
        
    }



    // Inner class Month
    private class Month {
        private int mon;
    
        final private String[] names = {
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
        };

        final private String[] roman = {
            "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "XI", "X", "XI", "XII"
        };
    
        final static private int[] numOfDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    
        public Month(int _val) throws RuntimeException {
            if(_val > 12) throw new RuntimeException();
            mon = _val;
        }
    
        public int getMonth() {
            return this.mon;
        }
    
        public int getNumberOfDays(boolean isLeapYear) {
            if(isLeapYear && mon == 2) return 29;
            else return numOfDays[mon-1];
        }

        public static int getNumberOfDays(int month, boolean isLeapYear) {
            if(isLeapYear && month == 2) return 29;
            else return numOfDays[month-1];
        }
    
        public String toString() {
            return names[mon-1];
        }

        public String getRoman() {
            return roman[mon-1];
        }
    
        public void nextMonth() throws YearException
        {
            int temp = mon+1;
            if(temp > 12) {
                mon = temp%12;
                throw new YearException();
            }
            mon = temp;
        }
    
        public void previousMonth() throws YearException
        {
            int temp = mon-1;
            if(temp < 1) {
                mon = 12 - temp;
                throw new YearException();
            }
            mon = temp;
        }        
    
        private class YearException extends Exception
        {
    
        }
    }
}
