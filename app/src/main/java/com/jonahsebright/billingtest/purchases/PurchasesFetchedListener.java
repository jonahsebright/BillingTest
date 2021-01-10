package com.jonahsebright.billingtest.purchases;

import com.android.billingclient.api.Purchase;

import java.util.List;

public interface PurchasesFetchedListener {
    void onPurchasesFetched(List<Purchase> purchases);

    static PurchasesFetchedListener NONE() {
        return new PurchasesFetchedListener() {
            @Override
            public void onPurchasesFetched(List<Purchase> purchases) {
                //do nothing
            }
        };
    }
}
