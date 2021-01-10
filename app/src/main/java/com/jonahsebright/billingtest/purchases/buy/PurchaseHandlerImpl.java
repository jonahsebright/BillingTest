package com.jonahsebright.billingtest.purchases.buy;

public abstract class PurchaseHandlerImpl implements PurchaseHandler {
    protected final String productId;

    protected PurchaseHandlerImpl(String productId) {
        this.productId = productId;
    }

    @Override
    public String getId() {
        return getProductId();
    }

    public String getProductId() {
        return productId;
    }
}
