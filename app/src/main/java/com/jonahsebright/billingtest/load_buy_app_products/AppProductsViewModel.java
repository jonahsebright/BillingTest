package com.jonahsebright.billingtest.load_buy_app_products;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class AppProductsViewModel extends ViewModel {
    private MutableLiveData<ArrayList<InAppProductModel>> inAppProducts = new MutableLiveData<>();
    private MutableLiveData<ArrayList<SubscriptionProductModel>> subscriptionModels = new MutableLiveData<>();

    public MutableLiveData<ArrayList<InAppProductModel>> getInAppProducts() {
        return inAppProducts;
    }

    public void setInAppProducts(ArrayList<InAppProductModel> inAppProducts) {
        this.inAppProducts.setValue(inAppProducts);
    }

    public MutableLiveData<ArrayList<SubscriptionProductModel>> getSubscriptionModels() {
        return subscriptionModels;
    }

    public void setSubscriptionModels(ArrayList<SubscriptionProductModel> subscriptionModels) {
        this.subscriptionModels.setValue(subscriptionModels);
    }
}
