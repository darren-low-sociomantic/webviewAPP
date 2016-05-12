package com.sociomantic;


import android.content.Context;

import com.sociomantic.network.RequestManager;
import com.sociomantic.network.HttpRequestTask;
import com.sociomantic.network.URLBuilder;
import com.sociomantic.utility.AdInfo;
import com.sociomantic.utility.AdvertisingClient;
import com.sociomantic.utility.SCMLog;
import com.sociomantic.utility.Validate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.util.Log;

public final class Sociomantic implements AdvertisingIdRetriever.Observer{

    private static final SCMLog LOG = new SCMLog(Sociomantic.class);
    private static final String CONNECTION_ERROR = "Connection error!";

    private static volatile Sociomantic instance;

    private Region currentRegion;
    private String advertisingId;
    private String clientToken;
    private RequestManager manager;
    private URLBuilder urlBuilder;

    public static enum Region {
        US,
        EU,
        AP
    }


    private Sociomantic(){
    }

    public Sociomantic(Context context, String clientToken, Region scmRegion){
        Validate.notNull(clientToken);
        this.clientToken = clientToken;
        this.currentRegion = scmRegion;
        this.urlBuilder = new URLBuilder(clientToken, advertisingId, currentRegion);
        this.manager = new RequestManager();
        AdvertisingIdRetriever advertisingIdRetriever = new AdvertisingIdRetriever(this, context, new AdvertisingClient());
        advertisingIdRetriever.runOn(this.manager);
    }

    private void prepareUrlBuilder(){
        this.urlBuilder = new URLBuilder(clientToken, advertisingId, currentRegion);
    }

    public Region getCurrentRegion() {
        return currentRegion;
    }

    public void setCurrentRegion(Region scmRegion){
        currentRegion = scmRegion;
    }


    @Override
    public void onAdvertisingIDSuccess(AdInfo adInfo) {
        if(adInfo.isLimitedTracking()){
            this.advertisingId = "";
        } else {
            advertisingId = adInfo.getId();
            urlBuilder.setAdvertisingId(advertisingId);
        }
    }

    @Override
    public void onAdvertisingIDFailure() {
        advertisingId = "";
    }

    private void submitTaskToManager(URL url){
        if(url == null ){
            Log.w(CONNECTION_ERROR, "url");
            return;
        }
        if(advertisingId == null) {
           Log.w(CONNECTION_ERROR, "advertisingId"); 
           return;
        }
        if("".equals(advertisingId)){
            Log.w(CONNECTION_ERROR, "option 3");
            return;
        }
        try{
            manager.submit(new HttpRequestTask((HttpURLConnection) url.openConnection()));
        } catch (IOException e) {
            LOG.e(CONNECTION_ERROR, e);
        }
    }

    public void reportView() {
        submitTaskToManager(urlBuilder.getReportViewUrl());
    }

    public void reportOptOut() {
        submitTaskToManager(urlBuilder.getOptOutUrl());
    }

    public void reportCategories(List<String> categories) {
        submitTaskToManager(urlBuilder.getCategoryUrl(categories));
    }

    public void reportCategories(List<String> categories, long date) {
        submitTaskToManager(urlBuilder.getCategoryUrlWithDate(categories, date));
    }

    public void reportProductView(SociomanticProduct sociomanticProduct) {
        submitTaskToManager(urlBuilder.getProductUrl(sociomanticProduct));
    }

    public void reportAddToBasket(List<SociomanticProduct> products) {
        submitTaskToManager(urlBuilder.getBasketUrl(products));
    }

    public void reportTransaction(SociomanticTransaction transaction) {
        submitTaskToManager(urlBuilder.getReportSaleUrl(transaction));
    }

    public void reportLead(SociomanticTransaction transaction){
        submitTaskToManager(urlBuilder.getReportLeadUrl(transaction));
    }

    public void setManager(RequestManager manager) {
        this.manager = manager;
    }

    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }

    public void setUrlBuilder(URLBuilder urlBuilder){
        this.urlBuilder = urlBuilder;
    }

    public void setCustomer(SociomanticCustomer customer) {
        urlBuilder.setCustomer(customer);
    }
}