package de.telekom.scstoragedemo;

import android.content.Context;

import de.telekom.scstoragedemo.storagetype.StorageType;

/**
 * Created by Alex.Graur@endava.com at 8/27/2020
 */
public class PreferenceManager {

    private final static String PREFERENCE_NAME = "storage_prefs";
    private final static String PREFERENCE_STORAGE_TYPE = "pref:storage_type";
    private final Context context;

    public PreferenceManager(Context context) {
        this.context = context;
    }

    public void setStorageType(StorageType storageType) {
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit()
                .putString(PREFERENCE_STORAGE_TYPE, storageType.name()).apply();
    }

    public StorageType getStorageType() {
        String name = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .getString(PREFERENCE_STORAGE_TYPE, StorageType.SENSITIVE.name());
        return StorageType.valueOf(name);
    }
}
