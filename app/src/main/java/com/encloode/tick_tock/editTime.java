package com.encloode.tick_tock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class editTime extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edittime_one);
        populateEditTimeList();
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
        setContentView(R.layout.edittime_two);
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