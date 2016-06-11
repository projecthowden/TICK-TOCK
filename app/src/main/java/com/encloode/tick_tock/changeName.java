package com.encloode.tick_tock;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class changeName extends AppCompatActivity {
    private int id;
    private String newName;
    private int  TextInt;
    private char text = ' ';
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);
        EditText ID = (EditText) findViewById(R.id.change_name_et_id);
        EditText Naming = (EditText) findViewById(R.id.change_name_et_newname);

       // FIRST LISTNER FOR THE DONE BUTTON.
        ID.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    whenNextIsClicked();
                    return true;
                }
                else {
                    return false;
                }
            }
        });


        Naming.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if(whenDoneIsClicked()) {
                    ImageButton btn = (ImageButton) findViewById(R.id.imageButtonchange_name_b_changename);
                    btn.performClick();;
                    return true;}
                }
                else {
                    return false;
                }
                return  false;
            }
        });

       // ANOTHER LISTNER FOR FOCUS
        Naming.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    whenNextIsClicked();
                }else {
                }
            }
        });



    }
    public boolean whenNextIsClicked() {
        // The old name should be put.
        EditText idEntered = (EditText) findViewById(R.id.change_name_et_id);
        //id the idEntered is not empty and if it exists then this if statement is true
        String Text;
        // int TextInt;
        Text = idEntered.getText().toString();


        if (!Text.equals("")) {
            TextInt = Integer.parseInt(Text);
            if (Global.accessDatabase().idValid(TextInt)) {
                Toast.makeText(getApplicationContext(), "ENTER A VALID ID", Toast.LENGTH_LONG).show();
                TextView tx = (TextView) findViewById(R.id.change_name_et_oldname);
                tx.setText(" ");
                return false;
            } else {
                TextView Name = (TextView) findViewById(R.id.change_name_et_oldname);
                Name.setText(Global.accessDatabase().getNameOf(TextInt));

                return true;
            }

        } else {
            Toast.makeText(getApplicationContext(), "ENTER AN ID", Toast.LENGTH_LONG).show();
            return false;
        }
    }
    public boolean whenDoneIsClicked() {
        // The old name should be put.
        EditText idEntered = (EditText) findViewById(R.id.change_name_et_id);
        EditText nameEntered = (EditText) findViewById(R.id.change_name_et_newname);
        //id the idEntered is not empty and if it exists then this if statement is true
        String Text;
        // int TextInt;
        Text = idEntered.getText().toString();


        if (whenNextIsClicked()) {
            if(!nameEntered.getText().toString().equals("")){
                Global.accessDatabase().setNameOf(TextInt,nameEntered.getText().toString());
                return true;
            }

            else return false;

        }
     return false;
    }


    public void onClickChangeName (View view) {
        if(whenDoneIsClicked()){
                Intent intent = new Intent(changeName.this, ownermenu.class);
                startActivity(intent);
        }

        }

    public void onClickExitForChangeName(View view){
        Intent intent=new Intent(changeName.this,ownermenu.class);
        startActivity(intent);
    }
    public boolean isNull (String str) {
        if(str.isEmpty()) return true;
        else return false;
    }
    @Override
    public void onBackPressed() {}

    @Override
    public void onPause() {
        super.onPause();
        new MyAsyncTask().execute();
    }

    //this async task runs on its own thread and saves the database to a file
    class MyAsyncTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {

                Global.saveState(changeName.this);

            return null;
        }
    }

}
