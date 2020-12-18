package com.jonahsebright.billingtest.pay;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PayViewModel extends ViewModel {
    private MutableLiveData<String> products = new MutableLiveData<>();

    public MutableLiveData<String> getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products.setValue(products);
    }
}
