package com.encloode.tick_tock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tx = (TextView) findViewById(R.id.companyName);
        tx.setText("Encloode");



        Employee newEmployee = new Employee("Riko Hamblin", 1111);
        Global.accessDatabase().addEmployee(newEmployee);

    }

    public void onClick(View view){
        Intent intent = new Intent(MainActivity.this, enterpin.class) ;
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {

        Toast myToast = Toast.makeText(
                getApplicationContext(), "Disabled", Toast.LENGTH_LONG);
        myToast.show();
    }

}
