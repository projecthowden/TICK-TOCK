package com.encloode.tick_tock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Riko Hamblin on 05/30/16.
 */
public class displayTotalTimeWorked_listAdapter_four extends ArrayAdapter<DateTime> implements Serializable {

    private DateTime start;
    private DateTime end;
    private ArrayList<Integer> hiddenItems;
    private ArrayList<DateTime> list;
    private int size;
    private Employee employee;

    /*
    * this adapter hides views that have no time worked during period
    * implented using the following guide:
    * http://stackoverflow.com/questions/18771923/listview-hide-some-items
    */

    public displayTotalTimeWorked_listAdapter_four(Context context, ArrayList<DateTime> inTimes, ArrayList<DateTime> outTimes,Employee emp) {
        super(context,0);

        //  size = dates.size();
        //  list = dates;
        hiddenItems = new ArrayList<>();
        employee = emp;
        hide();
    }

    public View getView(int position, View convertView, ViewGroup parent) {


        // Get the data item for this position
            DateTime date = getItem(position);

            //Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null)
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.displaytotaltime_four_listformat, parent, false);

            //Lookup view for data population
            TextView tv_inTime = (TextView) convertView.findViewById(R.id.displaytotaltime_four_listInTime);
            TextView tv_outTime = (TextView) convertView.findViewById(R.id.displaytotaltime_four_listOutTime);


            //populate the data into the template view using the data object
            tv_inTime.setText(date.getDayOfMonth()+"/"+date.getMonthOfYear()+"/"+date.getYear());
            tv_outTime.setText(""+date.dayOfWeek().getAsText());


            //return the completed view to render on the screen
            return convertView;

    }

    @Override
    public int getCount() {
        return size - hiddenItems.size();
    }

    @Override
    public DateTime getItem(int position)
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
            if(employee.getTimeSummary().totalTimeDuringInterval(list.get(i),list.get(i)) == 0){
                hiddenItems.add(i);
            }

        }


    }
}
