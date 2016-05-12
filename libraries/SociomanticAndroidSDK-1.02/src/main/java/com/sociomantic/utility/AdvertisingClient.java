package com.sociomantic.utility;

import android.content.Context;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import java.io.IOException;

/**
 * Encapsulates AdvertisingIdClient for testability.
 * Created by johnrikardnilsen on 19/12/13.
 */
public class AdvertisingClient {

    SCMLog log = new SCMLog(AdvertisingClient.class);

    public AdvertisingClient(){

    }

    public AdInfo getInfo(Context context) throws
            GooglePlayServicesNotAvailableException, IOException, GooglePlayServicesRepairableException {
        AdvertisingIdClient.Info rawInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
        return new AdInfo(rawInfo.getId(), rawInfo.isLimitAdTrackingEnabled());
    }
}
