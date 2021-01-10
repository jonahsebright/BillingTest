package com.jonahsebright.billingtest.showproducts;

import com.android.billingclient.api.SkuDetails;
import com.jonahsebright.billingtest.TestUtils;
import com.jonahsebright.billingtest.purchases.load.ProductsQueriedListener;
import com.jonahsebright.billingtest.util.Converter;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InAppProductsPresenterTest {
    @Test
    public void implementsInAppProductsQueriedListener() throws Exception {
        assertThat(new InAppProductsPresenter()).isInstanceOf(ProductsQueriedListener.class);
    }

    @Test
    public void implementsConverter() throws Exception {
        assertThat(new InAppProductsPresenter()).isInstanceOf(Converter.class);
    }

    @Test
    public void convertsSkuDetailsItemCorrectlyToProductModel() throws Exception {
        SkuDetails mockSkuDetailsItem = TestUtils.mockInAppSkuDetails("foo_id", "Foo", "3.50 CHF",
                "3500000", "CHF", "bar");

        assertThat(new InAppProductsPresenter().convert(mockSkuDetailsItem))
                .usingRecursiveComparison()
                .isEqualTo(new ProductModel("foo_id", "Foo", "bar", "3.50 CHF"));
    }

}