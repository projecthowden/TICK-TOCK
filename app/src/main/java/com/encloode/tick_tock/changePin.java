package com.encloode.tick_tock;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class changePin extends AppCompatActivity {
    private String id;
    private int enterIDValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pin);

        final EditText idEntered = (EditText) findViewById(R.id.change_pin_TF_idEntered);
        EditText reenterPin = (EditText) findViewById(R.id.activity_change_pin_TF_reenterPin);

        // listener after the done button has been pressed.
        idEntered.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT ) {
                    method();
                    return true;
                }

                else {
                    return false;
                }
            }
        });

        // listener for the editText losing focus.
        idEntered.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    method();;
                }
            }
        });

        // listener for the done button being pressed for the reEnterPin editText.
        reenterPin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    ImageButton submit = (ImageButton) findViewById(R.id.imageButton3);
                    submit.performClick();
                    return true;
                }
                else {
                    return false;
                }
            }
        });
    }

    public void method(){
        EditText idEntered = (EditText) findViewById(R.id.change_pin_TF_idEntered);

        id = idEntered.getText().toString();

        if(idEntered.getText().toString().equals("")) {
           Toast.makeText(getApplicationContext(),"Please Enter an Id", Toast.LENGTH_LONG).show();
        }
        else if( Global.accessDatabase().idValid(Integer.parseInt(id))) {
           Toast.makeText(getApplicationContext(),"No User Has That ID", Toast.LENGTH_LONG).show();
            TextView autofill = (TextView) findViewById(R.id.change_pin_TF_name);
            autofill.setText(" ");
        }
        else {

            // auto fill
            // always creating a object of the element (UI) to operate
            TextView autofill = (TextView) findViewById(R.id.change_pin_TF_name);
            String employeename;
            int onlyid;
            onlyid = Integer.parseInt(id);
            // converts a string to an integer,
            employeename = Global.accessDatabase().getNameOf(onlyid);
            autofill.setText(employeename);
        }
    }

    public void onClickExit(View view){
        Intent intent = new Intent(changePin.this,ownermenu.class);
        startActivity(intent);
    }

    public boolean idFieldValid() {

       EditText idEntered = (EditText) findViewById(R.id.change_pin_TF_idEntered);

        if (!idEntered.getText().toString().equals("")) {
            enterIDValue = Integer.parseInt(idEntered.getText().toString());
            if(!Global.accessDatabase().idValid(enterIDValue)){
                return true;
            }
            else {

                return false;
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Enter an ID", Toast.LENGTH_LONG).show();

            return false;
        }
    }

    public void onClickSubmit(View view) {
        if(!idFieldValid()) return;

        EditText passcode = (EditText) findViewById(R.id.activity_change_pin_TF_newPin);
        EditText passcode2 = (EditText) findViewById(R.id.activity_change_pin_TF_reenterPin);
        int pass, pass2;

        if (passcode.getText().toString().equals("") || passcode2.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "PLEASE FILL", Toast.LENGTH_LONG).show();
        }
        if(passcode.getText().toString().length() < 4 || passcode2.getText().toString().length() < 4  ){
            Toast myToast = Toast.makeText(
                    getApplicationContext(), "ENTER A PIN OF 4 DIGITS", Toast.LENGTH_LONG);
            myToast.show();
        }
        else {
            pass = Integer.parseInt(passcode.getText().toString());
            pass2 = Integer.parseInt(passcode2.getText().toString());

            if (pass != pass2) {
                Toast.makeText(getApplicationContext(), "THE CODE DOES NOT MATCH", Toast.LENGTH_LONG).show();
            }

            else if (pass == pass2 && !Global.accessDatabase().pinValid(pass)) {
                Toast.makeText(getApplicationContext(), "PLEASE CHOOSE ANOTHER PIN", Toast.LENGTH_LONG).show();
            }

            else {
                int onclickid = Integer.parseInt(id);
                Global.accessDatabase().setPinOf(onclickid, pass);
                Toast.makeText(getApplicationContext(), "YOUR PIN HAS BEEN CHANGED", Toast.LENGTH_LONG).show();
                Intent nextscreen = new Intent(this, ownermenu.class);
                startActivity(nextscreen);
            }
        }
    }
    @Override
    public void onBackPressed() {}

    @Override
    public void onPause() {
        super.onPause();
        new MyAsyncTask().execute();
    }

    //this async task runs on its own thread and saves the database to a file
    class MyAsyncTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
                Global.saveState(changePin.this);
            return null;
        }
    }

}

