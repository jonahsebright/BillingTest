package com.jonahsebright.billingtest.pay;

import androidx.lifecycle.MutableLiveData;

import com.android.billingclient.api.SkuDetails;

import java.util.List;

public class ProductsPresenter implements InAppProductsQueriedListener{
    private PayViewModel payViewModel;

    public void setPayViewModel(PayViewModel payViewModel) {
        this.payViewModel = payViewModel;
        payViewModel.setProducts("No products fetched yet");
    }

    @Override
    public void onQueried(List<SkuDetails> skuDetailsList) {
        payViewModel.setProducts(skuDetailsList.toString());
    }
}
