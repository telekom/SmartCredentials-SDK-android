package de.telekom.scdocumentscannerdemo;

import android.content.Context;

import de.telekom.scdocumentscannerdemo.recognizer.RecognizerType;

/**
 * Created by gabriel.blaj@endava.com at 9/16/2020
 */
public class PreferenceManager {

    private final static String PREFERENCE_NAME = "documentscanner_prefs";
    private final static String PREFERENCE_RECOGNIZER_TYPE = "pref:recognizer_type";
    private final Context context;

    public PreferenceManager(Context context) {
        this.context = context;
    }

    public void setRecognizerType(RecognizerType recognizerType) {
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit()
                .putString(PREFERENCE_RECOGNIZER_TYPE, recognizerType.name()).apply();
    }

    public RecognizerType getRecognizerType() {
        String name = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .getString(PREFERENCE_RECOGNIZER_TYPE, RecognizerType.ID_COMBINED_RECOGNIZER.name());
        return RecognizerType.valueOf(name);
    }
}