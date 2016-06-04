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
    public int numOfSignIn;
    public int numOfSignOut;
    static int numOfEmployees;


    public Employee(String name, int pin) {

        numOfEmployees++;
        assignID();
        this.name = name;
        this.pin = pin;
        this.signedIn = false;
        numOfSignIn = 0;
        numOfSignOut = 0;
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
        timeSummary.inTime[week - 1][day - 1][numOfSignIn] = time;

        toggleSignIn();
        numOfSignIn++;

    }

    public void setInTime(int week, int day, int position, DateTime time) {
        timeSummary.inTime[week - 1][day - 1][position] = time;

    }

    public void setOutTime(int week, int day, DateTime time) {
        timeSummary.outTime[week - 1][day - 1][numOfSignOut] = time;

        toggleSignIn();

        numOfSignOut++;
        calculateTimeWorkedToday(week, day);

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

        int timeWorked;
        DateTime start = timeSummary.inTime[weekOfYear - 1][dayOfWeek - 1][numOfSignIn - 1];
        DateTime end = timeSummary.outTime[weekOfYear - 1][dayOfWeek - 1][numOfSignOut-1];

        //this returns period in minutes as an integer
        timeWorked = Minutes.minutesBetween(start,end).getMinutes();

        timeSummary.minutesWorked[weekOfYear - 1][dayOfWeek - 1] += timeWorked;
    }
}



