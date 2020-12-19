package com.jonahsebright.billingtest.loadInAppProducts;

import com.android.billingclient.api.SkuDetails;

import java.util.List;

public class ProductsPresenter implements InAppProductsQueriedListener{
    private LoadInAppProductsViewModel loadInAppProductsViewModel;

    public void setLoadInAppProductsViewModel(LoadInAppProductsViewModel loadInAppProductsViewModel) {
        this.loadInAppProductsViewModel = loadInAppProductsViewModel;
        loadInAppProductsViewModel.setProducts("No products fetched yet");
    }

    @Override
    public void onQueried(List<SkuDetails> skuDetailsList) {
        loadInAppProductsViewModel.setProducts(skuDetailsList.toString());
    }
}
