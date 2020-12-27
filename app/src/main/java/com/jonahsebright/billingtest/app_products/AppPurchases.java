package com.jonahsebright.billingtest.app_products;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClient.BillingResponseCode;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Controller of the Pay use-case
 */
public class AppPurchases implements SkuDetailsResponseListener {
    private PurchasesUpdatedListener purchasesUpdatedListener;
    private BillingClient billingClient;
    private InAppProductsQueriedListener inAppProductsQueriedListener;
    private List<SkuDetails> skuDetailsList;

    public AppPurchases(@NonNull Context context) {
        inAppProductsQueriedListener = new NoInAppProductsQueriedListener();
        initPurchaseUpdatedListener();
        initBillingClient(context);
    }

    public List<SkuDetails> getSkuDetailsList() {
        return skuDetailsList;
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
        billingClient.querySkuDetailsAsync(getSkuDetailsParams(), this);
    }

    private void initBillingClient(@NonNull Context context) {
        billingClient = BillingClient.newBuilder(context)
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();
    }

    private void initPurchaseUpdatedListener() {
        purchasesUpdatedListener = new PurchasesUpdatedListener() {
            @Override
            public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {

            }
        };
    }

    public PurchasesUpdatedListener getPurchasesUpdatedListener() {
        return purchasesUpdatedListener;
    }

    public BillingClient getBillingClient() {
        return billingClient;
    }

    public ArrayList<String> getSkuList() {
        return new ArrayList<>(Arrays.asList(
                "product_2_test",
                "product_1_test",
                "upgrade_premium"
        ));
    }

    public SkuDetailsParams getSkuDetailsParams() {
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(getSkuList()).setType(BillingClient.SkuType.INAPP);
        return params.build();
    }

    @Override
    public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> list) {
        System.out.println("LoadInAppPurchases.onSkuDetailsResponse");
        int responseCode = billingResult.getResponseCode();
        boolean billingUnavailable = responseCode == BillingResponseCode.BILLING_UNAVAILABLE;
        System.out.println("billingUnavailable = " + billingUnavailable);
        String debugMessage = billingResult.getDebugMessage();
        System.out.println("debugMessage = " + debugMessage);
        this.skuDetailsList = list;
        inAppProductsQueriedListener.onQueried(list);
    }

    public void setInAppProductsQueriedListener(InAppProductsQueriedListener inAppProductsQueriedListener) {
        this.inAppProductsQueriedListener = inAppProductsQueriedListener;
    }

    public InAppProductsQueriedListener getInAppProductsQueriedListener() {
        return inAppProductsQueriedListener;
    }

    public static class NoInAppProductsQueriedListener implements InAppProductsQueriedListener {
        @Override
        public void onQueried(List<SkuDetails> skuDetailsList) {

        }
    }
}