package com.example.sociomantic_darren.sonartestwebviewapp.utils;

import android.app.Activity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import com.example.sociomantic_darren.sonartestwebviewapp.utils.SociomanticUtils;
import org.json.JSONException;

/**
 * Created by Sociomantic_Darren on 4/28/16.
 */

public class JavascriptInterfaceUtils {

    private Activity activity;

    public JavascriptInterfaceUtils(Activity activity) {

        this.activity = activity;
    }

    @JavascriptInterface
    public void reportSociomanticView(){
        SociomanticUtils.instance().reportView();

    }

    @JavascriptInterface
    public void reportSociomanticCategoryView(String list){
        String[] categories = list.split(",");
        SociomanticUtils.instance().reportCategory(categories);

    }

    @JavascriptInterface
    public void reportSociomanticProductView(String productInfo) throws JSONException {
        SociomanticUtils.instance().reportProduct(productInfo);

    }

    @JavascriptInterface
    public void reportSociomanticBasketView(String basketInfo) throws JSONException {
        SociomanticUtils.instance().reportBasket(basketInfo);

    }

    @JavascriptInterface
    public void reportSociomanticTransactionView(String transactionInfo) throws JSONException {
        SociomanticUtils.instance().reportSale(transactionInfo);

    }

}
