package com.jonahsebright.billingtest.launchpurchaseflow;

import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.SkuDetails;

import org.junit.Test;

import static com.jonahsebright.billingtest.TestUtils.mockSkuDetails;
import static org.assertj.core.api.Assertions.assertThat;

public class LaunchPurchaseFlowTest {

    @Test
    public void createdCorrectBillingFlowParams() throws Exception {
        SkuDetails skuDetails = mockSkuDetails("foo_id", "IN_APP", "Foo", "3.50 CHF",
                "3500000", "CHF", "bar");
        BillingFlowParams expected = BillingFlowParams.newBuilder()
                .setSkuDetails(skuDetails)
                .build();
        assertThat(new LaunchPurchaseFlow().buildBillingFlowParams(skuDetails))
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}