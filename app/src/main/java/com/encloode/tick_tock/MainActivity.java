package com.encloode.tick_tock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tx = (TextView) findViewById(R.id.companyName);
        tx.setText("Encloode");

        EmployeeDatabase.listOfAvailableIDs = new int[100];

        for(int i=0; i<100;i++)
            EmployeeDatabase.listOfAvailableIDs[i] = i;

       Global.accessDatabase().addEmployee(new Employee("Riko", 1111));
        Global.accessDatabase().addEmployee(new Employee("Ibukun", 2222));
        Global.accessDatabase().addEmployee(new Employee("Matthew", 3333));

        Global.accessDatabase().addEmployee(new Employee("Rikxo", 1119));
        Global.accessDatabase().addEmployee(new Employee("Ibukcun", 2022));
        Global.accessDatabase().addEmployee(new Employee("Matbthew", 4333));
        Global.accessDatabase().addEmployee(new Employee("Rikso", 1115));
        Global.accessDatabase().addEmployee(new Employee("Ibuqkun", 2322));
        Global.accessDatabase().addEmployee(new Employee("Mattahew", 6333));
        Global.accessDatabase().addEmployee(new Employee("Rikod", 1118));
        Global.accessDatabase().addEmployee(new Employee("Ibukund", 2522));
        Global.accessDatabase().addEmployee(new Employee("Matthdew", 8333));

    }

    public void onClick(View view){
        Intent intent = new Intent(MainActivity.this, enterpin.class) ;
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
    }

}
