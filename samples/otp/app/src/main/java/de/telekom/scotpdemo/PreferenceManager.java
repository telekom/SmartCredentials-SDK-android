package de.telekom.scotpdemo;

import android.content.Context;

import de.telekom.smartcredentials.core.model.otp.OTPType;

/**
 * Created by gabriel.blaj@endava.com at 9/1/2020
 */
public class PreferenceManager {

    private final static String PREFERENCE_NAME = "otp_prefs";
    private final static String PREFERENCE_OTP_TYPE = "pref:otp_type";
    private final Context context;

    public PreferenceManager(Context context) {
        this.context = context;
    }

    public void setOtpType(OTPType storageType) {
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit()
                .putString(PREFERENCE_OTP_TYPE, storageType.name()).apply();
    }

    public OTPType getOtpType() {
        String name = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .getString(PREFERENCE_OTP_TYPE, OTPType.HOTP.name());
        return OTPType.valueOf(name);
    }
}