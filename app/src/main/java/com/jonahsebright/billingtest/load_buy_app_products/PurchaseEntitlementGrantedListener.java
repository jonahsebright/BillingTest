package com.jonahsebright.billingtest.load_buy_app_products;

import com.android.billingclient.api.Purchase;
import com.jonahsebright.billingtest.util.IdContainer;

public abstract class PurchaseEntitlementGrantedListener implements IdContainer {
    private String productId;

    public PurchaseEntitlementGrantedListener(String productId) {
        this.productId = productId;
    }

    @Override
    public String getProductId() {
        return productId;
    }

    public abstract void onEntitlementGranted(Purchase purchase);
}
