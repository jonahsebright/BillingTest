package com.jonahsebright.billingtest.purchases.buy;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.jonahsebright.billingtest.util.OnCompleteListener;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BuyProductsTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void createsCorrectBillingFlowParams() throws Exception {
        SkuDetails skuDetails = mockInAppSkuDetails("foo_id", "Foo", "3.50 CHF",
                3500000, "CHF", "bar");
        BillingFlowParams expected = BillingFlowParams.newBuilder()
                .setSkuDetails(skuDetails)
                .build();
        assertThat(BuyProducts.buildBillingFlowParams(skuDetails))
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private SkuDetails mockInAppSkuDetails(String id, String title, String price, long priceMicros, String currencyCode, String description) {
        SkuDetails skuDetails = mock(SkuDetails.class);
        when(skuDetails.getSku()).thenReturn(id);
        when(skuDetails.getTitle()).thenReturn(title);
        when(skuDetails.getPrice()).thenReturn(price);
        when(skuDetails.getPriceAmountMicros()).thenReturn(priceMicros);
        when(skuDetails.getPriceCurrencyCode()).thenReturn(currencyCode);
        return skuDetails;
    }

    @Test
    public void implementsPurchasesUpdatedListener() throws Exception {
        assertThat(new BuyProducts(null, new PurchaseHandlingFailedListener() {
            @Override
            public void onHandleFailed(Purchase purchase) {
            }
        })).isInstanceOf(PurchasesUpdatedListener.class);
    }

    @Test
    public void callingPurchasesUpdatedCalls_verify_on_PurchaseHandlers() throws Exception {
        final boolean[] handler1Called = {false};
        final boolean[] handler2Called = {false};
        Purchase purchase1 = mockPurchase("product1");
        Purchase purchase2 = mockPurchase("product2");
        PurchaseHandler purchaseHandler1 = new ConsumablePurchaseHandler("product1") {
            @Override
            public void entitlementAlreadyGranted(Purchase purchase) {
            }

            @Override
            public void onAcknowledgedOrConsumed(Purchase purchase) {
            }

            @Override
            public void verify(Purchase toVerify, OnCompleteListener onCompleteListener) {
                assertEquals("product1", toVerify.getSku());
                assertEquals("product1", getProductId());
                handler1Called[0] = true;
            }
        };
        PurchaseHandler purchaseHandler2 = new ConsumablePurchaseHandler("product2") {
            @Override
            public void entitlementAlreadyGranted(Purchase purchase) {
            }

            @Override
            public void onAcknowledgedOrConsumed(Purchase purchase) {
            }

            @Override
            public void verify(Purchase toVerify, OnCompleteListener onCompleteListener) {
                assertEquals("product2", toVerify.getSku());
                assertEquals("product2", getProductId());
                handler2Called[0] = true;
            }
        };
        ArrayList<PurchaseHandler> purchaseHandlers = new ArrayList<>(Arrays.asList(
                purchaseHandler1,
                purchaseHandler2
        ));
        BuyProducts buyProducts = new BuyProducts(purchaseHandlers, new PurchaseHandlingFailedListener() {
            @Override
            public void onHandleFailed(Purchase purchase) {
            }
        });

        List<Purchase> purchases = new ArrayList<>(Arrays.asList(
                purchase1,
                purchase2
        ));
        buyProducts.onPurchasesUpdated(OK_BILLING_RESULT(), purchases);
        assertTrue(handler1Called[0]);
        assertTrue(handler2Called[0]);
    }

    @Test
    public void callingPurchasesUpdatedWithPurchasesAndErrorResponseCodeDoesNotCallPurchaseMadeListener() throws Exception {
        final int[] numPurchasesMade = {0};
        BuyProducts buyProducts = new BuyProducts(new ArrayList<>(Collections.singletonList(
                new ConsumablePurchaseHandler("productFoo") {
                    @Override
                    public void entitlementAlreadyGranted(Purchase purchase) {

                    }

                    @Override
                    public void onAcknowledgedOrConsumed(Purchase purchase) {

                    }

                    @Override
                    public void verify(Purchase toVerify, OnCompleteListener onCompleteListener) {
                        numPurchasesMade[0]++;
                    }
                }
        )), new PurchaseHandlingFailedListener() {
            @Override
            public void onHandleFailed(Purchase purchase) {
            }
        });

        List<Purchase> purchases = new ArrayList<>();
        purchases.add(mockPurchase("productFoo"));
        buyProducts.onPurchasesUpdated(BILLING_RESULT(BillingClient.BillingResponseCode.ERROR), purchases);
        assertEquals(0, numPurchasesMade[0]);
    }

    @Test
    public void ifCannotFindPurchaseHandlerForPurchaseIdThePurchaseHandlingFailedListenerIsCalled() throws Exception {
        final int[] numPurchasesMade = {0};
        final int[] numHandlesFailed = {0};
        BuyProducts buyProducts = new BuyProducts(new ArrayList<>(Collections.singletonList(
                new ConsumablePurchaseHandler("productId") {
                    @Override
                    public void entitlementAlreadyGranted(Purchase purchase) {

                    }

                    @Override
                    public void onAcknowledgedOrConsumed(Purchase purchase) {

                    }

                    @Override
                    public void verify(Purchase toVerify, OnCompleteListener onCompleteListener) {
                        numPurchasesMade[0]++;
                    }
                }
        )), new PurchaseHandlingFailedListener() {

            @Override
            public void onHandleFailed(Purchase purchase) {
                numHandlesFailed[0]++;
            }
        });

        List<Purchase> purchases = new ArrayList<>();
        purchases.add(mockPurchase("productIdDifferent"));
        buyProducts.onPurchasesUpdated(BILLING_RESULT(BillingClient.BillingResponseCode.OK), purchases);
        assertEquals(0, numPurchasesMade[0]);
        assertEquals(1, numHandlesFailed[0]);
    }

    public static BillingResult BILLING_RESULT(@BillingClient.BillingResponseCode int code) {
        return BillingResult.newBuilder()
                .setResponseCode(code)
                .build();
    }

    public static BillingResult OK_BILLING_RESULT() {
        return BILLING_RESULT(BillingClient.BillingResponseCode.OK);
    }

    private Purchase mockPurchase(String id) throws JSONException {
        Purchase purchase = mock(Purchase.class);
        when(purchase.getSku()).thenReturn(id);
        return purchase;
    }
}