package com.jonahsebright.billingtest.showproducts;

import java.util.List;

public class SubscriptionProductModel extends ProductModel {
    private final String period;
    private final List<String> advantages;

    public SubscriptionProductModel(String id, String name, String description, String price,
                                    String period, List<String> advantages) {
        super(id, name, description, price);
        this.period = period;
        this.advantages = advantages;
    }

    public String getPeriod() {
        return period;
    }

    public List<String> getAdvantages() {
        return advantages;
    }
}
