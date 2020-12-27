package com.jonahsebright.billingtest.launchpurchaseflow;

import android.app.Activity;
import android.util.Log;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.SkuDetails;

public class LaunchPurchaseFlow {
    private static final String TAG = "LaunchPurchaseFlow";

    public BillingFlowParams buildBillingFlowParams(SkuDetails skuDetails) {
        return BillingFlowParams.newBuilder()
                .setSkuDetails(skuDetails)
                .build();
    }

    public void launchBillingFlow(BillingClient billingClient, Activity activity, SkuDetails skuDetails){
        BillingResult billingResult = billingClient.launchBillingFlow(activity, buildBillingFlowParams(skuDetails));
        int responseCode = billingResult.getResponseCode();
        if(responseCode == BillingClient.BillingResponseCode.OK){
            System.out.println( "launchBillingFlow: successful launching of billing result");
            //handle
        }else {
            String message = billingResult.getDebugMessage();
            System.out.println("debug message launch billing = " + message);
        }
    }
}
