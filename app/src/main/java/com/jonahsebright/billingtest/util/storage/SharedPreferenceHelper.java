package com.jonahsebright.billingtest.util.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceHelper {

    private SharedPreferences sharedPreferences;

    public static final String DEFAULT_SHARED_PREFERENCES = "default_shared_prefs";

    public SharedPreferenceHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(DEFAULT_SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }



    public boolean saveString(String string, String key) {
        EditorPutter<String> editorPutter = (editor, value, key1) -> editor.putString(key1, value);
        return edit(string, key, editorPutter);
    }

    private <T> boolean edit(T value, String key, EditorPutter<T> editorPutter) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editorPutter.put(editor, value, key);
        return editor.commit();
    }

    public String getString(String key) {
        return getString(key, null);
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public boolean saveInt(int i, String key) {
        EditorPutter<Integer> editorPutter = (editor, value, key1) -> editor.putInt(key1, value);
        return edit(i, key, editorPutter);
    }

    public int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }
}
