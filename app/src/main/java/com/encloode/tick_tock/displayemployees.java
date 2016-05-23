package com.encloode.tick_tock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class displayemployees extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayemployees);
    }
    public void onClickExit(View view){
        //just go back to ownermenu
        Intent intent = new Intent(this,ownermenu.class);
        startActivity(intent);
    }
    //i dont think we will need the previous and the next buttons if we use the adapter class.
}
