package com.jonahsebright.billingtest;

import android.content.Context;

import androidx.test.InstrumentationRegistry;

import com.android.billingclient.api.SkuDetails;

import org.json.JSONException;

public class TestUtils {
    public static Context getAppContext() {
        return InstrumentationRegistry.getTargetContext();
    }

    public static SkuDetails mockInAppSkuDetails(String productId, String name,
                                                 String price, String price_amount_micros, String currencyCode, String descr) throws JSONException {
        return new SkuDetails(
                "{\"productId\":\"" + productId +
                        "\",\"type\":\"" + "IN_APP" +
                        "\"," +
                        "\"title\":\"" + name +
                        "\",\"price\":\"" + price +
                        "\",\"price_amount_micros\":\"" + price_amount_micros +
                        "\"," +
                        "\"price_currency_code\":\"" + currencyCode +
                        "\",\"description\":\"" + descr +
                        "\"}");
    }

    public static SkuDetails mockSubscriptionSkuDetails(String productId, String name,
                                                        String price, String price_amount_micros, String currencyCode, String descr,
                                                        String subscriptionPeriod) throws JSONException {
        return new SkuDetails("{\"productId\":\"" + productId +
                "\",\"type\":\"" + "SUBS" +
                "\"," +
                "\"title\":\"" + name +
                "\",\"price\":\"" + price +
                "\",\"price_amount_micros\":\"" + price_amount_micros +
                "\",\"price_currency_code\":\"" + currencyCode +
                "\",\"description\":\"" + descr +
                "\",\"subscriptionPeriod\":\"" + subscriptionPeriod +
                "\"}");
    }
}
