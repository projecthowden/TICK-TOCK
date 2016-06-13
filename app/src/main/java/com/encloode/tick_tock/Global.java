package com.encloode.tick_tock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Riko Hamblin on 05/20/16.
 */
public class Global  extends AppCompatActivity  implements Serializable {

    static private EmployeeDatabase empDatabase;
    static public int masterCode = 1694;
    static public String masterString = "encloode22";
    static public AlarmManager manager;
    static public PendingIntent pendingIntent ;

    static String fileName = "c3.dat";

    static public EmployeeDatabase accessDatabase ()    {

       return empDatabase ;
    }

    static public void setDatabase (EmployeeDatabase empDatabaseSent)    {

       empDatabase = empDatabaseSent ;
    }

    static public void saveState(Context context) {

        try {
            File internalDatabaseFile = new File(context.getFilesDir(), File.separator +fileName);

            // File tempDatabaseFile = new File(context.getFilesDir(), File.separator +"temp.dat");
            // if(!tempDatabaseFile.exists()) tempDatabaseFile.createNewFile();
               FileOutputStream outStream =  new FileOutputStream(internalDatabaseFile);
            //FileOutputStream outStream =  new FileOutputStream(tempDatabaseFile);
            ObjectOutputStream objectOutStream = new ObjectOutputStream(outStream);

            objectOutStream.writeObject(Global.empDatabase);
            objectOutStream.close();
            outStream.close();

            //    Path from = tempDatabaseFile.
        }
        catch (FileNotFoundException e1) {}
        catch (IOException e1) {}


        //save also to SD card
        saveDatabaseToSDCard(context);
    }

    static public void saveDatabaseToSDCard (Context context) {

        File newFolder = null;
        File externalDatabaseFile = null;
        try {
            newFolder = new File(Environment.getExternalStorageDirectory(),"hey");
          //  if (!newFolder.exists())
               if( newFolder.mkdirs()){

            Toast m = Toast.makeText( context, "fdgfdf", Toast.LENGTH_LONG);
               m.show();

               }
           // System.out.println("fsf");

            try {
                externalDatabaseFile = new File(newFolder, "database_BACKUP.dat");
              //  if(!externalDatabaseFile.exists()) externalDatabaseFile.createNewFile();
            } catch (Exception ex) {
             //   System.out.println("ex: " + ex);
            }
        } catch (Exception e) {
          //  System.out.println("e: " + e);
        }
/*
        try {
            FileOutputStream outStream =  new FileOutputStream(externalDatabaseFile);
            ObjectOutputStream objectOutStream = new ObjectOutputStream(outStream);

            objectOutStream.writeObject(Global.empDatabase);
            objectOutStream.close();
            outStream.close();
        }
        catch (FileNotFoundException e1) {}
        catch (IOException e1) {}*/
    }

    /*static public void loadDatabaseFromSDCard() throws IOException {
        EmployeeDatabase employeeDatabaseFromFile = null;
        FileInputStream inStream;
        ObjectInputStream is;

        File newFolder = null;
        File externalDatabaseFile = null;
        //create files if none exist
        try {
            newFolder = new File(Environment.getExternalStorageDirectory(), "ARCHIVE");
            if (!newFolder.exists()) newFolder.mkdir();


            try {
                externalDatabaseFile = new File(newFolder, "database_BACKUP.dat");
                if(!externalDatabaseFile.exists()) externalDatabaseFile.createNewFile();
            } catch (Exception ex) {
                System.out.println("ex: " + ex);
            }
        } catch (Exception e) {}


        //load database or create new one
        try {
            inStream = new FileInputStream(externalDatabaseFile); //allow for reading of a file
            is = new ObjectInputStream(inStream); // allow for reading of a object from a file
            employeeDatabaseFromFile = ((EmployeeDatabase) is.readObject()); //gets object and typecast it
            inStream.close(); // close streams
            is.close();

        }
        catch (EOFException ex){}


        catch (IOException e1) { //if no file is found then create and initialize a new file

            externalDatabaseFile.createNewFile();
            Global.empDatabase = new EmployeeDatabase();
            Global.empDatabase.employees = new ArrayList<>();
            Global.accessDatabase().autoBackUpDate = new DateTime().withDate(2016,1,1).toDateTime(); //defualt jan 1
            Global.accessDatabase().autoClockOutTime = new DateTime().withTime(3,0,0,0).toDateTime(); // default 3am


            Employee.numOfEmployees = 0;
            EmployeeDatabase.listOfAvailableIDs = new int[100];

            for(int i=0; i<100;i++)
                EmployeeDatabase.listOfAvailableIDs[i] = i;

            e1.printStackTrace();
        }

        catch (ClassNotFoundException e1) {    }

        if (employeeDatabaseFromFile != null) // if object is found deserialize
            deserialize(employeeDatabaseFromFile);
        else { //else  create and initialize a new file
            Global.empDatabase = new EmployeeDatabase();
            Global.empDatabase.employees = new ArrayList<>();

            Employee.numOfEmployees = 0;

            EmployeeDatabase.listOfAvailableIDs = new int[100];

            for(int i=0; i<100;i++)
                EmployeeDatabase.listOfAvailableIDs[i] = i;

            Global.accessDatabase().autoBackUpDate = new DateTime().withDate(2016,1,1).toDateTime();
            Global.accessDatabase().autoClockOutTime = new DateTime().withTime(3,0,0,0).toDateTime();

        }

    }*/

