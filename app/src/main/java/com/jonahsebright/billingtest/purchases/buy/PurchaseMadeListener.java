package com.jonahsebright.billingtest.purchases.buy;

import com.android.billingclient.api.Purchase;

public interface PurchaseMadeListener {
    void onMade(Purchase purchase);
}
