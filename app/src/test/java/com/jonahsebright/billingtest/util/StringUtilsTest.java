package com.jonahsebright.billingtest.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringUtilsTest {
    @Test
    public void canConvertISO_8601FormatToNormalFormat() throws Exception {
        //see https://developer.android.com/reference/com/android/billingclient/api/SkuDetails#getsubscriptionperiod
        assertEquals("1 year", StringUtils.iso8601ToNormal("P1Y"));
        assertEquals("1 month", StringUtils.iso8601ToNormal("P1M"));
        assertEquals("1 week", StringUtils.iso8601ToNormal("P1W"));
        assertEquals("3 weeks", StringUtils.iso8601ToNormal("P3W"));
        assertEquals("6 months", StringUtils.iso8601ToNormal("P6M"));
    }
}