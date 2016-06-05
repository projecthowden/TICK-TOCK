package com.encloode.tick_tock;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class editTime extends AppCompatActivity {

    String nameChosen;
    int employeeID;
    int newTimeEntered[] = new int[2];
    DateTime date = new DateTime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edittime_one);
        populateEditTimeList();

        final CalendarView calendar = (CalendarView) findViewById(R.id.edittime_one_calendarView);



        //setting listener to enable autofill of fields
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                TextView tx = (TextView) findViewById(R.id.edittime_one_TF_dateChosen);
                tx.setText("Date Chosen: " + dayOfMonth +"/"+ (month+1) + "/"+year);

                Calendar temp = Calendar.getInstance();
                temp.set(Calendar.YEAR,year);
                temp.set(Calendar.MONTH, month);
                temp.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                date = new DateTime(temp);
            }
        });

        calendar.setDate(new DateTime().withDayOfMonth(1).getMillis()); //change calendar to first of month


        ListView listView=(ListView) findViewById(R.id.edittime_one_EmployeeList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                TextView tx = (TextView) findViewById(R.id.edittime_one_TF_employeeChosen);
                nameChosen = Global.accessDatabase().getEmployeeList().get(position).getName();
                tx.setText("Employee: "+ nameChosen);
                employeeID = Global.accessDatabase().getEmployeeList().get(position).getID();
            }
        });

    }
    private void populateEditTimeList(){
        //Construct the data source
        ArrayList<Employee> arrayOfEmployees = Global.accessDatabase().getEmployeeList();
        //create the Adapter to convert the array to view
        edittime_listAdapter ourAdapter = new edittime_listAdapter(this,arrayOfEmployees);
        //attach the adapter to listView
        ListView listView=(ListView) findViewById(R.id.edittime_one_EmployeeList);
        listView.setAdapter(ourAdapter);



    }

    //functions for handling foward progression
    public void onClickNext_to_2(View view){
        TextView employee = (TextView) findViewById(R.id.edittime_one_TF_employeeChosen);
        TextView calendarView = (TextView) findViewById(R.id.edittime_one_TF_dateChosen);
        if(employee.getText().toString().equals("Employee: ")|| calendarView.getText().toString().equals("Date Chosen: ")) {
            Toast myToast = Toast.makeText(
                    getApplicationContext(), "Select Employee or Date", Toast.LENGTH_LONG);
            myToast.show();
        } else if(date.isAfter(new DateTime()))  {
            Toast myToast = Toast.makeText(
                    getApplicationContext(), "Select Today's Date or previous", Toast.LENGTH_LONG);
            myToast.show();
        } else {
            populateEditTime_two();
        }
    }
    public  void populateEditTime_two(){

            setContentView(R.layout.edittime_two);
            TextView headerName = (TextView) findViewById(R.id.edittime_two_TV_Emolyeechoosen);
            TextView headerDate = (TextView) findViewById(R.id.edittime_two_TV_datechoosen);
            TextView previousHour = (TextView) findViewById(R.id.edittime_two_TV_previousHours);
            TextView previousMinutes = (TextView) findViewById(R.id.edittime_two_TV_previousMiniutes);


            headerName.setText(nameChosen + "'s Activity for: ");
            headerDate.setText(date.getDayOfMonth() + "/" + date.getMonthOfYear() + "/" + date.getYear());

            int minWorked = Global.accessDatabase().getMinutesWorkedFor(employeeID, date.getWeekOfWeekyear(), date.getDayOfWeek());
            int hoursWorked = minWorked / 60;
            int minLeft = minWorked - (hoursWorked * 60);

            previousHour.setText("" + hoursWorked);
            previousMinutes.setText("" + minLeft);



       }
    public void onClickSave(View view) {
        //close keyboard
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        TextView newHour = (TextView) findViewById(R.id.edittime_two_ET_changeHours);
        TextView newMinutes = (TextView) findViewById(R.id.edittime_two_ET_changeMinutes);
        if (!newHour.getText().toString().equals("") && !newMinutes.getText().toString().equals("")) { //if fields are filled
        if (Integer.parseInt(newHour.getText().toString()) <= 23 && Integer.parseInt(newMinutes.getText().toString()) <= 59) { //and hours and minutes are in acceptable range

                newTimeEntered[0] = Integer.parseInt(newHour.getText().toString());
                newTimeEntered[1] = Integer.parseInt(newMinutes.getText().toString());

                setContentView(R.layout.edittime_three);

                TextView headerName = (TextView) findViewById(R.id.edittime_three_TF_topMsg);
                TextView employee = (TextView) findViewById(R.id.edittime_three_ET_employee);
                TextView dateChoosen = (TextView) findViewById(R.id.edittime_three_ET_date);
                TextView previousTime = (TextView) findViewById(R.id.edittime_three_ET_previousTime);
                TextView newTime = (TextView) findViewById(R.id.edittime_three_ET_newTime);

                headerName.setText("SAVE?");
                employee.setText(nameChosen);
                dateChoosen.setText(date.getDayOfMonth() + "/" + date.getMonthOfYear() + "/" + date.getYear());

                int minWorked = Global.accessDatabase().getMinutesWorkedFor(employeeID, date.getWeekOfWeekyear(), date.getDayOfWeek());
                int hoursWorked = minWorked / 60;
                int minLeft = minWorked - (hoursWorked * 60);

                previousTime.setText(hoursWorked + "Hours " + minLeft + " Minutes");
                newTime.setText(newTimeEntered[0] + "Hours " + newTimeEntered[1] + " Minutes");

            } else {
            Toast myToast = Toast.makeText(
                    getApplicationContext(), "Enter A Valid Time", Toast.LENGTH_LONG);
            myToast.show();
        }
        }else {
                Toast myToast = Toast.makeText(
                        getApplicationContext(), "Enter A Valid Time", Toast.LENGTH_LONG);
                myToast.show();
            }

    }

    public void onClickDiscard(View view){
        startActivity(new Intent(this, editTime.class));
    }

    public void onClickYES(View view){

                Toast myToast1 = Toast.makeText(
                        getApplicationContext(), "Saving...", Toast.LENGTH_LONG);
                myToast1.show();


        Global.accessDatabase().clearIn_Out_timesFor(employeeID, date.getWeekOfWeekyear(), date.getDayOfWeek());

        DateTime in  = date.withTime(0,0,0,0);
        DateTime out = date.withTime(newTimeEntered[0],newTimeEntered[1],0,0);

        Global.accessDatabase().setInTimeOf(employeeID, date.getWeekOfWeekyear(), date.getDayOfWeek(),in);
        Global.accessDatabase().setOutTimeOf(employeeID, date.getWeekOfWeekyear(), date.getDayOfWeek(), out);

        int minsWorked = newTimeEntered[0]*60+newTimeEntered[1];
        Global.accessDatabase().setMinutesWorkedFor(employeeID,date.getWeekOfWeekyear(), date.getDayOfWeek(),minsWorked);

        Toast myToast = Toast.makeText(
                getApplicationContext(), "Time Changed", Toast.LENGTH_LONG);
        myToast.show();

        startActivity(new Intent(this,editTime.class));
    }

    public void onClickNO(View view){
        populateEditTime_two();
    }

    //fucntions to handle backwards progression
    public void onClickBack_to_ownerMenu(View view){
        Intent intent= new Intent(this,ownermenu.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {}

    @Override
    public void onPause() {
        super.onPause();
        new MyAsyncTask().execute();
    }

    //this async task runs on its own thread and saves the database to a file
    class MyAsyncTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Global.saveState(editTime.this);
            return null;
        }
    }
}