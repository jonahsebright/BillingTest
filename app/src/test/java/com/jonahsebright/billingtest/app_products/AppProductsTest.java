package com.jonahsebright.billingtest.app_products;

import org.junit.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class AppProductsTest {
    @Test
    public void getInAppSkuList() throws Exception {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("product_1_test");
        expected.add("product_2_test");
        expected.add("upgrade_premium");
        //TODO: subscription expected.add("upgrade_basic");
        expected.add("buy_image");

        assertThat(AppProducts.getInAppSkuList())
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

}