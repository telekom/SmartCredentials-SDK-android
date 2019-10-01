///*
// * Developed by Lucian Iacob in Cluj-Napoca.
// * Project: SmartCredentials_aOS
// * Email: lucian.iacob@endava.com
// * Last modified 16/11/18 16:01.
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
//import de.telekom.smartcredentials.core.di.ObjectGraphCreator;
//import de.telekom.smartcredentials.core.domain.controllers.ApiController;
//import de.telekom.smartcredentials.core.exceptions.EncryptionException;
//import de.telekom.smartcredentials.core.model.EncryptionAlgorithm;
//import de.telekom.smartcredentials.core.responses.FeatureNotSupportedThrowable;
//import de.telekom.smartcredentials.core.responses.RootedThrowable;
//import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
//
//import static junit.framework.Assert.assertEquals;
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
//@PrepareForTest({ObjectGraphCreator.class, FilesManager.class,
//        SmartCredentialsSystemPropertyMap.class})
//@RunWith(PowerMockRunner.class)
//public class SmartCredentialsEncryptionApiImplTest {
//
//    private ApiController mApiController;
//
//    private SmartCredentialsEncryptionApi mSmartCredentialsEncryptionApi;
//
//    @Before
//    public void setUp() {
//        PowerMockito.mockStatic(ObjectGraphCreator.class);
//        PowerMockito.mockStatic(FilesManager.class);
//        PowerMockito.mockStatic(SmartCredentialsSystemPropertyMap.class);
//
//        mApiController = Mockito.mock(ApiController.class);
//        mSmartCredentialsEncryptionApi = new SmartCredentialsEncryptionApiImpl(mApiController);
//        ObjectGraphCreator objectGraphCreator = Mockito.mock(ObjectGraphCreator.class);
//
//        when(ObjectGraphCreator.getInstance()).thenReturn(objectGraphCreator);
//        when(FilesManager.readDeviceBlackListJSON(any(), any())).thenReturn(new JSONObject());
//    }
//
//    @Test
//    public void getPublicKeyReturnsUnsuccessfulResponseWhenDeviceIsRooted() {
//        String alias = "test";
//        when(mApiController.isSecurityCompromised()).thenReturn(true);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSmartCredentialsEncryptionApi
//                .getPublicKey(alias);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof RootedThrowable);
//    }
//
//    @Test
//    public void getPublicKeyReturnsUnsuccessfulResponseWhenDeviceIsBlacklisted() {
//        String alias = "test";
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        PowerMockito.when(SmartCredentialsSystemPropertyMap.isFeatureBlockedOnCurrentDevice(any(),
//                eq(SmartCredentialsFeatureSet.PUBLIC_KEY_GENERATION))).thenReturn(true);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSmartCredentialsEncryptionApi
//                .getPublicKey(alias);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof FeatureNotSupportedThrowable);
//    }
//
//    @Test
//    public void getPublicKeyReturnsSuccessfulResponseWhenDeviceIsNotRooted() throws EncryptionException {
//        String alias = "test";
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSmartCredentialsEncryptionApi
//                .getPublicKey(alias);
//
//        verify(mApiController, times(1)).getPublicKey(alias);
//        assertTrue(smartCredentialsResponse.isSuccessful());
//    }
//
//    @Test
//    public void getPublicKeyReturnsUnsuccessfulResponseWhenApiControllerThrowsException() throws EncryptionException {
//        String alias = "test";
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        when(mApiController.getPublicKey(alias)).thenThrow(new EncryptionException(""));
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSmartCredentialsEncryptionApi
//                .getPublicKey(alias);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof EncryptionException);
//    }
//
//    @Test
//    public void encryptReturnsUnsuccessfulResponseWhenDeviceIsRooted() {
//        String alias = "test";
//        String toEncrypt = "toEncrypt";
//        when(mApiController.isSecurityCompromised()).thenReturn(true);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSmartCredentialsEncryptionApi
//                .encrypt(toEncrypt, alias, EncryptionAlgorithm.AES_256);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof RootedThrowable);
//    }
//
//    @Test
//    public void encryptReturnsUnsuccessfulResponseWhenDeviceIsBlacklisted() {
//        String alias = "test";
//        String toEncrypt = "toEncrypt";
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        PowerMockito.when(SmartCredentialsSystemPropertyMap.isFeatureBlockedOnCurrentDevice(any(),
//                eq(SmartCredentialsFeatureSet.ENCRYPT))).thenReturn(true);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSmartCredentialsEncryptionApi
//                .encrypt(toEncrypt, alias, EncryptionAlgorithm.AES_256);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof FeatureNotSupportedThrowable);
//    }
//
//    @Test
//    public void encryptReturnsUnsuccessfulResponseWhenApiControllerThrowsException() throws EncryptionException {
//        String alias = "test";
//        String toEncrypt = "toEncrypt";
//        String errorMessage = "error";
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        when(mApiController.encrypt(toEncrypt, alias, EncryptionAlgorithm.DEFAULT))
//                .thenThrow(new EncryptionException(errorMessage));
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSmartCredentialsEncryptionApi
//                .encrypt(toEncrypt, alias, EncryptionAlgorithm.DEFAULT);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof EncryptionException);
//        assertEquals(smartCredentialsResponse.getError().getMessage(), errorMessage);
//    }
//
//    @Test
//    public void encryptReturnsSuccessfulResponseWhenDeviceIsNotRooted() throws EncryptionException {
//        String alias = "test";
//        String toEncrypt = "toEncrypt";
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSmartCredentialsEncryptionApi
//                .encrypt(toEncrypt, alias, EncryptionAlgorithm.RSA_2048);
//
//        verify(mApiController, times(1))
//                .encrypt(toEncrypt, alias, EncryptionAlgorithm.RSA_2048);
//        assertTrue(smartCredentialsResponse.isSuccessful());
//    }
//}