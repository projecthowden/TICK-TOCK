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
 * Created by Riko on 2016-06-06.
 */
public class listAdapter_currentlyClockedIn extends ArrayAdapter<Employee> implements Serializable{

    private ArrayList<Integer> hiddenItems;
    private ArrayList<Employee> list;
    private int size;

    /*
    * this adapter hides views that have no time worked during period
    * implented using the following guide:
    * http://stackoverflow.com/questions/18771923/listview-hide-some-items
    */

    public listAdapter_currentlyClockedIn(Context context,ArrayList<Employee> employees) {
        super(context,0,employees);

        size = employees.size();
        list = employees;
        hiddenItems = new ArrayList<>();
        hide();
    }

    public View getView(int position, View convertView, ViewGroup parent) {


        // Get the data item for this position
        Employee employee = getItem(position);

        //Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listformat_currentlyclockedin, parent, false);
        }
        //Lookup view for data population
        TextView tv_Name = (TextView) convertView.findViewById(R.id.listNameCurrentlyClockedIn);

        //populate the data into the template view using the data object
        tv_Name.setText(employee.getName());

        //return the completed view to render on the screen
        return convertView;

    }

    @Override
    public int getCount() {
        return size - hiddenItems.size();
    }

    @Override
    public Employee getItem(int position)
    {
        //this bypasses employees with no time worked
        for (Integer hiddenIndex : hiddenItems) {
            if(hiddenIndex <= position)
                position++;
        }

        return list.get(position);
    }

    public void hide(){

        //this builds a list with all indexes that have no time worked
        for (int i = 0; i < size; i++) {
            if(!list.get(i).isSignedIn()){
                hiddenItems.add(i);
            }

        }


    }
}
