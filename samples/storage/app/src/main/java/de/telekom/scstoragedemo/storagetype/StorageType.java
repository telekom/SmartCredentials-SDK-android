package de.telekom.scstoragedemo.storagetype;

import de.telekom.scstoragedemo.R;

/**
 * Created by Alex.Graur@endava.com at 8/27/2020
 */
public enum StorageType {
    SENSITIVE(R.string.sensitive),
    NON_SENSITIVE(R.string.non_sensitive);

    private final int resId;

    StorageType(int resId) {
        this.resId = resId;
    }

    public int getResId() {
        return resId;
    }
}
