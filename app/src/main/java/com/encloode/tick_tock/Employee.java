/**
 *
 * @author Riko Hamblin
 */
package com.encloode.tick_tock;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.Period;

import java.io.Serializable;
import java.util.Calendar ;
import java.util.Date;
import java.util.GregorianCalendar;

public class Employee implements Serializable {

    private String name;
    private int id;
    private int pin;
    private TimeSummary timeSummary;
    private boolean signedIn;
    private DateTime lastSignInDate; //this is to enable signout past midnight to work properly
  //  public int numOfSignIn;
 //   public int numOfSignOut;
    static int numOfEmployees;


    public Employee(String name, int pin) {

        numOfEmployees++;
        assignID();
        this.name = name;
        this.pin = pin;
        this.signedIn = false;
    //    numOfSignIn = 0;
    //    numOfSignOut = 0;
        timeSummary = new TimeSummary();

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public void setSignedIn(boolean isSignedIn) {
        this.signedIn = isSignedIn;
    }

    public String getName() {
        return name;
    }

    public int getPin() {
        return pin;
    }

    public int getID() {
        return id;
    }

    public DateTime getLastSignInDate() { return  lastSignInDate;}

    public boolean isSignedIn() {
        return signedIn;
    }

    public void toggleSignIn() {

        signedIn = !signedIn;

    }

    private void assignID() {
        /*
         * this generates a random integer between 0 and 99 inclusive
         * it then checks through all available IDs and if the id is available it is assigned
         * if it goes thru the entire array and the id is not found then the process is restarted
         */
        int rand = (int) (Math.random() * 100);

        loop:
        for (int i = 0; i < 100; i++) {
            if (EmployeeDatabase.listOfAvailableIDs[i] == rand) {
                this.id = rand;
                EmployeeDatabase.listOfAvailableIDs[i] = 1000;
                return;
            }
        }
        assignID();
    }

    public boolean isEmpty() {
        if (name == null)
            return true;
        else
            return false;
    }

    //these were added on may 26th to implement the new time summary class
    //employee
    public void setInTime(int week, int day, DateTime time) {
        int counterValue = timeSummary.getINCountValue(week,day);
        timeSummary.inTime[week - 1][day - 1][counterValue] = time;
        lastSignInDate = time.toDateTime();

        toggleSignIn();

       timeSummary.incrementINCounter(week,day);
      //  numOfSignIn++;

    }

    public void setInTime(int week, int day, int position, DateTime time) {
        timeSummary.inTime[week - 1][day - 1][position] = time;

    }

    public void setOutTime(int week, int day, DateTime time) {

        int counterOUTValue = timeSummary.getOUTCountValue(week, day);

        //check if the employee if clocking out on a different day
        if (lastSignInDate.getDayOfYear() != time.getDayOfYear()) { //if so then it means they stayed past midnight

            //so clock them out at 11:59:59 on previous day
            DateTime dayBeforeClockOut = time.minusDays(1).withTime(23,59,59,59); //creating object with date as date fore the clock out day

            //getting which week and day that object is
            int weekofDateBefore, dayofWeekOfDateBefore, counterOfDateBefore;
            weekofDateBefore = dayBeforeClockOut.getWeekOfWeekyear();
            dayofWeekOfDateBefore = dayBeforeClockOut.getDayOfWeek();

            //clock out
            counterOfDateBefore = timeSummary.getOUTCountValue(weekofDateBefore,dayofWeekOfDateBefore); //gets location of current slot to be filled
            timeSummary.outTime[weekofDateBefore-1][dayofWeekOfDateBefore-1][counterOfDateBefore] = dayBeforeClockOut; //fills slot

            toggleSignIn();
            timeSummary.incrementOUTCounter(weekofDateBefore,dayofWeekOfDateBefore);
            calculateTimeWorkedToday(weekofDateBefore,dayofWeekOfDateBefore); //computing hours

            //now clock in at midnight of current day then out at time entered
            //get time to midnight of current day
             DateTime midnightOfDay = time.withTime(0,0,1,0);

            //clock in
            setInTime(week,day,midnightOfDay);

            //clock out at time entered
            timeSummary.outTime[week-1][day-1][counterOUTValue] = time; //fills slot
            toggleSignIn();
            timeSummary.incrementOUTCounter(week,day);
            calculateTimeWorkedToday(week,day); //computing hours

        }

       else { //everything happened on the same day

        timeSummary.outTime[week - 1][day - 1][counterOUTValue] = time;
        toggleSignIn();
        timeSummary.incrementOUTCounter(week,day);
        calculateTimeWorkedToday(week, day);}

    }

    public void setOutTime(int week, int day, int position, DateTime time) {
        timeSummary.outTime[week - 1][day - 1][position] = time;
    }

    public void setMinutesWorkedInDay(int week, int day, int time) {
        timeSummary.minutesWorked[week - 1][day - 1] = time;
    }

    public DateTime getInTime(int week, int day, int signInPosition) {
        return timeSummary.inTime[week - 1][day - 1][signInPosition];
    }

    public DateTime getOutTime(int week, int day, int signOutPosition) {
        return timeSummary.outTime[week - 1][day - 1][signOutPosition];
    }

    public int getMinutesWorkedInDay(int week, int day) {
        return timeSummary.minutesWorked[week - 1][day - 1];
    }

    public TimeSummary getTimeSummary() {
        return timeSummary;
    }

    public void calculateTimeWorkedToday(int weekOfYear, int dayOfWeek) {

        int counterInValue = timeSummary.getINCountValue(weekOfYear,dayOfWeek);
        int counterOutValue = timeSummary.getOUTCountValue(weekOfYear, dayOfWeek);

        int timeWorked;
        DateTime start = timeSummary.inTime[weekOfYear - 1][dayOfWeek - 1][counterInValue - 1];
        DateTime end = timeSummary.outTime[weekOfYear - 1][dayOfWeek - 1][counterOutValue-1];

        //this returns period in minutes as an integer
        timeWorked = Minutes.minutesBetween(start,end).getMinutes();

        timeSummary.minutesWorked[weekOfYear - 1][dayOfWeek - 1] += timeWorked;
    }
}



