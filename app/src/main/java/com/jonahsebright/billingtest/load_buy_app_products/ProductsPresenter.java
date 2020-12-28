package com.jonahsebright.billingtest.load_buy_app_products;

import com.android.billingclient.api.SkuDetails;
import com.jonahsebright.billingtest.util.Converter;

import java.util.ArrayList;
import java.util.List;

public class ProductsPresenter implements InAppProductsQueriedListener, Converter<SkuDetails, ProductModel> {
    private AppProductsViewModel appProductsViewModel;

    public void setAppProductsViewModel(AppProductsViewModel appProductsViewModel) {
        this.appProductsViewModel = appProductsViewModel;
        appProductsViewModel.setProducts(new ArrayList<>());
    }

    @Override
    public void onQueried(List<SkuDetails> skuDetailsList) {
        appProductsViewModel.setProducts(convertItems(skuDetailsList));
    }

    @Override
    public ProductModel convert(SkuDetails skuDetails) {
        return new ProductModel(skuDetails.getSku(),
                skuDetails.getTitle(),
                skuDetails.getDescription(),
                skuDetails.getPrice());
    }
}
