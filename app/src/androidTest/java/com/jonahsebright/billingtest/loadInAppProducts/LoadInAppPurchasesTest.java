package com.jonahsebright.billingtest.loadInAppProducts;

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

public class LoadInAppPurchasesTest {

    private LoadInAppPurchases loadInAppPurchases;

    @Before
    public void setUp() throws Exception {
        loadInAppPurchases = new LoadInAppPurchases(TestUtils.getAppContext());
    }

    @Test
    public void Create_NoInAppProductsQueriedListenerOnInit() throws Exception {
        assertThat(loadInAppPurchases.getInAppProductsQueriedListener()).isInstanceOf(LoadInAppPurchases.NoInAppProductsQueriedListener.class);
    }

    @Test
    public void createsPurchaseUpdateListenerInConstructor() throws Exception {
        assertThat(loadInAppPurchases.getPurchasesUpdatedListener())
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
                .setListener(loadInAppPurchases.getPurchasesUpdatedListener())
                .build();
        assertThat(loadInAppPurchases.getBillingClient())
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void getSkuList() throws Exception {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("product_1_test");
        expected.add("product_2_test");
        assertThat(loadInAppPurchases.getSkuList())
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void getSkuDetailsParamsForInAppPurchases() throws Exception {
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder()
                .setSkusList(loadInAppPurchases.getSkuList())
                .setType(BillingClient.SkuType.INAPP);
        assertThat(loadInAppPurchases.getSkuDetailsParams())
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
        loadInAppPurchases.setInAppProductsQueriedListener(new InAppProductsQueriedListener() {
            @Override
            public void onQueried(List<SkuDetails> skuDetailsList) {
                assertEquals(mockSkuDetails, skuDetailsList);
                whereQueried[0] = true;
            }
        });

        loadInAppPurchases.onSkuDetailsResponse(
                BillingResult.newBuilder().build(),
                mockSkuDetails);
        assertTrue(whereQueried[0]);
    }
}