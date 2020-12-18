package com.jonahsebright.billingtest.pay;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductsPresenterTest {
    @Test
    public void implementsInAppProductsQueriedListener() throws Exception {
        assertThat(new ProductsPresenter()).isInstanceOf(InAppProductsQueriedListener.class);
    }
}