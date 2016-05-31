package com.encloode.tick_tock;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;

import java.util.Calendar;
import java.util.Date;

public class timedisplay extends AppCompatActivity {

    private int idOfCurrentEmp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timedisplay);

        Handler mHandler = new Handler();
        TextView tx1 = (TextView) findViewById(R.id.timedisplayMessage);
        TextView tx2 = (TextView) findViewById(R.id.thankYouMessage);
        TextView tx3 = (TextView) findViewById(R.id.totalHours);
        TextView tx4 = (TextView) findViewById(R.id.hoursDescription);

        Calendar c = Calendar.getInstance();
        DateTime b = new DateTime(); //using this object now instead of 'c'
        int currentTime = (int) c.getTimeInMillis();
        Calendar currentTime1 = Calendar.getInstance();
        currentTime1.setTimeInMillis(currentTime);
        Date obj3 = currentTime1.getTime();
        Date obj = c.getTime();

        idOfCurrentEmp= Integer.parseInt(getIntent().getExtras().getString("id"));

        // if false it means they are clocked out so we must clocked them in
        if(Global.accessDatabase().getEmployee(idOfCurrentEmp).isSignedIn() == false)  {
            Global.accessDatabase().setInTimeOf(idOfCurrentEmp,b.getWeekOfWeekyear(), b.getDayOfWeek(),b);

            tx1.setText("CLOCKED IN AT:");
            tx2.setText("Thank you for logging in!");
        }

        else {

            Global.accessDatabase().setOutTimeOf(idOfCurrentEmp,b.getWeekOfWeekyear(), b.getDayOfWeek(),b);

            tx1.setText("CLOCKED OUT AT:");
            tx2.setText("Thank you for logging out!");

            int minWorked = Global.accessDatabase().getMinutesWorkedFor(idOfCurrentEmp,b.getWeekOfWeekyear(), b.getDayOfWeek());

            int hoursWorked = minWorked/60;
            int minLeft = minWorked - (hoursWorked*60);
            // idealy all this should be done using a tostring method of the joda time class.

            tx3.setText(hoursWorked + " Hours " + minLeft + "Minutes");

            tx4.setText("Total Hours Worked Today:");
            tx3.setVisibility(View.VISIBLE);
            tx4.setVisibility(View.VISIBLE);

        }

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

    public void onClickForTimeDisplay(View v) {
       // transistionToHomeScreen();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onPause() {
        super.onPause();
        Global.saveState(this);
    }
}

