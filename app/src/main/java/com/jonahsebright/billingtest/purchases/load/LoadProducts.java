package com.jonahsebright.billingtest.purchases.load;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.jonahsebright.billingtest.purchases.products.AppProducts;

import java.util.List;

import static com.jonahsebright.billingtest.purchases.products.AppProducts.getSubscriptionSkuList;

public class LoadProducts {

    private ProductsQueriedListener inAppProductsQueriedListener = new NoProductsQueriedListener();
    private ProductsQueriedListener subsQueriedListener = new NoProductsQueriedListener();
    private final SkuDetailsResponseListener inAppSkuDetailsResponseListener = new SkuDetailsResponseListener() {
        @Override
        public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> list) {
            int resultCode = billingResult.getResponseCode();
            inAppSkuDetailsList = list;
            inAppProductsQueriedListener.onQueried(list);
        }
    };

    private final SkuDetailsResponseListener subsSkuDetailsResponseListener = new SkuDetailsResponseListener() {
        @Override
        public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> list) {
            subsSkuDetailsList = list;
            subsQueriedListener.onQueried(list);
        }
    };

    private List<SkuDetails> inAppSkuDetailsList;
    private List<SkuDetails> subsSkuDetailsList;
    private final BillingClient billingClient;

    public LoadProducts(BillingClient billingClient) {
        this.billingClient = billingClient;
    }

    public List<SkuDetails> getInAppSkuDetailsList() {
        return inAppSkuDetailsList;
    }

    public List<SkuDetails> getSubsSkuDetailsList() {
        return subsSkuDetailsList;
    }

    public void setInAppProductsQueriedListener(@NonNull ProductsQueriedListener inAppProductsQueriedListener) {
        this.inAppProductsQueriedListener = inAppProductsQueriedListener;
        if (inAppSkuDetailsList != null)
            this.inAppProductsQueriedListener.onQueried(inAppSkuDetailsList);
    }

    public void setSubsQueriedListener(@NonNull ProductsQueriedListener subsQueriedListener) {
        this.subsQueriedListener = subsQueriedListener;
        if (subsSkuDetailsList != null) this.subsQueriedListener.onQueried(subsSkuDetailsList);
    }

    public SkuDetailsResponseListener getInAppSkuDetailsResponseListener() {
        return inAppSkuDetailsResponseListener;
    }

    public ProductsQueriedListener getInAppProductsQueriedListener() {
        return inAppProductsQueriedListener;
    }

    public void queryInAppProducts() {
        //See https://developer.android.com/google/play/billing/integrate#show-products
        billingClient.querySkuDetailsAsync(getInAppSkuDetailsParams(), inAppSkuDetailsResponseListener);
    }

    public void querySubs() {
        billingClient.querySkuDetailsAsync(getSubsSkuDetailsParams(), subsSkuDetailsResponseListener);
    }

    public static SkuDetailsParams getInAppSkuDetailsParams() {
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(AppProducts.getInAppSkuList()).setType(BillingClient.SkuType.INAPP);
        return params.build();
    }

    public static SkuDetailsParams getSubsSkuDetailsParams() {
        return SkuDetailsParams.newBuilder()
                .setSkusList(getSubscriptionSkuList())
                .setType(BillingClient.SkuType.SUBS)
                .build();
    }

    public static class NoProductsQueriedListener implements ProductsQueriedListener {
        @Override
        public void onQueried(List<SkuDetails> skuDetailsList) {
            //do nothing
        }
    }
}
