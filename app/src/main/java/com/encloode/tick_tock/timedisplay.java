package com.encloode.tick_tock;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class timedisplay extends AppCompatActivity {

    private int idOfCurrentEmp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timedisplay);

        TextView tx1 = (TextView) findViewById(R.id.timedisplayMessage);
        TextView tx2 = (TextView) findViewById(R.id.thankYouMessage);
        TextView tx3 = (TextView) findViewById(R.id.totalHours);
        TextView tx4 = (TextView) findViewById(R.id.hoursDescription);
        TextView clock = (TextView) findViewById(R.id.textClock);
        DateTime currentTime = new DateTime(); //this sets the currentTime object to the current time

        clock.setText(currentTime.toString("h:mm a"));

        idOfCurrentEmp= Integer.parseInt(getIntent().getExtras().getString("id"));

        // if false it means they are clocked out so we must clocked them in
        if(Global.accessDatabase().getEmployee(idOfCurrentEmp).isSignedIn() == false)  {

            Global.accessDatabase().setInTimeOf(idOfCurrentEmp,currentTime.getWeekOfWeekyear(), currentTime.getDayOfWeek(),currentTime);

            tx1.setText("CLOCKED IN AT:");
            tx2.setText("Thank you for logging in!");
        }

        else {

            Global.accessDatabase().setOutTimeOf(idOfCurrentEmp,currentTime.getWeekOfWeekyear(), currentTime.getDayOfWeek(),currentTime);

            tx1.setText("CLOCKED OUT AT:");
            tx2.setText("Thank you for logging out!");

            //calculating hours and minutes
            int minWorked = Global.accessDatabase().getMinutesWorkedFor(idOfCurrentEmp,currentTime.getWeekOfWeekyear(), currentTime.getDayOfWeek());
            int hoursWorked = minWorked/60;
            int minLeft = minWorked - (hoursWorked*60);

            //displaying information
            tx3.setText(hoursWorked + " Hours " + minLeft + "Minutes");
            tx4.setText("Total Hours Worked Today:");

            tx3.setVisibility(View.VISIBLE);
            tx4.setVisibility(View.VISIBLE);

        }
        new MyAsyncTask().execute();

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                transistionToHomeScreen();
            }
        }, 2000);
    }

    private void transistionToHomeScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity ( intent );

        TextView tx4 = (TextView) findViewById(R.id.hoursDescription);
        TextView tx3 = (TextView) findViewById(R.id.totalHours);

        tx4.setVisibility(View.INVISIBLE);
        tx3.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {  }

    @Override
    public void onPause() { //this runs when application exits the activity
        super.onPause();

    }

    class MyAsyncTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
                Global.saveState(timedisplay.this);  //save database
                return null;

        }
    }
}

