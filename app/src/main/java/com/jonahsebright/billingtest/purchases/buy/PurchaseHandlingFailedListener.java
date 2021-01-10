package com.jonahsebright.billingtest.purchases.buy;

import com.android.billingclient.api.Purchase;

public interface PurchaseHandlingFailedListener {
    void onHandleFailed(Purchase purchase);
}
