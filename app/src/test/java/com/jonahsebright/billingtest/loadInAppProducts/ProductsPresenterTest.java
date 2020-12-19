package com.jonahsebright.billingtest.loadInAppProducts;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductsPresenterTest {
    @Test
    public void implementsInAppProductsQueriedListener() throws Exception {
        assertThat(new ProductsPresenter()).isInstanceOf(InAppProductsQueriedListener.class);
    }
}