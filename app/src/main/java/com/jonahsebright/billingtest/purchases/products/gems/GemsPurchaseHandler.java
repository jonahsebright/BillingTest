package com.jonahsebright.billingtest.purchases.products.gems;

import com.android.billingclient.api.Purchase;
import com.jonahsebright.billingtest.purchases.buy.ConsumablePurchaseHandler;
import com.jonahsebright.billingtest.util.OnCompleteListener;

public class GemsPurchaseHandler extends ConsumablePurchaseHandler {

    protected GemsPurchaseHandler(String productId) {
        super(productId);
    }

    @Override
    public void entitlementAlreadyGranted(Purchase purchase) {
        //TODO
    }

    @Override
    public void onAcknowledgedOrConsumed(Purchase purchase) {
        //TODO
    }

    @Override
    public void verify(Purchase toVerify, OnCompleteListener onCompleteListener) {
        //TODO
    }
}
