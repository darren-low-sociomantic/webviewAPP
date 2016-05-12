package com.sociomantic.utility;

import com.sociomantic.SociomanticProduct;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by John Nilsen on 11.12.13.
 */
public final class JsonUtility {

    private JsonUtility(){
    }

    public static String convertToJsonList(List<String> strings){
        StringBuilder jsonList = new StringBuilder();
        jsonList.append("[");
        Iterator<String> iterator = strings.iterator();
        while(iterator.hasNext()){
            jsonList.append("\"").append(iterator.next()).append("\"");
            if(iterator.hasNext()){
                jsonList.append(",");
            }
        }
        return jsonList.append("]").toString();
    }

    public static String getJsonParameterStringFromMap(Map<? extends Enum<?>, String> map) {

        StringBuilder response = new StringBuilder();
        for (Map.Entry<? extends Enum<?>, String> entry : map.entrySet()) {
            response.append(buildKeyValueJsonElement(entry)).append(",");
        }
        return removeTrailingComma(response.toString());
    }

    public static String buildProductList(List<SociomanticProduct> products){
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\"products\":[");
        boolean hasAddedProducts = false;
        for(SociomanticProduct product: products){
            jsonBuilder.append(product.getBasicJsonStringForBasket()).append(",");
            hasAddedProducts = true;
        }
        String jsonString;
        if(hasAddedProducts){
            jsonString = JsonUtility.removeTrailingComma(jsonBuilder.toString());
        } else {
            jsonString = jsonBuilder.toString();
        }
        return jsonString + "]}";
    }

    private static String buildKeyValueJsonElement(Map.Entry<? extends Enum<?>, String> entry){
        String keyName = "\"" + entry.getKey().name().toLowerCase() + "\":";
        String value =  "\"" + entry.getValue() + "\"";
        return keyName + value;
    }

    private static String removeTrailingComma(String responseString){
        if (responseString.length() > 0) {
            responseString = responseString.substring(0, responseString.length()-1);
        }
        return responseString;
    }
}
