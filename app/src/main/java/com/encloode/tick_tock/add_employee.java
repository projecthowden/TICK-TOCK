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
    }

    public void onClickForAddingEmployees(View v) {
        EditText editTextPin = (EditText) findViewById(R.id.add_employee_et_pin); // creted an pbject of type edittext view and retrived
        EditText editTextTypePinAgain = (EditText) findViewById(R.id.add_employee_et_Reenterpin);
        EditText editTextName = (EditText) findViewById(R.id.add_employee_et_name);



        //check that fields are filled
        if (isNull(editTextPin.getText().toString()) || isNull(editTextTypePinAgain.getText().toString()) || isNull(editTextName.getText().toString())) {

            Toast myToast = Toast.makeText(
                    getApplicationContext(), "Fill all fields", Toast.LENGTH_LONG);
            myToast.show();
        }

        //now we need to validate the pin.
       else if (EmployeeDatabase.getNumOfCurrentEmployees() < EmployeeDatabase.maxEmployeeSize){//only allows 3 employees for now

            //and stored the value in a variable after typecasting.
            int pin = Integer.parseInt(editTextPin.getText().toString());
            int pinRetyped = Integer.parseInt(editTextTypePinAgain.getText().toString());
            String newEmployeeName = editTextName.getText().toString();

           //pins do not match
           if (!pinsMatch(pin,pinRetyped)) {
               Toast myToast = Toast.makeText(
                       getApplicationContext(), "Pins Do Not Match", Toast.LENGTH_LONG);
               myToast.show();
           }

           //pins match so check that it is valid
           else if (Global.accessDatabase().pinValid(pin) && pin != Global.masterCode) {
                //pin is valid...create a new employee and add it to it the employee database.
                Employee newEmployee = new Employee(newEmployeeName, pin);
                Global.accessDatabase().addEmployee(newEmployee);// new employee has been added to the database.

               Toast myToast = Toast.makeText(
                       getApplicationContext(), "Action Completed", Toast.LENGTH_LONG);
               myToast.show();

                //go to owner menu activity
                Intent intent = new Intent(this, ownermenu.class);
                startActivity(intent);
                //employee is sucessfully
                //now we return to the owner menu
            }
            else { //pin matches but not valid OR THE PERSON ENTERS THE MASTER CODE
                Toast myToast = Toast.makeText(
                        getApplicationContext(), "Pin Taken", Toast.LENGTH_LONG);
                myToast.show();
            }
        }

       //max num of user already met
        else {
           String msg1 = "Maximum number of employees have been met. ";
           String msg2 = "Please contact Tech Support at encloode for more information";
           String msg3 = "info@encloode.com";

           Toast myToast = Toast.makeText(
                   getApplicationContext(), msg1 + msg2 + msg3, Toast.LENGTH_LONG);
           myToast.show();


        }
    }

    public boolean pinsMatch (int pin1, int pin2) {
        if(pin1 == pin2 ) return true;
        else return false;
    }

    public boolean isNull (String str) {
        if(str.isEmpty()) return true;
        else return false;
    }

    public void onClickExitFromAddEmployee(View view) {
        Intent intent = new Intent(this, MainActivity.class) ;
        startActivity(intent);
    }
}