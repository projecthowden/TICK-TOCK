package com.encloode.tick_tock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class add_employee extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        /* features:
            1) if they are EmployeeDatabase.maxEmployeeSize current employee
                then raise a toast

         this is more or less the code but use your best judgement when implementing:

                 String msg1 = "Maximum Number of employees have been met. ";
                String msg2 = "Please contact Tech Support at encloode for more information";

        if(EmployeeDatabase.getNumOfCurrentEmployees() == EmployeeDatabase.maxEmployeeSize) {

         Toast myToast = Toast.makeText(
                    getApplicationContext(), msg1 + msg2, Toast.LENGTH_LONG);
            myToast.show();
         */


    }

    public void onClick(View v) {
        EditText editTextPin = (EditText) findViewById(R.id.add_employee_et_pin); // creted an pbject of type edittext view and retrived
        //and stored the value in a variable after typecasting.
        int pin = Integer.parseInt(editTextPin.getText().toString());

        EditText editTextName = (EditText) findViewById(R.id.add_employee_et_name);
        String newEmployeeName = editTextName.getText().toString();
        //now we need to validate the pin.
       if (EmployeeDatabase.getNumOfCurrentEmployees() < EmployeeDatabase.maxEmployeeSize) {
            if (Global.accessDatabase().pinValid(pin)) {
                //pin is valid...create a new employee and add it to it the employee database.
                Employee newEmployee = new Employee(newEmployeeName, pin);
                Global.accessDatabase().addEmployee(newEmployee);// new employee has been added to the database.
                //research toaast.//go to owner menu activity
                Intent intent = new Intent(this, ownermenu.class);
                startActivity(intent);
                //employee is sucessfully
                //now we return to the owner menu
            }
            else {
                //notify user that pin  is taken.
            }
        }
        else {
            //contact encloode

        }
    }
}