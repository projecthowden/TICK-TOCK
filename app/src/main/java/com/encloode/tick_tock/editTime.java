package com.encloode.tick_tock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class editTime extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edittime_one);
    }
    @Override
    public void onBackPressed() {}

    @Override
    public void onPause() {
        super.onPause();
        Global.saveState(this);
    }
}