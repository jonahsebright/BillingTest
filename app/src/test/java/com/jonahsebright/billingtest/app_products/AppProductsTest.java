package com.jonahsebright.billingtest.app_products;

import org.junit.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class AppProductsTest {
    @Test
    public void getInAppSkuList() throws Exception {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("product_1_test");
        expected.add("product_2_test");
        expected.add("buy_image");

        assertThat(AppProducts.getInAppSkuList())
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void getSubscriptionSkuList() throws Exception {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("upgrade_basic");
        expected.add("upgrade_premium_abo");

        assertThat(AppProducts.getSubscriptionSkuList())
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

}