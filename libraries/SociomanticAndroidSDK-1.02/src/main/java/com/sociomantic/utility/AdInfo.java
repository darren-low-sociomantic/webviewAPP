package com.sociomantic.utility;

/**
 * Created by johnrikardnilsen on 20/12/13.
 */
public class AdInfo {
    private String id;
    private boolean limitedTracking;

    public AdInfo(String id, boolean limitedTracking){
        this.id = id;
        this.limitedTracking = limitedTracking;
    }

    public String getId() {
        return id;
    }

    public boolean isLimitedTracking() {
        return limitedTracking;
    }

    @Override
    public String toString(){
        return "AdvertisingId: " + id + (limitedTracking ? " with limited tracking":"");
    }
}
