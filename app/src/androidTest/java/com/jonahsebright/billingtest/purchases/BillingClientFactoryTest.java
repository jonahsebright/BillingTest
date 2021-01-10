package com.jonahsebright.billingtest.purchases;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.jonahsebright.billingtest.TestUtils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BillingClientFactoryTest {
    @Test
    public void createsBillingClient() throws Exception {
        PurchasesUpdatedListener purchasesUpdatedListener = (billingResult, list) -> {
        };
        BillingClient expected = BillingClient.newBuilder(TestUtils.getAppContext())
                .enablePendingPurchases()
                .setListener(purchasesUpdatedListener)
                .build();
        assertThat(BillingClientFactory.createBillingClient(TestUtils.getAppContext(), purchasesUpdatedListener))
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}