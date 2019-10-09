///*
// * Developed by Lucian Iacob in Cluj-Napoca.
// * Project: SmartCredentials_aOS
// * Email: lucian.iacob@endava.com
// * Last modified 16/11/18 18:05.
// * Copyright (c) Deutsche Telekom, 2018. All rights reserved.
// */
//
//package de.telekom.smartcredentials.core.api;
//
//import android.support.v4.app.Fragment;
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
//import de.telekom.smartcredentials.core.ModelGenerator;
//import de.telekom.smartcredentials.core.blacklisting.FilesManager;
//import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsFeatureSet;
//import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsSystemPropertyMap;
//import de.telekom.smartcredentials.core.callback.AuthenticationCallback;
//import de.telekom.smartcredentials.core.di.ObjectGraphCreator;
//import de.telekom.smartcredentials.core.domain.controllers.ApiController;
//import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
//import de.telekom.smartcredentials.core.responses.EnvelopeException;
//import de.telekom.smartcredentials.core.responses.FeatureNotSupportedThrowable;
//import de.telekom.smartcredentials.core.responses.RootedThrowable;
//import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
//
//import static junit.framework.Assert.assertFalse;
//import static junit.framework.Assert.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
///**
// * Created by Lucian Iacob on November 16, 2018.
// */
//@PrepareForTest({ObjectGraphCreator.class, FilesManager.class, SmartCredentialsSystemPropertyMap.class})
//@RunWith(PowerMockRunner.class)
//public class SmartCredentialsQrLoginApiImplTest {
//
//    public AuthenticationCallback mAuthenticationCallback;
//    private ApiController mApiController;
//    private SmartCredentialsQrLoginApiImpl mSCQrLoginApi;
//    private ItemEnvelope mItemEnvelope;
//
//    @Before
//    public void setup() {
//        PowerMockito.mockStatic(ObjectGraphCreator.class);
//        PowerMockito.mockStatic(FilesManager.class);
//        PowerMockito.mockStatic(SmartCredentialsSystemPropertyMap.class);
//
//        mApiController = Mockito.mock(ApiController.class);
//        mAuthenticationCallback = Mockito.mock(AuthenticationCallback.class);
//        mSCQrLoginApi = new SmartCredentialsQrLoginApiImpl(mApiController);
//        mItemEnvelope = ModelGenerator.generateItemEnvelope();
//        ObjectGraphCreator objectGraphCreator = Mockito.mock(ObjectGraphCreator.class);
//
//        when(ObjectGraphCreator.getInstance()).thenReturn(objectGraphCreator);
//        when(FilesManager.readDeviceBlackListJSON(any(), any())).thenReturn(new JSONObject());
//    }
//
//    @Test
//    public void getAuthUserLoginFragmentReturnsUnsuccessfulResponseWhenDeviceIsRooted() {
//        when(mApiController.isSecurityCompromised()).thenReturn(true);
//
//        SmartCredentialsApiResponse<Fragment> response = mSCQrLoginApi
//                .getAuthorizeUserLogInFragment(mAuthenticationCallback, mItemEnvelope);
//
//        verify(mApiController, times(1)).handleSecurityCompromised();
//        verify(mApiController, never()).getLogInWithAuthorizationTool(any(), any());
//        assertFalse(response.isSuccessful());
//        assertTrue(response.getError() instanceof RootedThrowable);
//    }
//
//    @Test
//    public void getAuthUserLoginFragmentReturnsUnsuccessfulResponseWhenDeviceIsBlacklisted() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        PowerMockito.when(SmartCredentialsSystemPropertyMap.isFeatureBlockedOnCurrentDevice(any(),
//                eq(SmartCredentialsFeatureSet.QR_LOGIN))).thenReturn(true);
//
//        SmartCredentialsApiResponse<Fragment> response = mSCQrLoginApi
//                .getAuthorizeUserLogInFragment(mAuthenticationCallback, mItemEnvelope);
//
//        verify(mApiController, never()).getLogInWithAuthorizationTool(any(), any());
//        assertFalse(response.isSuccessful());
//        assertTrue(response.getError() instanceof FeatureNotSupportedThrowable);
//    }
//
//    @Test
//    public void getAuthUserLoginFragmentReturnsUnsuccessfulResponseWhenItemEnvelopeIsNull() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//
//        SmartCredentialsApiResponse<Fragment> response = mSCQrLoginApi
//                .getAuthorizeUserLogInFragment(mAuthenticationCallback, null);
//
//        verify(mApiController, never()).handleSecurityCompromised();
//        verify(mApiController, never()).getLogInWithAuthorizationTool(any(), any());
//        assertFalse(response.isSuccessful());
//        assertTrue(response.getError() instanceof EnvelopeException);
//    }
//
//    @Test
//    public void getAuthUserLoginFragmentReturnsSuccessfulResponse() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//
//        SmartCredentialsApiResponse<Fragment> response = mSCQrLoginApi
//                .getAuthorizeUserLogInFragment(mAuthenticationCallback, mItemEnvelope);
//
//        verify(mApiController, never()).handleSecurityCompromised();
//        verify(mApiController, times(1))
//                .getLogInWithAuthorizationTool(any(), eq(mItemEnvelope.getIdentifier()));
//        assertTrue(response.isSuccessful());
//    }
//}