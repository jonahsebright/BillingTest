package com.jonahsebright.billingtest.loadInAppProducts;

public class ProductModel {
    private final String id;
    private final String name;
    private final String description;
    private final String price;

    public ProductModel(String id, String name, String description, String price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }
}
