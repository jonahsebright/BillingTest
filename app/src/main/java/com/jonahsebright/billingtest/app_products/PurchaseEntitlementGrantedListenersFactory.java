package com.jonahsebright.billingtest.app_products;

import android.content.Context;

import com.jonahsebright.billingtest.app_products.gems.GemsChangedListener;
import com.jonahsebright.billingtest.app_products.gems.GemsConsumedHandler;
import com.jonahsebright.billingtest.load_buy_app_products.PurchaseEntitlementGrantedListener;
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
