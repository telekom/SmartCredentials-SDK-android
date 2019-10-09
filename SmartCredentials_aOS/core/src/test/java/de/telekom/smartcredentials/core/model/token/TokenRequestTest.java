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
//import de.telekom.smartcredentials.core.model.utils.Time;
//import de.telekom.smartcredentials.core.strategies.EncryptionStrategy;
//import de.telekom.smartcredentials.core.domain.utils.MocksProvider;
//import de.telekom.smartcredentials.core.domain.utils.ObjectsProvider;
//
//import static de.telekom.smartcredentials.core.model.otp.OTPType.HOTP;
//import static org.junit.Assert.assertEquals;
//import static org.powermock.api.mockito.PowerMockito.when;
//
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({Time.class, TextUtils.class})
//public class TokenRequestTest {
//
//    private String mSecretKey;
//    private long mCounter;
//    private int mOtpValueDigitsCount;
//    private boolean mAddCheckSum;
//    private int mTruncationOffset;
//    private String mAlgorithm;
//    private int mValidPeriod;
//
//    private String mEncryptedModel;
//    private String mDecryptedModel;
//
//    private Gson mGson;
//    private TokenRequest mTokenRequest;
//    private EncryptionStrategy mEncryptionStrategy;
//    private String mAlias;
//
//    @Before
//    public void setUp() throws Exception {
//        PowerMockito.mockStatic(Time.class);
//        PowerMockito.mockStatic(TextUtils.class);
//        long time = 1522245174420L;
//        when(Time.millisWithBuffer()).thenReturn(time);
//
//        mEncryptionStrategy = MocksProvider.provideEncryptionStrategy();
//        mAlias = "someAlias";
//        mGson = ObjectsProvider.provideGson();
//        mEncryptedModel = "akdjfhdaifgbjSDJLGBNspfigbWRPUIORGNw";
//
//        String itemId = "someId";
//        mTokenRequest = new TokenRequest(itemId, mGson, HOTP.getDesc(), mEncryptionStrategy, mAlias);
//        mTokenRequest.setEncryptedModel(mEncryptedModel);
//
//        mSecretKey = "12345678901234567890";
//        mOtpValueDigitsCount = 7;
//        mAddCheckSum = false;
//        mTruncationOffset = 16;
//        mValidPeriod = 45;
//        mAlgorithm = "SHA256";
//        String type = OTPType.TOTP.getDesc();
//        mCounter = 50735711L;
//
//        mDecryptedModel = "{"
//                + "\"key\": \"" + mSecretKey + "\","
//                + "\"counter\": " + mCounter + ","
//                + "\"digits\":" + mOtpValueDigitsCount + ","
//                + "\"algorithm\": \"" + mAlgorithm + "\","
//                + "\"hasCheckSum\":" + mAddCheckSum + ","
//                + "\"truncationOffset\": " + mTruncationOffset + ","
//                + "\"type\":\"" + type + "\","
//                + "\"validityPeriod\":" + mValidPeriod + "}";
//        mGson.fromJson(mDecryptedModel, Token.class);
//
//        when(mEncryptionStrategy.decrypt(mEncryptedModel, mAlias)).thenReturn(mDecryptedModel);
//    }
//
//    @Test
//    public void getEncryptedModelReturnsEncryptedModel() {
//        assertEquals(mEncryptedModel, mTokenRequest.getEncryptedModel());
//    }
//
//    @Test
//    public void getKeyCallsDecryptOnToken() throws Exception {
//        assertEquals(mSecretKey, mTokenRequest.getKey());
//    }
//
//    @Test
//    public void getCounterCallsDecryptOnTokenAndReturnsTOTPCounter() throws Exception {
//        assertEquals(mCounter, mTokenRequest.getCounter());
//    }
//
//    @Test
//    public void getCounterCallsDecryptOnTokenAndReturnsHOTPCounter() throws Exception {
//        mDecryptedModel = "{"
//                + "\"key\": \"" + mSecretKey + "\","
//                + "\"counter\": " + mCounter + ","
//                + "\"digits\":" + mOtpValueDigitsCount + ","
//                + "\"algorithm\": \"" + mAlgorithm + "\","
//                + "\"hasCheckSum\":" + mAddCheckSum + ","
//                + "\"truncationOffset\": " + mTruncationOffset + ","
//                + "\"type\":\"" + HOTP.getDesc() + "\","
//                + "\"validityPeriod\":" + mValidPeriod + "}";
//        mGson.fromJson(mDecryptedModel, Token.class);
//
//        when(mEncryptionStrategy.decrypt(mEncryptedModel, mAlias)).thenReturn(mDecryptedModel);
//
//        assertEquals(mCounter, mTokenRequest.getCounter());
//    }
//
//    @Test
//    public void getOtpValueDigitsCountCallsDecryptOnToken() throws Exception {
//        assertEquals(mOtpValueDigitsCount, mTokenRequest.getOtpValueDigitsCount());
//    }
//
//    @Test
//    public void getAlgorithmCallsDecryptOnToken() throws Exception {
//        when(TextUtils.isEmpty(mAlgorithm)).thenReturn(false);
//        assertEquals(mAlgorithm, mTokenRequest.getAlgorithm());
//    }
//
//    @Test
//    public void addChecksumCallsDecryptOnToken() throws Exception {
//        assertEquals(mAddCheckSum, mTokenRequest.addChecksum());
//    }
//
//    @Test
//    public void getTruncationOffsetCallsDecryptOnToken() throws Exception {
//        assertEquals(mTruncationOffset, mTokenRequest.getTruncationOffset());
//    }
//
//    @Test
//    public void getValidityPeriodMillisCallsDecryptOnToken() throws Exception {
//        assertEquals(mValidPeriod * Token.MILLIS_PER_SECOND, mTokenRequest.getValidityPeriodMillis());
//    }
//
//}