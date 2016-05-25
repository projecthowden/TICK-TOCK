package com.encloode.tick_tock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class validate_confirm extends AppCompatActivity {
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_confirm);

        Intent intent=getIntent();
        id = intent.getExtras().getInt("id");
        TextView textViewName=(TextView)findViewById(R.id.validate_confirm_tv_name);
        TextView textViewId = (TextView) findViewById(R.id.validate_confirm_tv_id);
        TextView msg = (TextView) findViewById(R.id.validateConfirmMessage);
        msg.setVisibility(View.VISIBLE);
        msg.setText("DELETE?");

        textViewName.setText(Global.accessDatabase().getEmployee(id).getName());
        textViewId.setText(Integer.toString(id));

    }
    public void onClickYes(View view){
        //get the employee using the get and delete employee methods.
            String name;
        name = Global.accessDatabase().getNameOf(id);
           Global.accessDatabase().deleteEmployee(Global.accessDatabase().getEmployee(id));

           Toast.makeText(getApplicationContext(),"Employee "+name+" Deleted",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(validate_confirm.this,ownermenu.class);
        startActivity(intent);

    }
    public void onclickNo(View view){
        //since no, there is no deletion to execute.
        //just return to previous activity.
        Intent intent=new Intent(validate_confirm.this,delete_employee.class);
        startActivity(intent);
    }
    //this disables the android back buttpon
    @Override
    public void onBackPressed() {
    }
}
