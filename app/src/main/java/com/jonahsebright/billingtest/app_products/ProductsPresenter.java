package com.jonahsebright.billingtest.app_products;

import com.android.billingclient.api.SkuDetails;
import com.jonahsebright.billingtest.util.Converter;

import java.util.ArrayList;
import java.util.List;

public class ProductsPresenter implements InAppProductsQueriedListener, Converter<SkuDetails, ProductModel> {
    private LoadInAppProductsViewModel loadInAppProductsViewModel;

    public void setLoadInAppProductsViewModel(LoadInAppProductsViewModel loadInAppProductsViewModel) {
        this.loadInAppProductsViewModel = loadInAppProductsViewModel;
        loadInAppProductsViewModel.setProducts(new ArrayList<>());
    }

    @Override
    public void onQueried(List<SkuDetails> skuDetailsList) {
        loadInAppProductsViewModel.setProducts(convertItems(skuDetailsList));
    }

    @Override
    public ProductModel convert(SkuDetails skuDetails) {
        return new ProductModel(skuDetails.getSku(),
                skuDetails.getTitle(),
                skuDetails.getDescription(),
                skuDetails.getPrice());
    }
}
