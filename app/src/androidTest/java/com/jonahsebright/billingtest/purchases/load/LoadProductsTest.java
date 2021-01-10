package com.jonahsebright.billingtest.purchases.load;

import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.SkuDetails;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoadProductsTest {
    private LoadProducts loadProducts;

    @Before
    public void setUp() throws Exception {
        loadProducts = new LoadProducts(null);
    }

    @Test
    public void OnSkuDetailsResponseCalls_InAppProductsQueriedListener_and_saves_queriedListAsMember() throws Exception {
        final ArrayList<SkuDetails> mockSkuDetails = new ArrayList<>(Arrays.asList(
                new SkuDetails("{\"productId\":\"foo\",\"type\":\"IN_APP\",\"price\":\"3.50\"}"),
                new SkuDetails("{\"productId\":\"bar\",\"type\":\"IN_APP\",\"price\":\"1.20\"}")
        ));
        final boolean[] whereQueried = {false};
        loadProducts.setInAppProductsQueriedListener(new ProductsQueriedListener() {
            @Override
            public void onQueried(List<SkuDetails> skuDetailsList) {
                assertEquals(mockSkuDetails, skuDetailsList);
                whereQueried[0] = true;
            }
        });

        loadProducts.getInAppSkuDetailsResponseListener().onSkuDetailsResponse(
                BillingResult.newBuilder().build(),
                mockSkuDetails);
        assertTrue(whereQueried[0]);
        assertThat(loadProducts.getInAppSkuDetailsList());
    }
}