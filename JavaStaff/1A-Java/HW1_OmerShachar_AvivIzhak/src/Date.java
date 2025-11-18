/**
 * Maman 12
 * this class represents a given date in the Gregorian Calendar
 * @author Omer Shachar (ID - *********)
 * @version 2024a
 */

public class Date {
    //instance variables:
    private int _day;
    private int _month;
    private int _year;

    //default values:
    final static int DEFAULT_DAY = 1;
    final static int DEFAULT_MONTH = 1;
    final static int DEFAULT_YEAR = 2000;

    //the maximum and minimum values allowed:
    final static int MAX_DAY = 31;
    final static int MIN_DAY = 1;

    final static int MAX_DAY_SHORT = 30;
    final static int MAX_DAY_MONTH_2 = 28;
    final static int MAX_DAY_MONTH_2_LEAP_YEAR = 29;    

    final static int MAX_MONTH = 12;
    final static int MIN_MONTH = 1;

    final static int MAX_YEAR = 9999;
    final static int MIN_YEAR = 1000;

    //other finals:
    final static int MONTH_1 = 1;
    final static int MONTH_2 = 2;
    final static int MONTH_3 = 3;
    final static int MONTH_4 = 4;
    final static int MONTH_5 = 5;
    final static int MONTH_6 = 6;
    final static int MONTH_7 = 7;
    final static int MONTH_8 = 8;
    final static int MONTH_9 = 9;
    final static int MONTH_10 = 10;
    final static int MONTH_11 = 11;
    final static int MONTH_12 = 12;

    final static int DAY_9 = 9;

    private boolean isLeapYear(int year) { //checks if it is a leap year
        return ( ((year % 4) == 0) && !((year % 100) == 0) || ((year % 400) == 0) );
    }

    private boolean isShortMonth(int month) { //checks if it is a month with 30 days
        return ((month == MONTH_4) || (month == MONTH_6) || (month == MONTH_9) || (month == MONTH_11));     
    }   

    private boolean isDayValid(int day, int month, int year) { //checks if the day value legal
        if (((day >= MIN_DAY) && (day <= MAX_DAY))){ //checks that the day is in the correct range between 1-31
            if (isShortMonth(month)) {
                return day <= MAX_DAY_SHORT;
            } else if (month == MONTH_2) {
                if (isLeapYear(year)) {
                    return day <= MAX_DAY_MONTH_2_LEAP_YEAR;
                }
                else return day <= MAX_DAY_MONTH_2;
            } else return true;
        } else return false;
    }

    private boolean isMonthValid(int month) { //checks if the month value legal
        return ((month >= MIN_MONTH) && (month <= MAX_MONTH)); //checks that the month is in the correct range between 1-12 
    }

    private boolean isYearValid(int year) { //checks if the year value legal
        return ((year >= MIN_YEAR) && (year <= MAX_YEAR)); //checks that the year is in the correct range between 1000-9999
    }

    private boolean isDateValid(int day, int month, int year) {
        return (isDayValid(day, month, year) && isMonthValid(month) && isYearValid(year));
    }

    //constructors:
    /**
     * Date constructor - If the given date is valid - creates a new Date object, otherwise creates the date 01/01/2000.
     * @param day the day in the month(1-31)
     * @param month the month in the year
     * @param year the year (in 4 digits)
     */
    public Date (int day, int month, int year) {
        _day = (isDayValid(day, month, year))? day : DEFAULT_DAY;

        _month = (isMonthValid(month))? month : DEFAULT_MONTH;

        _year = (isYearValid(year))? year : DEFAULT_YEAR;
    }

    /**
     * Copy Constructor
     * @param other the date to be copied
     */
    public Date(Date other) {
        _day = other._day;
        _month = other._month;
        _year = other._year;
    }

    //method definitions:
    //getters:
    /**
     * Gets the year
     * @return the year of this date
     */
    public int getYear(){
        return _year;
    }

    /**
     * Gets the month
     * @return the month of this date
     */
    public int getMonth(){
        return _month;
    }

    /** 
     * Gets the day
     * @return the day of this date
     */
    public int getDay(){
        return _day;
    }

    //setters:
    /** 
     * Sets the year (only if date remains valid)
     * @param yearToSet the new year value
     */
    public void setYear(int yearToSet){
        _year = (isDateValid(yearToSet,_month,_year))? yearToSet : _year;
    }

    /** 
     * Sets the month (only if date remains valid)
     * @param monthToSet the new month value
     */
    public void setMonth(int monthToSet){
        _month = (isDateValid(_day,monthToSet,_year))?monthToSet : _month;
    }

    /** 
     * Sets the day (only if date remains valid)
     * @param dayToSet the new day value
     */
    public void setDay(int dayToSet){
        _day = (isDateValid(dayToSet, _month, _year))? dayToSet : _day;
    }

    //other methods:
    /**
     * Checks if two dates are the same
     * @param other the date to compare this date to
     * @return true if the dates are the same
     */
    public boolean equals (Date other) {
        return ((other._day == _day) && (other._month == _month) && (other._year == _year));
    }

    /**
     * Checks if this date comes before another date
     * @param other date to compare this date to
     * @return true if this date is before the other date
     */
    public boolean before (Date other) {
        if (other._year >= _year) {
            if (other._year > _year) {
                return true;
            }
            else if (other._month >= _month) {
                if (other._month > _month) {
                    return true;
                }
                else if (other._day >= _day) {
                    if (other._day > _day) {
                        return true;
                    } else return false;
                } else return false;
            } else return false;
        } else return false;
    }

    /**
     * Checks if this date comes after another date
     * @param other date to compare this date to
     * @return true if this date is after the other date
     */
    public boolean after (Date other) {
        return !before(other); 
    }

    // computes the day number since the beginning of the Christian counting of years 
    private int calculateDate ( int day, int month, int year)
    {
        if (month < 3) {
            year--;
            month = month + 12;
        } 
        return 365 * year + year/4 - year/100 + year/400 + ((month+1) * 306)/10 + (day - 62); 
    }

    /**
     * Calculates the difference in days between two dates
     * @param other the date to calculate the difference between
     * @return the number of days between the dates (non negative value)
     */
    public int difference (Date other) {
        int otherDays = calculateDate(other._day, other._month, other._year);
        int thisDays = calculateDate(_day, _month, _year);
        int difference = (otherDays - thisDays);
        return difference = (difference > 0)? difference : (-difference);
    }

    /**
     * Returns a new Date which is the current date after adding a number of years to it (the current date does not change)
     * @param num the number of years to add (a positive number)
     * @return the new date: this date plus num years

     */
    public Date addYearsToDate​(int num) {
        return this;
    }

    /**
     * Returns a String that represents this date
     * @override toString in class java.lang.Object
     * @return a String that represents this date in the following format: day (2 digits) / month(2 digits) / year (4 digits) for example: 02/03/1998
     */
    public String toString() {
        // boolean dayInTwoDigits = _day > 9;
        // boolean monthInTwoDigits = _month > 9;
        int option_1 = (_day > DAY_9)? 1 : 0;
        int option_2 = (_month > MONTH_9)? 2 : 0;
        int options = option_1 + option_2;
        switch(options) {
            case 0: return  "0" +_day + "/" + "0" + _month + "/" + _year;
            case 1: return   _day + "/" + "0" + _month + "/" + _year;
            case 2: return  "0" +_day + "/" + _month + "/" + _year;
            case 3: return  _day + "/" + _month + "/" + _year;
            default: return "01/01/2000";
        } 
    }
}