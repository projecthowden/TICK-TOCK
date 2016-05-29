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
 * Created by Riko Hamblin on 05/26/16.
 */
public class edittime_listAdapter extends ArrayAdapter<Employee> implements Serializable {

    public edittime_listAdapter(Context context, ArrayList<Employee> employees) {
        super(context, 0, employees);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Employee employee = getItem(position);
        //Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.edittime_one_listformat, parent, false);
        }
        //Lookup view for data population
        TextView tv_Name = (TextView) convertView.findViewById(R.id.edittime_one_listViewName);

        //populate the data into the template view using the data object
        tv_Name.setText(employee.getName());
        //return the completed view to render on the screen
        return convertView;
    }
}


