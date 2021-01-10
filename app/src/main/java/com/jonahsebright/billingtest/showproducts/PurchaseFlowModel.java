package com.jonahsebright.billingtest.showproducts;

import com.android.billingclient.api.Purchase;

import java.util.List;

public class PurchaseFlowModel {
    private final String errorMessage;
    private final List<Purchase> purchaseModels;

    public PurchaseFlowModel(String errorMessage, List<Purchase> purchaseModels) {
        this.errorMessage = errorMessage;
        this.purchaseModels = purchaseModels;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public List<Purchase> getPurchaseModels() {
        return purchaseModels;
    }
}
