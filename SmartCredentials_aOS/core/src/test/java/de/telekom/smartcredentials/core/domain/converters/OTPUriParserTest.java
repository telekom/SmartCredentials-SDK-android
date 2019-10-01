///*
// * Developed by Lucian Iacob in Cluj-Napoca.
// * Project: SmartCredentials_aOS
// * Email: lucian.iacob@endava.com
// * Last modified 07/11/18 14:02.
// * Copyright (c) Deutsche Telekom, 2018. All rights reserved.
// */
//
//package de.telekom.smartcredentials.core.domain.converters;
//
//import android.net.Uri;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mockito;
//
//import java.util.Locale;
//
//import de.telekom.smartcredentials.core.model.otp.OTPType;
//import de.telekom.smartcredentials.core.domain.utils.MocksProvider;
//
//import static de.telekom.smartcredentials.core.domain.converters.OTPUriParser.ALGORITHM_QUERY_PARAM_KEY;
//import static de.telekom.smartcredentials.core.domain.converters.OTPUriParser.COUNTER_QUERY_PARAM_KEY;
//import static de.telekom.smartcredentials.core.domain.converters.OTPUriParser.DEFAULT_ALGORITHM;
//import static de.telekom.smartcredentials.core.domain.converters.OTPUriParser.DEFAULT_COUNTER;
//import static de.telekom.smartcredentials.core.domain.converters.OTPUriParser.DEFAULT_DIGITS;
//import static de.telekom.smartcredentials.core.domain.converters.OTPUriParser.DEFAULT_PERIOD;
//import static de.telekom.smartcredentials.core.domain.converters.OTPUriParser.DIGITS_QUERY_PARAM_KEY;
//import static de.telekom.smartcredentials.core.domain.converters.OTPUriParser.IMAGE_QUERY_PARAM_KEY;
//import static de.telekom.smartcredentials.core.domain.converters.OTPUriParser.ISSUER_QUERY_PARAM_KEY;
//import static de.telekom.smartcredentials.core.domain.converters.OTPUriParser.OTP_SCHEME;
//import static de.telekom.smartcredentials.core.domain.converters.OTPUriParser.PERIOD_QUERY_PARAM_KEY;
//import static de.telekom.smartcredentials.core.domain.converters.OTPUriParser.SECRET_QUERY_PARAM_KEY;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.verify;
//import static org.powermock.api.mockito.PowerMockito.when;
//
//public class OTPUriParserTest {
//    private Uri mUri;
//    private OTPUriParser mOTPUriParser;
//
//    @Before
//    public void setUp() {
//        mUri = MocksProvider.provideUri();
//        mOTPUriParser = new OTPUriParser();
//    }
//
//    @Test
//    public void isTokenUriValidReturnsFalseIfUriIsNull() {
//        assertFalse(mOTPUriParser.isTokenUriValid(null));
//    }
//
//    @Test
//    public void isTokenUriValidReturnsFalseIfUriSchemeIsNull() {
//        when(mUri.getScheme()).thenReturn(null);
//        assertFalse(mOTPUriParser.isTokenUriValid(mUri));
//    }
//
//    @Test
//    public void isTokenUriValidReturnsFalseIfUriSchemeIsNotOTPScheme() {
//        when(mUri.getScheme()).thenReturn("someScheme");
//        assertFalse(mOTPUriParser.isTokenUriValid(mUri));
//    }
//
//    @Test
//    public void isTokenUriValidReturnsFalseIfUriAuthorityIsNull() {
//        when(mUri.getScheme()).thenReturn(OTP_SCHEME);
//        when(mUri.getAuthority()).thenReturn(null);
//        assertFalse(mOTPUriParser.isTokenUriValid(mUri));
//    }
//
//    @Test
//    public void isTokenUriValidReturnsFalseIfUriAuthorityIsNotOTPAuthority() {
//        when(mUri.getScheme()).thenReturn(OTP_SCHEME);
//        when(mUri.getAuthority()).thenReturn("test");
//        assertFalse(mOTPUriParser.isTokenUriValid(mUri));
//    }
//
//    @Test
//    public void isTokenUriValidReturnsFalseIfUriPathIsNull() {
//        when(mUri.getScheme()).thenReturn(OTP_SCHEME);
//        when(mUri.getAuthority()).thenReturn(OTPType.TOTP.getDesc());
//        when(mUri.getPath()).thenReturn(null);
//        assertFalse(mOTPUriParser.isTokenUriValid(mUri));
//    }
//
//    @Test
//    public void isTokenUriValidReturnsFalseIfUriPathIsEmpty() {
//        when(mUri.getScheme()).thenReturn(OTP_SCHEME);
//        when(mUri.getAuthority()).thenReturn(OTPType.TOTP.getDesc());
//        when(mUri.getPath()).thenReturn("");
//        assertFalse(mOTPUriParser.isTokenUriValid(mUri));
//    }
//
//    @Test
//    public void isTokenUriValidReturnsFalseIfUriPathIsEmptyAfterReplace() {
//        when(mUri.getScheme()).thenReturn(OTP_SCHEME);
//        when(mUri.getAuthority()).thenReturn(OTPType.TOTP.getDesc());
//        when(mUri.getPath()).thenReturn("/");
//        assertFalse(mOTPUriParser.isTokenUriValid(mUri));
//    }
//
//    @Test
//    public void isTokenUriValidReturnsTrue() {
//        when(mUri.getScheme()).thenReturn(OTP_SCHEME);
//        when(mUri.getAuthority()).thenReturn(OTPType.TOTP.getDesc());
//        when(mUri.getPath()).thenReturn("/otp_test");
//        assertTrue(mOTPUriParser.isTokenUriValid(mUri));
//    }
//
//    @Test
//    public void extractOTPTypeExtractsOtpItemType() {
//        when(mUri.getAuthority()).thenReturn(OTPType.HOTP.getDesc());
//        mOTPUriParser.extractOTPType(mUri);
//        assertEquals(mOTPUriParser.mOTPType, OTPType.HOTP);
//
//        when(mUri.getAuthority()).thenReturn(OTPType.TOTP.getDesc());
//        mOTPUriParser.extractOTPType(mUri);
//        assertEquals(mOTPUriParser.mOTPType, OTPType.TOTP);
//    }
//
//    @Test
//    public void extractUserLabelExtractsLabel() {
//        String label = "test.test";
//        when(mUri.getPath()).thenReturn("/" + label);
//
//        mOTPUriParser.extractUserLabel(mUri);
//
//        assertEquals(mOTPUriParser.mUserLabel, label);
//    }
//
//    @Test
//    public void extractUserLabelExtractsLabelWhenSeparatedWithSemicolon() {
//        String label = "test.test";
//        when(mUri.getPath()).thenReturn("/test:" + label);
//
//        mOTPUriParser.extractUserLabel(mUri);
//
//        assertEquals(mOTPUriParser.mUserLabel, label);
//    }
//
//    @Test
//    public void extractAlgorithmExtractsAlgorithmFromUri() {
//        String alg = "AES256";
//        when(mUri.getQueryParameter(ALGORITHM_QUERY_PARAM_KEY)).thenReturn(alg);
//
//        mOTPUriParser.extractAlgorithm(mUri);
//
//        assertEquals(mOTPUriParser.mAlgorithm, alg);
//    }
//
//    @Test
//    public void extractAlgorithmExtractsAlgorithmFromUriWithUppercaseLetters() {
//        String alg = "aes256";
//        when(mUri.getQueryParameter(ALGORITHM_QUERY_PARAM_KEY)).thenReturn(alg);
//
//        mOTPUriParser.extractAlgorithm(mUri);
//
//        assertEquals(mOTPUriParser.mAlgorithm, alg.toUpperCase(Locale.US));
//    }
//
//    @Test
//    public void extractAlgorithmSetsUppercaseDefaultAlgorithmWhenNotSpecifiedUri() {
//        when(mUri.getQueryParameter(ALGORITHM_QUERY_PARAM_KEY)).thenReturn(null);
//
//        mOTPUriParser.extractAlgorithm(mUri);
//
//        assertEquals(mOTPUriParser.mAlgorithm, DEFAULT_ALGORITHM.toUpperCase(Locale.US));
//    }
//
//    @Test
//    public void extractDigitsExtractsDigitsCountFromUri() {
//        int digits = 8;
//        when(mUri.getQueryParameter(DIGITS_QUERY_PARAM_KEY)).thenReturn(String.valueOf(digits));
//
//        mOTPUriParser.extractDigits(mUri);
//
//        assertEquals(mOTPUriParser.mDigits, digits);
//    }
//
//    @Test
//    public void extractDigitsSetsDefaultDigitsCountWhenNotPresentInUri() {
//        when(mUri.getQueryParameter(DIGITS_QUERY_PARAM_KEY)).thenReturn(null);
//
//        mOTPUriParser.extractDigits(mUri);
//
//        assertEquals(mOTPUriParser.mDigits, DEFAULT_DIGITS);
//    }
//
//    @Test
//    public void extractDigitsSetsDefaultDigitsCountWhenDigitsValueFromUriCouldNotBeParsed() {
//        when(mUri.getQueryParameter(DIGITS_QUERY_PARAM_KEY)).thenReturn("d");
//
//        mOTPUriParser.extractDigits(mUri);
//
//        assertEquals(mOTPUriParser.mDigits, DEFAULT_DIGITS);
//    }
//
//    @Test
//    public void extractCounterDoesNotExtractCounterWhenNotNeeded() {
//        mOTPUriParser.mOTPType = OTPType.TOTP;
//
//        mOTPUriParser.extractCounter(mUri);
//
//        assertEquals(mOTPUriParser.mCounter, -1);
//    }
//
//    @Test
//    public void extractCounterExtractsCounterFromUri() {
//        mOTPUriParser.mOTPType = OTPType.HOTP;
//        int counter = 12345678;
//        when(mUri.getQueryParameter(COUNTER_QUERY_PARAM_KEY)).thenReturn(String.valueOf(counter));
//
//        mOTPUriParser.extractCounter(mUri);
//
//        assertEquals(mOTPUriParser.mCounter, counter);
//    }
//
//    @Test
//    public void extractCounterSetsDefaultCounterWhenNotPresentInUri() {
//        mOTPUriParser.mOTPType = OTPType.HOTP;
//        when(mUri.getQueryParameter(COUNTER_QUERY_PARAM_KEY)).thenReturn(null);
//
//        mOTPUriParser.extractCounter(mUri);
//
//        assertEquals(mOTPUriParser.mCounter, DEFAULT_COUNTER);
//    }
//
//    @Test
//    public void extractCounterSetsDefaultCounterWhenCounterValueFromUriCouldNotBeParsed() {
//        mOTPUriParser.mOTPType = OTPType.HOTP;
//        when(mUri.getQueryParameter(COUNTER_QUERY_PARAM_KEY)).thenReturn("c");
//
//        mOTPUriParser.extractCounter(mUri);
//
//        assertEquals(mOTPUriParser.mCounter, DEFAULT_COUNTER);
//    }
//
//    @Test
//    public void extractPeriodExtractsPeriodFromUri() {
//        int period = 80;
//        when(mUri.getQueryParameter(PERIOD_QUERY_PARAM_KEY)).thenReturn(String.valueOf(period));
//
//        mOTPUriParser.extractPeriod(mUri);
//
//        assertEquals(mOTPUriParser.mPeriod, period);
//    }
//
//    @Test
//    public void extractPeriodSetsDefaultPeriodWhenNotPresentInUri() {
//        when(mUri.getQueryParameter(PERIOD_QUERY_PARAM_KEY)).thenReturn(null);
//
//        mOTPUriParser.extractPeriod(mUri);
//
//        assertEquals(mOTPUriParser.mPeriod, DEFAULT_PERIOD);
//    }
//
//    @Test
//    public void extractPeriodSetsDefaultPeriodWhenPeriodValueFromUriCouldNotBeParsed() {
//        when(mUri.getQueryParameter(PERIOD_QUERY_PARAM_KEY)).thenReturn("p");
//
//        mOTPUriParser.extractPeriod(mUri);
//
//        assertEquals(mOTPUriParser.mPeriod, DEFAULT_PERIOD);
//    }
//
//    @Test
//    public void extractPeriodSetsDefaultPeriodWhenPeriodValueFromUriWasZero() {
//        when(mUri.getQueryParameter(PERIOD_QUERY_PARAM_KEY)).thenReturn("0");
//
//        mOTPUriParser.extractPeriod(mUri);
//
//        assertEquals(mOTPUriParser.mPeriod, DEFAULT_PERIOD);
//    }
//
//    @Test
//    public void extractSecretExtractsSecretFromUri() {
//        String secret = "qwertyuiopasdfgh";
//        when(mUri.getQueryParameter(SECRET_QUERY_PARAM_KEY)).thenReturn(secret);
//
//        mOTPUriParser.extractSecret(mUri);
//
//        assertEquals(mOTPUriParser.mSecret, secret);
//    }
//
//    @Test
//    public void extractIssuerExtractsIssuerFromUri() {
//        String issuer = "facebook";
//        when(mUri.getQueryParameter(ISSUER_QUERY_PARAM_KEY)).thenReturn(issuer);
//
//        mOTPUriParser.extractIssuer(mUri);
//
//        assertEquals(mOTPUriParser.mIssuer, issuer);
//    }
//
//    @Test
//    public void extractImageExtractsIssuerFromUri() {
//        String imageUrl = "http://image.com/image.png";
//        when(mUri.getQueryParameter(IMAGE_QUERY_PARAM_KEY)).thenReturn(imageUrl);
//
//        mOTPUriParser.extractImage(mUri);
//
//        assertEquals(mOTPUriParser.mImage, imageUrl);
//    }
//
//    @Test
//    public void extractOTPItemPropertiesCallsAllOtherExtractMethods() {
//        OTPUriParser otpUriParser = Mockito.spy(mOTPUriParser);
//
//        doNothing().when(otpUriParser).extractOTPType(mUri);
//        doNothing().when(otpUriParser).extractUserLabel(mUri);
//        doNothing().when(otpUriParser).extractIssuer(mUri);
//        doNothing().when(otpUriParser).extractAlgorithm(mUri);
//        doNothing().when(otpUriParser).extractDigits(mUri);
//        doNothing().when(otpUriParser).extractPeriod(mUri);
//        doNothing().when(otpUriParser).extractCounter(mUri);
//        doNothing().when(otpUriParser).extractSecret(mUri);
//        doNothing().when(otpUriParser).extractImage(mUri);
//
//        otpUriParser.extractOTPItemProperties(mUri);
//
//        verify(otpUriParser).extractOTPType(mUri);
//        verify(otpUriParser).extractUserLabel(mUri);
//        verify(otpUriParser).extractIssuer(mUri);
//        verify(otpUriParser).extractAlgorithm(mUri);
//        verify(otpUriParser).extractDigits(mUri);
//        verify(otpUriParser).extractPeriod(mUri);
//        verify(otpUriParser).extractCounter(mUri);
//        verify(otpUriParser).extractSecret(mUri);
//        verify(otpUriParser).extractImage(mUri);
//    }
//
//}