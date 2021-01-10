package com.jonahsebright.billingtest.purchases.buy;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.Purchase;
import com.jonahsebright.billingtest.util.IdContainer;
import com.jonahsebright.billingtest.util.verification.AsyncVerifiable;

/**
 * @see <a https://developer.android.com/google/play/billing/integrate#life></a>
 */
public interface PurchaseHandler extends AsyncVerifiable<Purchase>, IdContainer {

    void entitlementAlreadyGranted(Purchase purchase);

    void grantEntitlement(Purchase purchase, BillingClient billingClient);

    void onAcknowledgedOrConsumed(Purchase purchase);
}
