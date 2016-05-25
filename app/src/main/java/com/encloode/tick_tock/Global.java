package com.encloode.tick_tock;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Riko Hamblin on 05/20/16.
 */
public class Global implements Serializable{

    static private EmployeeDatabase empDatabase;
    static public int masterCode = 1234;
    static File internalDatabaseFile= null;
    //static FILE externalDatabaseFile = null;

    static public EmployeeDatabase accessDatabase ()    {

       return empDatabase ;
    }

    static public void setDatabase (EmployeeDatabase empDatabaseSent)    {

       empDatabase = empDatabaseSent ;
    }


    static public void saveState(Context context) {
        FileOutputStream outStream = null;
        try {
            File internalDatabaseFile = new File(context.getFilesDir(), File.separator +"EmployeeDatabase.dat");
            outStream =  new FileOutputStream(internalDatabaseFile);
            ObjectOutputStream objectOutStream = new ObjectOutputStream(outStream);


            objectOutStream.writeObject(Global.accessDatabase());
            objectOutStream.close();
        }
        catch (FileNotFoundException e1) {}
        catch (IOException e1) {}

    }

    static public void loadState(Context context) throws IOException {
        EmployeeDatabase s = null;
        FileInputStream inStream = null;
        FileInputStream inStreamExt = null;

        try {
            if (internalDatabaseFile == null) {
            /*
            * nested if statement: if internal is null check if external != null
            * if thats true then load it to the internal
             */

                //UNCOMMENT TO SAVE TO EXTERNAL DRIVE
                //  externalDatabaseFile = new File(Environment.getExternalStorageDirectory(), File.separator + "BACKUP_employeeDatabase.dat");
                internalDatabaseFile = new File(context.getFilesDir(), File.separator + "employeeDatabase.dat");

            }
            inStream = new FileInputStream(internalDatabaseFile);
            ObjectInputStream objectInStream = new ObjectInputStream(inStream);

            s = ((EmployeeDatabase) objectInStream.readObject());
            objectInStream.close();


        }
        catch (IOException e1) {
            internalDatabaseFile.createNewFile();
            Global.setDatabase(new EmployeeDatabase());
        }

        catch (ClassNotFoundException e1) {
            internalDatabaseFile.createNewFile();
            Global.setDatabase(new EmployeeDatabase());
        }

        if (s != null)
            Global.setDatabase(s);
        else
            Global.setDatabase(new EmployeeDatabase());

    }

}
