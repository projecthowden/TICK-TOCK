package com.encloode.tick_tock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class validatePin extends AppCompatActivity {

    private int idOfCurrentEmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_pin);

        String name;

        idOfCurrentEmp= getIntent().getExtras().getInt("id");
        name = Global.accessDatabase().getNameOf(idOfCurrentEmp);

        TextView tx = (TextView) findViewById(R.id.validate_pin_tv_name);
        tx.setText(name);
    }


    public void onClickYes(View view){
        Intent intent = new Intent(this, timedisplay.class) ;
        intent.putExtra("id",Integer.toString(idOfCurrentEmp));
        startActivity(intent);
    }

    public void onClickNo(View view){
        Intent intent = new Intent(this, MainActivity.class) ;
        startActivity(intent);
    }
}
