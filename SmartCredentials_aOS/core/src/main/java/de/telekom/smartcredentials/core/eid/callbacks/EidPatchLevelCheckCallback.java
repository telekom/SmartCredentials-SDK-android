package de.telekom.smartcredentials.core.eid.callbacks;

public interface EidPatchLevelCheckCallback {

    void onSupportedPatchLevel();

    void onUnsupportedPatchLevel();

    void onFailed(Throwable throwable);
}
