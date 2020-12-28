package com.jonahsebright.billingtest.load_buy_app_products;

import com.android.billingclient.api.SkuDetails;
import com.jonahsebright.billingtest.TestUtils;
import com.jonahsebright.billingtest.util.Converter;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductsPresenterTest {
    @Test
    public void implementsInAppProductsQueriedListener() throws Exception {
        assertThat(new ProductsPresenter()).isInstanceOf(InAppProductsQueriedListener.class);
    }

    @Test
    public void implementsConverter() throws Exception {
        assertThat(new ProductsPresenter()).isInstanceOf(Converter.class);
    }

    @Test
    public void convertsSkuDetailsItemCorrectlyToProductModel() throws Exception {
        SkuDetails mockSkuDetailsItem = TestUtils.mockSkuDetails("foo_id", "IN_APP", "Foo", "3.50 CHF",
                "3500000", "CHF", "bar");

        assertThat(new ProductsPresenter().convert(mockSkuDetailsItem))
                .usingRecursiveComparison()
                .isEqualTo(new ProductModel("foo_id", "Foo", "bar", "3.50 CHF"));
    }

}