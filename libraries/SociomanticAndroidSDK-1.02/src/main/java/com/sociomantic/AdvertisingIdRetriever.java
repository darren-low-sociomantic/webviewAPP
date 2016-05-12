package com.sociomantic;

import android.content.Context;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.sociomantic.network.RequestManager;
import com.sociomantic.utility.AdInfo;
import com.sociomantic.utility.AdvertisingClient;
import com.sociomantic.utility.SCMLog;

import java.io.IOException;

/**
 *
 * Created by John Nilsen on 04.12.13.
 */
public class AdvertisingIdRetriever implements Runnable{

    public interface Observer{
        void onAdvertisingIDSuccess(AdInfo adInfo);
        void onAdvertisingIDFailure();
    }

    private SCMLog log = new SCMLog(AdvertisingIdRetriever.class);

    AdInfo adInfo;
    Observer observer;
    AdvertisingClient client;
    Context context;

    public AdvertisingIdRetriever(Observer observer, Context context, AdvertisingClient client){
        log.d("constructed ad id retriever");
        this.observer = observer;
        this.client = client;
        this.context = context;
    }

    public void runOn(RequestManager manager){
        manager.submit(this);
    }

    @Override
    public void run() {
        try {
            log.d("running adIdRetriever");
            adInfo = client.getInfo(context);
            log.d("retrieved " + (adInfo!= null ? adInfo.toString():"null advertising id!"));
            notifyObserver(true);
        } catch (IOException e) {
            log.e("Connection error",e);
            notifyObserver(false);
        } catch (GooglePlayServicesNotAvailableException e) {
            log.e("GooglePlayServices not available!",e);
            notifyObserver(false);
        } catch (GooglePlayServicesRepairableException e) {
            log.e("Google play not available or not up to date!", e);
        }
    }

    private void notifyObserver(boolean success){
        if(success){
            observer.onAdvertisingIDSuccess(adInfo);
        } else {
            observer.onAdvertisingIDFailure();
        }
    }
}
