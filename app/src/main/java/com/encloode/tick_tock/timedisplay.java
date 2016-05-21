package com.encloode.tick_tock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class timedisplay extends AppCompatActivity {

    private int idOfCurrentEmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timedisplay);

        String name;

        idOfCurrentEmp= Integer.parseInt(getIntent().getExtras().getString("id"));
        name = Global.accessDatabase().getNameOf(idOfCurrentEmp);

        TextView tx = (TextView) findViewById(R.id.validate_pin_tv_name);
        tx.setText(name);

        try {
            Thread.sleep(4000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity ( intent );
    }
}

