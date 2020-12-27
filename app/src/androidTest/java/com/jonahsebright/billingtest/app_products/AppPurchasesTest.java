package com.jonahsebright.billingtest.app_products;

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

public class AppPurchasesTest {

    private AppPurchases appPurchases;

    @Before
    public void setUp() throws Exception {
        appPurchases = new AppPurchases(TestUtils.getAppContext());
    }

    @Test
    public void Create_NoInAppProductsQueriedListenerOnInit() throws Exception {
        assertThat(appPurchases.getInAppProductsQueriedListener()).isInstanceOf(AppPurchases.NoInAppProductsQueriedListener.class);
    }

    @Test
    public void createsPurchaseUpdateListenerInConstructor() throws Exception {
        assertThat(appPurchases.getPurchasesUpdatedListener())
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
                .setListener(appPurchases.getPurchasesUpdatedListener())
                .build();
        assertThat(appPurchases.getBillingClient())
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void getSkuList() throws Exception {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("product_1_test");
        expected.add("product_2_test");
        assertThat(appPurchases.getSkuList())
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void getSkuDetailsParamsForInAppPurchases() throws Exception {
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder()
                .setSkusList(appPurchases.getSkuList())
                .setType(BillingClient.SkuType.INAPP);
        assertThat(appPurchases.getSkuDetailsParams())
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
        appPurchases.setInAppProductsQueriedListener(new InAppProductsQueriedListener() {
            @Override
            public void onQueried(List<SkuDetails> skuDetailsList) {
                assertEquals(mockSkuDetails, skuDetailsList);
                whereQueried[0] = true;
            }
        });

        appPurchases.onSkuDetailsResponse(
                BillingResult.newBuilder().build(),
                mockSkuDetails);
        assertTrue(whereQueried[0]);
    }
}