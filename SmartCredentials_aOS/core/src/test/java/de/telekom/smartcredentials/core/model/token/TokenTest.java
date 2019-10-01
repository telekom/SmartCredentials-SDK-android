///*
// * Developed by Lucian Iacob in Cluj-Napoca.
// * Project: SmartCredentials_aOS
// * Email: lucian.iacob@endava.com
// * Last modified 07/11/18 14:02.
// * Copyright (c) Deutsche Telekom, 2018. All rights reserved.
// */
//
//package de.telekom.smartcredentials.core.model.token;
//
//import android.text.TextUtils;
//
//import com.google.gson.Gson;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//
//import de.telekom.smartcredentials.core.model.otp.OTPType;
//import de.telekom.smartcredentials.core.domain.utils.ObjectsProvider;
//
//import static org.junit.Assert.assertEquals;
//import static org.powermock.api.mockito.PowerMockito.when;
//
//@RunWith(PowerMockRunner.class)
//@PrepareForTest(TextUtils.class)
//public class TokenTest {
//
//    private String mSecretKey;
//    private long mCounter;
//    private int mOtpValueDigitsCount;
//    private boolean mAddCheckSum;
//    private int mTruncationOffset;
//    private String mAlgorithm;
//    private int mValidPeriod;
//    private String mType;
//
//    private Token mToken;
//    private Gson mGson;
//
//    @Before
//    public void setUp() {
//        PowerMockito.mockStatic(TextUtils.class);
//
//        mSecretKey = "12345678901234567890";
//        mOtpValueDigitsCount = 7;
//        mAddCheckSum = false;
//        mTruncationOffset = 16;
//        mValidPeriod = 45;
//        mAlgorithm = "SHA256";
//        mType = OTPType.TOTP.getDesc();
//        mCounter = 50735711L;
//
//        mGson = ObjectsProvider.provideGson();
//
//        mToken = mGson.fromJson("{"
//                + "\"key\": \"" + mSecretKey + "\","
//                + "\"counter\": " + mCounter + ","
//                + "\"digits\":" + mOtpValueDigitsCount + ","
//                + "\"algorithm\": \"" + mAlgorithm + "\","
//                + "\"hasCheckSum\":" + mAddCheckSum + ","
//                + "\"truncationOffset\": " + mTruncationOffset + ","
//                + "\"type\":\"" + mType + "\","
//                + "\"validityPeriod\":" + mValidPeriod + "}", Token.class);
//    }
//
//    @Test
//    public void getKeyReturnsKey() {
//        mToken = mGson.fromJson("{"
//                + "\"key\": \"" + mSecretKey + "\","
//                + "\"counter\": " + mCounter + ","
//                + "\"digits\":" + mOtpValueDigitsCount + ","
//                + "\"algorithm\": \"" + mAlgorithm + "\","
//                + "\"hasCheckSum\":" + mAddCheckSum + ","
//                + "\"truncationOffset\": " + mTruncationOffset + ","
//                + "\"type\":\"" + mType + "\","
//                + "\"validityPeriod\":" + mValidPeriod + "}", Token.class);
//
//        assertEquals(mToken.getKey(), mSecretKey);
//    }
//
//    @Test
//    public void getCounterReturnsTokenCounterWhenSet() {
//        mToken = mGson.fromJson("{"
//                + "\"key\": \"" + mSecretKey + "\","
//                + "\"counter\": " + mCounter + ","
//                + "\"digits\":" + mOtpValueDigitsCount + ","
//                + "\"algorithm\": \"" + mAlgorithm + "\","
//                + "\"hasCheckSum\":" + mAddCheckSum + ","
//                + "\"truncationOffset\": " + mTruncationOffset + ","
//                + "\"type\":\"" + mType + "\","
//                + "\"validityPeriod\":" + mValidPeriod + "}", Token.class);
//
//        assertEquals(mToken.getCounter(), mCounter);
//    }
//
//    @Test
//    public void getCounterReturnsDefaultTokenCounterWhenNotPreviouslySet() {
//        mToken = mGson.fromJson("{"
//                + "\"key\": \"" + mSecretKey + "\","
//                + "\"digits\":" + mOtpValueDigitsCount + ","
//                + "\"algorithm\": \"" + mAlgorithm + "\","
//                + "\"hasCheckSum\":" + mAddCheckSum + ","
//                + "\"truncationOffset\": " + mTruncationOffset + ","
//                + "\"type\":\"" + mType + "\","
//                + "\"validityPeriod\":" + mValidPeriod + "}", Token.class);
//
//        assertEquals(mToken.getCounter(), Token.DEFAULT_COUNTER);
//    }
//
//    @Test
//    public void getOtpValueDigitsCountReturnsDigitsCountWhenPreviouslySet() {
//        mToken = mGson.fromJson("{"
//                + "\"key\": \"" + mSecretKey + "\","
//                + "\"counter\": " + mCounter + ","
//                + "\"digits\":" + mOtpValueDigitsCount + ","
//                + "\"algorithm\": \"" + mAlgorithm + "\","
//                + "\"hasCheckSum\":" + mAddCheckSum + ","
//                + "\"truncationOffset\": " + mTruncationOffset + ","
//                + "\"type\":\"" + mType + "\","
//                + "\"validityPeriod\":" + mValidPeriod + "}", Token.class);
//
//        assertEquals(mToken.getOtpValueDigitsCount(), mOtpValueDigitsCount);
//    }
//
//    @Test
//    public void getOtpValueDigitsCountReturnsDefaultDigitsCountWhenPreviouslyNotSet() {
//        mToken = mGson.fromJson("{"
//                + "\"key\": \"" + mSecretKey + "\","
//                + "\"counter\": " + mCounter + ","
//                + "\"algorithm\": \"" + mAlgorithm + "\","
//                + "\"hasCheckSum\":" + mAddCheckSum + ","
//                + "\"truncationOffset\": " + mTruncationOffset + ","
//                + "\"type\":\"" + mType + "\","
//                + "\"validityPeriod\":" + mValidPeriod + "}", Token.class);
//
//        assertEquals(mToken.getOtpValueDigitsCount(), Token.DEFAULT_DIGITS);
//    }
//
//    @Test
//    public void getAlgorithmReturnsAlgorithmWhenPreviouslySet() {
//        when(TextUtils.isEmpty(mAlgorithm)).thenReturn(false);
//        mToken = mGson.fromJson("{"
//                + "\"key\": \"" + mSecretKey + "\","
//                + "\"counter\": " + mCounter + ","
//                + "\"digits\":" + mOtpValueDigitsCount + ","
//                + "\"algorithm\": \"" + mAlgorithm + "\","
//                + "\"hasCheckSum\":" + mAddCheckSum + ","
//                + "\"truncationOffset\": " + mTruncationOffset + ","
//                + "\"type\":\"" + mType + "\","
//                + "\"validityPeriod\":" + mValidPeriod + "}", Token.class);
//
//        assertEquals(mToken.getAlgorithm(), mAlgorithm);
//    }
//
//    @Test
//    public void getAlgorithmReturnsDefaultAlgorithmWhenPreviouslyNotSet() {
//        when(TextUtils.isEmpty(null)).thenReturn(true);
//        mToken = mGson.fromJson("{"
//                + "\"key\": \"" + mSecretKey + "\","
//                + "\"counter\": " + mCounter + ","
//                + "\"digits\":" + mOtpValueDigitsCount + ","
//                + "\"hasCheckSum\":" + mAddCheckSum + ","
//                + "\"truncationOffset\": " + mTruncationOffset + ","
//                + "\"type\":\"" + mType + "\","
//                + "\"validityPeriod\":" + mValidPeriod + "}", Token.class);
//
//        assertEquals(mToken.getAlgorithm(), Token.DEFAULT_ALGORITHM);
//    }
//
//    @Test
//    public void addChecksumReturnsSetValue() {
//        mToken = mGson.fromJson("{"
//                + "\"key\": \"" + mSecretKey + "\","
//                + "\"counter\": " + mCounter + ","
//                + "\"digits\":" + mOtpValueDigitsCount + ","
//                + "\"algorithm\": \"" + mAlgorithm + "\","
//                + "\"hasCheckSum\":" + mAddCheckSum + ","
//                + "\"truncationOffset\": " + mTruncationOffset + ","
//                + "\"type\":\"" + mType + "\","
//                + "\"validityPeriod\":" + mValidPeriod + "}", Token.class);
//
//        assertEquals(mToken.addChecksum(), mAddCheckSum);
//    }
//
//    @Test
//    public void getTruncationOffsetReturnsValueIfIsBiggerThanZero() {
//        mToken = mGson.fromJson("{"
//                + "\"key\": \"" + mSecretKey + "\","
//                + "\"counter\": " + mCounter + ","
//                + "\"digits\":" + mOtpValueDigitsCount + ","
//                + "\"algorithm\": \"" + mAlgorithm + "\","
//                + "\"hasCheckSum\":" + mAddCheckSum + ","
//                + "\"truncationOffset\": " + mTruncationOffset + ","
//                + "\"type\":\"" + mType + "\","
//                + "\"validityPeriod\":" + mValidPeriod + "}", Token.class);
//
//        assertEquals(mToken.getTruncationOffset(), mTruncationOffset);
//    }
//
//    @Test
//    public void getTruncationOffsetReturnsDefaultIfValue() {
//        mToken = mGson.fromJson("{"
//                + "\"key\": \"" + mSecretKey + "\","
//                + "\"counter\": " + mCounter + ","
//                + "\"digits\":" + mOtpValueDigitsCount + ","
//                + "\"algorithm\": \"" + mAlgorithm + "\","
//                + "\"hasCheckSum\":" + mAddCheckSum + ","
//                + "\"type\":\"" + mType + "\","
//                + "\"validityPeriod\":" + mValidPeriod + "}", Token.class);
//
//        assertEquals(mToken.getTruncationOffset(), mTruncationOffset);
//    }
//
//    @Test
//    public void getValidityPeriodMillisReturnsValueMultipliedWithMillisPerSecond() {
//        mToken = mGson.fromJson("{"
//                + "\"key\": \"" + mSecretKey + "\","
//                + "\"counter\": " + mCounter + ","
//                + "\"digits\":" + mOtpValueDigitsCount + ","
//                + "\"algorithm\": \"" + mAlgorithm + "\","
//                + "\"hasCheckSum\":" + mAddCheckSum + ","
//                + "\"truncationOffset\": " + mTruncationOffset + ","
//                + "\"type\":\"" + mType + "\","
//                + "\"validityPeriod\":" + mValidPeriod + "}", Token.class);
//
//        assertEquals(mToken.getValidityPeriodMillis(), mValidPeriod * Token.MILLIS_PER_SECOND);
//    }
//
//    @Test
//    public void getValidityPeriodMillisReturnsDefaultValueMultipliedWithMillisPerSecond() {
//        mToken = mGson.fromJson("{"
//                + "\"key\": \"" + mSecretKey + "\","
//                + "\"counter\": " + mCounter + ","
//                + "\"digits\":" + mOtpValueDigitsCount + ","
//                + "\"algorithm\": \"" + mAlgorithm + "\","
//                + "\"hasCheckSum\":" + mAddCheckSum + ","
//                + "\"truncationOffset\": " + mTruncationOffset + ","
//                + "\"type\":\"" + mType + "\"" + "}", Token.class);
//
//        assertEquals(mToken.getValidityPeriodMillis(), Token.DEFAULT_VALIDITY_PERIOD * Token.MILLIS_PER_SECOND);
//    }
//
//}