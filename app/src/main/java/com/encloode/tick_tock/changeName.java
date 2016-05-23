package com.encloode.tick_tock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class changeName extends AppCompatActivity {
    private int id;
    private String newName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);
       /* TextView textview =(TextView) findViewById(R.id.change_name_et_id);
        textview.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
        EditText editText=(EditText) findViewById(R.id.change_name_et_newname);
        newName=editText.getText().toString();
*/
    }
    public boolean whenDoneIsClicked() {
        EditText idEntered=(EditText) findViewById(R.id.change_name_et_id);
        //id the idEntered is not emplty and if it exists then this if statement is true
        if (!idEntered.getText().toString().equals("")&& !Global.accessDatabase().idValid(Integer.parseInt(idEntered.getText().toString()))) {
            TextView currentName = (TextView) findViewById(R.id.change_name_tv_oldname);
            currentName.setText(Global.accessDatabase().getNameOf(Integer.parseInt(idEntered.getText().toString())));
            return true;
        }
        else{
            Toast.makeText(getApplicationContext(),"ENTER A VALID ID", Toast.LENGTH_LONG).show();
            return false;
        }
    }
    public void onClickChangeName (View view){
        //instructions to change employeename.
        //toast to indicate name change to owner if change is successful.
        if(whenDoneIsClicked()){
            if(!isNull(newName)){
                Global.accessDatabase().setNameOf(id,newName);
                Toast.makeText(getApplicationContext(),"NAME CHANGE SUCCESSFUL", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(changeName.this,ownermenu.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(),"ENTER NAME", Toast.LENGTH_LONG).show();
                //since failed, it should reload same activity for user to try again.
                /*Intent intent=new Intent(changeName.this,changeName.class);
                startActivity(intent);*/
            }
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

}
