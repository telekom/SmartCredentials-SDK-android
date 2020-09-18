///*
// * Developed by Lucian Iacob in Cluj-Napoca.
// * Project: SmartCredentials_aOS
// * Email: lucian.iacob@endava.com
// * Last modified 16/11/18 16:04.
// * Copyright (c) Deutsche Telekom, 2018. All rights reserved.
// */
//
//package de.telekom.smartcredentials.core.api;
//
//import org.json.JSONObject;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//
//import de.telekom.smartcredentials.core.blacklisting.FilesManager;
//import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsFeatureSet;
//import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsSystemPropertyMap;
//import de.telekom.smartcredentials.core.callback.ServiceCallback;
//import de.telekom.smartcredentials.core.di.ObjectGraphCreator;
//import de.telekom.smartcredentials.core.domain.controllers.ApiController;
//import de.telekom.smartcredentials.qrlogin.RequestParams;
//import de.telekom.smartcredentials.core.responses.FeatureNotSupportedThrowable;
//import de.telekom.smartcredentials.core.responses.RootedThrowable;
//import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
//
//import static junit.framework.Assert.assertFalse;
//import static junit.framework.Assert.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
///**
// * Created by Lucian Iacob on November 16, 2018.
// */
//@PrepareForTest({ObjectGraphCreator.class, FilesManager.class, SmartCredentialsSystemPropertyMap.class})
//@RunWith(PowerMockRunner.class)
//public class SmartCredentialsNetworkingApiImplTest {
//
//    private ApiController mApiController;
//    private NetworkingApi mSmartCredentialsNetworkingApi;
//
//    @Before
//    public void setUp() {
//        PowerMockito.mockStatic(ObjectGraphCreator.class);
//        PowerMockito.mockStatic(FilesManager.class);
//        PowerMockito.mockStatic(SmartCredentialsSystemPropertyMap.class);
//
//        mApiController = Mockito.mock(ApiController.class);
//        mSmartCredentialsNetworkingApi = new SmartCredentialsNetworkingApiImpl(mApiController);
//        ObjectGraphCreator objectGraphCreator = Mockito.mock(ObjectGraphCreator.class);
//
//        when(ObjectGraphCreator.getInstance()).thenReturn(objectGraphCreator);
//        when(FilesManager.readDeviceBlackListJSON(any(), any())).thenReturn(new JSONObject());
//    }
//
//    @Test
//    public void callServiceReturnsUnsuccessfulResponseWhenDeviceIsRooted() {
//        RequestParams requestParams = Mockito.mock(RequestParams.class);
//        ServiceCallback serviceCallback = Mockito.mock(ServiceCallback.class);
//        when(mApiController.isSecurityCompromised()).thenReturn(true);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSmartCredentialsNetworkingApi
//                .callService(requestParams, serviceCallback);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof RootedThrowable);
//    }
//
//    @Test
//    public void callServiceReturnsUnsuccessfulResponseWhenDeviceIsBlacklisted() {
//        RequestParams requestParams = Mockito.mock(RequestParams.class);
//        ServiceCallback serviceCallback = Mockito.mock(ServiceCallback.class);
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        PowerMockito.when(SmartCredentialsSystemPropertyMap.isFeatureBlockedOnCurrentDevice(any(),
//                eq(SmartCredentialsFeatureSet.CALL_SERVICE))).thenReturn(true);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSmartCredentialsNetworkingApi
//                .callService(requestParams, serviceCallback);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof FeatureNotSupportedThrowable);
//    }
//
//    @Test
//    public void callServiceReturnsSuccessfulResponseWhenDeviceIsNotRooted() {
//        RequestParams requestParams = Mockito.mock(RequestParams.class);
//        ServiceCallback serviceCallback = Mockito.mock(ServiceCallback.class);
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSmartCredentialsNetworkingApi
//                .callService(requestParams, serviceCallback);
//
//        verify(mApiController, times(1))
//                .callService(requestParams, serviceCallback);
//        assertTrue(smartCredentialsResponse.isSuccessful());
//    }
//}