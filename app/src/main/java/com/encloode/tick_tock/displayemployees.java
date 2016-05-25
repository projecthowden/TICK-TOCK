package com.encloode.tick_tock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class displayemployees extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayemployees);
        populateList();

    }
    public void onClickExit(View view){
        //just go back to ownermenu
        Intent intent = new Intent(this,ownermenu.class);
        startActivity(intent);
    }
    //i dont think we will need the previous and the next buttons if we use the adapter class.
    private void populateList(){
        //Construct the data source
        ArrayList<Employee> arrayOfEmployees = Global.accessDatabase().getEmployeeList();
        //create the Adapter to convert the array to view
        ListAdapter ourAdapter = new ListAdapter(this,arrayOfEmployees);
        //attach the adapter to listView
        ListView listView=(ListView) findViewById(R.id.list);
        listView.setAdapter(ourAdapter);

    }
    @Override
    public void onBackPressed() {}
}
