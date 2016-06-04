package com.encloode.tick_tock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Riko Hamblin on 05/30/16.
 */
public class displayTotalTimeWorked_listAdapter_four extends ArrayAdapter<DateTime> implements Serializable {



    private int i;

    /*
    * IN ORRDER FOR THIS TO WORK A LIST WAS CREATED WHICH IS THE COMBINATION OF
    * BOTH THE LIST OF IN TIMES AND THE LIST OF OUT TIMES
     */
    public displayTotalTimeWorked_listAdapter_four(Context context, ArrayList<DateTime> times) {
        super(context,0, times);
        i=0;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

            //Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null)
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.displaytotaltime_four_listformat, parent, false);

            //Lookup view for data population
            TextView label = (TextView) convertView.findViewById(R.id.displaytotaltime_four_listStatus);
            TextView tv_Time = (TextView) convertView.findViewById(R.id.displaytotaltime_four_listTime);
            DateTimeFormatter dateFormat = DateTimeFormat.forPattern("hh:mm a");

            if(i%2==0) { //if i is even then it is an inTime
                DateTime timeIn = getItem(position);
                label.setText("IN");

                if(timeIn != null) {
                    tv_Time.setText(dateFormat.print(timeIn));
                }
                else
                    tv_Time.setText("-----------");

            }
        else { //i is odd therefore it is outTime
                DateTime timeOut = getItem(position);
                label.setText("OUT");

                if (timeOut != null)
                    tv_Time.setText(dateFormat.print(timeOut));
                else
                    tv_Time.setText("-----------");
            }

        i++;
            //return the completed view to render on the screen
            return convertView;
    }

}
