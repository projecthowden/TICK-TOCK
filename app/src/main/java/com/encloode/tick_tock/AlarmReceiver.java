package com.encloode.tick_tock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Created by Riko on 2016-06-13.
 */
public class AlarmReceiver extends BroadcastReceiver {

    static public int CLOCK_OUT_ALL = 0;
    static public int WIPE_DATABASE = 1;

    public void onReceive(Context context, Intent intent) {
        //this is what we want to happen once the broadcast has been sent from device


        //what we want to do is clock out all employees that are clocked in
        if(intent.getExtras().getInt("choice") == CLOCK_OUT_ALL)
            clockOutAllEmployees(context);

        else if (intent.getExtras().getInt("choice") == WIPE_DATABASE)
            wipeAndBackUpDatabase(context);

    }

    private void clockOutAllEmployees(Context context)  {

        DateTime now = new DateTime(); //get current time
        ArrayList<Employee> list = Global.accessDatabase().getEmployeeList();
        int count = 0;
        /*
         * we will go thru the entire list of employees
         * if they are signed in we will clock them out
         */

        for (Employee  emp : list)
            if(emp.isSignedIn()){
                emp.setOutTime(now.getWeekOfWeekyear(),now.getDayOfWeek(),now);
                count++;
            }

        Toast.makeText(context,"Automatically Clocked Out " + count + " Employees",Toast.LENGTH_LONG).show();
    }

    private void wipeAndBackUpDatabase(Context context){

        // save database and wipe
        new archiveDatabaseToExternalStorage().execute( );

        Toast.makeText(context,"System Wiped and archived",Toast.LENGTH_LONG).show();
    }

    public class archiveDatabaseToExternalStorage extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Global.archiveDatabaseToExternalStorage(); //save

            for (Employee emp : Global.accessDatabase().getEmployeeList()) {//wipe
                //  emp.setTimeSummary(new TimeSummary());//uncomment to enable cleaning of system
            }
            return null;
        }
    }
}
