///*
// * Developed by Lucian Iacob in Cluj-Napoca.
// * Project: SmartCredentials_aOS
// * Email: lucian.iacob@endava.com
// * Last modified 16/11/18 17:14.
// * Copyright (c) Deutsche Telekom, 2018. All rights reserved.
// */
//
//package de.telekom.smartcredentials.core.api;
//
//import com.microblink.util.RecognizerCompatibility;
//import com.microblink.util.RecognizerCompatibilityStatus;
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
//import de.telekom.smartcredentials.documentscanner.config.DocumentScanConfiguration;
//import de.telekom.smartcredentials.documentscanner.view.DocumentScannerLayout;
//import de.telekom.smartcredentials.documentscanner.view.DocumentScannerLayoutImpl;
//import de.telekom.smartcredentials.core.domain.controllers.ApiController;
//import de.telekom.smartcredentials.core.responses.FeatureNotSupportedThrowable;
//import de.telekom.smartcredentials.core.responses.RootedThrowable;
//import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
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
//public class SmartCredentialsDocumentScannerApiImplTest {
//
//    private ApiController mApiController;
//    private SmartCredentialsDocumentScannerApiImpl mSCDocumentScannerApi;
//    private DocumentScanConfiguration mDocumentScannerConfiguration;
//
//    @Before
//    public void setup() {
//        PowerMockito.mockStatic(ObjectGraphCreator.class);
//        PowerMockito.mockStatic(FilesManager.class);
//        PowerMockito.mockStatic(SmartCredentialsSystemPropertyMap.class);
//
//        mDocumentScannerConfiguration = Mockito.mock(DocumentScanConfiguration.class);
//        mApiController = Mockito.mock(ApiController.class);
//        mSCDocumentScannerApi = new SmartCredentialsDocumentScannerApiImpl(mApiController);
//        ObjectGraphCreator objectGraphCreator = Mockito.mock(ObjectGraphCreator.class);
//
//        when(ObjectGraphCreator.getInstance()).thenReturn(objectGraphCreator);
//        when(FilesManager.readDeviceBlackListJSON(any(), any())).thenReturn(new JSONObject());
//    }
//
//    @Test
//    public void getDocumentScannerViewReturnsUnsuccessfulResponseIfDeviceIsRooted() {
//        when(mApiController.isSecurityCompromised()).thenReturn(true);
//
//        SmartCredentialsApiResponse<DocumentScannerLayout> response = mSCDocumentScannerApi
//                .getDocumentScannerView(mDocumentScannerConfiguration, null);
//
//        assertFalse(response.isSuccessful());
//        assertNotNull(response.getError());
//        assertTrue(response.getError() instanceof RootedThrowable);
//    }
//
//    @Test
//    public void getDocumentScannerViewReturnsUnsuccessfulResponseIfDeviceIsBlacklisted() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        PowerMockito.when(SmartCredentialsSystemPropertyMap.isFeatureBlockedOnCurrentDevice(any(),
//                eq(SmartCredentialsFeatureSet.DOCUMENT_SCANNER))).thenReturn(true);
//
//        SmartCredentialsApiResponse<DocumentScannerLayout> response = mSCDocumentScannerApi
//                .getDocumentScannerView(mDocumentScannerConfiguration, null);
//
//        assertFalse(response.isSuccessful());
//        assertNotNull(response.getError());
//        assertTrue(response.getError() instanceof FeatureNotSupportedThrowable);
//    }
//
//    @Test
//    @PrepareForTest({RecognizerCompatibility.class, ObjectGraphCreator.class, FilesManager.class,
//            SmartCredentialsSystemPropertyMap.class})
//    public void getDocumentScannerViewReturnsUnsuccessfulResponseIfDeviceHasNoCamera() {
//        PowerMockito.mockStatic(RecognizerCompatibility.class);
//        PowerMockito.when(RecognizerCompatibility.getRecognizerCompatibilityStatus(any()))
//                .thenReturn(RecognizerCompatibilityStatus.NO_CAMERA);
//
//        SmartCredentialsApiResponse<DocumentScannerLayout> response = mSCDocumentScannerApi
//                .getDocumentScannerView(mDocumentScannerConfiguration, null);
//
//        assertFalse(response.isSuccessful());
//        assertNotNull(response.getError());
//        assertTrue(response.getError() instanceof FeatureNotSupportedThrowable);
//        assertEquals(response.getError().getMessage(), RecognizerCompatibilityStatus.NO_CAMERA.name());
//    }
//
//    @Test
//    @PrepareForTest({RecognizerCompatibility.class, ObjectGraphCreator.class, FilesManager.class,
//            SmartCredentialsSystemPropertyMap.class})
//    public void getDocumentScannerViewReturnsUnsuccessfulResponseIfArchitectureNotSupported() {
//        PowerMockito.mockStatic(RecognizerCompatibility.class);
//        PowerMockito.when(RecognizerCompatibility.getRecognizerCompatibilityStatus(any()))
//                .thenReturn(RecognizerCompatibilityStatus.PROCESSOR_ARCHITECTURE_NOT_SUPPORTED);
//
//        SmartCredentialsApiResponse<DocumentScannerLayout> response = mSCDocumentScannerApi
//                .getDocumentScannerView(mDocumentScannerConfiguration, null);
//
//        assertFalse(response.isSuccessful());
//        assertNotNull(response.getError());
//        assertTrue(response.getError() instanceof FeatureNotSupportedThrowable);
//        assertEquals(response.getError().getMessage(), RecognizerCompatibilityStatus.PROCESSOR_ARCHITECTURE_NOT_SUPPORTED.name());
//    }
//
//    @Test
//    @PrepareForTest({RecognizerCompatibility.class, ObjectGraphCreator.class, FilesManager.class,
//            SmartCredentialsSystemPropertyMap.class})
//    public void getDocumentScannerViewReturnsUnsuccessfulResponseIfRecognizerNotSupported() {
//        PowerMockito.mockStatic(RecognizerCompatibility.class);
//        PowerMockito.when(RecognizerCompatibility.getRecognizerCompatibilityStatus(any()))
//                .thenReturn(RecognizerCompatibilityStatus.RECOGNIZER_NOT_SUPPORTED);
//
//        SmartCredentialsApiResponse<DocumentScannerLayout> response = mSCDocumentScannerApi
//                .getDocumentScannerView(mDocumentScannerConfiguration, null);
//
//        assertFalse(response.isSuccessful());
//        assertNotNull(response.getError());
//        assertTrue(response.getError() instanceof FeatureNotSupportedThrowable);
//        assertEquals(response.getError().getMessage(), RecognizerCompatibilityStatus.RECOGNIZER_NOT_SUPPORTED.name());
//    }
//
//    @Test
//    @PrepareForTest({RecognizerCompatibility.class, ObjectGraphCreator.class, FilesManager.class,
//            SmartCredentialsSystemPropertyMap.class})
//    public void getDocumentScannerViewReturnsUnsuccessfulResponseIfAndroidVersionNotSupported() {
//        PowerMockito.mockStatic(RecognizerCompatibility.class);
//        PowerMockito.when(RecognizerCompatibility.getRecognizerCompatibilityStatus(any()))
//                .thenReturn(RecognizerCompatibilityStatus.UNSUPPORTED_ANDROID_VERSION);
//
//        SmartCredentialsApiResponse<DocumentScannerLayout> response = mSCDocumentScannerApi
//                .getDocumentScannerView(mDocumentScannerConfiguration, null);
//
//        assertFalse(response.isSuccessful());
//        assertNotNull(response.getError());
//        assertTrue(response.getError() instanceof FeatureNotSupportedThrowable);
//        assertEquals(response.getError().getMessage(), RecognizerCompatibilityStatus.UNSUPPORTED_ANDROID_VERSION.name());
//    }
//
//    @Test
//    @PrepareForTest({RecognizerCompatibility.class, ObjectGraphCreator.class, FilesManager.class,
//            SmartCredentialsSystemPropertyMap.class})
//    public void getDocumentScannerViewReturnsResponseWithScannerView() {
//        PowerMockito.mockStatic(RecognizerCompatibility.class);
//        PowerMockito.when(RecognizerCompatibility.getRecognizerCompatibilityStatus(any()))
//                .thenReturn(RecognizerCompatibilityStatus.RECOGNIZER_SUPPORTED);
//        when(mApiController.getDocumentScanner(eq(mDocumentScannerConfiguration), any()))
//                .thenReturn(Mockito.mock(DocumentScannerLayoutImpl.class));
//
//        SmartCredentialsApiResponse<DocumentScannerLayout> response = mSCDocumentScannerApi
//                .getDocumentScannerView(mDocumentScannerConfiguration, null);
//
//        verify(mApiController, times(1)).getDocumentScanner(eq(mDocumentScannerConfiguration), any());
//        assertNotNull(response);
//        assertNotNull(response.getData());
//    }
//
//}