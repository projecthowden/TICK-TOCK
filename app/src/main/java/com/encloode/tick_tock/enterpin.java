package com.encloode.tick_tock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;

public class enterpin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterpin);

        EditText edittext = (EditText) findViewById(R.id.enterpin_TF_pin);
        edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                   method();
                    return true;
                }
                else {
                    return false;
                }
            }
        });


    }


    public void onClickForgotPassword(View view){
        Toast myToast = Toast.makeText(
                getApplicationContext(), "Contact your manager", Toast.LENGTH_LONG);
        myToast.show();
    }


    public void method(){

        int pin;
        int idOfCurrentEmp;
        TextView tx = (TextView) findViewById(R.id.enterpin_TF_pin);

        if(!tx.getText().toString().equals("")) {
            pin = Integer.parseInt(tx.getText().toString());


            if (Global.accessDatabase().pinValid(pin) == false) {
            /*send id to other screen*/
                idOfCurrentEmp = Global.accessDatabase().getEmployeebyPin(pin).getID();

            /*send id to next activity*/
                Intent intent1 = new Intent(this, validatePin.class);
                intent1.putExtra("id", idOfCurrentEmp);
                startActivity(intent1);
            } else if (Global.accessDatabase().pinValid(pin) == true && pin != Global.masterCode) {
                Toast myToast = Toast.makeText(
                        getApplicationContext(), "No Employee Exist With That Pin", Toast.LENGTH_LONG);
                myToast.show();
            } else if (pin == Global.masterCode) {
                // proceed onto new activity for master code.
                Intent intent2 = new Intent(this, ownermenu.class);

                startActivity(intent2);
            }
        }
        else {
            Toast myToast = Toast.makeText(
                    getApplicationContext(), "Enter Pin", Toast.LENGTH_LONG);
            myToast.show();
        }
   }
    public void onClick_exit(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
    }



}

