public class Date implements Comparable<Date> {
    private int day;
    private Month month;
    private int year;

    final String[] daysOfWeek = {
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday", 
        "Friday",
        "Saturday",
        "Sunday"
    };

    public Date(int _day, int _month, int _year) throws RuntimeException
    {
        year = _year;
        month = new Month(_month);

        if(_day > month.getNumberOfDays(isLeapYear())) throw new RuntimeException();
        else day = _day;        
    }

    public String toString()
    {
        return day + "/" + month.getMonth() + "/" + year;
    }

    public void daysForward(int numOfDaysForward)
    {   
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


    public void nextYear()
    {
        year++;
    }

    public void PreviousYear()
    {
        year--;
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

    public String nameDayOfWeek()
    {
        return daysOfWeek[dayOfWeek()-1];
    }

    public int getDays()
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
