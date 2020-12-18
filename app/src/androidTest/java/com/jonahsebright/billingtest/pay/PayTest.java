package com.jonahsebright.billingtest.pay;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.jonahsebright.billingtest.TestUtils;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PayTest {

    private Pay pay;

    @Before
    public void setUp() throws Exception {
        pay = new Pay(TestUtils.getAppContext());
    }

    @Test
    public void Create_NoInAppProductsQueriedListenerOnInit() throws Exception {
        assertThat(pay.getInAppProductsQueriedListener()).isInstanceOf(Pay.NoInAppProductsQueriedListener.class);
    }

    @Test
    public void createsPurchaseUpdateListenerInConstructor() throws Exception {
        assertThat(pay.getPurchasesUpdatedListener())
                .usingRecursiveComparison()
                .isEqualTo(new PurchasesUpdatedListener() {
                    @Override
                    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {

                    }
                }).isInstanceOf(PurchasesUpdatedListener.class);
    }

    @Test
    public void initialisesBillingClient() throws Exception {
        BillingClient expected = BillingClient.newBuilder(TestUtils.getAppContext())
                .enablePendingPurchases()
                .setListener(pay.getPurchasesUpdatedListener())
                .build();
        assertThat(pay.getBillingClient())
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void getSkuList() throws Exception {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("upgrade_premium");
        assertThat(pay.getSkuList())
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void getSkuDetailsParamsForInAppPurchases() throws Exception {
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder()
                .setSkusList(pay.getSkuList())
                .setType(BillingClient.SkuType.INAPP);
        assertThat(pay.getSkuDetailsParams())
                .usingRecursiveComparison()
                .isEqualTo(params.build());
    }

    @Test
    public void canSetInAppProductsQueriedListener() throws Exception {
        final ArrayList<SkuDetails> mockSkuDetails = new ArrayList<>(Arrays.asList(
                new SkuDetails("{\"productId\":\"foo\",\"type\":\"IN_APP\",\"price\":\"3.50\"}"),
                new SkuDetails("{\"productId\":\"bar\",\"type\":\"IN_APP\",\"price\":\"1.20\"}")
        ));
        final boolean[] whereQueried = {false};
        pay.setInAppProductsQueriedListener(new InAppProductsQueriedListener() {
            @Override
            public void onQueried(List<SkuDetails> skuDetailsList) {
                assertEquals(mockSkuDetails, skuDetailsList);
                whereQueried[0] = true;
            }
        });

        pay.onSkuDetailsResponse(
                BillingResult.newBuilder().build(),
                mockSkuDetails);
        assertTrue(whereQueried[0]);
    }
}