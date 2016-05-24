package com.encloode.tick_tock;

import android.content.Intent;
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

public class changePin extends AppCompatActivity {
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pin);

        EditText pin = (EditText) findViewById(R.id.change_pin_TF_idEntered);
        EditText reenterPin = (EditText) findViewById(R.id.activity_change_pin_TF_reenterPin);

        // listner after the done button (pin) has been pressed.
        pin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    method();
                    return true;
                }
                else {
                    return false;
                }
            }
        });

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
        EditText pin = (EditText) findViewById(R.id.change_pin_TF_idEntered);
        String secondpin;
        secondpin =  pin.getText().toString();
        id = secondpin;
       ////////////////////////////
        /////////////////////////////
        if(pin.getText().toString().equals("")) {
           Toast.makeText(getApplicationContext(),"Please Enter an Id", Toast.LENGTH_LONG).show();
        }
        else if( Global.accessDatabase().idValid(Integer.parseInt(id))) {
           Toast.makeText(getApplicationContext(),"DOES NOT EXIST", Toast.LENGTH_LONG).show();
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
    public void onClickSubmit(View view) {
        EditText passcode = (EditText) findViewById(R.id.activity_change_pin_TF_newPin);
        EditText passcode2 = (EditText) findViewById(R.id.activity_change_pin_TF_reenterPin);
        int pass, pass2;

        if (passcode.getText().toString().equals("") || passcode2.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "PLEASE FILL", Toast.LENGTH_LONG).show();
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

    }

