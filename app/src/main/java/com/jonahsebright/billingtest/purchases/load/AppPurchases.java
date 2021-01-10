package com.jonahsebright.billingtest.purchases.load;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClient.BillingResponseCode;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.jonahsebright.billingtest.purchases.buy.PurchaseEntitlementGrantedListener;
import com.jonahsebright.billingtest.purchases.products.ProductTypeIdentifier;
import com.jonahsebright.billingtest.util.IdContainer;

import java.util.ArrayList;
import java.util.List;

import static com.jonahsebright.billingtest.purchases.products.AppProducts.getSubscriptionSkuList;

/**
 * Controller of the Pay use-case
 * <p>
 * see https://developer.android.com/google/play/billing/integrate#initialize
 */
public class AppPurchases implements PurchasesUpdatedListener {

    private BillingClient billingClient;

    private ArrayList<PurchaseEntitlementGrantedListener> entitlementGrantedListeners;

    public AppPurchases(ArrayList<PurchaseEntitlementGrantedListener> entitlementGrantedListeners) {
        this.entitlementGrantedListeners = entitlementGrantedListeners;
    }

    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> purchases) {
        // see https://developer.android.com/google/play/billing/integrate#launch
        // see https://developer.android.com/google/play/billing/integrate#process
        if (billingResult.getResponseCode() == BillingResponseCode.OK
                && purchases != null) {
            for (Purchase purchase : purchases) {
                handlePurchase(purchase);
            }
        } else if (billingResult.getResponseCode() == BillingResponseCode.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
        } else {
            // Handle any other error codes.
        }
    }

    void handlePurchase(Purchase purchase) {
        // Purchase retrieved from BillingClient#queryPurchases or your PurchasesUpdatedListener.

        // Verify the purchase.
        // Ensure entitlement was not already granted for this purchaseToken.
        // Grant entitlement to the user.

        String productId = purchase.getSku();
        if (ProductTypeIdentifier.isConsumable(productId))
            grantEntitlementToConsumableAndConsume(purchase);
        else if (ProductTypeIdentifier.isNonConsumable(productId))
            grantEntitlementToNonConsumable(purchase);
        else if (ProductTypeIdentifier.isSubscription(productId)) {
            grantEntitlementToSubscription(purchase);
        }
    }

    private void grantEntitlementToSubscription(Purchase purchase) {
        //TODO
    }

    private void grantEntitlementToNonConsumable(Purchase purchase) {
        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
            if (!purchase.isAcknowledged()) {
                AcknowledgePurchaseParams acknowledgePurchaseParams =
                        AcknowledgePurchaseParams.newBuilder()
                                .setPurchaseToken(purchase.getPurchaseToken())
                                .build();
                billingClient.acknowledgePurchase(acknowledgePurchaseParams, new AcknowledgePurchaseResponseListener() {
                    @Override
                    public void onAcknowledgePurchaseResponse(@NonNull BillingResult billingResult) {
                        notifyEntitlementGrantedListener(purchase);
                    }
                });
            }
        }
    }

    private void grantEntitlementToConsumableAndConsume(Purchase purchase) {
        ConsumeParams consumeParams =
                ConsumeParams.newBuilder()
                        .setPurchaseToken(purchase.getPurchaseToken())
                        .build();

        ConsumeResponseListener listener = new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(BillingResult billingResult, String purchaseToken) {
                if (billingResult.getResponseCode() == BillingResponseCode.OK) {
                    // Handle the success of the consume operation.
                    notifyEntitlementGrantedListener(purchase);
                }
            }
        };
        billingClient.consumeAsync(consumeParams, listener);
    }

    private void notifyEntitlementGrantedListener(Purchase purchase) {
        PurchaseEntitlementGrantedListener listener = IdContainer.findItem(
                purchase.getSku(), entitlementGrantedListeners);
        if (listener != null) {
            listener.onEntitlementGranted(purchase);
        } else {
            //TODO: tell user granting entitlement failed
        }
    }

    public static SkuDetailsParams getSubsSkuDetailsParams() {
        return SkuDetailsParams.newBuilder()
                .setSkusList(getSubscriptionSkuList())
                .setType(BillingClient.SkuType.SUBS)
                .build();
    }

    public static class NoProductsQueriedListener implements ProductsQueriedListener {
        @Override
        public void onQueried(List<SkuDetails> skuDetailsList) {

        }
    }
}
