package com.encloode.tick_tock;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ViewUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.Period;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class displayTotalTimeWorked extends AppCompatActivity {

    private int daysSelected;
    private DateTime startDate ;
    private DateTime endDate ;
    private String nameChosen;
    private int employeeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displaytotaltime_one);

        CalendarView startDateCalendar = (CalendarView) findViewById(R.id.displaytotaltime_one_calendarStart);
        CalendarView endDateCalendar   = (CalendarView) findViewById(R.id.displaytotaltime_one_calendarEnd);

        final Calendar start = Calendar.getInstance();
        final Calendar end = Calendar.getInstance();
        startDateCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                start.set(Calendar.YEAR, year);
                start.set(Calendar.MONTH, month);
                start.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                startDate = new DateTime(start.getTimeInMillis());

                TextView startDateText = (TextView) findViewById(R.id.displaytotaltime_one_TV_startDate);
                startDateText.setText(dayOfMonth + "/" + (month+1) +"/"+year);

                printNumDays();


            }
        });

        endDateCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                end.set(Calendar.YEAR, year);
                end.set(Calendar.MONTH, month);
                end.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                endDate = new DateTime(end.getTimeInMillis());

                TextView endDateText = (TextView) findViewById(R.id.displaytotaltime_one_TV_endDate);
                endDateText.setText(dayOfMonth + "/" + (month+1) +"/"+year);

               printNumDays();
            }
        });


   }

    public boolean printNumDays() {

        TextView numDaysSelected = (TextView) findViewById(R.id.displaytotaltime_one_TV_numberOfDays);
        TextView tx = (TextView) findViewById(R.id.text);

        if (startDate != null && endDate != null){
        if (startDate.isBefore(endDate) || startDate.isEqual(endDate)) {

            tx.setVisibility(View.VISIBLE);
            numDaysSelected.setTextColor(Color.BLACK);
            daysSelected = Days.daysBetween(startDate, endDate).getDays()+1;

            if (daysSelected >= 0) numDaysSelected.setText("" + daysSelected);
            return true;

        }
        else
        {
            numDaysSelected.setText("Choose an End date that is after or on Start date");
            numDaysSelected.setTextColor(Color.RED);

            tx.setVisibility(View.INVISIBLE);


        }

    }
        return false;
    }
    //fucntions to handle layout changes in forward progression
    public void onClickNextTo_2(View view){
        if(printNumDays()) {
            setContentView(R.layout.displaytotaltime_two);
            logicForScreen2();
        }
        else {
            Toast myToast = Toast.makeText(getApplicationContext(), "Choose an End date that is after the Start Date", Toast.LENGTH_LONG);
            myToast.show();
        }
    }
    public void onClickNextTo_3(View view){
        TextView tx = (TextView) findViewById(R.id.displaytotaltime_two_TV_nameOfEmployee);
        if(!tx.getText().equals("")) {
            setContentView(R.layout.displaytotaltime_three);
            logicForScreen3();
        }
        else {
            Toast myToast1 = Toast.makeText(
                    getApplicationContext(), "Select An Employee", Toast.LENGTH_LONG);
            myToast1.show();
        }
    }

    //----->complete this validate that a date was selected
    public void onClickNextTo_4(View view){
        setContentView(R.layout.displaytotaltime_four);
    }
    //functions to handle layout changes in backwards progression
    public void onClickBackTo_3(View view){
        setContentView(R.layout.displaytotaltime_three);
    }

    //------->complete this
    public void onClickBackTo_2(View view){
        setContentView(R.layout.displaytotaltime_two);
    }
    public  void onClickBackTo_1(View view){
        startActivity(new Intent(this,displayTotalTimeWorked.class));
    }
    public  void onClickBackTo_menu(View view){
               startActivity(new Intent(this,ownermenu.class));
    }

    private void logicForScreen2() {
        ListView list = (ListView) findViewById(R.id.displaytotaltime_two_LV_employee_time);

        displayTotalTimeWorked_listAdapter_two adapter = new displayTotalTimeWorked_listAdapter_two(this,Global.accessDatabase().getEmployeeList(),startDate,endDate);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                TextView tx = (TextView) findViewById(R.id.displaytotaltime_two_TV_nameOfEmployee);

                //this way of retrieving object data is based on the actual row selected
                nameChosen = ((Employee) parent.getAdapter().getItem(position)).getName();
                employeeID = ((Employee) parent.getAdapter().getItem(position)).getID();

                tx.setText(nameChosen);
            }
        });

    }

    //-------> complete this
    private void logicForScreen3(){
        //build list adapter
        //POPULATE total time for each date and day

    }


    @Override
    public void onBackPressed() {}

  /*
    @Override
    public void onPause() {
        super.onPause();
        Global.saveState(this);
    }
*/
}

