package com.sociomantic;

import com.sociomantic.utility.JsonUtility;
import com.sociomantic.utility.Validate;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by John Nilsen on 11.12.13 at Sociomantic Labs.
 */
public final class SociomanticTransaction {

    private enum SCMTransactionKeys{
        AMOUNT,
        CURRENCY
    }

    private final String transactionId;
    private boolean isConfirmed;
    private List<SociomanticProduct> purchases;
    private Map<SCMTransactionKeys, String> transactionMap =
            new HashMap<SCMTransactionKeys, String>();

    public SociomanticTransaction(String transactionId){
        Validate.notNull(transactionId);
        this.transactionId = transactionId;
    }

    public SociomanticTransaction(String transactionId, List<SociomanticProduct> purchases){
        Validate.notNull(transactionId);
        Validate.notEmpty(purchases);
        this.transactionId = transactionId;
        this.purchases = purchases;
    }

    public String getJsonString() {
        String jsonString = "{\"transaction\":{\"transaction\":\"" + transactionId + "\"";
        if(!transactionMap.isEmpty()){
            jsonString = jsonString + ",";
        }
        jsonString = jsonString + JsonUtility.getJsonParameterStringFromMap(transactionMap) + "}";
        if(isConfirmed){
            jsonString = jsonString + ",\"confirmed\":\"true\"";
        }
        return jsonString + "}";
    }

    public String getPurchasesJsonString() {
        return JsonUtility.buildProductList(purchases);
    }

    public void setAmount(float amount){
        transactionMap.put(SCMTransactionKeys.AMOUNT,  String.format(Locale.US, "%.2f", amount));
    }

    public void setCurrency(String currency){
        transactionMap.put(SCMTransactionKeys.CURRENCY, currency);
    }

    public void setConfirmed(boolean isConfirmed){
        this.isConfirmed = isConfirmed;
    }
}