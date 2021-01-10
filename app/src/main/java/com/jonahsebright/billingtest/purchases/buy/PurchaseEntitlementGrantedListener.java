package com.jonahsebright.billingtest.purchases.buy;

import com.android.billingclient.api.Purchase;
import com.jonahsebright.billingtest.util.IdContainer;

public abstract class PurchaseEntitlementGrantedListener implements IdContainer {
    private String productId;

    public PurchaseEntitlementGrantedListener(String productId) {
        this.productId = productId;
    }

    @Override
    public String getId() {
        return productId;
    }

    public abstract void onEntitlementGranted(Purchase purchase);
}
