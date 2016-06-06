package com.encloode.tick_tock;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main); //pulls up the layout as the UI for this activity

        //only when the database is empty(when it first loads) does it load the database from file
       if(Global.accessDatabase() == null)
           try {
            Global.loadState(this);
        } catch (IOException e) {  e.printStackTrace();  }


        TextView tx = (TextView) findViewById(R.id.companyName); // this is where the company name on title is changed
        tx.setText("*clients company name goes here*");
        tx.setTextColor(Color.BLUE);
    }

    public void onClick(View view){
        Intent intent = new Intent(MainActivity.this, enterpin.class) ;
        startActivity(intent);
    }

    //for debugging purposes only
    public void onClickAddEmps(View view){
        Global.accessDatabase().addEmployee(new Employee("Riko", 1111));
        Global.accessDatabase().addEmployee(new Employee("Ibukun", 2222));
       Global.accessDatabase().addEmployee(new Employee("Matthew", 3333));
        Global.accessDatabase().addEmployee(new Employee("Mary", 4412));
         Global.accessDatabase().addEmployee(new Employee("Jane", 4421));
        Global.accessDatabase().addEmployee(new Employee("Jay", 4453));
        Global.accessDatabase().addEmployee(new Employee("Ricky", 1879));
        Global.accessDatabase().addEmployee(new Employee("Renny", 1919));
        Global.accessDatabase().addEmployee(new Employee("Joesph", 8484));
        Global.accessDatabase().addEmployee(new Employee("Khalia", 4582));
        Global.accessDatabase().addEmployee(new Employee("Jacob", 1541));
        Global.accessDatabase().addEmployee(new Employee("Joke", 4559));
    }

    @Override
    public void onBackPressed() {  } //this disables the physical back button on tablet

}
