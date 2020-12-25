package com.jonahsebright.billingtest.loadInAppProducts;

import com.android.billingclient.api.SkuDetails;
import com.jonahsebright.billingtest.util.Converter;

import org.json.JSONException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

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
        SkuDetails mockSkuDetailsItem = mockSkuDetails("foo_id", "IN_APP", "Foo", "3.50 CHF",
                "3500000", "CHF", "bar");

        assertThat(new ProductsPresenter().convert(mockSkuDetailsItem))
                .usingRecursiveComparison()
                .isEqualTo(new ProductModel("foo_id", "Foo", "bar", "3.50 CHF"));
    }

    private SkuDetails mockSkuDetails(String productId, String type, String name,
                                      String price, String price_amount_micros, String currencyCode, String descr) throws JSONException {
        return new SkuDetails("{\"productId\":\"" + productId +
                "\",\"type\":\"" + type +
                "\"," +
                "\"title\":\"" + name +
                "\",\"price\":\"" + price +
                "\",\"price_amount_micros\":\"" + price_amount_micros +
                "\"," +
                "\"price_currency_code\":\"" + currencyCode +
                "\",\"description\":\"" + descr +
                "\"}");
    }
}