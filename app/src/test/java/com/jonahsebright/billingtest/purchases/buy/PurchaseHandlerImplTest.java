package com.jonahsebright.billingtest.purchases.buy;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.Purchase;
import com.jonahsebright.billingtest.util.OnCompleteListener;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PurchaseHandlerImplTest {

    @BeforeEach
    void setUp() {

    }

    @Test
    public void returnsProductIdWhenCallingGetId() throws Exception {
        PurchaseHandlerImpl purchaseHandler = new PurchaseHandlerImpl("randomProductId") {
            @Override
            public void entitlementAlreadyGranted(Purchase purchase) {

            }

            @Override
            public void grantEntitlement(Purchase purchase, BillingClient billingClient) {

            }

            @Override
            public void onAcknowledgedOrConsumed(Purchase purchase) {

            }

            @Override
            public void verify(Purchase toVerify, OnCompleteListener onCompleteListener) {

            }
        };
        assertEquals("randomProductId", purchaseHandler.getId());
    }
}