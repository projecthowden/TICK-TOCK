package com.encloode.tick_tock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.sql.Time;

public class autoClockOut extends AppCompatActivity {
    NumberPicker numberPicker = null;
    CheckBox changeDate = null;
    CheckBox changeTime = null;
    TimePicker timePicker =null;
    Spinner spinner = null;
    Time time;
    DateTime backupDate = new DateTime();
    DateTime clockOutTime = new DateTime();

    AlarmManager manager;
    PendingIntent pendingClockOutIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_clock_out);
        spinner = (Spinner) findViewById(R.id.autoClockout_s_spinnerMonth);
        timePicker = (TimePicker) findViewById(R.id.autoClockout_T_timePicker);
        numberPicker = (NumberPicker) findViewById(R.id.numberPicker);

        numberPicker.setMinValue(1);
        numberPicker.setWrapSelectorWheel(false);

        Intent clockOutIntent = new Intent(this, AlarmReceiver.class);
        pendingClockOutIntent = PendingIntent.getBroadcast(this, 0, clockOutIntent, 0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*The next lines of code are to handle the number of days in different months of the year
                * based on the selected month, thew number picker will be configured to have the appropriate number of days.
                * eg February = 28 days, November = 30 days
                * */
                String selectedMonth = parent.getSelectedItem().toString();//this assigns the selected value of the dropdown menu to the string variable
                String september = "september";
                String april = "april";
                String june = "june";
                String november = "november";

                /* the 3 unique cases are 30 days, 31 days and 28 days*/
                if(selectedMonth.equalsIgnoreCase("february")){
                    numberPicker.setMaxValue(28);
                }
                else if (selectedMonth.equalsIgnoreCase(september)||selectedMonth.equalsIgnoreCase(april)||selectedMonth.equalsIgnoreCase(june)||selectedMonth.equalsIgnoreCase(november)){
                    numberPicker.setMaxValue(30);
                }
                else {
                    numberPicker.setMaxValue(31);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }
    /*logic for exit button functionality*/
    public void onClickExit(View view){
        Intent intent = new Intent(this, ownermenu.class);
        startActivity(intent);
    }
    /*
    logic for confirming changes.*/
    public  void onClickCorrect(View view){
        /*The idea here is that when the button is clicked, the condition for the if statement is evaluated.
        * It involves calling the changesMade() method. The method only returns true when when neither checkboxes are checked'
        * The ownermenu activity is then started without making any changes to the auto clockout time or backup date.
        * see the changesMade().*/
        if(changesMade()){
            Intent intent = new Intent(this, ownermenu.class);
            startActivity(intent);
        }

    }
    /*This method is called when the correct button is clicked.
    * The method involves 4 distinct cases which are based on the state of the 2 checkboxes. changeDate and changeTime.
    * the 4 cases are  00 01 10 11 respectively, where 0 refers to an unchecked checkbox and 1 refers to a checked checkbox.*/
    public boolean changesMade(){
        changeDate = (CheckBox)findViewById(R.id.autoClockout_Cb_changeDate);
        changeTime = (CheckBox) findViewById(R.id.autoClockout_Cb_changeTime);

        /*If this condition is true, then the user intends to change only the date*/
        if(changeDate.isChecked() && !changeTime.isChecked()){
            Global.setDateToWipeDatabase(this, backupDate.withDate(2016,spinner.getSelectedItemPosition()+1,numberPicker.getValue()));
            Toast.makeText(autoClockOut.this, "Automatic Yearly Backup Date has been set to: "+ Global.accessDatabase().getAutoBackUpDate().toString("MMM/dd"), Toast.LENGTH_SHORT).show();
            //done, so go back to owner menu after a toast to notify user.
            Intent intent = new Intent(this, ownermenu.class);
            startActivity(intent);
            return false;
        }
        /*If this condition is true, then the user intends to change only the time*/
        if(changeTime.isChecked() && !changeDate.isChecked()){
            Global.setClockOutTime(this, clockOutTime.withTime(timePicker.getCurrentHour(),timePicker.getCurrentMinute(),0,0));
            Toast.makeText(autoClockOut.this, "Automatic Daily ClockOut time has been set to: " + Global.accessDatabase().getAutoClockOutTime().toString("hh:mm a"), Toast.LENGTH_SHORT).show();
            //done, so go back to owner menu after a toast to notify user.
            Intent intent = new Intent(this, ownermenu.class);
            startActivity(intent);
            return false;
        }
        /*If this condition is true, then the user intends to change both the date and the time*/
        if(changeTime.isChecked()&& changeDate.isChecked()){
            Global.setDateToWipeDatabase(this, backupDate.withDate(2016,spinner.getSelectedItemPosition()+1,numberPicker.getValue()));
            Global.setClockOutTime(this, clockOutTime.withTime(timePicker.getCurrentHour(),timePicker.getCurrentMinute(),0,0));
            Toast.makeText(autoClockOut.this, "Automatic Yearly Backup Date has been set to: "+ Global.accessDatabase().getAutoBackUpDate().toString("MMM/dd")+"." + "\n Automatic Daily ClockOut time has been set to: " + Global.accessDatabase().getAutoClockOutTime().toString("hh:mm a"), Toast.LENGTH_SHORT).show();
            //done, so go back to owner menu after a toast to notify user.
            Intent intent = new Intent(this, ownermenu.class);
            startActivity(intent);
            return false;
        }
        /*If this condition is true, then the user has not checked any the checkboxes
        * so it notifies the user and remains in the same activity.
        * The user can exit the activity by clicking the exit button*/
        else if (!changeDate.isChecked() && !changeTime.isChecked()){
            Toast.makeText(autoClockOut.this, "PLEASE CHECK ONE OR BOTH OF THE BOXES", Toast.LENGTH_SHORT).show();
            return false;
        }
        /*It is impossible for none of the above conditions to true at any given instant,
        * so this return true statement will never be executed. However, handling the cases like this, prevents the problem
        * of starting a new activity when the user has not checked any of the boxes.*/
        return true;
    }

}
