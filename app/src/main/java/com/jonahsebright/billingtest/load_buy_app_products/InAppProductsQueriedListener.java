package com.jonahsebright.billingtest.load_buy_app_products;

import com.android.billingclient.api.SkuDetails;

import java.util.List;

public interface InAppProductsQueriedListener {
    void onQueried(List<SkuDetails> skuDetailsList);
}