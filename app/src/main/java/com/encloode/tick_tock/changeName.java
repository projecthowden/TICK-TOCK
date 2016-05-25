package com.encloode.tick_tock;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class changeName extends AppCompatActivity {
    private int id;
    private String newName;
    private int  TextInt;
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
        EditText ID = (EditText) findViewById(R.id.change_name_et_id);
        EditText Naming = (EditText) findViewById(R.id.change_name_et_newname);


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




    }
    public boolean whenDoneIsClicked() {
        // The old name should be put.
        EditText idEntered = (EditText) findViewById(R.id.change_name_et_id);
        //id the idEntered is not empty and if it exists then this if statement is true
        String Text;
       // int TextInt;
        Text = idEntered.getText().toString();
        TextInt = Integer.parseInt(Text);

        if (!Text.equals("")) {
            TextView Name = (TextView) findViewById(R.id.change_name_et_oldname);
            Name.setText(Global.accessDatabase().getNameOf(TextInt));
            return true;
        } else if (!Global.accessDatabase().idValid(TextInt)) {
            TextView Name = (TextView) findViewById(R.id.change_name_et_oldname);
            Name.setText(Global.accessDatabase().getNameOf(TextInt));
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "ENTER A VALID ID", Toast.LENGTH_LONG).show();
            return false;
        }
    }

   // InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
   // imm.hideSoftInputFromWindow(edittext.getWindowToken(), 0);



    public void onClickChangeName (View view){
        EditText Newname = (EditText) findViewById(R.id.change_name_et_newname);
        // populate the text field.
        String StoreNewName;
        StoreNewName  = Newname.getText().toString();
        if(StoreNewName.equals(isNull(StoreNewName))) {
            Toast.makeText(getApplicationContext(),"ENTER NAME", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(),"NAME CHANGE SUCCESSFUL", Toast.LENGTH_LONG).show();
            Global.accessDatabase().setNameOf(TextInt, StoreNewName);
            Intent intent=new Intent(changeName.this,ownermenu.class);
            startActivity(intent);
        }






        //instructions to change employeename.
        //toast to indicate name change to owner if change is successful.
 //       if(whenDoneIsClicked()){
 //           if(!isNull(newName)){
//                Global.accessDatabase().setNameOf(id,newName);
  //              Toast.makeText(getApplicationContext(),"NAME CHANGE SUCCESSFUL", Toast.LENGTH_LONG).show();
 //               Intent intent=new Intent(changeName.this,ownermenu.class);
 //               startActivity(intent);
 //           }
 //           else{
 //               Toast.makeText(getApplicationContext(),"ENTER NAME", Toast.LENGTH_LONG).show();
 //               //since failed, it should reload same activity for user to try again.
 //               /*Intent intent=new Intent(changeName.this,changeName.class);
 //               startActivity(intent);*/
 //           }
 //       }
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

}
