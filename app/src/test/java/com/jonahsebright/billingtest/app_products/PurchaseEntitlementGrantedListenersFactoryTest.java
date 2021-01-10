package com.jonahsebright.billingtest.app_products;

import com.jonahsebright.billingtest.purchases.PurchaseEntitlementGrantedListenersFactory;
import com.jonahsebright.billingtest.purchases.buy.PurchaseEntitlementGrantedListener;
import com.jonahsebright.billingtest.purchases.products.AppProducts;
import com.jonahsebright.billingtest.purchases.products.gems.GemsChangedListener;
import com.jonahsebright.billingtest.purchases.products.gems.GemsConsumedHandler;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class PurchaseEntitlementGrantedListenersFactoryTest {

    @Test
    void createAll() {
        ArrayList<PurchaseEntitlementGrantedListener> expected = new ArrayList<>();
        GemsChangedListener gemsChangedListener = new GemsChangedListener() {
            @Override
            public void onGemsChanged(int gems) {

            }
        };
        expected.add(new GemsConsumedHandler(AppProducts.Product.TEST_1, gemsChangedListener, null));
        assertThat(PurchaseEntitlementGrantedListenersFactory.createAll(gemsChangedListener, null))
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}