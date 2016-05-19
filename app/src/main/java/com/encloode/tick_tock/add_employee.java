package com.encloode.tick_tock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
}
