package com.jonahsebright.billingtest.pay;

import com.android.billingclient.api.SkuDetails;

import java.util.List;

public interface InAppProductsQueriedListener {
    void onQueried(List<SkuDetails> skuDetailsList);
}
