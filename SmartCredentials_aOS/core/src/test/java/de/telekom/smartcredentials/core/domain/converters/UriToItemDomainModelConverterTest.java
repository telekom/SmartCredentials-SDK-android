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
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mockito;
//
//import de.telekom.smartcredentials.core.model.item.ContentType;
//import de.telekom.smartcredentials.core.model.item.ItemDomainData;
//import de.telekom.smartcredentials.core.model.item.ItemDomainMetadata;
//import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
//import de.telekom.smartcredentials.core.model.otp.OTPType;
//import de.telekom.smartcredentials.core.domain.utils.MocksProvider;
//
//import static de.telekom.smartcredentials.core.domain.converters.OTPUriParser.DEFAULT_ALGORITHM;
//import static de.telekom.smartcredentials.core.domain.converters.OTPUriParser.DEFAULT_DIGITS;
//import static de.telekom.smartcredentials.core.domain.converters.OTPUriParser.DEFAULT_PERIOD;
//import static de.telekom.smartcredentials.core.model.otp.OTPTokenKey.ALGORITHM;
//import static de.telekom.smartcredentials.core.model.otp.OTPTokenKey.COUNTER;
//import static de.telekom.smartcredentials.core.model.otp.OTPTokenKey.DIGITS_COUNT;
//import static de.telekom.smartcredentials.core.model.otp.OTPTokenKey.KEY;
//import static de.telekom.smartcredentials.core.model.otp.OTPTokenKey.VALIDITY_PERIOD;
//import static de.telekom.smartcredentials.core.model.otp.OTPTokenKeyExtras.IMAGE;
//import static de.telekom.smartcredentials.core.model.otp.OTPTokenKeyExtras.ISSUER;
//import static de.telekom.smartcredentials.core.model.otp.OTPTokenKeyExtras.USER_LABEL;
//import static junit.framework.Assert.assertEquals;
//import static junit.framework.Assert.assertNotNull;
//import static junit.framework.Assert.assertNull;
//import static junit.framework.Assert.assertTrue;
//import static org.powermock.api.mockito.PowerMockito.doThrow;
//import static org.powermock.api.mockito.PowerMockito.when;
//
//public class UriToItemDomainModelConverterTest {
//
//    private Uri mUri;
//    private String mItemId;
//    private String mUserId;
//    private OTPUriParser mOTPUriParser;
//    private UriToItemDomainModelConverter mUriToItemDomainModelConverter;
//
//    private OTPType mOTPType;
//    private String mIssuer;
//    private String mUserLabel;
//    private String mAlgorithm;
//    private int mDigits;
//    private int mPeriod;
//    private long mCounter;
//    private String mSecret;
//    private String mImage;
//
//    @Before
//    public void setUp() {
//        mUri = MocksProvider.provideUri();
//        mItemId = "itemId";
//        mUserId = "userId";
//        mOTPUriParser = MocksProvider.provideOtpUriParser();
//        mUriToItemDomainModelConverter = new UriToItemDomainModelConverter(mOTPUriParser);
//
//        mOTPType = OTPType.HOTP;
//        mIssuer = "issuer";
//        mUserLabel = "test.test";
//        mAlgorithm = DEFAULT_ALGORITHM;
//        mDigits = DEFAULT_DIGITS;
//        mPeriod = DEFAULT_PERIOD;
//        mCounter = 123456;
//        mSecret = "qwertyuiopasdfgh";
//        mImage = "http://image.com/image.png";
//    }
//
//    @Test
//    public void parseOTPUriReturnsNullWhenUriIsNotValid() {
//        when(mOTPUriParser.isTokenUriValid(mUri)).thenReturn(false);
//
//        assertNull(mUriToItemDomainModelConverter.parseOTPUri(mItemId, mUserId, mUri));
//    }
//
//    @Test
//    public void parseOTPUriReturnsNullWhenJSONExceptionWasThrown() throws JSONException {
//        initOTPParserProperties();
//        UriToItemDomainModelConverter uriToItemDomainModelConverterSpy =
//                Mockito.spy(mUriToItemDomainModelConverter);
//
//        when(mOTPUriParser.isTokenUriValid(mUri)).thenReturn(true);
//        doThrow(new JSONException("identifier error")).when(uriToItemDomainModelConverterSpy).getIdentifier();
//
//        assertNull(uriToItemDomainModelConverterSpy.parseOTPUri(mItemId, mUserId, mUri));
//    }
//
//    @Test
//    public void parseOTPUriReturnsItemDomainModel() throws JSONException {
//        initOTPParserProperties();
//        when(mOTPUriParser.isTokenUriValid(mUri)).thenReturn(true);
//
//        ItemDomainModel itemDomainModel = mUriToItemDomainModelConverter.parseOTPUri(mItemId, mUserId, mUri);
//
//        assertNotNull(itemDomainModel);
//        assertEquals(itemDomainModel.getUid(), mItemId);
//
//        ItemDomainMetadata metadata = itemDomainModel.getMetadata();
//        assertNotNull(metadata);
//        assertEquals(metadata.getContentType(), ContentType.SENSITIVE);
//        assertEquals(metadata.getItemType(), mOTPType.getDesc());
//        assertEquals(metadata.getUserId(), mUserId);
//        assertTrue(metadata.isDataEncrypted());
//
//        ItemDomainData data = itemDomainModel.getData();
//        assertNotNull(data);
//        assertNotNull(data.getIdentifier());
//        assertNotNull(data.getPrivateData());
//
//        JSONObject identifier = new JSONObject(data.getIdentifier());
//        assertNotNull(identifier);
//        assertEquals(identifier.get(ISSUER), mOTPUriParser.mIssuer);
//        assertEquals(identifier.get(USER_LABEL), mOTPUriParser.mUserLabel);
//        assertEquals(identifier.get(IMAGE), mOTPUriParser.mImage);
//
//        JSONObject details = new JSONObject(data.getPrivateData());
//        assertNotNull(identifier);
//        assertEquals(details.get(KEY), mOTPUriParser.mSecret);
//        assertEquals(((Integer) details.get(COUNTER)).longValue(), mOTPUriParser.mCounter);
//        assertEquals(details.get(DIGITS_COUNT), mOTPUriParser.mDigits);
//        assertEquals(details.get(ALGORITHM), mOTPUriParser.mAlgorithm);
//        assertEquals(details.get(VALIDITY_PERIOD), mOTPUriParser.mPeriod);
//    }
//
//    private void initOTPParserProperties() {
//        mOTPUriParser.mOTPType = mOTPType;
//        mOTPUriParser.mIssuer = mIssuer;
//        mOTPUriParser.mUserLabel = mUserLabel;
//        mOTPUriParser.mAlgorithm = mAlgorithm;
//        mOTPUriParser.mDigits = mDigits;
//        mOTPUriParser.mPeriod = mPeriod;
//        mOTPUriParser.mCounter = mCounter;
//        mOTPUriParser.mSecret = mSecret;
//        mOTPUriParser.mImage = mImage;
//    }
//
//}