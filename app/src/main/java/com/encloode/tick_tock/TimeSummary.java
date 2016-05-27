package com.encloode.tick_tock;

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
        inTime = new Date[52][7][numOfClockIn_OutAllowedPerDay]; //allows for 100 sign ins per day
        outTime = new Date[52][7][numOfClockIn_OutAllowedPerDay]; // allows for 100 sign outs per day
    }
}