package com.jonahsebright.billingtest.purchases.buy;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;

/**
 * @see <a>https://developer.android.com/reference/com/android/billingclient/api/ConsumeResponseListener</a>
 */
public abstract class ConsumablePurchaseHandler extends PurchaseHandlerImpl implements ConsumeResponseListener {

    protected ConsumablePurchaseHandler(String productId) {
        super(productId);
    }

    @Override
    public void grantEntitlement(Purchase purchase, BillingClient billingClient) {
        ConsumeParams consumeParams =
                ConsumeParams.newBuilder()
                        .setPurchaseToken(purchase.getPurchaseToken())
                        .build();

        billingClient.consumeAsync(consumeParams, this);
    }
}
