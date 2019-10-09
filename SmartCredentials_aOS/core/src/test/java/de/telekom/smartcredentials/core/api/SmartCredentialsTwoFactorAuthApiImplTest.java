///*
// * Developed by Lucian Iacob in Cluj-Napoca.
// * Project: SmartCredentials_aOS
// * Email: lucian.iacob@endava.com
// * Last modified 16/11/18 17:41.
// * Copyright (c) Deutsche Telekom, 2018. All rights reserved.
// */
//
//package de.telekom.smartcredentials.core.api;
//
//import android.os.Looper;
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
//import de.telekom.smartcredentials.core.callback.HOTPHandlerCallback;
//import de.telekom.smartcredentials.core.callback.OTPHandlerCallback;
//import de.telekom.smartcredentials.core.callback.OTPImporterCallback;
//import de.telekom.smartcredentials.core.callback.PluginCallbackTwoFactorAuthConverter;
//import de.telekom.smartcredentials.core.callback.TOTPHandlerCallback;
//import de.telekom.smartcredentials.camera.views.CameraScannerLayout;
//import de.telekom.smartcredentials.core.context.BarcodeType;
//import de.telekom.smartcredentials.core.di.ObjectGraphCreator;
//import de.telekom.smartcredentials.core.domain.controllers.ApiController;
//import de.telekom.smartcredentials.core.exceptions.EncryptionException;
//import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
//import de.telekom.smartcredentials.core.model.otp.OTPType;
//import de.telekom.smartcredentials.core.model.token.TokenRequest;
//import de.telekom.smartcredentials.core.plugins.callbacks.OTPScannerPluginCallback;
//import de.telekom.smartcredentials.core.filter.SmartCredentialsFilter;
//import de.telekom.smartcredentials.core.responses.FeatureNotSupportedThrowable;
//import de.telekom.smartcredentials.core.responses.RootedThrowable;
//import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
//import de.telekom.smartcredentials.core.task.InitOTPTask;
//import de.telekom.smartcredentials.core.task.OTPHandlerResolver;
//import de.telekom.smartcredentials.core.task.TaskManager;
//
//import static junit.framework.Assert.assertFalse;
//import static junit.framework.Assert.assertNotNull;
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
//
//@PrepareForTest({Looper.class, ObjectGraphCreator.class, FilesManager.class,
//        SmartCredentialsSystemPropertyMap.class})
//@RunWith(PowerMockRunner.class)
//public class SmartCredentialsTwoFactorAuthApiImplTest {
//
//    private SmartCredentialsTwoFactorAuthApiImpl mSmartCredentialsApi;
//    private ApiController mApiController;
//    private TaskManager mTaskManager;
//    private OTPImporterCallback mOTPImporterCallback;
//    private Looper mMockMainThreadLooper;
//
//    @Before
//    public void setup() {
//        PowerMockito.mockStatic(Looper.class);
//        PowerMockito.mockStatic(ObjectGraphCreator.class);
//        PowerMockito.mockStatic(FilesManager.class);
//        PowerMockito.mockStatic(SmartCredentialsSystemPropertyMap.class);
//
//        mMockMainThreadLooper = Mockito.mock(Looper.class);
//        mOTPImporterCallback = Mockito.mock(OTPImporterCallback.class);
//        mApiController = Mockito.mock(ApiController.class);
//        mTaskManager = Mockito.mock(TaskManager.class);
//        mSmartCredentialsApi = new SmartCredentialsTwoFactorAuthApiImpl(mApiController, mTaskManager);
//        ObjectGraphCreator objectGraphCreator = Mockito.mock(ObjectGraphCreator.class);
//
//        when(Looper.getMainLooper()).thenReturn(mMockMainThreadLooper);
//        when(ObjectGraphCreator.getInstance()).thenReturn(objectGraphCreator);
//        when(FilesManager.readDeviceBlackListJSON(any(), any())).thenReturn(new JSONObject());
//    }
//
//    @Test
//    public void importOTPItemViaQRForIdReturnsUnsuccessfulResponseWhenDeviceIsRooted() {
//        String otpItemId = "itemId";
//        when(mApiController.isSecurityCompromised()).thenReturn(true);
//        SmartCredentialsApiResponse smartCredentialsResponse = mSmartCredentialsApi
//                .importOTPItemViaQRForId(otpItemId, mOTPImporterCallback);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof RootedThrowable);
//    }
//
//    @Test
//    public void importOTPItemViaQRForIdReturnsUnsuccessfulResponseWhenDeviceIsBlacklisted() {
//        String otpItemId = "itemId";
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        PowerMockito.when(SmartCredentialsSystemPropertyMap.isFeatureBlockedOnCurrentDevice(any(),
//                eq(SmartCredentialsFeatureSet.OTP_VIA_QR))).thenReturn(true);
//        SmartCredentialsApiResponse smartCredentialsResponse = mSmartCredentialsApi
//                .importOTPItemViaQRForId(otpItemId, mOTPImporterCallback);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof FeatureNotSupportedThrowable);
//    }
//
//    @PrepareForTest({PluginCallbackTwoFactorAuthConverter.class, Looper.class,
//            ObjectGraphCreator.class, FilesManager.class, SmartCredentialsSystemPropertyMap.class})
//    @Test
//    public void importOTPItemViaQRForIdReturnsSuccessfulResponse() {
//        PowerMockito.mockStatic(PluginCallbackTwoFactorAuthConverter.class);
//        OTPScannerPluginCallback pluginCallback = Mockito.mock(OTPScannerPluginCallback.class);
//        String otpItemId = "itemId";
//
//        when(mApiController.getQROTPScanner(otpItemId, "123", pluginCallback, BarcodeType.BARCODE_2D_QR_CODE.getFormat()))
//                .thenReturn(Mockito.mock(CameraScannerLayout.class));
//
//        SmartCredentialsApiResponse<CameraScannerLayout> response = mSmartCredentialsApi.importOTPItemViaQRForId(otpItemId, mOTPImporterCallback);
//
//        assertNotNull(response);
//        assertTrue(response.isSuccessful());
//    }
//
//    @Test
//    public void initOTPExecutesApiControllerMethodOnCurrentThreadIfCurrentlyNotOnMainThread() throws EncryptionException {
//        InitOTPTask initOTPTask = Mockito.mock(InitOTPTask.class);
//        ItemDomainModel itemDomainModel = Mockito.mock(ItemDomainModel.class);
//        OTPHandlerCallback otpHandlerCallback = Mockito.mock(OTPHandlerCallback.class);
//        OTPHandlerResolver otpHandlerResolver = Mockito.mock(OTPHandlerResolver.class);
//        TokenRequest tokenRequest = Mockito.mock(TokenRequest.class);
//
//        when(mMockMainThreadLooper.getThread()).thenReturn(new Thread());
//        when(mApiController.retrieveTokenRequest(itemDomainModel)).thenReturn(tokenRequest);
//        when(mTaskManager.getInitOTPTask(mApiController, otpHandlerCallback, itemDomainModel)).thenReturn(initOTPTask);
//        when(initOTPTask.getOTPHandlerResolver()).thenReturn(otpHandlerResolver);
//        when(tokenRequest.getEncryptedModel()).thenReturn("");
//
//        mSmartCredentialsApi.initOTP(otpHandlerCallback, itemDomainModel);
//
//        verify(otpHandlerResolver, times(1))
//                .resolveHandler(mApiController, otpHandlerCallback, itemDomainModel);
//    }
//
//    @Test
//    public void initOTPExecutesRootedTaskMethodOnNewThreadIfCurrentlyOnMainThread() {
//        InitOTPTask initOTPTask = Mockito.mock(InitOTPTask.class);
//        ItemDomainModel itemDomainModel = Mockito.mock(ItemDomainModel.class);
//        OTPHandlerCallback otpHandlerCallback = Mockito.mock(OTPHandlerCallback.class);
//
//        when(mMockMainThreadLooper.getThread()).thenReturn(Thread.currentThread());
//        when(mTaskManager.getInitOTPTask(mApiController, otpHandlerCallback, itemDomainModel))
//                .thenReturn(initOTPTask);
//
//        mSmartCredentialsApi.initOTP(otpHandlerCallback, itemDomainModel);
//
//        verify(initOTPTask, times(1)).run();
//    }
//
//
//    @Test
//    public void getHOTPHandlerReturnsUnsuccessfulResponseIfDeviceIsRooted() {
//        HOTPHandlerCallback callback = Mockito.mock(HOTPHandlerCallback.class);
//        when(mApiController.isSecurityCompromised()).thenReturn(true);
//
//        SmartCredentialsApiResponse<Void> response = mSmartCredentialsApi
//                .createHOTPGenerator("12", callback);
//
//        assertFalse(response.isSuccessful());
//        assertNotNull(response.getError());
//        assertTrue(response.getError() instanceof RootedThrowable);
//    }
//
//    @Test
//    public void getHOTPHandlerReturnsUnsuccessfulResponseIfDeviceIsBlacklisted() {
//        HOTPHandlerCallback callback = Mockito.mock(HOTPHandlerCallback.class);
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        PowerMockito.when(SmartCredentialsSystemPropertyMap.isFeatureBlockedOnCurrentDevice(any(),
//                eq(SmartCredentialsFeatureSet.OTP_HOTP))).thenReturn(true);
//
//        SmartCredentialsApiResponse<Void> response = mSmartCredentialsApi
//                .createHOTPGenerator("12", callback);
//
//        assertFalse(response.isSuccessful());
//        assertNotNull(response.getError());
//        assertTrue(response.getError() instanceof FeatureNotSupportedThrowable);
//    }
//
//    @Test
//    public void getTOTPHandlerReturnsUnsuccessfulResponseIfDeviceIsRooted() {
//        TOTPHandlerCallback callback = Mockito.mock(TOTPHandlerCallback.class);
//        when(mApiController.isSecurityCompromised()).thenReturn(true);
//
//        SmartCredentialsApiResponse<Void> response = mSmartCredentialsApi
//                .createTOTPGenerator("124", callback);
//
//        assertFalse(response.isSuccessful());
//        assertNotNull(response.getError());
//        assertTrue(response.getError() instanceof RootedThrowable);
//    }
//
//    @Test
//    public void getTOTPHandlerReturnsUnsuccessfulResponseIfDeviceIsBlacklisted() {
//        TOTPHandlerCallback callback = Mockito.mock(TOTPHandlerCallback.class);
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        PowerMockito.when(SmartCredentialsSystemPropertyMap.isFeatureBlockedOnCurrentDevice(any(),
//                eq(SmartCredentialsFeatureSet.OTP_TOTP))).thenReturn(true);
//
//        SmartCredentialsApiResponse<Void> response = mSmartCredentialsApi
//                .createTOTPGenerator("124", callback);
//
//        assertFalse(response.isSuccessful());
//        assertNotNull(response.getError());
//        assertTrue(response.getError() instanceof FeatureNotSupportedThrowable);
//    }
//
//    @Test
//    public void getOTPHandlerReturnsSuccessfulResponse() throws EncryptionException {
//        InitOTPTask initOTPTask = Mockito.mock(InitOTPTask.class);
//        ItemDomainModel itemDomainModel = Mockito.mock(ItemDomainModel.class);
//        OTPHandlerResolver otpHandlerResolver = Mockito.mock(OTPHandlerResolver.class);
//        OTPHandlerCallback otpHandlerCallback = Mockito.mock(OTPHandlerCallback.class);
//        SmartCredentialsFilter smartCredentialsFilter = Mockito.mock(SmartCredentialsFilter.class);
//        TokenRequest request = Mockito.mock(TokenRequest.class);
//
//        when(request.getEncryptedModel()).thenReturn("");
//        when(mApiController.retrieveTokenRequest(any())).thenReturn(request);
//        when(initOTPTask.getOTPHandlerResolver()).thenReturn(otpHandlerResolver);
//        when(smartCredentialsFilter.toItemDomainModel(mApiController.getUserId())).thenReturn(itemDomainModel);
//        when(mTaskManager.getInitOTPTask(mApiController, otpHandlerCallback, itemDomainModel))
//                .thenReturn(initOTPTask);
//
//
//        SmartCredentialsApiResponse<Void> response = mSmartCredentialsApi
//                .getOTPGenerator(smartCredentialsFilter, otpHandlerCallback, OTPType.HOTP);
//
//        assertNotNull(response);
//        assertTrue(response.isSuccessful());
//    }
//}