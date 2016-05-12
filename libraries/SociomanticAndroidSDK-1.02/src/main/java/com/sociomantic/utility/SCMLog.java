package com.sociomantic.utility;


import android.util.Log;

import com.sociomantic.SociomanticConstants;

/**
 * Created by John Nilsen on 09.12.13.
 */
public class SCMLog {

    private String tag;

    public SCMLog(Class clazz){
        this.tag = clazz.getName();
    }

    public String getTag() {
        return tag;
    }

    public void d(String message) {
        if(SociomanticConstants.DEBUG){
            Log.d(tag, message);
        }
    }

    public void d(String message, Throwable tr) {
        if(SociomanticConstants.DEBUG){
            Log.d(tag, message, tr);
        }
    }

    public void e(String message){
        if(SociomanticConstants.DEBUG){
            Log.e(tag, message);
        }
    }

    public void e(String message, Throwable tr){
        if(SociomanticConstants.DEBUG){
            Log.e(tag, message, tr);
        }
    }
}