    static public void loadState(Context context) throws IOException {
        EmployeeDatabase employeeDatabaseFromFile = null;
        File internalDatabaseFile=null;
        FileInputStream inStream;
        ObjectInputStream is;
      //  FileInputStream inStreamExt = null;

        try {
            internalDatabaseFile = new File(context.getFilesDir(),File.separator+fileName); //get file
            inStream = new FileInputStream(internalDatabaseFile); //allow for reading of a file
            is = new ObjectInputStream(inStream); // allow for reading of a object from a file
            employeeDatabaseFromFile = ((EmployeeDatabase) is.readObject()); //gets object and typecast it
            inStream.close(); // close streams
            is.close();

        }
        catch (EOFException ex){}
        catch (IOException e1) { //if no file is found then create and initialize a new file

            internalDatabaseFile.createNewFile();
            createNewDatabase(context);
            addEmployees();

            e1.printStackTrace();
        }

        catch (ClassNotFoundException e1) {    }

        if (employeeDatabaseFromFile != null) // if object is found deserialize
            deserialize(context,employeeDatabaseFromFile);
        else {
            //else  create and initialize a new file and add employees
            createNewDatabase(context);
            addEmployees();

        }

    }
    static private void createNewDatabase (Context context) {

        Global.empDatabase = new EmployeeDatabase();
        Global.empDatabase.employees = new ArrayList<>();

        Employee.numOfEmployees = 0;
        EmployeeDatabase.listOfAvailableIDs = new int[100];

        Global.accessDatabase().setAutoBackUpDate(
                    new DateTime()
                            .withDate(2016,1,1)
                            .withTime(3, 2, 0, 0 )
                            .toDateTime()
        );

        Global.accessDatabase().setAutoClockOutTime(
                new DateTime().withTime(3,0,0,0).toDateTime()
        );

        for(int i=0; i<100;i++)
            EmployeeDatabase.listOfAvailableIDs[i] = i;

        setClockOutAlarm(context, Global.accessDatabase().getAutoClockOutTime()); //sets default time to 3am
        setDateToWipeDatabaseAlarm(context,Global.accessDatabase().getAutoBackUpDate());
    }
    static  private void deserialize(Context context, EmployeeDatabase temp){

        Global.empDatabase = new EmployeeDatabase();
        Global.empDatabase.employees = new ArrayList<>();
        EmployeeDatabase.listOfAvailableIDs = new int[100];

        Global.empDatabase = temp;
        if(temp.getEmployeeList().size()>0 && Global.empDatabase.employees.size()>0) { //if size is more than zero then set every employee to that of the one from the file

            for(int i=0;i<temp.getEmployeeList().size();i++)
                Global.empDatabase.getEmployeeList().set(i,temp.getEmployeeList().get(i));

            for(int i=0; i<100;i++)
                EmployeeDatabase.listOfAvailableIDs[i] = i;

            for(int i=0; i<temp.getEmployeeList().size();i++) //this re-establishes the taken ids
                for (int j=0; j<100;j++ )
                    if (temp.getEmployeeList().get(i).getID() == EmployeeDatabase.listOfAvailableIDs[j])
                        EmployeeDatabase.listOfAvailableIDs[j] = 1000;
        }

        Employee.numOfEmployees = temp.getEmployeeList().size(); //this re assigns the number of employees in system
        Global.accessDatabase().setAutoBackUpDate(
                temp.getAutoBackUpDate().toDateTime()
        );

        Global.accessDatabase().setAutoClockOutTime(
                temp.getAutoClockOutTime().toDateTime()
        );

        setClockOutAlarm(context,temp.getAutoClockOutTime().toDateTime());
        setDateToWipeDatabaseAlarm(context,temp.getAutoBackUpDate());
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    static public void setClockOutTime (Context context, DateTime time){
        accessDatabase().setAutoClockOutTime(time);
        setClockOutAlarm(context,time);
    }
    static public void setDateToWipeDatabase (Context context, DateTime time){
        accessDatabase().setAutoBackUpDate( time );
        setDateToWipeDatabaseAlarm(context,time);
    }

    static private void setClockOutAlarm(Context context, DateTime time) {

        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        alarmIntent.putExtra("choice",AlarmReceiver.CLOCK_OUT_ALL);

        pendingIntent = PendingIntent.getBroadcast(context, AlarmReceiver.CLOCK_OUT_ALL, alarmIntent, 0);
        manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        int interval = Seconds.secondsBetween(time, time.plusDays(1)).getSeconds() * 1000 ; //MILLIS * SECS * MINUTE* HOUR = ONCE A DAY

        //if time set has past already set changes to occur in future
        //this ensures if they set a time that has gone this doesnt just automatically clock out everyone
        //else we use whatever time was chosen i.e. do nothing
        if(time.getMillis() < new DateTime().getMillis())  time = time.plusDays(1);
          else {}


        manager.setRepeating(AlarmManager.RTC_WAKEUP,time.getMillis(),interval,pendingIntent);
    }
    static private void setDateToWipeDatabaseAlarm(Context context, DateTime time) {

        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        alarmIntent.putExtra("choice",AlarmReceiver.WIPE_DATABASE);

        pendingIntent = PendingIntent.getBroadcast(context, AlarmReceiver.WIPE_DATABASE, alarmIntent, 0);

        manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        int interval = 1000 * 30;  // Seconds.secondsBetween(time,time.plusYears(1)).getSeconds() * 1000;

        //if time set has past already set changes to occur in future
        //this ensures if they set a time that has gone this doesnt just automatically backUp
        //else we use whatever time was chosen i.e. do nothing
         if(time.getMillis() < new DateTime().getMillis()) time = time.minus(1);  //time = time.plusYears(1);
         else {}

        manager.setRepeating(AlarmManager.RTC_WAKEUP,time.getMillis(),interval,pendingIntent);
    }

    static public void addEmployees(){
        //12
        Global.accessDatabase().addEmployee(new Employee("Phyllis Kamil ", 1111));
        Global.accessDatabase().addEmployee(new Employee("Kathi Soledad", 1112));
        Global.accessDatabase().addEmployee(new Employee("Fabiola Olympias", 1113));
        Global.accessDatabase().addEmployee(new Employee("Riko Hamblin", 1114));
        Global.accessDatabase().addEmployee(new Employee("Ludmilla Doran", 1115 ));
        Global.accessDatabase().addEmployee(new Employee("Agostina Willihard", 1116));
        Global.accessDatabase().addEmployee(new Employee("Larunda Jehu", 1117));
        Global.accessDatabase().addEmployee(new Employee("Sheine Fausta", 1118));
        Global.accessDatabase().addEmployee(new Employee("Elham Gilbert", 1119));
        Global.accessDatabase().addEmployee(new Employee("Hurik Engel", 1121));
        Global.accessDatabase().addEmployee(new Employee("Gracelyn Ceridwen", 1122));
        Global.accessDatabase().addEmployee(new Employee("Ryuu Zuzen", 1123));
        //12
        Global.accessDatabase().addEmployee(new Employee("Kirill Ramaz", 2222));
        Global.accessDatabase().addEmployee(new Employee("Gallus Margarida", 2223));
        Global.accessDatabase().addEmployee(new Employee("Agnese Kirsteen", 2224));
        Global.accessDatabase().addEmployee(new Employee("Elspeth Law", 2225));
        Global.accessDatabase().addEmployee(new Employee("Maalik Nicoline", 2226));
        Global.accessDatabase().addEmployee(new Employee("Phoibe Azra", 2227));
        Global.accessDatabase().addEmployee(new Employee("Staffan Estinne", 2228));
        Global.accessDatabase().addEmployee(new Employee("Benj Tamara", 2229));
        Global.accessDatabase().addEmployee(new Employee("Lexus Anton", 2231));
        Global.accessDatabase().addEmployee(new Employee("Caroline Ahmose", 2232));
        Global.accessDatabase().addEmployee(new Employee("Collyn Lalla", 2233));
        Global.accessDatabase().addEmployee(new Employee("Hercules Mechislav", 2234));
        //12
        Global.accessDatabase().addEmployee(new Employee("Mazin Sebastjan", 3333));
        Global.accessDatabase().addEmployee(new Employee("Britta Anthousa", 3334));
        Global.accessDatabase().addEmployee(new Employee("Madalena Liberia", 3335));
        Global.accessDatabase().addEmployee(new Employee("Patrizia Asar", 3336));
        Global.accessDatabase().addEmployee(new Employee("Theothelm Drest", 3337));
        Global.accessDatabase().addEmployee(new Employee("Tayeb Hanif", 3338));
        Global.accessDatabase().addEmployee(new Employee("Yvonne Brígida", 3339));
        Global.accessDatabase().addEmployee(new Employee("Xavier Terese", 3341));
        Global.accessDatabase().addEmployee(new Employee("Mervyn Cleveland", 3342));
        Global.accessDatabase().addEmployee(new Employee("Endymion Spartak", 3343));
        Global.accessDatabase().addEmployee(new Employee("Bruce Fotis", 3344));
        Global.accessDatabase().addEmployee(new Employee("Luc Sopheap", 3345));

        addTimesForMay();
        addTimesForApril();
    }
    static public void addTimesForMay (){

        int dayOfMay=1;
        Employee emp;
        DateTime may = (new DateTime()).withDate(2016,5,dayOfMay);
        int randEmployee;
        int randDay;
        int randHour;
        int randMin;
        int randHourThatHasPassed;
        int randMinThatHasPassed;

        for (int i = 0; i < 51; i++) {
            randEmployee = (int) (Math.random() * 35);
            emp = accessDatabase().getEmployeeList().get(randEmployee); //gets a random employee

            randDay = (int) (Math.random() * 28);
            DateTime randDayInMay = may.withDayOfMonth(randDay+1); //gets a random day in may

            randHour = ((int) (Math.random() * 9)) + 1;
            randMin = ((int) (Math.random() * 58)) + 1;
            DateTime clockIn = randDayInMay.withTime(randHour,randMin,0,0);//gets a random time on that day from midnight to 9am

            randHourThatHasPassed = ((int) (Math.random() * 8)) + 1;
            randMinThatHasPassed = ((int) (Math.random() * 57)) + 1;
            DateTime clockOut = clockIn.plusHours(randHourThatHasPassed);
            clockOut = clockOut.plusMinutes(randMinThatHasPassed);  //adds a random amount of time to clock in

            //clock in and out
            emp.setInTime(clockIn.getWeekOfWeekyear(),clockIn.getDayOfWeek(),clockIn);
            emp.setOutTime(clockOut.getWeekOfWeekyear(),clockOut.getDayOfWeek(),clockOut);

        }
    }
    static public void addTimesForApril (){

        int dayOfApril=1;
        Employee emp;
        DateTime june = (new DateTime()).withDate(2016,4,dayOfApril);
        int randEmployee;
        int randDay;
        int randHour;
        int randMin;
        int randHourThatHasPassed;
        int randMinThatHasPassed;

        for (int i = 0; i < 51; i++) {
            randEmployee = (int) (Math.random() * 35);
            emp = accessDatabase().getEmployeeList().get(randEmployee); //gets a random employee

            randDay = (int) (Math.random() * 28);
            DateTime randDayInApril = june.withDayOfMonth(randDay+1); //gets a random day in june

            randHour = ((int) (Math.random() * 9)) + 1;
            randMin = ((int) (Math.random() * 58)) + 1;
            DateTime clockIn = randDayInApril.withTime(randHour,randMin,0,0);//gets a random time on that day from midnight to 9am

            randHourThatHasPassed = ((int) (Math.random() * 8)) + 1;
            randMinThatHasPassed = ((int) (Math.random() * 57)) + 1;
            DateTime clockOut = clockIn.plusHours(randHourThatHasPassed);
            clockOut = clockOut.plusMinutes(randMinThatHasPassed);  //adds a random amount of time to clock in

            //clock in and out
            emp.setInTime(clockIn.getWeekOfWeekyear(),clockIn.getDayOfWeek(),clockIn);
            emp.setOutTime(clockOut.getWeekOfWeekyear(),clockOut.getDayOfWeek(),clockOut);

        }
    }
    static public void clockIn8People () {

        for (int i = 0; i < 9; i++) {
            int randEmployee = (int) (Math.random() * 35);

            Employee emp = accessDatabase().getEmployeeList().get(randEmployee); //gets a random employee

            if(emp.isSignedIn()) break;

            DateTime now = new DateTime();
            emp.setInTime(now.getWeekOfWeekyear(),now.getDayOfWeek(),now);


        }
    }


}
