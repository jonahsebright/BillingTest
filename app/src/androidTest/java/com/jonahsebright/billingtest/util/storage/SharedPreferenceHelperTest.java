package com.jonahsebright.billingtest.util.storage;

import android.content.Context;

import com.jonahsebright.billingtest.TestUtils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SharedPreferenceHelperTest {

    private SharedPreferenceHelper sharedPreferenceHelper;

    @Before
    public void setUp() {
        Context context = TestUtils.getAppContext();
        sharedPreferenceHelper = new SharedPreferenceHelper(context);
    }

    @Test
    public void canSaveAndRetrieveString() throws Exception {
        String toSave = "hello";
        assertTrue(sharedPreferenceHelper.saveString(toSave, "KEY_HELLO"));
        String retrieved = sharedPreferenceHelper.getString("KEY_HELLO");
        assertEquals(toSave, retrieved);
        toSave = "world";
        assertTrue(sharedPreferenceHelper.saveString(toSave, "KEY_HELLO"));
        String retrieved2 = sharedPreferenceHelper.getString("KEY_HELLO");
        assertEquals(toSave, retrieved2);
    }

    @Test
    public void canSaveAndRetrieveInt() throws Exception {
        int toSave = 1234;
        assertTrue(sharedPreferenceHelper.saveInt(toSave, "KEY_INT_HELLO"));
        int retrieved = sharedPreferenceHelper.getInt("KEY_INT_HELLO", 0);
        assertEquals(toSave, retrieved);
        toSave = 56789;
        assertTrue(sharedPreferenceHelper.saveInt(toSave, "KEY_INT_HELLO"));
        int retrieved2 = sharedPreferenceHelper.getInt("KEY_INT_HELLO", 0);
        assertEquals(toSave, retrieved2);
    }
}