package com.sociomantic;


/**
 * Created by John Nilsen on 09.12.13.
 */
public class SociomanticRegion {

    Sociomantic.Region region;

    public SociomanticRegion(Sociomantic.Region region){
        this.region = region;
    }

    public String getBaseUrl() {
        switch(region){
            case EU:
                return SociomanticConstants.SCM_EU_URL;
            case US:
                return SociomanticConstants.SCM_US_URL;
            case AP:
                return SociomanticConstants.SCM_AP_URL;
            default:
                throw new IllegalArgumentException();
        }
    }
}
