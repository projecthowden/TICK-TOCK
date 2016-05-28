package com.encloode.tick_tock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ViewUtils;
import android.view.View;

public class displayTotalTimeWorked extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displaytotaltime_one);

    }
    //fucntions to handle layout changes in forward progression
    public void onClickNextTo_2(View view){
        setContentView(R.layout.displaytotaltime_two);
    }
    public void onClickNextTo_3(View view){
        setContentView(R.layout.displaytotaltime_three);
    }
    public void onClickNextTo_4(View view){
        setContentView(R.layout.displaytotaltime_four);
    }
    //functions to handle layout changes in backwards progression
    public void onClickBackTo_3(View view){
        setContentView(R.layout.displaytotaltime_three);
    }
    public void onClickBackTo_2(View view){
        setContentView(R.layout.displaytotaltime_two);
    }
    public  void onClickBackTo_1(View view){
        setContentView(R.layout.displaytotaltime_one);
    }
    public  void onClickBackTo_menu(View view){
        Intent intent = new Intent(this,ownermenu.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {}

    @Override
    public void onPause() {
        super.onPause();
        Global.saveState(this);
    }
}

