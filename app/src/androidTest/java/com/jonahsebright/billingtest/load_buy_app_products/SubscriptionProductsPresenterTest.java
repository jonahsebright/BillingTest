package com.jonahsebright.billingtest.load_buy_app_products;

import com.android.billingclient.api.SkuDetails;
import com.jonahsebright.billingtest.TestUtils;
import com.jonahsebright.billingtest.util.Converter;

import org.junit.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class SubscriptionProductsPresenterTest {
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
        SkuDetails mockSkuDetailsItem = TestUtils.mockSubscriptionSkuDetails("foo_id", "Foo", "3.50 CHF",
                "3500000", "CHF", "bar", "month");

        assertThat(new InAppProductsPresenter().convert(mockSkuDetailsItem))
                .usingRecursiveComparison()
                .isEqualTo(new SubscriptionProductModel("foo_id", "Foo", "bar", "3.50 CHF", "month",
                        new ArrayList<>()));
    }
}