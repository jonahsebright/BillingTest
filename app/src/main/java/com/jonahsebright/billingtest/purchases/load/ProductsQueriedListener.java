package com.jonahsebright.billingtest.purchases.load;

import com.android.billingclient.api.SkuDetails;

import java.util.List;

public interface ProductsQueriedListener {
    void onQueried(List<SkuDetails> skuDetailsList);
}
