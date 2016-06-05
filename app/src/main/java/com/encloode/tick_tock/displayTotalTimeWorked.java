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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class displayTotalTimeWorked extends AppCompatActivity {

    private int daysSelected;
    private DateTime startDate ;
    private DateTime endDate ;
    private String nameChosen;
    private int employeeID;
    private DateTime dateChosenOnScreen3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displaytotaltime_one);

        //create objects of UI Calendars
        CalendarView startDateCalendar = (CalendarView) findViewById(R.id.displaytotaltime_one_calendarStart);
        CalendarView endDateCalendar   = (CalendarView) findViewById(R.id.displaytotaltime_one_calendarEnd);

        final Calendar start = Calendar.getInstance();
        final Calendar end = Calendar.getInstance();

        //listen for a click on both calendars
        startDateCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                //create an object of the date date selected
                start.set(Calendar.YEAR, year);
                start.set(Calendar.MONTH, month);
                start.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                startDate = new DateTime(start.getTimeInMillis()); //assign that date as a DateTime object

                TextView startDateText = (TextView) findViewById(R.id.displaytotaltime_one_TV_startDate);
                startDateText.setText(dayOfMonth + "/" + (month+1) +"/"+year); //+1 because Calendar class' month is zero indexed

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

        //setting calendar view to first day of month to force user to pick a date
        startDateCalendar.setDate(new DateTime().withDayOfMonth(1).getMillis());
        endDateCalendar.setDate(new DateTime().withDayOfMonth(1).getMillis());
   }

    public boolean printNumDays() {

        TextView numDaysSelected = (TextView) findViewById(R.id.displaytotaltime_one_TV_numberOfDays);
        TextView tx = (TextView) findViewById(R.id.text);

        if (startDate != null && endDate != null){ //if a date was entered for both start and end

            //assure that start date is before or on end date
            if (startDate.isBefore(endDate) || startDate.isEqual(endDate)) {

            tx.setVisibility(View.VISIBLE);
            numDaysSelected.setTextColor(Color.BLACK);

            daysSelected = Days.daysBetween(startDate, endDate).getDays()+1;
            if (daysSelected >= 0) numDaysSelected.setText("" + daysSelected);
            return true;

             }
            else  {

            numDaysSelected.setText("Choose an End date that is after or on Start date");
            numDaysSelected.setTextColor(Color.RED);

            tx.setVisibility(View.INVISIBLE);
            }

    }
        return false;
    }

    //fucntions to handle layout changes in forward progression
    public void onClickNextTo_2(View view){

        if(printNumDays())
            logicForScreen2();
        else {
            Toast myToast = Toast.makeText(getApplicationContext(), "Choose an End date that is after the Start Date", Toast.LENGTH_LONG);
            myToast.show();
        }
    }
    public void onClickNextTo_3(View view){
        TextView tx = (TextView) findViewById(R.id.displaytotaltime_two_TV_nameOfEmployee);
        if(!tx.getText().equals(""))
            logicForScreen3();
        else {
            Toast myToast1 = Toast.makeText(
                    getApplicationContext(), "Select An Employee", Toast.LENGTH_LONG);
            myToast1.show();
        }
    }
    public void onClickNextTo_4(View view){
        TextView tx = (TextView) findViewById(R.id.displaytotaltime_three_TV_dateSelected);

        if(!tx.getText().equals(""))
            logicForScreen4();

        else {
            Toast myToast1 = Toast.makeText(
                    getApplicationContext(), "Select A Date", Toast.LENGTH_LONG);
            myToast1.show();
        }
    }

    //functions to handle layout changes in backwards progression
    public void onClickBackTo_3(View view){
        logicForScreen3();
    }
    public void onClickBackTo_2(View view){
        logicForScreen2();
    }
    public  void onClickBackTo_1(View view){
        startActivity(new Intent(this,displayTotalTimeWorked.class));
    }
    public  void onClickBackTo_menu(View view){
               startActivity(new Intent(this,ownermenu.class));
    }

    //logic for individual screens
    private void logicForScreen2() {
        setContentView(R.layout.displaytotaltime_two);
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
    private void logicForScreen3(){
        setContentView(R.layout.displaytotaltime_three);
        TextView tx = (TextView) findViewById(R.id.displaytotaltime_three_TV_enployeeName);
        Employee person = Global.accessDatabase().getEmployee(employeeID);
        tx.setText(nameChosen);

        ListView list = (ListView) findViewById(R.id.displaytotaltime_three_LV_employee_time);
        displayTotalTimeWorked_listAdapter_three adapter = new displayTotalTimeWorked_listAdapter_three(this,Global.accessDatabase().getEmployee(employeeID).getTimeSummary().getListOfDates(startDate,endDate),person);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                DateTime temp = ((DateTime) parent.getAdapter().getItem(position));
                dateChosenOnScreen3 = temp;
                TextView tx = (TextView) findViewById(R.id.displaytotaltime_three_TV_dateSelected);
                tx.setText(temp.getDayOfMonth()+"/"+temp.getMonthOfYear()+"/"+temp.getYear());
            }
        });
    }

    private void logicForScreen4() {
        setContentView(R.layout.displaytotaltime_four);

        TextView name = (TextView) findViewById(R.id.displaytotaltime_four_TV_enployeeName);
        TextView date = (TextView) findViewById(R.id.displaytotaltime_four_TV_date);

        name.setText(nameChosen);
        date.setText(dateChosenOnScreen3.getDayOfMonth()+"/"+dateChosenOnScreen3.getMonthOfYear()+"/"+dateChosenOnScreen3.getYear());

        ListView list = (ListView) findViewById(R.id.displaytotaltime_four_employee_status);

        ArrayList<DateTime> times = Global.accessDatabase().getEmployee(employeeID).getTimeSummary().getListOfIN_OUTTimes(dateChosenOnScreen3);

        displayTotalTimeWorked_listAdapter_four adapter = new displayTotalTimeWorked_listAdapter_four(this,times);
         list.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {}

}

