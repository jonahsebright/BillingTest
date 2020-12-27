package com.jonahsebright.billingtest;

import android.content.Context;

import androidx.test.InstrumentationRegistry;

import com.android.billingclient.api.SkuDetails;

import org.json.JSONException;

public class TestUtils {
    public static Context getAppContext() {
        return InstrumentationRegistry.getTargetContext();
    }

    public static SkuDetails mockSkuDetails(String productId, String type, String name,
                                            String price, String price_amount_micros, String currencyCode, String descr) throws JSONException {
        return new SkuDetails("{\"productId\":\"" + productId +
                "\",\"type\":\"" + type +
                "\"," +
                "\"title\":\"" + name +
                "\",\"price\":\"" + price +
                "\",\"price_amount_micros\":\"" + price_amount_micros +
                "\"," +
                "\"price_currency_code\":\"" + currencyCode +
                "\",\"description\":\"" + descr +
                "\"}");
    }
}
