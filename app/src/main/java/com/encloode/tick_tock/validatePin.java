package com.encloode.tick_tock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class validatePin extends AppCompatActivity {

    private int idOfCurrentEmp;
    private  String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_pin);

        idOfCurrentEmp= getIntent().getExtras().getInt("id"); //retrieve sent information from previous screen using id string
        name = Global.accessDatabase().getNameOf(idOfCurrentEmp); //retrieves name of that employee from database

        TextView tx = (TextView) findViewById(R.id.validate_pin_tv_name);
        tx.setText(name);
    }

    public void onClickYes(View view){
        Intent intent = new Intent(this, timedisplay.class) ;
        intent.putExtra("id",Integer.toString(idOfCurrentEmp)); //attach extra information to be sent to next screen
        startActivity(intent);
    }

    public void onClickNo(View view){
        Intent intent = new Intent(this, MainActivity.class) ;
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {  } //this disables the physical back button on tablet

}
