package com.encloode.tick_tock;

import android.content.Intent;
import android.media.Image;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class editTime extends AppCompatActivity {

    String nameChosen;
    int employeeID;
    int newTimeEntered[] = new int[2];
    Calendar date = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edittime_one);
        populateEditTimeList();

        final CalendarView calendar = (CalendarView) findViewById(R.id.edittime_one_calendarView);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                TextView tx = (TextView) findViewById(R.id.edittime_one_TF_dateChosen);
                tx.setText("Date Chosen: " + dayOfMonth +"/"+ (month+1) + "/"+year);

                date.set(Calendar.YEAR, year);
                date.set(Calendar.MONTH, month);
                date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }
        });

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
        if(!employee.getText().toString().equals("Employee: ") && !calendarView.getText().toString().equals("Date Chosen: ")) {
            populateEditTime_two();
        }  else {
            Toast myToast = Toast.makeText(
                    getApplicationContext(), "Select Employee or Date", Toast.LENGTH_LONG);
            myToast.show();

        }
    }
    public  void populateEditTime_two(){

            setContentView(R.layout.edittime_two);
            TextView headerName = (TextView) findViewById(R.id.edittime_two_TV_Emolyeechoosen);
            TextView headerDate = (TextView) findViewById(R.id.edittime_two_TV_datechoosen);
            TextView previousHour = (TextView) findViewById(R.id.edittime_two_TV_previousHours);
            TextView previousMinutes = (TextView) findViewById(R.id.edittime_two_TV_previousMiniutes);


            headerName.setText(nameChosen + "'s Activity for: ");
            headerDate.setText(date.get(Calendar.DAY_OF_MONTH) + "/" + (date.get(Calendar.MONTH) + 1) + "/" + date.get(Calendar.YEAR));

            int minWorked = Global.accessDatabase().getMinutesWorkedFor(employeeID, date.get(Calendar.WEEK_OF_YEAR), date.get(Calendar.DAY_OF_WEEK));
            int hoursWorked = minWorked / 60;
            int minLeft = minWorked - (hoursWorked * 60);

            previousHour.setText("" + hoursWorked);
            previousMinutes.setText("" + minLeft);



       }
    public void onClickSave(View view) {

        TextView newHour = (TextView) findViewById(R.id.edittime_two_ET_changeHours);
        TextView newMinutes = (TextView) findViewById(R.id.edittime_two_ET_changeMinutes);
        if (!newHour.getText().toString().equals("") && !newMinutes.getText().toString().equals("")) {
        if (Integer.parseInt(newHour.getText().toString()) <= 23 && Integer.parseInt(newMinutes.getText().toString()) <= 59) {

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
                dateChoosen.setText(date.get(Calendar.DAY_OF_MONTH) + "/" + (date.get(Calendar.MONTH) + 1) + "/" + date.get(Calendar.YEAR));

                int minWorked = Global.accessDatabase().getMinutesWorkedFor(employeeID, date.get(Calendar.WEEK_OF_YEAR), date.get(Calendar.DAY_OF_WEEK));
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
        Global.accessDatabase().clearIn_Out_timesFor(employeeID, date.get(Calendar.WEEK_OF_YEAR), date.get(Calendar.DAY_OF_WEEK));

        Calendar inTime = Calendar.getInstance();
        Calendar outTime =  Calendar.getInstance();

        inTime.set(Calendar.WEEK_OF_YEAR, date.get(Calendar.WEEK_OF_YEAR));
        inTime.set(Calendar.DAY_OF_WEEK, date.get(Calendar.DAY_OF_WEEK));
        inTime.set(Calendar.HOUR_OF_DAY, 0);
        inTime.set(Calendar.MINUTE, 0 );

        outTime.set(Calendar.WEEK_OF_YEAR, date.get(Calendar.WEEK_OF_YEAR));
        outTime.set(Calendar.DAY_OF_WEEK, date.get(Calendar.DAY_OF_WEEK));
        outTime.set(Calendar.HOUR_OF_DAY, newTimeEntered[0]);
        outTime.set(Calendar.MINUTE, newTimeEntered[1] );

        Global.accessDatabase().setInTimeOf(employeeID, date.get(Calendar.WEEK_OF_YEAR), date.get(Calendar.DAY_OF_WEEK), inTime.getTime());
        Global.accessDatabase().setOutTimeOf(employeeID, date.get(Calendar.WEEK_OF_YEAR), date.get(Calendar.DAY_OF_WEEK), outTime.getTime());

        System.out.println(""+Global.accessDatabase().getMinutesWorkedFor(employeeID, date.get(Calendar.WEEK_OF_YEAR), date.get(Calendar.DAY_OF_WEEK)));

        Toast myToast = Toast.makeText(
                getApplicationContext(), "Time Changed", Toast.LENGTH_LONG);
        myToast.show();
    }

    public void onClickNO(View view){
        populateEditTime_two();
    }

    //fucntions to handle backwards progresion
    public void onClickBack_to_ownerMenu(View view){
        Intent intent= new Intent(this,ownermenu.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {}

    @Override
    public void onPause() {
        super.onPause();
        Global.saveState(this);
    }
}