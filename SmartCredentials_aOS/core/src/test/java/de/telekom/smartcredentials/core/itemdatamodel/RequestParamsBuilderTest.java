///*
// * Developed by Lucian Iacob in Cluj-Napoca.
// * Project: SmartCredentials_aOS
// * Email: lucian.iacob@endava.com
// * Last modified 07/11/18 14:02.
// * Copyright (c) Deutsche Telekom, 2018. All rights reserved.
// */
//
//package de.telekom.smartcredentials.core.itemdatamodel;
//
//import android.text.TextUtils;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//import org.junit.runner.RunWith;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import de.telekom.smartcredentials.core.model.request.Method;
//import de.telekom.smartcredentials.core.model.request.NetworkConnectionType;
//import de.telekom.smartcredentials.core.model.request.RequestParams;
//import de.telekom.smartcredentials.core.responses.EnvelopeException;
//import de.telekom.smartcredentials.core.responses.EnvelopeExceptionReason;
//import okhttp3.Dns;
//import okhttp3.Interceptor;
//
//import static de.telekom.smartcredentials.core.itemdatamodel.RequestParamsBuilder.DEFAULT_METHOD;
//import static de.telekom.smartcredentials.core.itemdatamodel.RequestParamsBuilder.DEFAULT_NETWORK_CONNECTION;
//import static de.telekom.smartcredentials.core.itemdatamodel.RequestParamsBuilder.JSON_MEDIA_TYPE;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.ArgumentMatchers.anyString;
//
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({TextUtils.class})
//public class RequestParamsBuilderTest {
//
//    private String mEndpoint = "http://test.com";
//    private Map<String, String> mHeaders = new HashMap<>();
//    private Map<String, String> mQueryParams = new HashMap<>();
//    private String mRequestBody = "{\"id\": 4}";
//    private String mRequestBodyMediaType = "application/json";
//    private Method mMethod = Method.UPDATE;
//    private NetworkConnectionType mNetworkConnectionType = NetworkConnectionType.MOBILE;
//    private Map<String, List<String>> mCertPins = new HashMap<>();
//    private boolean mFollowsRedirect = false;
//    private long mTimeout = 1000;
//    private List<Interceptor> mInterceptorList = new ArrayList<>();
//    private Dns mDns = Dns.SYSTEM;
//
//    private RequestParamsBuilder mRequestParamsBuilder;
//
//    @Rule
//    ExpectedException mExpectedExceptionRule = ExpectedException.none();
//
//    @Before
//    public void setUp() {
//        PowerMockito.mockStatic(TextUtils.class);
//        mHeaders.put("header1", "headerValue");
//        mCertPins.put(mEndpoint, Collections.singletonList("certTest"));
//        mQueryParams.put("name", "test");
//
//        mRequestParamsBuilder = new RequestParamsBuilder()
//                .setEndpoint(mEndpoint)
//                .setHeaders(mHeaders)
//                .setQueryParams(mQueryParams)
//                .setRequestBody(mRequestBody)
//                .setRequestBodyMediaType(mRequestBodyMediaType)
//                .setRequestMethod(mMethod)
//                .setNetworkConnectionType(mNetworkConnectionType)
//                .setPinsCertificatesMap(mCertPins)
//                .setFollowsRedirects(mFollowsRedirect)
//                .setTimeoutMillis(mTimeout)
//                .setInterceptorList(mInterceptorList)
//                .setDns(mDns);
//    }
//
//    @Test
//    public void constructorCreatesInstanceWithDefaultPropertiesValues() {
//        RequestParamsBuilder requestParamsBuilder = new RequestParamsBuilder();
//
//        assertEquals("", requestParamsBuilder.getEndpoint());
//
//        assertNotNull(requestParamsBuilder.getHeaders());
//        assertTrue(requestParamsBuilder.getHeaders().isEmpty());
//
//        assertNotNull(requestParamsBuilder.getQueryParams());
//        assertTrue(requestParamsBuilder.getQueryParams().isEmpty());
//
//        assertEquals("", requestParamsBuilder.getRequestBody());
//        assertEquals(JSON_MEDIA_TYPE, requestParamsBuilder.getRequestBodyMediaType());
//        assertEquals(DEFAULT_METHOD, requestParamsBuilder.getMethod());
//        assertEquals(DEFAULT_NETWORK_CONNECTION, requestParamsBuilder.getNetworkConnectionType());
//        assertTrue(requestParamsBuilder.followsRedirects());
//        assertEquals(0, requestParamsBuilder.getTimeoutMillis());
//        assertTrue(requestParamsBuilder.getPinsCertificatesMap().isEmpty());
//        assertTrue(requestParamsBuilder.getInterceptorList().isEmpty());
//        assertEquals(Dns.SYSTEM, requestParamsBuilder.getDns());
//    }
//
//    @Test
//    public void setterMethods() {
//        assertEquals(mEndpoint, mRequestParamsBuilder.getEndpoint());
//        assertEquals(mHeaders, mRequestParamsBuilder.getHeaders());
//        assertEquals(mQueryParams, mRequestParamsBuilder.getQueryParams());
//        assertEquals(mRequestBody, mRequestParamsBuilder.getRequestBody());
//        assertEquals(mRequestBodyMediaType, mRequestParamsBuilder.getRequestBodyMediaType());
//        assertEquals(mMethod, mRequestParamsBuilder.getMethod());
//        assertEquals(mNetworkConnectionType, mRequestParamsBuilder.getNetworkConnectionType());
//        assertEquals(mFollowsRedirect, mRequestParamsBuilder.followsRedirects());
//        assertEquals(mTimeout, mRequestParamsBuilder.getTimeoutMillis());
//        assertEquals(mCertPins, mRequestParamsBuilder.getPinsCertificatesMap());
//        assertEquals(mInterceptorList, mRequestParamsBuilder.getInterceptorList());
//        assertEquals(mDns, mRequestParamsBuilder.getDns());
//    }
//
//    @Test
//    public void toRequestParamsCreatesARequestParamsObjectFromItemEnvelopeObject() {
//        PowerMockito.when(TextUtils.isEmpty(mEndpoint)).thenReturn(false);
//        RequestParams requestParams = mRequestParamsBuilder.build();
//
//        assertEquals(mEndpoint, requestParams.getEndpoint());
//        assertEquals(mHeaders, requestParams.getHeaders());
//        assertEquals(mQueryParams, requestParams.getQueryParams());
//        assertEquals(mRequestBody, requestParams.getBody());
//        assertEquals(mRequestBodyMediaType, requestParams.getBodyType());
//        assertEquals(mMethod, requestParams.getMethod());
//        assertEquals(mNetworkConnectionType, requestParams.getNetworkConnectionType());
//        assertEquals(mCertPins, requestParams.getPinsCertificatesMap());
//        assertEquals(mFollowsRedirect, requestParams.followsRedirects());
//        assertEquals(mTimeout, requestParams.getTimeoutMillis());
//        assertEquals(mInterceptorList, requestParams.getInterceptorList());
//        assertEquals(mDns, requestParams.getDns());
//    }
//
//    @Test
//    public void toRequestParamsThrowsExceptionWhenItemEnvelopeIdentifierDoesNotContainEndpoint() {
//        PowerMockito.when(TextUtils.isEmpty(anyString())).thenReturn(true);
//
//        mExpectedExceptionRule.expect(EnvelopeException.class);
//        mExpectedExceptionRule.expectMessage(EnvelopeExceptionReason.REQUEST_PARAMS_EXCEPTION.getReason());
//
//        mRequestParamsBuilder.build();
//    }
//}