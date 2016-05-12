package com.example.sociomantic_darren.sonartestwebviewapp.utils;

import android.content.Context;
import android.util.Log;
import com.sociomantic.Sociomantic;
import com.sociomantic.Sociomantic.Region;
import com.sociomantic.SociomanticCustomer;
import com.sociomantic.SociomanticProduct;
import com.sociomantic.SociomanticTransaction;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sociomantic_Darren on 4/28/16.
 */

public class SociomanticUtils {
    private static String CLIENT_TOKEN = null;
    private static Region REGION = null;
    private static SociomanticUtils utils;
    private Context mContext;
    private Sociomantic sociomantic;

    static {
        CLIENT_TOKEN = "adv-test-eu";
        REGION = Region.EU;
    }

    public static SociomanticUtils instance() {
        if (utils == null) {
            utils = new SociomanticUtils();
        }
        return utils;
    }

    public void setContext(Context context) {
        this.mContext = context;
        this.sociomantic = new Sociomantic(context, CLIENT_TOKEN, REGION);
    }

    public void reportView() {
        this.sociomantic.reportView();
    }

    public void reportCategory(String[] category) {
        List<String> categories = Arrays.asList(category);
        this.sociomantic.reportCategories(categories);
    }

    public void reportProduct(String productInfo) throws JSONException {
        // convert json string to object by using Gson
        /*
        Gson gson = new Gson();
        Product product = gson.fromJson(productInfo, Product.class);
        */

        JSONObject productObj = new JSONObject(productInfo);

        // convert string array to arraylist
        String regex = "\\[|\\]";
        String categoriesStr = productObj.getString("category");
        categoriesStr = categoriesStr.replaceAll(regex, "");
        String[] categories = categoriesStr.replace("\"", "").split(",");
        List<String> categoriesList = Arrays.asList(categories);

        SociomanticProduct sociomanticProduct = new SociomanticProduct(productObj.getString("identifier"));
        sociomanticProduct.setBrand(productObj.getString("brand"));
        sociomanticProduct.setProductName(productObj.getString("fn"));
        sociomanticProduct.setCategories(categoriesList);
        sociomanticProduct.setAmount(Float.valueOf(productObj.getString("amount")));
        sociomanticProduct.setPrice(Float.valueOf(productObj.getString("price")));
        sociomanticProduct.setCurrency(productObj.getString("currency"));
        sociomanticProduct.setProductUrl(productObj.getString("url"));
        sociomanticProduct.setImageUrl(productObj.getString("photo"));
        sociomanticProduct.setDescription(productObj.getString("description"));
        sociomanticProduct.setValidUntil(Long.valueOf(productObj.getString("valid")));
        this.sociomantic.reportProductView(sociomanticProduct);
    }

    public void reportBasket(String basketInfo) throws JSONException {
        this.sociomantic.reportAddToBasket(setBasketProducts(basketInfo));
    }

    public void reportSale(String transactionInfo) throws JSONException {

        JSONObject transactionObj = new JSONObject(transactionInfo);

        /*
        SociomanticCustomer simpleCustomer = getSimpleCustomer();
        simpleCustomer.setEmail(transaction.getEmail());
        this.sociomantic.setCustomer(simpleCustomer);
        */

        SociomanticTransaction transaction = new SociomanticTransaction(transactionObj.getString("transaction"), setBasketProducts(transactionInfo));
        transaction.setAmount(Float.valueOf(transactionObj.getString("amount")));
        transaction.setCurrency(transactionObj.getString("currency"));
        this.sociomantic.reportTransaction(transaction);
    }

    private List<SociomanticProduct> setBasketProducts(String basketInfo) throws JSONException {
        String identifier;
        String currency;
        String amount;
        int quantity;

        JSONObject jsonObj = new JSONObject(basketInfo);

        JSONArray products = jsonObj.getJSONArray("products");

        // Iterate the products object to add product into an array
        List productList = new ArrayList();

        for (int i = 0 ; i < products.length(); i++) {

            JSONObject obj = products.getJSONObject(i);
            
            identifier =    obj.getString("identifier");
            currency =      obj.getString("currency");
            amount =        obj.getString("amount");
            quantity =      obj.getInt("quantity");

            productList.add(setSingleProduct(identifier, currency, amount, quantity));
        }

        return productList;
    }

    private SociomanticProduct setSingleProduct(String id, String currency, String amount, int quantity) {
        SociomanticProduct sociomanticProduct = new SociomanticProduct(id);
        sociomanticProduct.setAmount(Float.valueOf(amount));
        sociomanticProduct.setCurrency(currency);
        sociomanticProduct.setQuantity(quantity);
        return sociomanticProduct;
    }

    private SociomanticCustomer getSimpleCustomer() {
        SociomanticCustomer sociomanticCustomer = new SociomanticCustomer();
        return sociomanticCustomer;
    }
}

