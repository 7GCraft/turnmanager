package com.clancraft.turnmanager.calendar;

public class Date {
    public enum Month {
        JANUARY("January", 1, 31), 
        FEBRUARY("February", 2, 28), 
        MARCH("March", 3, 31), 
        APRIL("April", 4, 30),
        MAY("May", 5, 31), 
        JUNE("June", 6, 30), 
        JULY("July", 7, 31), 
        AUGUST("August", 8, 31),
        SEPTEMBER("September", 9, 30), 
        OCTOBER("October", 10, 31), 
        NOVEMBER("November", 11, 30),
        DECEMBER("December", 12, 31) {
            @Override
            public Month next() {
                return values()[0];
            }
        };

        Month(String monthName, int monthNum, int numDays) {
            this.monthName = monthName;
            this.monthNum = monthNum;
            this.numDays = numDays;
        }
        
        public Month next() {
            return values()[ordinal() + 1];
        }

        public final String monthName;
        public final int monthNum;
        public final int numDays;
    }

    private int day;
    private Month month;
    private int year;
    private boolean isSynced;

    /**
     * Default constructor. Initialises fields.
     */
    public Date() {
        // default date: 1-1-401
        setDate(1, Month.JANUARY, 401);
        setIsSynced(true);
    }

    /**
     * Constructor overload. Initialises fields according to args.
     * 
     * @param day   date day
     * @param month date month
     * @param year  date year
     */
    public Date(int day, Month month, int year) {
        setDate(day, month, year);
    }
    
    /**
     * Constructor overload. Initialises fields according to args.
     * 
     * @param day       date day
     * @param month     date month
     * @param year      date year
     * @param isSynced  sync with world date
     */
    public Date(int day, Month month, int year, boolean isSynced) {
        setDate(day, month, year);
        setIsSynced(isSynced);
    }

    /**
     * Gets a Month from its month number.
     * @param monthNum  number of the month (1 - 12)
     * @return  specified Month
     */
    public static Month getMonthEnum(int monthNum) {
        return Date.values()[monthNum - 1];
    }
    
    /**
     * Advances date by specified number of days.
     * @param daysToAdd days to advance
     * @return
     */
    public void add(int daysToAdd) {
        int tempDay = getDay() + daysToAdd;
        Month tempMonth = getMonth();
        int tempYear = getYear();
        
        int maxDays = month.numDays;
        if (tempMonth == Month.FEBRUARY && isLeapYear(tempYear)) {
            maxDays = 29;
        }
        
        while (tempDay > maxDays) {
            tempDay -= maxDays;
            tempMonth = tempMonth.next();
            if (tempMonth.monthNum == 1) {
                tempYear++;
            }
            
            maxDays = month.numDays;
            if (tempMonth == Month.FEBRUARY && isLeapYear(tempYear)) {
                maxDays = 29;
            }
        }
        
        setDate(tempDay, tempMonth, tempYear);
    }

    /**
     * Checks if current year is a leap year.
     * @return
     */
    private boolean isLeapYear() {
        if ((year % 4 == 0) && (year % 100 != 0))
            return true;
        if (year % 400 == 0)
            return true;
        return false;
    }
    
    /**
     * Checks if specified year is a leap year.
     * @param year  year to check
     * @return
     */
    private boolean isLeapYear(int year) {
        if ((year % 4 == 0) && (year % 100 != 0))
            return true;
        if (year % 400 == 0)
            return true;
        return false;
    }

    /**
     * Get formatted date.
     * 
     * @return formatted date
     */
    public String getDate() {
        return String.format("%s %s %s", getDay(), getMonthName(), getYear());
    }

    /**
     * Gets the current day number.
     * 
     * @return day number
     */
    public int getDay() {
        return day;
    }
    
    /**
     * Gets month.
     * 
     * @return
     */
    public Month getMonth() {
        return month;
    }

    /**
     * Gets month name.
     * 
     * @return name of the month
     */
    public String getMonthName() {
        return month.monthName;
    }

    /**
     * Gets year.
     * 
     * @return year
     */
    public int getYear() {
        return year;
    }
    
    /**
     * Checks whether the date is synced to world date or not.
     * 
     * @return
     */
    public boolean getIsSynced() {
        return isSynced;
    }

    /**
     * 
     * @param day
     * @param month
     * @param year
     */
    public void setDate(int day, Month month, int year) {
        setYear(year);
        setMonth(month);
        setDay(day);
    }

    /**
     * Sets and validates the day.
     * 
     * @param newDay new day
     */
    public void setDay(int newDay) {
        int maxDay = month.numDays;
        if (month == Month.FEBRUARY && isLeapYear()) {
            maxDay = 29;
        }

        if (newDay < 1) {
            day = 1;
        } else if (newDay > maxDay) {
            day = maxDay;
        } else {
            day = newDay;
        }
    }

    /**
     * Sets and validates the month.
     * 
     * @param newMonth new month
     */
    public void setMonth(Month newMonth) {
        month = newMonth;
    }

    /**
     * Sets and validates the year.
     * 
     * @param newYear new year
     */
    public void setYear(int newYear) {
        year = newYear;
    }
    
    /**
     * Sets date to sync with world date or not.
     * 
     * @param isSynced
     */
    public void setIsSynced(boolean isSynced) {
        this.isSynced = isSynced;
    }
}
