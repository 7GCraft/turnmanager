package com.clancraft.turnmanager;

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
        DECEMBER("December", 12, 31);

        Month(String monthName, int monthNum, int numDays) {
            this.monthName = monthName;
            this.monthNum = monthNum;
            this.numDays = numDays;
        }

        public final String monthName;
        public final int monthNum;
        public final int numDays;
    }

    private int day;
    private Month month;
    private int year;

    /**
     * Default constructor. Initialises fields.
     */
    public Date() {
        // default date: 1-1-401
        setDate(1, Month.JANUARY, 401);
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

    private boolean isLeapYear() {
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
}
