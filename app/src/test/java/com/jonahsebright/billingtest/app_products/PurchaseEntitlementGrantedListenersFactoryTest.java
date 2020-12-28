package com.jonahsebright.billingtest.app_products;

import com.jonahsebright.billingtest.app_products.gems.GemsChangedListener;
import com.jonahsebright.billingtest.app_products.gems.GemsConsumedHandler;
import com.jonahsebright.billingtest.load_buy_app_products.PurchaseEntitlementGrantedListener;

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