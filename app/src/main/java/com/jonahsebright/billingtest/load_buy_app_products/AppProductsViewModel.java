package com.jonahsebright.billingtest.load_buy_app_products;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class AppProductsViewModel extends ViewModel {
    private MutableLiveData<ArrayList<ProductModel>> products = new MutableLiveData<>();

    public MutableLiveData<ArrayList<ProductModel>> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductModel> products) {
        this.products.setValue(products);
    }
}
