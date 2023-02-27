package com.example.oneclickbusinessclient.recommendation;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

public class PreferencesHelper {

    public static final String SHARED_PREFS_NAME = "MoonlightPrefs";
    private static final String KEY_RECOMMENDATION = "recommendation";

    public static PreferencesHelper instance;

    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    private PreferencesHelper(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        this.gson = new Gson();
    }

    public static PreferencesHelper getInstance(Context context) {
        if (instance == null) {
            instance = new PreferencesHelper(context.getSharedPreferences(
                    SHARED_PREFS_NAME, Context.MODE_PRIVATE));
        }
        return instance;
    }

    public void saveRecommendation(Recommendation recommendation) {
        sharedPreferences.edit().putString(KEY_RECOMMENDATION, gson.toJson(recommendation)).apply();
        Log.d(RecommendationConstants.TAG, "saveRecommendation");
    }

    public boolean isAnyRecommendationAvailable() {
        Log.d(RecommendationConstants.TAG, "isAnyRecommendationAvailable: " + sharedPreferences.contains(KEY_RECOMMENDATION));
        return sharedPreferences.contains(KEY_RECOMMENDATION);
    }

    public void clearRecommendations() {
        sharedPreferences.edit().remove(KEY_RECOMMENDATION).apply();
        Log.d(RecommendationConstants.TAG, "clearRecommendations");
    }

    public Recommendation retrieveRecommendation() {
        Log.d(RecommendationConstants.TAG, "retrieveRecommendation");
        String json = sharedPreferences.getString(KEY_RECOMMENDATION, "");
        return gson.fromJson(json, Recommendation.class);
    }
}
