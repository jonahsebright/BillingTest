package com.jonahsebright.billingtest.load_buy_app_products;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClient.BillingResponseCode;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.jonahsebright.billingtest.app_products.AppProducts;
import com.jonahsebright.billingtest.app_products.ProductTypeIdentifier;
import com.jonahsebright.billingtest.util.IdContainer;

import java.util.ArrayList;
import java.util.List;

import static com.jonahsebright.billingtest.app_products.AppProducts.getSubscriptionSkuList;

/**
 * Controller of the Pay use-case
 * <p>
 * see https://developer.android.com/google/play/billing/integrate#initialize
 */
public class AppPurchases implements PurchasesUpdatedListener {

    private BillingClient billingClient;
    private ProductsQueriedListener inAppProductsQueriedListener;
    private ProductsQueriedListener subsQueriedListener;
    private List<SkuDetails> inAppSkuDetailsList;
    private List<SkuDetails> subsSkuDetailsList;
    private ArrayList<PurchaseEntitlementGrantedListener> entitlementGrantedListeners;
    private SkuDetailsResponseListener inAppSkuDetailsResponseListener;
    private SkuDetailsResponseListener subsSkuDetailsResponseListener;

    public AppPurchases(@NonNull Context context, ArrayList<PurchaseEntitlementGrantedListener> entitlementGrantedListeners) {
        this.entitlementGrantedListeners = entitlementGrantedListeners;
        inAppProductsQueriedListener = new NoProductsQueriedListener();
        subsQueriedListener = new NoProductsQueriedListener();
        initSkuDetailsResponseListeners();
        initBillingClient(context);
    }

    private void initSkuDetailsResponseListeners() {
        inAppSkuDetailsResponseListener = new SkuDetailsResponseListener() {
            @Override
            public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> list) {
                inAppSkuDetailsList = list;
                inAppProductsQueriedListener.onQueried(list);
            }
        };
        subsSkuDetailsResponseListener = new SkuDetailsResponseListener() {
            @Override
            public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> list) {
                subsSkuDetailsList = list;
                subsQueriedListener.onQueried(list);
            }
        };
    }

    public SkuDetailsResponseListener getInAppSkuDetailsResponseListener() {
        return inAppSkuDetailsResponseListener;
    }

    public SkuDetailsResponseListener getSubsSkuDetailsResponseListener() {
        return subsSkuDetailsResponseListener;
    }

    public List<SkuDetails> getInAppSkuDetailsList() {
        return inAppSkuDetailsList;
    }

    public List<SkuDetails> getSubsSkuDetailsList() {
        return subsSkuDetailsList;
    }

    public static BillingFlowParams buildBillingFlowParams(SkuDetails skuDetails) {
        return BillingFlowParams.newBuilder()
                .setSkuDetails(skuDetails)
                .build();
    }

    public void launchBillingFlow(Activity activity, SkuDetails skuDetails) {
        //see https://developer.android.com/google/play/billing/integrate#launch
        BillingResult billingResult = billingClient.launchBillingFlow(activity, buildBillingFlowParams(skuDetails));
        int responseCode = billingResult.getResponseCode();
        if (responseCode == BillingResponseCode.OK) {
            System.out.println("launchBillingFlow: successful launching of billing result");
            //handle
        } else {
            String message = billingResult.getDebugMessage();
            System.out.println("debug message launch billing = " + message);
        }
    }

    public void startConnectionToGooglePlay() {
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                System.out.println("Pay.onBillingSetupFinished: ");
                int responseCode = billingResult.getResponseCode();
                boolean billingUnavailable = responseCode == BillingResponseCode.BILLING_UNAVAILABLE;
                System.out.println("billingUnavailable = " + billingUnavailable);
                String debugMessage = billingResult.getDebugMessage();
                System.out.println("debugMessage = " + debugMessage);
                if (billingResult.getResponseCode() == BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    queryInAppPurchases();
                    querySubs();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to Google Play
                startConnectionToGooglePlay();
            }
        });
    }

    private void queryInAppPurchases() {
        //See https://developer.android.com/google/play/billing/integrate#show-products
        billingClient.querySkuDetailsAsync(getInAppSkuDetailsParams(), inAppSkuDetailsResponseListener);
    }

    private void querySubs() {
        billingClient.querySkuDetailsAsync(getSubsSkuDetailsParams(), subsSkuDetailsResponseListener);
    }

    private void initBillingClient(@NonNull Context context) {
        billingClient = BillingClient.newBuilder(context)
                .setListener(this)
                .enablePendingPurchases()
                .build();
    }

    public BillingClient getBillingClient() {
        return billingClient;
    }

    public static SkuDetailsParams getInAppSkuDetailsParams() {
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(AppProducts.getInAppSkuList()).setType(BillingClient.SkuType.INAPP);
        return params.build();
    }

    public void setInAppProductsQueriedListener(ProductsQueriedListener inAppProductsQueriedListener) {
        this.inAppProductsQueriedListener = inAppProductsQueriedListener;
    }

    public void setSubsQueriedListener(ProductsQueriedListener subsQueriedListener) {
        this.subsQueriedListener = subsQueriedListener;
    }

    public ProductsQueriedListener getInAppProductsQueriedListener() {
        return inAppProductsQueriedListener;
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
