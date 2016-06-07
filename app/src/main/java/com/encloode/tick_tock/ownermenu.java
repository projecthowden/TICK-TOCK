package com.encloode.tick_tock;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ownermenu extends AppCompatActivity {

    private final int delayTime = 10000;
    private Handler myHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownermenu);

        //displaying current number of employees in the system
        TextView textView= (TextView) findViewById(R.id.no_employees);
        textView.setText(""+EmployeeDatabase.getNumOfCurrentEmployees());

        //this allows for something to happen for a period of inactivity
        myHandler.postDelayed(closeSettings, delayTime);

    }

    //not sure how it works but it does
    public void onUserInteraction(){
        myHandler.removeCallbacks(closeSettings);
        myHandler.postDelayed(closeSettings, delayTime);

    }

    //this is what runs after 10secs of inactivity
    private Runnable closeSettings = new Runnable() {
        public void run() {
            finish();
            startActivity(new Intent(ownermenu.this, MainActivity.class));


        }
    };

    public void onClick_addEmployee(View view){
        Intent intent = new Intent(this, add_employee.class);
        startActivity(intent);
    }

    public void onClick_deleteEmployee(View view){
        Intent intent = new Intent(this, delete_employee.class);
        startActivity(intent);
    }

    public void onClick_seeLoggedHours(View view){
        Intent intent = new Intent(this, displayTotalTimeWorked.class);
        startActivity(intent);
    }

    public void onClick_listOfStaff(View view){
        Intent intent = new Intent(this, displayemployees.class);
        startActivity(intent);
    }

    public void onClick_editName(View view){
        Intent intent = new Intent(this, changeName.class);
        startActivity(intent);
    }

    public void onClick_changePin(View view){
        Intent intent = new Intent(this, changePin.class);
        startActivity(intent);
    }

    public void onClick_editTime(View view){
        Intent intent = new Intent(this, editTime.class);
        startActivity(intent);
    }

    public void onClick_exit(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClick_currentlyClockedIn(View view){
        Intent intent = new Intent(this, currentlyClockedIn.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {  }

    @Override
    public void onPause(){
        super.onPause();
        myHandler.removeCallbacks(closeSettings);
    }
}
