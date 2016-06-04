/**
 *
 * @author Riko Hamblin
 */
package com.encloode.tick_tock;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class EmployeeDatabase implements Serializable {

    public ArrayList<Employee> employees ;

    static public int maxEmployeeSize = 20;
    static public int listOfAvailableIDs[];

    public EmployeeDatabase() {

    }

    /**
     *  THIS METHOD TAKES A PIN AND CHECKS THAT IT DOESNT EXIST
     *  false = PIN IS TAKEN , true = PIN IS AVAILABLE
     */
      public boolean pinValid (int pin) {

        /*
            Go through entire database of employees if the
            pin is found then it is invalid
            if it IS found then pin is valid
        */



        for (int i=0; i<employees.size(); i++) {
           //!employees.get(i).isEmpty() &&
            if(employees.get(i).getPin()== pin )
                return false;


        }

        return true;



    }

    /**
     *  THIS METHOD TAKES AN ID AND CHECKS THAT IT DOESNT EXIST
     *  false = ID IS TAKEN , true = ID IS AVAILABLE
     */
    public boolean idValid (int id) {

        /*
            Go through entire database of employees if the
            pin is found then it is invalid
            if it IS found then pin is valid
        */


        for (int i=0; i<employees.size(); i++) {
            //!employees.get(i).isEmpty() &&
            if(employees.get(i).getID()== id )
                return false;

        }
        return true;



    }



    //THIS METHOD TAKES AN ID AND CHANGES THE NAME OF THAT EMPLOYEE TO String name
    public void setNameOf(int id, String name) {

                getEmployee(id).setName(name);

    }

    //THIS METHOD TAKES AN ID AND SETS THE PIN OF THAT EMPLOYEE TO int newPin
    public void setPinOf(int id, int newPin) {

        getEmployee(id).setPin(newPin);

    }

    //THIS METHOD TAKES AN ID AND RETURNS THE CURRENT NAME OF THAT EMPLOYEE
    public String getNameOf(int id) {


                return  getEmployee(id).getName();

    }

    //THIS METHOD TAKES AN ID AND RETURNS THE CURRENT PIN OF THAT EMPLOYEE
    public int getPinOf(int id) {

                return  getEmployee(id).getPin();

    }

    //THIS METHOD TAKES AN ID AND RETURNS:
    // WHETHER THAT EMPLOYEE IS SIGNED IN, true = yes they are, false = no they're not
    public boolean isSignedIn(int id) {
        return getEmployee(id).isSignedIn();
    }

    //THIS METHOD TAKES AN ID AND TOGGLES THE signedIn FIELD OF THAT EMPLOYEE
    public void toggleSignInFor (int id) {

        getEmployee(id).toggleSignIn();

    }

    //THIS METHOD TAKES AN ID AND RETURNS THE EMPLOYEE OBJECT ASSOCIATED WITH IT
    public Employee getEmployee(int id) {

        for (int i=0; i< employees.size() ; i++) {
            if( employees.get(i).getID() == id) {
                return employees.get(i);
            }

        }

        return null;
    }

    //THIS METHOD TAKES A pin AND RETURNS THE EMPLOYEE OBJECT ASSOCIATED WITH IT
    public Employee getEmployeebyPin(int pin) {

        for (int i=0; i< employees.size() ; i++) {
            if( employees.get(i).getPin() == pin) {
                return employees.get(i);
            }

        }

        return null;
    }

    //THIS METHOD ADDS AN EMPLOYEE TO THE DATABASE
    public boolean addEmployee(Employee e) {
        try {
            employees.add(e);
            return true;
        } catch (Exception ex) { return false ;}

    }

    //THIS METHOD DELETES AN EMPLOYEE TO THE DATABASE
    public boolean deleteEmployee(Employee e) {

        try {
            //this makes the deleted id available to be taken again
            EmployeeDatabase.listOfAvailableIDs[e.getID()] = e.getID();

            Employee.numOfEmployees -- ;
            employees.remove(Global.accessDatabase().getEmployee(e.getID()));
            return true;

        } catch (Exception ex) { return false ;}

    }

    //THIS METHOD RETURNS THE CURRENT AMOUNT OF EMPLOYEES
    static int getNumOfCurrentEmployees(){
        return Employee.numOfEmployees;
    }

    //THIS METHOD RETURNS THE ARRAY LIST CONTAINING ALL THE EMPLOYEES IN THE DATABASE
    public ArrayList<Employee> getEmployeeList(){

      return employees;
    }

    //added for timeSummary
    public void setInTimeOf(int id, int week, int day, DateTime time) {
    getEmployee(id).setInTime(week,day,time);
    }

    public void setOutTimeOf(int id, int week, int day, DateTime time) {
        getEmployee(id).setOutTime(week,day,time);
    }

    public void setMinutesWorkedFor(int id, int week, int day, int minsWorked) {
getEmployee(id).setMinutesWorkedInDay(week,day,minsWorked);
    }

    public DateTime getInTimeOf(int id, int week, int day, int signInPosition) {
        return getEmployee(id).getTimeSummary().inTime[week][day][signInPosition];
    }

    public DateTime getOutTimeOf(int id, int week, int day,int signOutPosition) {
        return getEmployee(id).getTimeSummary().outTime[week][day][signOutPosition];
    }

    public int getMinutesWorkedFor(int id, int week, int day) {
        return getEmployee(id).getTimeSummary().minutesWorked[week-1][day-1];
    }

    public void clearIn_Out_timesFor(int id, int week, int day){
        for(int i=0;i< getEmployee(id).numOfSignIn ;i++){
            getEmployee(id).setInTime(week,day,i,null);
            getEmployee(id).setOutTime(week,day,i,null);

        }
        getEmployee(id).numOfSignIn = 0;
        getEmployee(id).numOfSignOut = 0;
    }

    public void resetNumOfSignIn_Out(){
        for(Employee emp: employees){
            emp.numOfSignIn = 0;
            emp.numOfSignOut = 0;
        }

    }

}
