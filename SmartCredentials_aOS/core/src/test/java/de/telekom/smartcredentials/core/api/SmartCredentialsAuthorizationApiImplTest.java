///*
// * Developed by Lucian Iacob in Cluj-Napoca.
// * Project: SmartCredentials_aOS
// * Email: lucian.iacob@endava.com
// * Last modified 16/11/18 17:21.
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
//import de.telekom.smartcredentials.core.blacklisting.FilesManager;
//import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsFeatureSet;
//import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsSystemPropertyMap;
//import de.telekom.smartcredentials.core.callback.AuthorizationCallback;
//import de.telekom.smartcredentials.core.di.ObjectGraphCreator;
//import de.telekom.smartcredentials.core.domain.controllers.ApiController;
//import de.telekom.smartcredentials.core.plugins.fingerprint.BiometricAuthorizationPresenter;
//import de.telekom.smartcredentials.core.plugins.fingerprint.FingerprintAuthorizationPresenter;
//import de.telekom.smartcredentials.core.responses.FeatureNotSupportedThrowable;
//import de.telekom.smartcredentials.core.responses.RootedThrowable;
//import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
//import de.telekom.smartcredentials.core.utils.ApiLevel;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
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
//@PrepareForTest({ApiLevel.class, ObjectGraphCreator.class, FilesManager.class,
//        SmartCredentialsSystemPropertyMap.class})
//@RunWith(PowerMockRunner.class)
//public class SmartCredentialsAuthorizationApiImplTest {
//
//    private AuthorizationCallback mAuthorizationCallback;
//    private ApiController mApiController;
//    private AuthorizationApi mSCAuthorizationApi;
//
//    @Before
//    public void setup() {
//        PowerMockito.mockStatic(ApiLevel.class);
//        PowerMockito.mockStatic(ObjectGraphCreator.class);
//        PowerMockito.mockStatic(FilesManager.class);
//        PowerMockito.mockStatic(SmartCredentialsSystemPropertyMap.class);
//
//        ObjectGraphCreator objectGraphCreator = Mockito.mock(ObjectGraphCreator.class);
//
//        mAuthorizationCallback = Mockito.mock(AuthorizationCallback.class);
//        mApiController = Mockito.mock(ApiController.class);
//        mSCAuthorizationApi = new SmartCredentialsAuthorizationApiImpl(mApiController);
//
//        when(FilesManager.readDeviceBlackListJSON(any(), any())).thenReturn(new JSONObject());
//        when(ObjectGraphCreator.getInstance()).thenReturn(objectGraphCreator);
//    }
//
//    @Test
//    public void getAuthorizeUserFragmentReturnsUnsuccessfulResponseIfDeviceIsRooted() {
//        when(mApiController.isSecurityCompromised()).thenReturn(true);
//
//        SmartCredentialsApiResponse<Fragment> response = mSCAuthorizationApi
//                .getAuthorizeUserFragment(mAuthorizationCallback);
//
//        assertFalse(response.isSuccessful());
//        assertNotNull(response.getError());
//        assertTrue(response.getError() instanceof RootedThrowable);
//    }
//
//    @Test
//    public void getAuthorizeUserFragmentReturnsUnsuccessfulResponseIfDeviceIsBlacklisted() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        PowerMockito.when(SmartCredentialsSystemPropertyMap.isFeatureBlockedOnCurrentDevice(any(),
//                eq(SmartCredentialsFeatureSet.AUTH_POP_UP))).thenReturn(true);
//
//        SmartCredentialsApiResponse<Fragment> response = mSCAuthorizationApi
//                .getAuthorizeUserFragment(mAuthorizationCallback);
//
//        assertFalse(response.isSuccessful());
//        assertNotNull(response.getError());
//        assertTrue(response.getError() instanceof FeatureNotSupportedThrowable);
//    }
//
//    @Test
//    public void getAuthorizeUserFragmentReturnsResponseWithAuthFragment() {
//        when(mApiController.getAuthorizationTool(any())).thenReturn(new Fragment());
//        PowerMockito.when(ApiLevel.isAbove28()).thenReturn(false);
//
//        SmartCredentialsApiResponse<Fragment> response = mSCAuthorizationApi
//                .getAuthorizeUserFragment(mAuthorizationCallback);
//
//        verify(mApiController, times(1)).getAuthorizationTool(any());
//        assertNotNull(response);
//        assertNotNull(response.getData());
//        assertTrue(response.getData() instanceof Fragment);
//    }
//
//    @Test
//    public void getAuthorizeUserFragmentReturnsUnsuccessfulResponseWhenAPILevelIsAbove28() {
//        PowerMockito.when(ApiLevel.isAbove28()).thenReturn(true);
//
//        SmartCredentialsApiResponse<Fragment> response = mSCAuthorizationApi
//                .getAuthorizeUserFragment(mAuthorizationCallback);
//
//        verify(mApiController, never()).getAuthorizationTool(any());
//        assertNotNull(response);
//        assertFalse(response.isSuccessful());
//        assertNotNull(response.getError());
//        assertTrue(response.getError() instanceof Throwable);
//        assertEquals(ApiLevel.API_ABOVE_28_ERROR_MESSAGE, response.getError().getMessage());
//    }
//
//    @Test
//    public void getBiometricAuthorizationPresenterReturnsUnsuccessfulResponseIfDeviceIsRooted() {
//        when(mApiController.isSecurityCompromised()).thenReturn(true);
//
//        SmartCredentialsApiResponse<BiometricAuthorizationPresenter> response = mSCAuthorizationApi
//                .getBiometricAuthorizationPresenter(mAuthorizationCallback);
//
//        assertNotNull(response);
//        assertFalse(response.isSuccessful());
//        assertNotNull(response.getError());
//        assertTrue(response.getError() instanceof RootedThrowable);
//    }
//
//    @Test
//    public void getBiometricAuthorizationPresenterReturnsUnsuccessfulResponseIfDeviceIsBlacklisted() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        PowerMockito.when(SmartCredentialsSystemPropertyMap.isFeatureBlockedOnCurrentDevice(any(),
//                eq(SmartCredentialsFeatureSet.AUTH_BIOMETRICS))).thenReturn(true);
//
//        SmartCredentialsApiResponse<BiometricAuthorizationPresenter> response = mSCAuthorizationApi
//                .getBiometricAuthorizationPresenter(mAuthorizationCallback);
//
//        assertNotNull(response);
//        assertFalse(response.isSuccessful());
//        assertNotNull(response.getError());
//        assertTrue(response.getError() instanceof FeatureNotSupportedThrowable);
//    }
//
//    @Test
//    public void getBiometricAuthorizationPresenterReturnsResponseWithBiometricAuthorizationPresenter() {
//        BiometricAuthorizationPresenter presenter = Mockito.mock(BiometricAuthorizationPresenter.class);
//        when(mApiController.getBiometricAuthorizationPresenter(any())).thenReturn(presenter);
//        PowerMockito.when(ApiLevel.isBelow28()).thenReturn(false);
//
//        SmartCredentialsApiResponse<BiometricAuthorizationPresenter> response = mSCAuthorizationApi
//                .getBiometricAuthorizationPresenter(mAuthorizationCallback);
//
//        verify(mApiController, times(1)).getBiometricAuthorizationPresenter(any());
//        assertNotNull(response);
//        assertTrue(response.isSuccessful());
//        assertNotNull(response.getData());
//        assertEquals(presenter, response.getData());
//    }
//
//    @Test
//    public void getBiometricAuthorizationPresenterReturnsUnsuccessfulResponseWhenAPILevelIsBelow28() {
//        BiometricAuthorizationPresenter presenter = Mockito.mock(BiometricAuthorizationPresenter.class);
//        when(mApiController.getBiometricAuthorizationPresenter(any())).thenReturn(presenter);
//        PowerMockito.when(ApiLevel.isBelow28()).thenReturn(true);
//
//        SmartCredentialsApiResponse<BiometricAuthorizationPresenter> response = mSCAuthorizationApi
//                .getBiometricAuthorizationPresenter(mAuthorizationCallback);
//
//        verify(mApiController, never()).getBiometricAuthorizationPresenter(any());
//        assertNotNull(response);
//        assertFalse(response.isSuccessful());
//        assertNotNull(response.getError());
//        assertTrue(response.getError() instanceof Throwable);
//        assertEquals(ApiLevel.API_BELOW_28_ERROR_MESSAGE, response.getError().getMessage());
//    }
//
//    @Test
//    public void getFingerprintAuthorizationPresenterReturnsUnsuccessfulResponseIfDeviceIsRooted() {
//        when(mApiController.isSecurityCompromised()).thenReturn(true);
//
//        SmartCredentialsApiResponse<FingerprintAuthorizationPresenter> response = mSCAuthorizationApi
//                .getFingerprintAuthorizationPresenter(mAuthorizationCallback);
//
//        assertNotNull(response);
//        assertFalse(response.isSuccessful());
//        assertNotNull(response.getError());
//        assertTrue(response.getError() instanceof RootedThrowable);
//    }
//
//    @Test
//    public void getFingerprintAuthorizationPresenterReturnsUnsuccessfulResponseIfDeviceIsBlacklisted() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        PowerMockito.when(SmartCredentialsSystemPropertyMap.isFeatureBlockedOnCurrentDevice(any(),
//                eq(SmartCredentialsFeatureSet.AUTH_CUSTOM_UI))).thenReturn(true);
//
//        SmartCredentialsApiResponse<FingerprintAuthorizationPresenter> response = mSCAuthorizationApi
//                .getFingerprintAuthorizationPresenter(mAuthorizationCallback);
//
//        assertNotNull(response);
//        assertFalse(response.isSuccessful());
//        assertNotNull(response.getError());
//        assertTrue(response.getError() instanceof FeatureNotSupportedThrowable);
//    }
//
//    @Test
//    public void getFingerprintAuthorizationPresenterReturnsResponseWithFingerprintAuthorizationPresenter() {
//        FingerprintAuthorizationPresenter presenter = Mockito.mock(FingerprintAuthorizationPresenter.class);
//        when(mApiController.getFingerprintAuthorizationPlugin(any())).thenReturn(presenter);
//        PowerMockito.when(ApiLevel.isBelow23()).thenReturn(false);
//        PowerMockito.when(ApiLevel.isAbove28()).thenReturn(false);
//
//        SmartCredentialsApiResponse<FingerprintAuthorizationPresenter> response = mSCAuthorizationApi
//                .getFingerprintAuthorizationPresenter(mAuthorizationCallback);
//
//        verify(mApiController, times(1)).getFingerprintAuthorizationPlugin(any());
//        assertNotNull(response);
//        assertTrue(response.isSuccessful());
//        assertNotNull(response.getData());
//        assertEquals(presenter, response.getData());
//    }
//
//    @Test
//    public void getFingerprintAuthorizationPresenterReturnsUnsuccessfulResponseWhenAPILevelIsAbove28() {
//        FingerprintAuthorizationPresenter presenter = Mockito.mock(FingerprintAuthorizationPresenter.class);
//        when(mApiController.getFingerprintAuthorizationPlugin(any())).thenReturn(presenter);
//        PowerMockito.when(ApiLevel.isBelow23()).thenReturn(false);
//        PowerMockito.when(ApiLevel.isAbove28()).thenReturn(true);
//
//        SmartCredentialsApiResponse<FingerprintAuthorizationPresenter> response = mSCAuthorizationApi
//                .getFingerprintAuthorizationPresenter(mAuthorizationCallback);
//
//        verify(mApiController, never()).getFingerprintAuthorizationPlugin(any());
//        assertNotNull(response);
//        assertFalse(response.isSuccessful());
//        assertNotNull(response.getError());
//        assertTrue(response.getError() instanceof Throwable);
//        assertEquals(ApiLevel.API_ABOVE_28_ERROR_MESSAGE, response.getError().getMessage());
//    }
//
//    @Test
//    public void getFingerprintAuthorizationPresenterReturnsUnsuccessfulResponseWhenAPILevelIsBelow23() {
//        FingerprintAuthorizationPresenter presenter = Mockito.mock(FingerprintAuthorizationPresenter.class);
//        when(mApiController.getFingerprintAuthorizationPlugin(any())).thenReturn(presenter);
//        PowerMockito.when(ApiLevel.isBelow23()).thenReturn(true);
//
//        SmartCredentialsApiResponse<FingerprintAuthorizationPresenter> response = mSCAuthorizationApi
//                .getFingerprintAuthorizationPresenter(mAuthorizationCallback);
//
//        verify(mApiController, never()).getFingerprintAuthorizationPlugin(any());
//        assertNotNull(response);
//        assertFalse(response.isSuccessful());
//        assertNotNull(response.getError());
//        assertTrue(response.getError() instanceof Throwable);
//        assertEquals(ApiLevel.API_BELOW_23_ERROR_MESSAGE, response.getError().getMessage());
//    }
//
//}