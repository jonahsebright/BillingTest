package com.jonahsebright.billingtest.app;

import android.app.Application;
import android.content.Context;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.Purchase;
import com.jonahsebright.billingtest.purchases.BillingClientFactory;
import com.jonahsebright.billingtest.purchases.PurchasesManager;
import com.jonahsebright.billingtest.purchases.buy.BuyProducts;
import com.jonahsebright.billingtest.purchases.buy.PurchaseHandlingFailedListener;
import com.jonahsebright.billingtest.purchases.load.LoadProducts;

import java.util.ArrayList;

public class BillingTestApplication extends Application {
    private static PurchasesManager purchasesManager;

    public static PurchasesManager getPurchasesManager(Context context) {
        if (purchasesManager == null) {
            initPurchaseManager(context);
            purchasesManager.startConnectionToGooglePlay();
        }
        return purchasesManager;
    }

    public static LoadProducts getLoadProducts(Context context) {
        return getPurchasesManager(context).getLoadProducts();
    }

    public static BuyProducts getBuyProducts(Context context) {
        return getPurchasesManager(context).getBuyProducts();
    }

    public static BillingClient getBillingClient(Context context) {
        return getPurchasesManager(context).getBillingClient();
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    private static void initPurchaseManager(Context context) {
        BuyProducts buyProducts = new BuyProducts(new ArrayList<>(),/*TODO*/new PurchaseHandlingFailedListener() {
            @Override
            public void onHandleFailed(Purchase purchase) {

            }
        });
        BillingClient billingClient = BillingClientFactory
                .createBillingClient(context, buyProducts);
        buyProducts.setBillingClient(billingClient);
        LoadProducts loadProducts = new LoadProducts(billingClient);
        purchasesManager = new PurchasesManager(billingClient, buyProducts, loadProducts);
    }
}
