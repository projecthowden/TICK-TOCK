package com.encloode.tick_tock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import javax.microedition.khronos.opengles.GL;

public class delete_employee extends AppCompatActivity {

    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_employee);

        TextView textView = (TextView) findViewById(R.id.delete_employee_et_id);
        textView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
    }

    public void whenDoneIsClicked() {
        TextView textView = (TextView) findViewById(R.id.activity_deleteEmp_TF_nameValue);
        textView.setText(Global.accessDatabase().getNameOf(id));
        int idOfSearchedEmployee = Integer.parseInt(textView.getText().toString());
            id = idOfSearchedEmployee;

    }

    public void onClickDelete(View view) {
        whenDoneIsClicked();
        Employee gone = Global.accessDatabase().getEmployee(id);
        Global.accessDatabase().deleteEmployee(gone);

    }
}
