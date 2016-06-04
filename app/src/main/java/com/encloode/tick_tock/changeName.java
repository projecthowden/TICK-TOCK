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
import android.widget.EditText;
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
                    whenDoneIsClicked();
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
                    whenDoneIsClicked();
                    return true;
                }
                else {
                    return false;
                }
            }
        });

       // ANOTHER LISTNER FOR FOCUS
        Naming.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    whenDoneIsClicked();
                }else {
                }
            }
        });



    }
    public boolean whenDoneIsClicked() {
        // The old name should be put.
        EditText idEntered = (EditText) findViewById(R.id.change_name_et_id);
        //id the idEntered is not empty and if it exists then this if statement is true
        String Text;
       // int TextInt;
        Text = idEntered.getText().toString();
        TextInt = Integer.parseInt(Text);
        boolean Invalid;

        if (!Text.equals("")) {
            TextView Name = (TextView) findViewById(R.id.change_name_et_oldname);
            Name.setText(Global.accessDatabase().getNameOf(TextInt));
            return true;
        }
          else if(Global.accessDatabase().idValid(TextInt)) {
                Toast.makeText(getApplicationContext(), "PLEASE ENTER A VALID ID", Toast.LENGTH_LONG).show();
            return true;
            }
         else if (!Global.accessDatabase().idValid(TextInt)) {
            TextView Name = (TextView) findViewById(R.id.change_name_et_oldname);
            Name.setText(Global.accessDatabase().getNameOf(TextInt));
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "ENTER A VALID ID", Toast.LENGTH_LONG).show();
            return false;
        }
    }


    public void onClickChangeName (View view) {
        EditText Newname = (EditText) findViewById(R.id.change_name_et_newname);
        // populate the text field.
        String StoreNewName;
        StoreNewName = Newname.getText().toString();
            if (StoreNewName.equals(isNull(StoreNewName))) {
                Toast.makeText(getApplicationContext(), "ENTER NAME", Toast.LENGTH_LONG).show();
            }
            else if(Newname.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "PLEASE ENTER A NAME", Toast.LENGTH_LONG).show();
            }
            else if(text == Newname.getText().toString().charAt(0)) {
                Toast.makeText(getApplicationContext(), "PLEASE ENTER A NAME", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "NAME CHANGE SUCCESSFUL", Toast.LENGTH_LONG).show();
                Global.accessDatabase().setNameOf(TextInt, StoreNewName);
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
