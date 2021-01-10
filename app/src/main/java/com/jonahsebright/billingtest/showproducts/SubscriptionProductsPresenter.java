package com.jonahsebright.billingtest.showproducts;

import com.android.billingclient.api.SkuDetails;
import com.jonahsebright.billingtest.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionProductsPresenter extends ProductsPresenter<SubscriptionProductModel> {
    @Override
    public SubscriptionProductModel convert(SkuDetails skuDetails) {
        return new SubscriptionProductModel(skuDetails.getSku(),
                skuDetails.getTitle(),
                skuDetails.getDescription(),
                skuDetails.getPrice(),
                StringUtils.iso8601ToNormal(skuDetails.getSubscriptionPeriod()),
                /*TODO: how get advantages??*/new ArrayList<>());
    }

    @Override
    public void onQueried(List<SkuDetails> skuDetailsList) {
        appProductsViewModel.setSubscriptionModels(convertItems(skuDetailsList));
    }
}
