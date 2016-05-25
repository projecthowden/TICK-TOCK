/**
 *
 * @author Riko Hamblin
 */
package com.encloode.tick_tock;
import java.io.Serializable;
import java.util.Calendar ;

public class Employee implements Serializable {

    private    String name;
    private    int id;
    private    int pin;
    private    int daylyHours [][];
    private    boolean signedIn;
    private    int signInTime;
    private    int signOutTime;
    static int numOfEmployees = 0;



    public Employee(String name, int pin) {

        numOfEmployees++;
         assignID();
       // id = numOfEmployees;
        this.name = name;
        this.pin = pin;
        this.signedIn = false;

        daylyHours = new int[52][7];

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

    //this method most likely will not be used however it is available if need be
    public void setDaylyHours(int[][] weeklyHours) {
        this.daylyHours = weeklyHours;
    }

    public void setSignedIn(boolean isSignedIn) {
        this.signedIn = isSignedIn;
    }

    public void setSignInTime(int signInTime) {
        this.signInTime = signInTime;
        toggleSignIn();
    }

    public void setSignOutTime(int signOutTime) {
        this.signOutTime = signOutTime;
        toggleSignIn();
        int weekOfYear = Calendar.WEEK_OF_YEAR;
        int dayOfWeek = Calendar.DAY_OF_WEEK;

        calculateHoursWorkedToday(weekOfYear,dayOfWeek);
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

    //this method most likely will not be used however it is available if need be
    public int[][] getDaylyHours() {
        return daylyHours;
    }

    public boolean isSignedIn() {
        return signedIn;
    }

    public int getSignInTime() {
        return signInTime;
    }

    public int getSignOutTime() {
        return signOutTime;
    }

    public int getHoursWorkedToday() {

        int weekOfYear = Calendar.WEEK_OF_YEAR;
        int dayOfWeek = Calendar.DAY_OF_WEEK;

        return daylyHours[weekOfYear-1][dayOfWeek-1] ;


    }

    public void calculateHoursWorkedToday (int weekOfYear, int dayOfWeek) {

        int temp;
        int hoursWorked;
        int numOfMilliSecsInHour = 3600000;

        temp = signOutTime - signInTime;
        hoursWorked = temp / numOfMilliSecsInHour ;

        daylyHours [weekOfYear-1][dayOfWeek-1] = daylyHours [weekOfYear-1][dayOfWeek-1]
                + hoursWorked;

       // return hoursWorked;
    }

    public int getHoursWorkedForWeek (int weekWanted) {
        int sum=0;
        for (int i=0; i<7; i++) {
            sum = sum + daylyHours[weekWanted-1][i] ;
        }

        return sum;
    }

    public void toggleSignIn () {

        signedIn = !signedIn;
    }

    private void assignID() {
        /*
         * this generates a random integer between 0 and 99 inclusive
         * it then checks through all available IDs and if the id is available it is assigned
         * if it goes thru the entire array and the id is not found then the process is restarted
         */
        int rand = (int)(Math.random() * 100) ;

       loop: for(int i=0; i<100; i++) {
            if(EmployeeDatabase.listOfAvailableIDs[i] == rand) {
                this.id = rand;
                EmployeeDatabase.listOfAvailableIDs[i] = 1000;
                return;
            }

        }
            assignID();

    }

    public boolean isEmpty() {
        if (name==null)
            return true;
        else
            return false;
    }
}
