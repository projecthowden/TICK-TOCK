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

    private ArrayList<Integer> hiddenItems;
    private ArrayList<DateTime> inTimes;
    private ArrayList<DateTime> outTimes;
    private ArrayList<DateTime> times;
    private int sizeIn;
    private int sizeOut;
    private int size;
    private Employee employee;

    /*
    * this adapter hides views that have no time worked during period
    * implented using the following guide:
    * http://stackoverflow.com/questions/18771923/listview-hide-some-items
    */

    public displayTotalTimeWorked_listAdapter_four(Context context, ArrayList<DateTime> inTimes, ArrayList<DateTime> outTimes, Employee emp) {
        super(context,0);

        employee = emp;

        this.inTimes = inTimes;
        this.outTimes = outTimes;

        sizeIn = inTimes.size();
        sizeOut = outTimes.size();

        hiddenItems = new ArrayList<>();
        hide();
    }

    public displayTotalTimeWorked_listAdapter_four(Context context, ArrayList<DateTime> times, Employee emp) {
        super(context,0, times);

        employee = emp;

       // this.inTimes = inTimes;
       // this.outTimes = outTimes;
          this.times = times;

     //   sizeIn = inTimes.size();
     //   sizeOut = outTimes.size();
          size = times.size();

        hiddenItems = new ArrayList<>();
        hide();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // break code below into two for the two lists and implement custom get methods to retrieve in out times

        int position1;
        int position2;

        // Get the data item for this position
            if(position == 0) {
                position1 = 0;
                position2 = 1;
            } else if(position == times.size()-1) {
                convertView.setVisibility(View.GONE);
                return convertView;
            }
        else{
                position1 = position  + 1;
                position2 = position1 + 1;
            }

            //Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null)
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.displaytotaltime_four_listformat, parent, false);

            //Lookup view for data population
            TextView tv_inTime = (TextView) convertView.findViewById(R.id.displaytotaltime_four_listInTime);
            TextView tv_outTime = (TextView) convertView.findViewById(R.id.displaytotaltime_four_listOutTime);
            DateTimeFormatter dateFormat = DateTimeFormat.forPattern("hh:mm a");

            DateTime timeIn = getItem(position1);
            DateTime timeOut = getItem(position2);

            //populate the data into the template view using the data object
           // tv_inTime.setText(dateFormat.print(timeIn));
           // tv_outTime.setText(dateFormat.print(timeOut));


         tv_inTime.setText("111");
         tv_outTime.setText("2222");

            //return the completed view to render on the screen
            return convertView;
    }

    @Override
    public int getCount() {
        return size - hiddenItems.size();
    }


    public DateTime getItem(int position)
    {
        //this bypasses employees with no time worked
        for (Integer hiddenIndex : hiddenItems) {
            if(hiddenIndex <= position)
                position++;
        }

        return times.get(position);
    }

    public void hide(){

        //this builds a list with all indexes that have no time worked
          for (int i = 0; i < size; i++) {
            //  int weekWanted = times.get(i).getWeekOfWeekyear();
            //  int dayOfWeek = times.get(i).getDayOfWeek();

             if(times.get(i) == null){
                hiddenItems.add(i);
           }

          }


    }
}
