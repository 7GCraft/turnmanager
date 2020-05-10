package com.clancraft.turnmanager;

public class Date {
    public int[] days = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    public static String[] months = { "", "January", "February", "March", "April", "May", "June", "July", "August",
            "September", "October", "November", "December" };

    private int day;
    private int month;
    private int year;

    /**
     * Default constructor. Initialises fields.
     */
    public Date() {
        // default date: 1-1-401
        setDate(1, 1, 401);
    }

    /**
     * Constructor overload. Initialises fields according to args.
     * 
     * @param day   date day
     * @param month date month
     * @param year  date year
     */
    public Date(int day, int month, int year) {
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
        return getDay() + getMonthName() + getYear();
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
        return months[month];
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
    public void setDate(int day, int month, int year) {
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
        int maxDay = days[month];
        if (month == 2 && isLeapYear()) {
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
    public void setMonth(int newMonth) {
        if (newMonth < 1) {
            month = 1;
        } else if (newMonth > 12) {
            month = 12;
        } else {
            month = newMonth;
        }
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
