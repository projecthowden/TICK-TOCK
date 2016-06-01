package com.encloode.tick_tock;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


/**
 *
 * @author Riko Hamblin
 */
public class TimeSummary implements Serializable {

    public int[][] minutesWorked;
    public DateTime[][][] inTime;
    public DateTime[][][] outTime;
    static int numOfClockIn_OutAllowedPerDay = 10;


    public TimeSummary() {
        minutesWorked = new int[53][7];
        for (int i = 0; i < 53; i++)
            for (int j = 0; j < 7; j++) {
                minutesWorked[i][j] = 0;
            }
        inTime = new DateTime[53][7][numOfClockIn_OutAllowedPerDay]; //allows for 100 sign ins per day
        outTime = new DateTime[53][7][numOfClockIn_OutAllowedPerDay]; // allows for 100 sign outs per day
          for (int i = 0; i < 53; i++)
              for (int j = 0; j < 7; j++)
               for (int k = 0; k < numOfClockIn_OutAllowedPerDay; k++) {
                   inTime[i][j][k]= null;
                   outTime[i][j][k]= null;
            }




    }

    public int totalTimeDuringInterval(DateTime start, DateTime end) {
        int sum = 0;
        int weekOfYear = start.getWeekOfWeekyear();
        int dayOfWeek = start.getDayOfWeek();
        end = end.plusDays(1);
       while (start.isBefore(end)){
           sum += minutesWorked[weekOfYear-1][dayOfWeek-1];


           start = start.plusDays(1);
           weekOfYear = start.getWeekOfWeekyear();
           dayOfWeek = start.getDayOfWeek();
        }
        return sum;
    }

    public int totalHoursDuringInterval(DateTime start, DateTime end) {
        int totalMinutesWorked = totalTimeDuringInterval(start, end);

        return totalMinutesWorked/60;



    }

    public int totalMinutesDuringInterval(DateTime start, DateTime end) {
        int totalMinutesWorked = totalTimeDuringInterval(start, end);

        int minutesWorked = totalMinutesWorked - totalHoursDuringInterval(start, end) * 60;

        return minutesWorked;
    }

    public ArrayList<DateTime> getListOfDates (DateTime start, DateTime end){
        ArrayList<DateTime> dates = new ArrayList<>();

        DateTime date = start;

        while(date.isBefore(end)){
            dates.add(date);
            date = date.plusDays(1);
           }
        dates.add(end);

        return dates;

    }

    public ArrayList<DateTime> getListOfInTimes (DateTime date){
        ArrayList<DateTime> inTimes = new ArrayList<>();

        int weekWanted = date.getWeekOfWeekyear();
        int dayOfWeekWanted = date.getDayOfWeek();

        for (int i = 0; i < numOfClockIn_OutAllowedPerDay; i++)
            inTimes.add(inTime[weekWanted-1][dayOfWeekWanted-1][i]);

        return inTimes;
    }

    public ArrayList<DateTime> getListOfOutTimes (DateTime date){
        ArrayList<DateTime> outTimes = new ArrayList<>();

        int weekWanted = date.getWeekOfWeekyear();
        int dayOfWeekWanted = date.getDayOfWeek();

        for (int i = 0; i < numOfClockIn_OutAllowedPerDay; i++)
            outTimes.add(inTime[weekWanted-1][dayOfWeekWanted-1][i]);

        return outTimes;
    }
}