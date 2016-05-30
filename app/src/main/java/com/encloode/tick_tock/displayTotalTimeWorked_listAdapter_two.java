package com.encloode.tick_tock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Riko Hamblin on 05/30/16.
 */
public class displayTotalTimeWorked_listAdapter_two extends ArrayAdapter<Employee> implements Serializable {


    public displayTotalTimeWorked_listAdapter_two(Context context, ArrayList<Employee> employees) {
        super(context,0, employees);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Employee employee = getItem(position);
        //Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.displaytotaltime_two_listformat, parent, false);
        }
        //Lookup view for data population
        TextView tv_Name = (TextView) convertView.findViewById(R.id.displaytotaltime_two_listName);
        TextView tv_Hours = (TextView) convertView.findViewById(R.id.displaytotaltime_two_listHours);
        TextView tv_Minutes = (TextView) convertView.findViewById(R.id.displaytotaltime_two_listMinutes);

        //populate the data into the template view using the data object
        tv_Name.setText(employee.getName());

        //calculate and display REALhours and minutes here

        tv_Hours.setText("12");
        tv_Minutes.setText("2");

        //return the completed view to render on the screen
        return convertView;
    }
}
