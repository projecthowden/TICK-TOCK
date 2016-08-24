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


        //back up
        backupCurrentDatabaseToExternalStorage();

    }

    static public void backupCurrentDatabaseToExternalStorage() {

        File newFolder = null;
        File externalDatabaseFile = null;
        try {
            newFolder = new File(Environment.getExternalStorageDirectory(),"DATABASE_BACKUP_TICK_TOCK");
            newFolder.mkdirs();

            try {
                externalDatabaseFile = new File(newFolder, "database_BACKUP.dat");
                if(!externalDatabaseFile.exists()) externalDatabaseFile.createNewFile();
            } catch (Exception ex) {}

        } catch (Exception e) {     }

        try {
            FileOutputStream outStream =  new FileOutputStream(externalDatabaseFile);
            ObjectOutputStream objectOutStream = new ObjectOutputStream(outStream);

            objectOutStream.writeObject(Global.empDatabase);
            objectOutStream.close();
            outStream.close();
        }
        catch (FileNotFoundException e1) {}
        catch (IOException e1) {}


    }
    static public String archiveDatabaseToExternalStorage() {

        File newFolder = null;
        File archivedDatabaseFile = null;
        String archiveFileName = null;
        try {
                newFolder = new File(Environment.getExternalStorageDirectory(),"ARCHIVE_TICK_TOCK");
                newFolder.mkdirs();
            } catch (Exception ex) {}

        //make string name Jan2015_to_Jan2016
        archiveFileName = Global.accessDatabase().getAutoBackUpDate().toString("MMMyyy")
                            + "_to_"
                            + Global.accessDatabase().getAutoBackUpDate().toString("MMM")
                            + (Global.accessDatabase().getAutoBackUpDate().getYear()+1);

        try {
                archivedDatabaseFile = new File(newFolder, archiveFileName + ".dat");
                if(!archivedDatabaseFile.exists()) archivedDatabaseFile.createNewFile();
            }   catch (Exception ex) {}

        //make string name

        try {
            FileOutputStream outStream =  new FileOutputStream(archivedDatabaseFile);
            ObjectOutputStream objectOutStream = new ObjectOutputStream(outStream);

            objectOutStream.writeObject(Global.empDatabase);
            objectOutStream.close();
            outStream.close();
        }
        catch (FileNotFoundException e1) {}
        catch (IOException e1) {}

        return archivedDatabaseFile.getAbsolutePath();
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

            e1.printStackTrace();
        }

        catch (ClassNotFoundException e1) {    }

        if (employeeDatabaseFromFile != null) // if object is found deserialize
            deserialize(context,employeeDatabaseFromFile);
        else {
            //else  create and initialize a new file and add employees
            createNewDatabase(context);

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

        int interval = Seconds.secondsBetween(time,time.plusYears(1)).getSeconds() * 1000;

        //if time set has past already set changes to occur in future
        //this ensures if they set a time that has gone this doesnt just automatically backUp
        //else we use whatever time was chosen i.e. do nothing
         if(time.getMillis() < new DateTime().getMillis()) time = time.minus(1);  //time = time.plusYears(1);
         else {}

        manager.setRepeating(AlarmManager.RTC_WAKEUP,time.getMillis(),interval,pendingIntent);
    }



}
