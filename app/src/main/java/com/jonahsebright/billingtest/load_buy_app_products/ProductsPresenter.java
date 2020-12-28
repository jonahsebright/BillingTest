package com.jonahsebright.billingtest.load_buy_app_products;

import com.android.billingclient.api.SkuDetails;
import com.jonahsebright.billingtest.util.Converter;

public abstract class ProductsPresenter<M extends ProductModel> implements ProductsQueriedListener, Converter<SkuDetails, M> {
    protected AppProductsViewModel appProductsViewModel;

    public void setAppProductsViewModel(AppProductsViewModel appProductsViewModel) {
        this.appProductsViewModel = appProductsViewModel;
    }
}
