package com.jonahsebright.billingtest.loadInAppProducts;

import com.android.billingclient.api.SkuDetails;

import java.util.List;

public interface InAppProductsQueriedListener {
    void onQueried(List<SkuDetails> skuDetailsList);
}
