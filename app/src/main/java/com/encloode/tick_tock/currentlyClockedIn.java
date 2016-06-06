package com.encloode.tick_tock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class currentlyClockedIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currently_clocked_in);

        populateList();
    }

    public void onClickExit(View view){
        //just go back to ownermenu
        Intent intent = new Intent(this,ownermenu.class);
        startActivity(intent);
    }

    private void populateList(){
        //Construct the data source
        ArrayList<Employee> arrayOfEmployees = Global.accessDatabase().getEmployeeList();
        //create the Adapter to convert the array to view
        listAdapter_currentlyClockedIn ourAdapter = new listAdapter_currentlyClockedIn(this,arrayOfEmployees);
        //attach the adapter to listView
        ListView listView=(ListView) findViewById(R.id.listClockedIn);
        listView.setAdapter(ourAdapter);

        TextView empty = (TextView) findViewById(R.id.currentlyClockedInEmptyView);
        listView.setEmptyView(empty);

    }
    @Override
    public void onBackPressed() {}
}
