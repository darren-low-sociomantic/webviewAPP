package com.sociomantic;

import com.sociomantic.utility.JsonUtility;
import com.sociomantic.utility.TimeStampUtility;
import com.sociomantic.utility.Validate;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by John Nilsen on 09.12.13.
 */
public class SociomanticProduct {

    //the name of the enum entries are the exact same ones as in the json request
    private enum SCMProductKey {
        BRAND,
        FN,
        PRICE,
        AMOUNT,
        CURRENCY,
        URL,
        PHOTO,
        DESCRIPTION,
        DATE,
        VALID,
        QUANTITY,
        SCORE
    }

    private SCMProductKey[] validBasketKeys = {
            SCMProductKey.AMOUNT,
            SCMProductKey.CURRENCY,
            SCMProductKey.QUANTITY};

    private String identifier;
    private Map<SCMProductKey, String> productMap = new HashMap<SCMProductKey, String>();
    private List<String> categories;
    private TimeStampUtility timeStampUtility = new TimeStampUtility();

    public SociomanticProduct(String identifier){
        Validate.notNull(identifier);
        this.identifier = identifier;
    }

    /**
     * returns a JSON that represents the SociomanticProduct object. For example:
     * {products:[{"identifier":"ID"}]}
     * @return a JSON representation of the object
     */
    public String getJsonString() {
        String jsonString = getJsonSuperObjectStart();
        boolean withCategories = false;
        if(categories != null && !categories.isEmpty()){
            withCategories = true;
        }
        jsonString = jsonString + getBasicJsonStringForKeySet(productMap, withCategories);
        jsonString = jsonString + getJsonSuperObjectEnd();
        return jsonString;
    }

    /**
     * returns a JSON that represents the SociomanticProduct object, excluding the fields that are not
     * valid for the Basket and confirm sale actions. For example:
     * {products:[{"identifier":"ID"}]}
     * @return a JSON representation of the object
     */
    public String getJsonStringForBasket() {
        String jsonString = getJsonSuperObjectStart();
        jsonString = jsonString + getBasicJsonStringForKeySet(getProductMapWithOnlyBasketKeys(), false);
        jsonString = jsonString + getJsonSuperObjectEnd();
        return jsonString;
    }

    public String getBasicJsonStringForBasket(){
        return getBasicJsonStringForKeySet(getProductMapWithOnlyBasketKeys(), false);
    }

    private String getBasicJsonStringForKeySet(Map<SCMProductKey, String> map, boolean withCategories) {
        String jsonString = "{\"identifier\":\"" + getIdentifier() + "\"";
        if(withCategories){
            jsonString = jsonString + getJsonListForCategories();
        }
        if(!map.isEmpty()){
            jsonString = jsonString + ",";
        }
        jsonString = jsonString + JsonUtility.getJsonParameterStringFromMap(map) + "}";
        return jsonString;
    }

    private String getJsonSuperObjectStart(){
        return "{\"products\":[";
    }

    private String getJsonSuperObjectEnd(){
        return "]}";
    }

    private Map<SCMProductKey, String> getProductMapWithOnlyBasketKeys() {
        Map<SCMProductKey, String> basketMap = new HashMap<SCMProductKey, String>();
        for(SCMProductKey basketKey: validBasketKeys){
            if(productMap.get(basketKey) != null){
                basketMap.put(basketKey, productMap.get(basketKey));
            }
        }
        return basketMap;
    }

    private String getJsonListForCategories() {
        if(categories == null || categories.isEmpty()){
            return "";
        }
        return ",\"category\":" + JsonUtility.convertToJsonList(categories);
    }

    public String getIdentifier() {
        return identifier;
    }


    public void setBrand(String brand) {
        productMap.put(SCMProductKey.BRAND, brand);
    }


    public void setQuantity(int quantityInStock) {
        productMap.put(SCMProductKey.QUANTITY, Integer.toString(quantityInStock));
    }

    public void setPrice(float price) {
        productMap.put(SCMProductKey.PRICE, String.format(Locale.US, "%.2f", price));
    }

    public void setImageUrl(String imageUrl) {
        productMap.put(SCMProductKey.PHOTO, imageUrl);
    }

    public void setProductName(String productName) {
        productMap.put(SCMProductKey.FN, productName);
    }

    public void setProductUrl(String productUrl) {
        productMap.put(SCMProductKey.URL, productUrl);
    }

    /**
     * Sets the currency for the product
     * @param currency currency code in ISO 4217
     */
    public void setCurrency(String currency) {
        productMap.put(SCMProductKey.CURRENCY, currency);
    }

    public void setDescription(String description) {
        productMap.put(SCMProductKey.DESCRIPTION, description);
    }

    public void setAmount(float regularPrice) {
        productMap.put(SCMProductKey.AMOUNT, String.format(Locale.US, "%.2f", regularPrice));
    }

    public void setScore(float score) {
        productMap.put(SCMProductKey.SCORE, Float.toString(score));
    }

    public void setDateTimestamp(long dateTimestamp) {
        productMap.put(SCMProductKey.DATE, Long.toString(timeStampUtility.getUnixTimeStamp(dateTimestamp)));
    }

    public void setValidUntil(long time) {
        productMap.put(SCMProductKey.VALID, Long.toString(timeStampUtility.getUnixTimeStamp(time)));
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
