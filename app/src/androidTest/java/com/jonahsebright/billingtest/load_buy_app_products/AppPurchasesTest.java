package com.jonahsebright.billingtest.load_buy_app_products;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.jonahsebright.billingtest.TestUtils;
import com.jonahsebright.billingtest.app_products.AppProducts;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.jonahsebright.billingtest.TestUtils.mockSkuDetails;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AppPurchasesTest {

    private AppPurchases appPurchases;

    @Before
    public void setUp() throws Exception {
        appPurchases = new AppPurchases(TestUtils.getAppContext(), new ArrayList<>());
    }

    @Test
    public void Create_NoInAppProductsQueriedListenerOnInit() throws Exception {
        assertThat(appPurchases.getInAppProductsQueriedListener()).isInstanceOf(AppPurchases.NoInAppProductsQueriedListener.class);
    }

    @Test
    public void implementsPurchasesUpdatedListener() throws Exception {
        assertThat(appPurchases)
                .usingRecursiveComparison()
                .isInstanceOf(PurchasesUpdatedListener.class);
    }

    @Test
    public void initialisesBillingClient() throws Exception {
        BillingClient expected = BillingClient.newBuilder(TestUtils.getAppContext())
                .enablePendingPurchases()
                .setListener(appPurchases)
                .build();
        assertThat(appPurchases.getBillingClient())
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void getInAppSkuDetailsParamsForInAppPurchases() throws Exception {
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder()
                .setSkusList(AppProducts.getInAppSkuList())
                .setType(BillingClient.SkuType.INAPP);
        assertThat(appPurchases.getInAppSkuDetailsParams())
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

    @Test
    public void createdCorrectBillingFlowParams() throws Exception {
        SkuDetails skuDetails = mockSkuDetails("foo_id", "IN_APP", "Foo", "3.50 CHF",
                "3500000", "CHF", "bar");
        BillingFlowParams expected = BillingFlowParams.newBuilder()
                .setSkuDetails(skuDetails)
                .build();
        assertThat(AppPurchases.buildBillingFlowParams(skuDetails))
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}