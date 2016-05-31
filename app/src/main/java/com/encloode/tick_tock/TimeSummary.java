package com.encloode.tick_tock;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.lang.reflect.Array;
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
        minutesWorked = new int[52][7];
        for (int i = 0; i < 52; i++)
            for (int j = 0; j < 7; j++) {
                minutesWorked[i][j] = 0;
            }
        inTime = new DateTime[52][7][numOfClockIn_OutAllowedPerDay]; //allows for 100 sign ins per day
        outTime = new DateTime[52][7][numOfClockIn_OutAllowedPerDay]; // allows for 100 sign outs per day
          for (int i = 0; i < 52; i++)
              for (int j = 0; j < 7; j++)
               for (int k = 0; k < numOfClockIn_OutAllowedPerDay; k++) {
                   inTime[i][j][k]= null;
                   outTime[i][j][k]= null;
            }




    }
}