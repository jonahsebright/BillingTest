package com.jonahsebright.billingtest.app_products;

import com.android.billingclient.api.SkuDetails;

import java.util.List;

public interface InAppProductsQueriedListener {
    void onQueried(List<SkuDetails> skuDetailsList);
}
