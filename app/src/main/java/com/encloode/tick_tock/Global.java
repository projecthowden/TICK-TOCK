package com.encloode.tick_tock;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.View;

import org.joda.time.DateTime;

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
public class Global implements Serializable{

    static public EmployeeDatabase empDatabase;
    static public int masterCode = 1234;
    static public String masterString = "AsDf1";
    static public DateTime autoClockOutTime;
    static public DateTime autoBackUpDate;

    static String fileName = "beta_7.dat";

    static public EmployeeDatabase accessDatabase ()    {

       return empDatabase ;
    }

    static public void setDatabase (EmployeeDatabase empDatabaseSent)    {

       empDatabase = empDatabaseSent ;
    }


    static public void saveState(Context context) {

        try {
            File internalDatabaseFile = new File(context.getFilesDir(), File.separator +fileName);
            FileOutputStream outStream =  new FileOutputStream(internalDatabaseFile);
            ObjectOutputStream objectOutStream = new ObjectOutputStream(outStream);

            objectOutStream.writeObject(Global.empDatabase);
            objectOutStream.close();
            outStream.close();
        }
        catch (FileNotFoundException e1) {}
        catch (IOException e1) {}

    }

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
            Global.empDatabase = new EmployeeDatabase();
            Global.empDatabase.employees = new ArrayList<>();

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

        }

    }

    static  private void deserialize(EmployeeDatabase temp){

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
    }

}
