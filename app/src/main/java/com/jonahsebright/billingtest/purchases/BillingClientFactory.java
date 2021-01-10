package com.jonahsebright.billingtest.purchases;

import android.content.Context;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.PurchasesUpdatedListener;

public class BillingClientFactory {

    public static BillingClient createBillingClient(Context context, PurchasesUpdatedListener purchasesUpdatedListener) {
        return BillingClient.newBuilder(context)
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();
    }
}
