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

import javax.microedition.khronos.opengles.GL;

public class delete_employee extends AppCompatActivity {
    private  int id;
    private EditText idEntered;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_employee);

        idEntered = (EditText) findViewById(R.id.delete_employee_et_id);
        name = (TextView) findViewById(R.id.activity_deleteEmp_TF_nameValue);


        TextView textView = (TextView) findViewById(R.id.delete_employee_et_id);
        textView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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

        name.setVisibility(View.VISIBLE);

        if(!idEntered.getText().toString().equals("")) {
            int idOfSearchedEmployee = Integer.parseInt(idEntered.getText().toString());

            if(Global.accessDatabase().idValid(idOfSearchedEmployee)){
                Toast myToast = Toast.makeText(
                        getApplicationContext(), "NO USER FOUND", Toast.LENGTH_LONG);
                myToast.show();
                return false;
            }

            else {
                id = idOfSearchedEmployee;
                name.setText(Global.accessDatabase().getNameOf(idOfSearchedEmployee));
                return true;
            }
        }
        else {
            Toast myToast = Toast.makeText(
                    getApplicationContext(), "ENTER ID", Toast.LENGTH_LONG);
            myToast.show();
            return false;
        }
    }

    public void onClickDelete(View view) {
        if(whenDoneIsClicked()) {

            Intent intent = new Intent(this, validate_confirm.class);
            intent.putExtra("id", id);
            startActivity(intent);
        }

    }

    public void onClickDeleteEmp(View view) {

        Intent intent = new Intent(this, ownermenu.class) ;
        startActivity(intent);

    }
}
