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
//import org.json.JSONObject;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//
//import de.telekom.smartcredentials.core.blacklisting.FilesManager;
//import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsFeatureSet;
//import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsSystemPropertyMap;
//import de.telekom.smartcredentials.core.callback.ScannerCallback;
//import de.telekom.smartcredentials.camera.barcode.BarcodeCameraScannerLayoutImpl;
//import de.telekom.smartcredentials.camera.views.CameraScannerLayout;
//import de.telekom.smartcredentials.core.context.BarcodeType;
//import de.telekom.smartcredentials.core.di.ObjectGraphCreator;
//import de.telekom.smartcredentials.core.domain.controllers.ApiController;
//import de.telekom.smartcredentials.core.responses.FeatureNotSupportedThrowable;
//import de.telekom.smartcredentials.core.responses.RootedThrowable;
//import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
//
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
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
//public class SmartCredentialsCameraApiImplTest {
//
//    @Rule
//    public ExpectedException mExceptionRule = ExpectedException.none();
//    private CameraApi mSmartCredentialsCameraApi;
//    private ScannerCallback mScannerCallback;
//    private ApiController mApiController;
//
//    @Before
//    public void setUp() {
//        PowerMockito.mockStatic(ObjectGraphCreator.class);
//        PowerMockito.mockStatic(FilesManager.class);
//        PowerMockito.mockStatic(SmartCredentialsSystemPropertyMap.class);
//
//        mApiController = Mockito.mock(ApiController.class);
//        mScannerCallback = Mockito.mock(ScannerCallback.class);
//        mSmartCredentialsCameraApi = new SmartCredentialsCameraApiImpl(mApiController);
//        ObjectGraphCreator objectGraphCreator = Mockito.mock(ObjectGraphCreator.class);
//
//        when(ObjectGraphCreator.getInstance()).thenReturn(objectGraphCreator);
//        when(FilesManager.readDeviceBlackListJSON(any(), any())).thenReturn(new JSONObject());
//    }
//
//    @Test
//    public void getBarcodeScannerViewReturnsUnsuccessfulResponseIfDeviceIsRooted() {
//        when(mApiController.isSecurityCompromised()).thenReturn(true);
//        SmartCredentialsApiResponse<CameraScannerLayout> response = mSmartCredentialsCameraApi
//                .getBarcodeScannerView(mScannerCallback, BarcodeType.BARCODE_1D_CODABAR);
//
//        assertFalse(response.isSuccessful());
//        assertNotNull(response.getError());
//        assertTrue(response.getError() instanceof RootedThrowable);
//    }
//
//    @Test
//    public void getBarcodeScannerViewDetectsAllBarcodeIfBarcodeArgIsNull() {
//        mSmartCredentialsCameraApi.getBarcodeScannerView(mScannerCallback, null);
//
//        verify(mApiController, times(1))
//                .getBarcodeScanner(any(), eq(BarcodeType.BARCODE_ALL_FORMATS.getFormat()));
//    }
//
//    @Test
//    public void getBarcodeScannerViewReturnsResponseWithBarcodeScannerView() {
//        when(mApiController.getBarcodeScanner(any(), anyInt()))
//                .thenReturn(Mockito.mock(BarcodeCameraScannerLayoutImpl.class));
//
//        SmartCredentialsApiResponse<CameraScannerLayout> response = mSmartCredentialsCameraApi
//                .getBarcodeScannerView(
//                        mScannerCallback, BarcodeType.BARCODE_ALL_FORMATS);
//
//        assertNotNull(response);
//        assertNotNull(response.getData());
//        assertTrue(response.getData() instanceof BarcodeCameraScannerLayoutImpl);
//    }
//
//    @Test
//    public void getOcrScannerViewReturnsUnsuccessfulResponseIfDeviceIsRooted() {
//        when(mApiController.isSecurityCompromised()).thenReturn(true);
//        SmartCredentialsApiResponse<CameraScannerLayout> response = mSmartCredentialsCameraApi
//                .getOcrScannerView(mScannerCallback);
//
//        assertFalse(response.isSuccessful());
//        assertNotNull(response.getError());
//        assertTrue(response.getError() instanceof RootedThrowable);
//    }
//
//    @Test
//    public void getOcrScannerViewReturnsResponseWithBarcodeScannerFragment() {
//        when(mApiController.getTextScanner(any()))
//                .thenReturn(Mockito.mock(CameraScannerLayout.class));
//
//        SmartCredentialsApiResponse<CameraScannerLayout> response = mSmartCredentialsCameraApi
//                .getOcrScannerView(mScannerCallback);
//
//        assertNotNull(response);
//        assertNotNull(response.getData());
//        assertTrue(response.getData() instanceof CameraScannerLayout);
//    }
//
//    @Test
//    public void getBarcodeScannerViewReturnsFeatureNotSupportedResponseIfDeviceIsBlacklisted() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        PowerMockito.when(SmartCredentialsSystemPropertyMap.isFeatureBlockedOnCurrentDevice(any(),
//                eq(SmartCredentialsFeatureSet.QR))).thenReturn(true);
//
//        SmartCredentialsApiResponse<CameraScannerLayout> response = mSmartCredentialsCameraApi
//                .getBarcodeScannerView(mScannerCallback, BarcodeType.BARCODE_1D_CODABAR);
//
//        assertFalse(response.isSuccessful());
//        assertNotNull(response.getError());
//        assertTrue(response.getError() instanceof FeatureNotSupportedThrowable);
//    }
//
//    @Test
//    public void getOcrScannerViewReturnsFeatureNotSupportedResponseIfDeviceIsBlacklisted() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        PowerMockito.when(SmartCredentialsSystemPropertyMap.isFeatureBlockedOnCurrentDevice(any(),
//                eq(SmartCredentialsFeatureSet.OCR))).thenReturn(true);
//
//        SmartCredentialsApiResponse<CameraScannerLayout> response = mSmartCredentialsCameraApi
//                .getOcrScannerView(mScannerCallback);
//
//        assertFalse(response.isSuccessful());
//        assertNotNull(response.getError());
//        assertTrue(response.getError() instanceof FeatureNotSupportedThrowable);
//    }
//
////    @Test
////    public void importOTPItemViaQRForIdReturnsUnsuccessfulResponseWhenDeviceIsRooted() {
////        String otpItemId = "itemId";
////        OTPImporterCallback otpImporterCallback = Mockito.mock(OTPImporterCallback.class);
////        when(mApiController.isSecurityCompromised()).thenReturn(true);
////        SmartCredentialsApiResponse smartCredentialsResponse = mSmartCredentialsCameraApi.importOTPItemViaQRForId(otpItemId, otpImporterCallback);
////
////        assertFalse(smartCredentialsResponse.isSuccessful());
////        assertTrue(smartCredentialsResponse.getError() instanceof RootedThrowable);
////    }
////
////    @PrepareForTest({PluginCallbackConverter.class})
////    @Test
////    public void importOTPItemViaQRForIdReturnsSuccessfulResponse() {
////        PowerMockito.mockStatic(PluginCallbackConverter.class);
////        String otpItemId = "itemId";
////        OTPImporterCallback otpImporterCallback = Mockito.mock(OTPImporterCallback.class);
////
////        OTPScannerPluginCallback pluginCallback = Mockito.mock(OTPScannerPluginCallback.class);
////        when(mSmartCredentialsCameraApi.mApiController.getQROTPScanner(otpItemId, mUserId, pluginCallback, BarcodeType.BARCODE_2D_QR_CODE.getFormat()))
////                .thenReturn(Mockito.mock(CameraScannerLayout.class));
////
////        SmartCredentialsApiResponse<CameraScannerLayout> response = mSmartCredentialsCameraApi.importOTPItemViaQRForId(otpItemId, otpImporterCallback);
////
////        assertNotNull(response);
////        assertTrue(response.isSuccessful());
////    }
//
//}