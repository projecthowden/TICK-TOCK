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

    public void onClick(View view){
        Intent intent = new Intent(MainActivity.this, enterpin.class) ;
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
    }

}
