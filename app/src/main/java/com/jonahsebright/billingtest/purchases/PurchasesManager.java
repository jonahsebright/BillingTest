package com.jonahsebright.billingtest.purchases;

import androidx.annotation.NonNull;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.jonahsebright.billingtest.purchases.buy.BuyProducts;
import com.jonahsebright.billingtest.purchases.load.LoadProducts;

import java.util.List;

public class PurchasesManager {
    private final LoadProducts loadProducts;
    private final BuyProducts buyProducts;
    private final BillingClient billingClient;
    private PurchasesFetchedListener purchasesFetchedListener = PurchasesFetchedListener.NONE();
    private List<Purchase> purchaseList;

    public PurchasesManager(BillingClient billingClient, BuyProducts buyProducts, LoadProducts loadProducts) {
        this.billingClient = billingClient;
        this.buyProducts = buyProducts;
        this.loadProducts = loadProducts;
    }

    public void startConnectionToGooglePlay() {
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                System.out.println("Pay.onBillingSetupFinished: ");
                int responseCode = billingResult.getResponseCode();
                boolean billingUnavailable = responseCode == BillingClient.BillingResponseCode.BILLING_UNAVAILABLE;
                System.out.println("billingUnavailable = " + billingUnavailable);
                String debugMessage = billingResult.getDebugMessage();
                System.out.println("debugMessage = " + debugMessage);
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    loadProducts.queryInAppProducts();
                    loadProducts.querySubs();
                }

                getPurchaseList();
            }

            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to Google Play
                startConnectionToGooglePlay();
            }
        });
    }

    public void setPurchasesFetchedListener(@NonNull PurchasesFetchedListener purchasesFetchedListener) {
        this.purchasesFetchedListener = purchasesFetchedListener;
        if (purchaseList != null) purchasesFetchedListener.onPurchasesFetched(purchaseList);
    }

    private void getPurchaseList() {
        Purchase.PurchasesResult purchasesResult = billingClient.queryPurchases(BillingClient.SkuType.INAPP);
        int responseCode = purchasesResult.getResponseCode();
        System.out.println("responseCode = " + responseCode);
        boolean disconnectedFromService = responseCode == BillingClient.BillingResponseCode.SERVICE_DISCONNECTED;
        System.out.println("disconnectedFromService = " + disconnectedFromService);
        List<Purchase> purchaseList = purchasesResult.getPurchasesList();
        System.out.println("purchasesList = " + purchaseList);
        if (responseCode == BillingClient.BillingResponseCode.OK) {
            this.purchaseList = purchaseList;
            purchasesFetchedListener.onPurchasesFetched(purchaseList);
        }
    }

    public LoadProducts getLoadProducts() {
        return loadProducts;
    }

    public BuyProducts getBuyProducts() {
        return buyProducts;
    }

    public BillingClient getBillingClient() {
        return billingClient;
    }
}
