package com.encloode.tick_tock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Ibukun on 5/24/2016.
 */
    public class ListAdapter extends ArrayAdapter<Employee>{
    public ListAdapter(Context context, ArrayList<Employee> employees){
        super(context,0, employees);
    }

    @Override
    public View getView(int position, View convertView,ViewGroup parent){
        // Get the data item for this position
        Employee employee = getItem(position);
        //Check if an existing view is being reused, otherwise inflate the view
        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_format,parent,false);
        }
        //Lookup view for data population
        TextView tv_Name = (TextView) convertView.findViewById(R.id.listName);
        TextView tv_id = (TextView) convertView.findViewById(R.id.listId);
        //populate the data into the template view using the data object
        tv_Name.setText(employee.getName());
        tv_id.setText(""+employee.getID());
       // tv_id.setText(new DecimalFormat("##").format(employee.getID()).toString());
        //tv_id.setText(String.format("%1$, 2d", employee.getID()));
        //return the completed view to render on the screen
        return convertView;
    }

}
