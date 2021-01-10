package com.jonahsebright.billingtest.purchases.buy;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.jonahsebright.billingtest.util.IdContainer;

import java.util.ArrayList;
import java.util.List;

public class BuyProducts implements PurchasesUpdatedListener {

    private BillingClient billingClient;
    private ArrayList<PurchaseHandler> purchaseHandlers;
    private PurchaseHandlingFailedListener purchaseHandlingFailedListener;

    public BuyProducts(ArrayList<PurchaseHandler> purchaseHandlers, PurchaseHandlingFailedListener purchaseHandlingFailedListener) {
        this.purchaseHandlers = purchaseHandlers;
        this.purchaseHandlingFailedListener = purchaseHandlingFailedListener;
    }

    public void setBillingClient(BillingClient billingClient) {
        this.billingClient = billingClient;
    }

    public static BillingFlowParams buildBillingFlowParams(SkuDetails skuDetails) {
        return BillingFlowParams.newBuilder()
                .setSkuDetails(skuDetails)
                .build();
    }

    public void launchBillingFlow(Activity activity, SkuDetails skuDetails) {
        //see https://developer.android.com/google/play/billing/integrate#launch
        BillingResult billingResult = billingClient.launchBillingFlow(activity, buildBillingFlowParams(skuDetails));
        int responseCode = billingResult.getResponseCode();
        if (responseCode == BillingClient.BillingResponseCode.OK) {
            System.out.println("launchBillingFlow: successful launching of billing result");
            //handle
        } else {
            String message = billingResult.getDebugMessage();
            System.out.println("debug message launch billing = " + message);
        }
    }


    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> purchases) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
                && purchases != null) {
            for (Purchase purchase : purchases) {
                PurchaseHandler purchaseHandler = IdContainer.findItem(purchase.getSku(), purchaseHandlers);
                if (purchaseHandler == null)
                    purchaseHandlingFailedListener.onHandleFailed(purchase);
                else
                    purchaseHandler.verify(purchase, null);
            }
        }
    }
}
