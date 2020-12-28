package com.jonahsebright.billingtest.load_buy_app_products;

import com.android.billingclient.api.SkuDetails;

import java.util.List;

public class InAppProductsPresenter extends ProductsPresenter<InAppProductModel> {
    @Override
    public InAppProductModel convert(SkuDetails skuDetails) {
        return new InAppProductModel(skuDetails.getSku(),
                skuDetails.getTitle(),
                skuDetails.getDescription(),
                skuDetails.getPrice());
    }

    @Override
    public void onQueried(List<SkuDetails> skuDetailsList) {
        appProductsViewModel.setInAppProducts(convertItems(skuDetailsList));
    }
}
