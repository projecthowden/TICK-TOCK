package com.encloode.tick_tock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class enterpin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterpin);

    }


    public void onClick33(View view){

        final EditText edittext = (EditText) findViewById(R.id.enterpin_TF_pin);
        edittext.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_NUMPAD_4)) {
                  //  method1();
                    return true;
                }
                return false;
            }
        });
    }
    public void onClick1(View view){


        int pin;
        int idOfCurrentEmp;
        TextView tx = (TextView) findViewById(R.id.enterpin_TF_pin);
        pin = Integer.parseInt(tx.getText().toString());


        if(Global.accessDatabase().pinValid(pin) == false) {
            /*send id to other screen*/
            idOfCurrentEmp = Global.accessDatabase().getEmployeebyPin(pin).getID();

            /*send id to next activity*/
            Intent intent1 = new Intent(this, validatePin.class);
            intent1.putExtra("id",idOfCurrentEmp);
            startActivity(intent1);
        }
        else if (Global.accessDatabase().pinValid(pin) == true && pin!=Global.masterCode) {
            Toast myToast = Toast.makeText(
                    getApplicationContext(), "WRONG PIN", Toast.LENGTH_LONG);
            myToast.show();
        }
        else if(pin == Global.masterCode){
            // proceed onto new activity for master code.
            Intent intent2 = new Intent(this, ownermenu.class);
           startActivity(intent2);
        }
    }
}
