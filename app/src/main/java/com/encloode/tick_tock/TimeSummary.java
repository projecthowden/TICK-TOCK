package com.encloode.tick_tock;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


/**
 *
 * @author Riko Hamblin
 */
public class TimeSummary {

    public int[][] minutesWorked;
    public Date[][][] inTime;
    public Date[][][] outTime;
    static int numOfClockIn_OutAllowedPerDay = 100;


    public TimeSummary() {
        minutesWorked = new int[52][7];
        for (int i = 0; i < 52; i++)
            for (int j = 0; j < 7; j++) {
                minutesWorked[i][j] = 0;
            }



        inTime = new Date[52][7][numOfClockIn_OutAllowedPerDay]; //allows for 100 sign ins per day
        outTime = new Date[52][7][numOfClockIn_OutAllowedPerDay]; // allows for 100 sign outs per day
        for (int i = 0; i < 52; i++)
            for (int j = 0; j < 7; j++)
                for (int k = 0; k < numOfClockIn_OutAllowedPerDay; k++) {
                    inTime[i][j][k]= Calendar.getInstance().getTime();
                    outTime[i][j][k]= Calendar.getInstance().getTime();
                }




    }
}