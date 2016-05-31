package com.encloode.tick_tock;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.View;

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
            internalDatabaseFile = new File(context.getFilesDir(),File.separator+fileName);
            inStream = new FileInputStream(internalDatabaseFile);
            is = new ObjectInputStream(inStream);
            employeeDatabaseFromFile = ((EmployeeDatabase) is.readObject());
            inStream.close();
            is.close();

        }
        catch (EOFException ex){}
        catch (IOException e1) {
            internalDatabaseFile.createNewFile();
           // Global.setDatabase(new EmployeeDatabase());

            Global.empDatabase = new EmployeeDatabase();

            Global.empDatabase.employees = new ArrayList<>();
          //  Global.accessDatabase().employees = new ArrayList<>();

            Employee.numOfEmployees = 0;

            EmployeeDatabase.listOfAvailableIDs = new int[100];

            for(int i=0; i<100;i++)
                EmployeeDatabase.listOfAvailableIDs[i] = i;

            e1.printStackTrace();

        }

        catch (ClassNotFoundException e1) {    }

        if (employeeDatabaseFromFile != null)
            deserialize(employeeDatabaseFromFile);
        else {
         //   Global.setDatabase(new EmployeeDatabase());

             Global.empDatabase = new EmployeeDatabase();

            Global.empDatabase.employees = new ArrayList<>();
           // Global.accessDatabase().employees = new ArrayList<>();

            Employee.numOfEmployees = 0;

            EmployeeDatabase.listOfAvailableIDs = new int[100];

            for(int i=0; i<100;i++)
                EmployeeDatabase.listOfAvailableIDs[i] = i;

        }

    }

    static  private void deserialize(EmployeeDatabase temp){

        Global.empDatabase = new EmployeeDatabase();

        Global.empDatabase.employees = new ArrayList<>();
      //  Global.accessDatabase().employees = new ArrayList<>();
        EmployeeDatabase.listOfAvailableIDs = new int[100];
       // Global.setDatabase(temp);
        Global.empDatabase = temp;
        if(temp.getEmployeeList().size()>0 && Global.empDatabase.employees.size()>0) {
            for(int i=0;i<temp.getEmployeeList().size();i++) {
                Global.empDatabase.getEmployeeList().set(i,temp.getEmployeeList().get(i));
            }
            for(int i=0; i<100;i++) {
                EmployeeDatabase.listOfAvailableIDs[i] = i;
            }

            for(int i=0; i<temp.getEmployeeList().size();i++)
                for (int j=0; j<100;j++ ){
                    if (temp.getEmployeeList().get(i).getID() == EmployeeDatabase.listOfAvailableIDs[j])
                        EmployeeDatabase.listOfAvailableIDs[j] = 1000;
                }

        }
        Employee.numOfEmployees = temp.getEmployeeList().size();
    }

}
