package com.jonahsebright.billingtest.purchases;

import android.content.Context;

import com.jonahsebright.billingtest.purchases.buy.PurchaseEntitlementGrantedListener;
import com.jonahsebright.billingtest.purchases.products.AppProducts;
import com.jonahsebright.billingtest.purchases.products.gems.GemsChangedListener;
import com.jonahsebright.billingtest.purchases.products.gems.GemsConsumedHandler;
import com.jonahsebright.billingtest.util.storage.SharedPreferenceHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class PurchaseEntitlementGrantedListenersFactory {
    public static ArrayList<PurchaseEntitlementGrantedListener> createAll(
            GemsChangedListener gemsChangedListener, Context context) {
        SharedPreferenceHelper sharedPreferenceHelper = new SharedPreferenceHelper(context);
        return new ArrayList<>(Arrays.asList(
                new GemsConsumedHandler(AppProducts.Product.TEST_1, gemsChangedListener, sharedPreferenceHelper)
        ));
    }
}
