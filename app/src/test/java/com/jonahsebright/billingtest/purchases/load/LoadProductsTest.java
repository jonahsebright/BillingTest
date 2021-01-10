package com.jonahsebright.billingtest.purchases.load;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.SkuDetailsParams;
import com.jonahsebright.billingtest.purchases.products.AppProducts;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LoadProductsTest {
    @Test
    public void Creates_NoInAppProductsQueriedListenerOnInit() throws Exception {
        assertThat(new LoadProducts(null).getInAppProductsQueriedListener())
                .isInstanceOf(LoadProducts.NoProductsQueriedListener.class);
    }

    @Test
    public void getInAppSkuDetailsParamsForInAppPurchases() throws Exception {
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder()
                .setSkusList(AppProducts.getInAppSkuList())
                .setType(BillingClient.SkuType.INAPP);
        assertThat(LoadProducts.getInAppSkuDetailsParams())
                .usingRecursiveComparison()
                .isEqualTo(params.build());
    }

    @Test
    public void getSkuDetailsParamsForSubsPurchases() throws Exception {
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder()
                .setSkusList(AppProducts.getSubscriptionSkuList())
                .setType(BillingClient.SkuType.SUBS);
        assertThat(LoadProducts.getSubsSkuDetailsParams())
                .usingRecursiveComparison()
                .isEqualTo(params.build());
    }


}