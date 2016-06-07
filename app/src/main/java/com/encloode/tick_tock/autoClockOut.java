package com.encloode.tick_tock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.lang.reflect.Array;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_clock_out);
        timePicker = (TimePicker) findViewById(R.id.autoClockout_T_timePicker);
        numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        numberPicker.setMinValue(1);
        numberPicker.setWrapSelectorWheel(false);
        spinner = (Spinner) findViewById(R.id.autoClockout_s_spinnerMonth);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedMonth = parent.getSelectedItem().toString();
                String september = "september";
                String april = "april";
                String june = "june";
                String november = "november";
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
    public void onClickExit(View view){
        Intent intent = new Intent(this, ownermenu.class);
        startActivity(intent);
    }
    public  void onClickCorrect(View view){
        if(changesMade()){
            Intent intent = new Intent(this, ownermenu.class);
            startActivity(intent);
        }

    }
    public boolean changesMade(){
        changeDate = (CheckBox)findViewById(R.id.autoClockout_Cb_changeDate);
        changeTime = (CheckBox) findViewById(R.id.autoClockout_Cb_changeTime);
        if(changeDate.isChecked() && !changeTime.isChecked()){
            Global.autoBackUpDate = backupDate.withDate(2016,spinner.getSelectedItemPosition()+1,numberPicker.getValue());
            Toast.makeText(autoClockOut.this, "Automatic Yearly Backup Date has been set to: "+ Global.autoBackUpDate.toString("MMM/dd"), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ownermenu.class);
            startActivity(intent);
            return false;
        }
        if(changeTime.isChecked() && !changeDate.isChecked()){
            Global.autoClockOutTime = clockOutTime.withTime(timePicker.getCurrentHour(),timePicker.getCurrentMinute(),0,0);
            Toast.makeText(autoClockOut.this, "Automatic Daily ClockOut time has been set to: " + Global.autoClockOutTime.toString("hh:mm a"), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ownermenu.class);
            startActivity(intent);
            return false;
        }
        if(changeTime.isChecked()&& changeDate.isChecked()){
            Global.autoBackUpDate = backupDate.withDate(2016,spinner.getSelectedItemPosition()+1,numberPicker.getValue());
            Global.autoClockOutTime = clockOutTime.withTime(timePicker.getCurrentHour(),timePicker.getCurrentMinute(),0,0);
            Toast.makeText(autoClockOut.this, "Automatic Yearly Backup Date has been set to: "+ Global.autoBackUpDate.toString("MMM/dd")+"." + "\n Automatic Daily ClockOut time has been set to: " + Global.autoClockOutTime.toString("hh:mm a"), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ownermenu.class);
            startActivity(intent);
            return false;
        }
        else if (!changeDate.isChecked() && !changeTime.isChecked()){
            Toast.makeText(autoClockOut.this, "PLEASE CHECK ONE OR ALL OF THE BOXES", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
