package com.jonahsebright.billingtest.showproducts;

import com.android.billingclient.api.SkuDetails;
import com.jonahsebright.billingtest.purchases.load.ProductsQueriedListener;
import com.jonahsebright.billingtest.util.Converter;

public abstract class ProductsPresenter<M extends ProductModel> implements ProductsQueriedListener, Converter<SkuDetails, M> {
    protected AppProductsViewModel appProductsViewModel;

    public void setAppProductsViewModel(AppProductsViewModel appProductsViewModel) {
        this.appProductsViewModel = appProductsViewModel;
    }
}
