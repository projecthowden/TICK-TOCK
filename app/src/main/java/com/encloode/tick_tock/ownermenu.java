package com.encloode.tick_tock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ownermenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownermenu);
        TextView textView= (TextView) findViewById(R.id.no_employees);
        textView.setText(""+EmployeeDatabase.getNumOfCurrentEmployees());
    }

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
        Intent intent = new Intent(this, MainActivity.class); //xxxxxxxxxxxxxxxxxxx
        startActivity(intent);
    }

    public void onBackPressed() {
    }

 /*
    @Override
    public void onPause() {
        super.onPause();
        Global.saveState(this);
    }
*/


}
