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

        //this code below:
        // 1) sets a listener on the editText field specified
        // 2) carries out an action when the DONE key is pressed on soft keyboard
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

    //raises a toast when the button is clicked
    public void onClickForgotPassword(View view){
        Toast myToast = Toast.makeText(
                getApplicationContext(), "Contact your manager", Toast.LENGTH_LONG);
        myToast.show();
    }

    //this is the method that runs when done is pressed
    public void method(){

        int pin;
        int idOfCurrentEmp;
        TextView tx = (TextView) findViewById(R.id.enterpin_TF_pin);

        if(!tx.getText().toString().equals("")) { //checks if field contains text
            pin = Integer.parseInt(tx.getText().toString());

            if(tx.getText().toString().length() < 4){ //if they put in less than 4 digits
                Toast myToast = Toast.makeText(
                        getApplicationContext(), "ENTER A PIN OF 4 DIGITS", Toast.LENGTH_LONG);
                myToast.show();
            }
             else if (Global.accessDatabase().pinValid(pin) == false) { //user exist!
           //retrieve id of employee entered
                idOfCurrentEmp = Global.accessDatabase().getEmployeebyPin(pin).getID();

            /*send id to next activity*/
                Intent intent1 = new Intent(this, validatePin.class);
                intent1.putExtra("id", idOfCurrentEmp); //attach id to be sent to next activity
                startActivity(intent1);

            }   else if (Global.accessDatabase().pinValid(pin) == true && pin != Global.masterCode) { //no user exist with that pin

                Toast myToast = Toast.makeText(
                        getApplicationContext(), "No Employee Exist With That Pin", Toast.LENGTH_LONG);
                myToast.show();

            } else if (pin == Global.masterCode) { //the masterCode was entered therefore the ownerMenu is opened
                // proceed onto new activity for master code.
              //  Intent intent2 = new Intent(this, ownermenu.class);
                Intent intent2 = new Intent(this, enterpassword.class);
                startActivity(intent2);
            }
        }
        else { //no text was entered and the user attempted to proceed
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
    public void onBackPressed() {  }  //this disables the physical back button on tablet
}

