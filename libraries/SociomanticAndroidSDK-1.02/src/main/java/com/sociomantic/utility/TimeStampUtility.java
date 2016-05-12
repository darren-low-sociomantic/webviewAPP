package com.sociomantic.utility;

/**
 * Created by johnrikardnilsen on 12/02/14.
 */
public class TimeStampUtility {

    public long getUnixTimeStamp(long timeStamp) {
        if(String.valueOf(timeStamp).length() == 13){
            return timeStamp / 1000;
        }
        return timeStamp;
    }
}
