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

public class enterpassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterpassword);

        EditText pass = (EditText) findViewById(R.id.secondpassword);
        pass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId == EditorInfo.IME_ACTION_DONE){
                    ImageButton btn = (ImageButton) findViewById(R.id.done);
                    btn.performClick();
                }
                return false;
            }
        });
    }

    public void onclickback (View view) {
        Intent intent = new Intent (this, enterpin.class);
        startActivity(intent);
    }

    public void main (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void passmethod(View view){
        String pin;
        EditText pass = (EditText) findViewById(R.id.secondpassword);
        pin = pass.getText().toString();

        if(pass.getText().toString().equals("")) {
            Toast myToast = Toast.makeText(
                    getApplicationContext(), " Enter Password", Toast.LENGTH_LONG);
            myToast.show();
        }
        else if (pin.equals(Global.masterString)) {
                Intent intent1 = new Intent(this, ownermenu.class);
                startActivity(intent1);
            }
        else {
            Toast myToast = Toast.makeText(
                    getApplicationContext(), "Password Invalid ", Toast.LENGTH_LONG);
            myToast.show();
        }

    }

    @Override
    public void onBackPressed() {  }  //this disables the physical back button on tablet

}
