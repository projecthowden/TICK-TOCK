package com.encloode.tick_tock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class changePin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pin);
    }
    public void onClickExit(View view){
        Intent intent = new Intent(changePin.this,ownermenu.class);
        startActivity(intent);
    }
    public void onClickSubmit(View view){
        //instructions to execute pin change
        //a toast to indicate success or failure.
        boolean success=false; // false by default,the change pin method of employee class will update sucess.
        if(success){
            Toast.makeText(getApplicationContext(),"Pin successfuly changed",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(changePin.this,ownermenu.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(),"Pin change FAILED",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(changePin.this,changePin.class);
            startActivity(intent);
        }

    }
}
