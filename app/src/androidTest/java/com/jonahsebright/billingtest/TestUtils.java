package com.jonahsebright.billingtest;

import android.content.Context;

import androidx.test.InstrumentationRegistry;

public class TestUtils {
    public static Context getAppContext() {
        return InstrumentationRegistry.getTargetContext();
    }

}
