package com.sociomantic.network;

import com.sociomantic.SociomanticCustomer;
import com.sociomantic.SociomanticProduct;
import com.sociomantic.SociomanticTransaction;
import com.sociomantic.Sociomantic;
import com.sociomantic.SociomanticConstants;
import com.sociomantic.SociomanticRegion;
import com.sociomantic.utility.JsonUtility;
import com.sociomantic.utility.SCMLog;
import com.sociomantic.utility.TimeStampUtility;
import com.sociomantic.utility.Validate;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;


/**
 * Created by John Nilsen on 09.12.13.
 */
public class URLBuilder {

    private static final String JS_DATE_ACTION = "/js/" + SociomanticConstants.SCM_URL_DATE + "/action";
    private static final String VIEW = "/view";
    private static final String BASKET = "/basket";
    private static final String OPTOUT_JSON = "&do={\"optout\":\"true\"}";
    private static final String IDFA = "?idfa=";
    private static final String AID = "&aid=";
    private static final String SALE = "/sale";
    private static final String LEAD = "/lead";

    private static final String ENCODING = "UTF-8";
    private static SociomanticCustomer customer = new SociomanticCustomer();

    private static final SCMLog LOG = new SCMLog(URLBuilder.class);

    private String clientToken;
    private String advertisingId;
    private Sociomantic.Region region;
    private String baseUrl;
    private TimeStampUtility timeStampUtility = new TimeStampUtility();

    public URLBuilder(String clientToken, String advertisingId, Sociomantic.Region region){
        this.clientToken = clientToken;
        this.advertisingId = advertisingId;
        this.region = region;
        updateBaseUrl();
    }

    private URL getUrlFromString(String urlString) {
        try {
            return new URL(urlString + getCompleteCustomerJson());
        } catch (MalformedURLException e) {
            LOG.e("URL is malformed!", e);
        }
        return null;
    }

    public URL getReportViewUrl(){
        return getUrlFromString(getBaseUrlForView());
    }

    public URL getOptOutUrl() {
        return getUrlFromString(getBaseUrlForView() + OPTOUT_JSON);
    }

    public URL getCategoryUrl(List<String> categories) {
        Validate.notNull(categories);
        Validate.notEmpty(categories);
        return getUrlFromString(getBaseUrlForView() + getCategoryJson(categories, null));
    }

    public URL getCategoryUrlWithDate(List<String> categories, long date) {
        return getUrlFromString(getBaseUrlForView() + getCategoryJson(categories, date));
    }

    public URL getProductUrl(SociomanticProduct product) {
        return getUrlFromString(getBaseUrlForView() + "&po=" + encode(product.getJsonString()));
    }

    public URL getBasketUrl(List<SociomanticProduct> products) {
        return getUrlFromString(getBaseUrlForBasket() + "&po=" + encode(JsonUtility
                .buildProductList(products)));
    }

    public URL getReportSaleUrl(SociomanticTransaction transaction) {
        return getUrlFromString(getBaseUrlForSale() + "&to=" + encode(transaction.getJsonString()) +
                "&po=" + encode(transaction.getPurchasesJsonString()));
    }

    public URL getReportLeadUrl(SociomanticTransaction transaction){
        return getUrlFromString(getBaseUrlForLead() + "&to=" + encode(transaction.getJsonString()));
    }

    public void setRegion(Sociomantic.Region region) {
        this.region = region;
        updateBaseUrl();
    }

    private String getBaseUrlForView(){
        return baseUrl + VIEW + IDFA + advertisingId + AID + clientToken;
    }

    private String getBaseUrlForBasket(){
        return baseUrl + BASKET + IDFA + advertisingId + AID + clientToken;
    }
    
    private String getBaseUrlForSale(){
        return baseUrl + SALE + IDFA + advertisingId + AID + clientToken;
    }

    private String getBaseUrlForLead(){
        return baseUrl + LEAD + IDFA + advertisingId + AID + clientToken;
    }

    private void updateBaseUrl(){
        baseUrl = new SociomanticRegion(region).getBaseUrl() + JS_DATE_ACTION;
    }

    private String getCategoryJson(List<String> categories, Long date){
        return "&co=" + encode("{\"category\":" + JsonUtility.convertToJsonList(categories)
                + getDateStringForJson(date) + "}");
    }

    private String getDateStringForJson(Long date){
        if(date == null){
            return "";
        } else {
            return ",\"date\":" + timeStampUtility.getUnixTimeStamp(date);
        }
    }

    public void setCustomer(SociomanticCustomer customer) {
        this.customer = customer;
    }

    public void setAdvertisingId(String advertisingId) {
        this.advertisingId = advertisingId;
    }

    private String getCompleteCustomerJson(){
        if(customer != null && customer.containsData()){
            return "&do=" + encode(customer.getJsonString());
        }
        return "";
    }

    private String encode(String toBeEncoded){
        try {
            return URLEncoder.encode(toBeEncoded, ENCODING);
        } catch (UnsupportedEncodingException e) {
            LOG.e("UnsupportedEncodign! Should never happen", e);
            return "";
        }
    }
}