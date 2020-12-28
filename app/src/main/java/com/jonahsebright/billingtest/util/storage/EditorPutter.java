package com.jonahsebright.billingtest.util.storage;

import android.content.SharedPreferences;

public interface EditorPutter<T> {
    void put(SharedPreferences.Editor editor, T value, String key);

}
