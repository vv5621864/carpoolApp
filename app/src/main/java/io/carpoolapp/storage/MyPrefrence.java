package io.carpoolapp.storage;

import android.content.Context;
import android.content.SharedPreferences;

import io.carpoolapp.constants.Constants;

public class MyPrefrence {

    private static SharedPreferences sharedPreferences = null;

    public MyPrefrence(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.PREFRENCE, Context.MODE_PRIVATE);
    }

    public String getValue(String key) {
        String value = sharedPreferences.getString(key, null);
        return value;
    }

    public void putValue(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public boolean isKeyExist(String key) {
        if (sharedPreferences.contains(key)) {
            return true;
        }
        return false;
    }
}
