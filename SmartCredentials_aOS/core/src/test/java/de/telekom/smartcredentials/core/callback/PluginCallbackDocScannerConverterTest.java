///*
// * Developed by Lucian Iacob in Cluj-Napoca.
// * Project: SmartCredentials_aOS
// * Email: lucian.iacob@endava.com
// * Last modified 16/11/18 15:52.
// * Copyright (c) Deutsche Telekom, 2018. All rights reserved.
// */
//
//package de.telekom.smartcredentials.core.callback;
//
//import org.junit.Test;
//import org.mockito.Mockito;
//
//import de.telekom.smartcredentials.core.model.DocumentScannerResult;
//import de.telekom.smartcredentials.core.plugins.callbacks.DocumentScannerPluginCallback;
//
//import static de.telekom.smartcredentials.core.plugins.callbacks.BaseScannerPluginCallback.TAG;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
///**
// * Created by Lucian Iacob on November 16, 2018.
// */
//@SuppressWarnings("unchecked")
//public class PluginCallbackDocScannerConverterTest {
//
//    @Test
//    public void convertToDomainPluginCallbackCreatesPluginCallbackThatCallsDocumentScannerCallbackMethods() {
//        DocumentScannerCallback callback = Mockito.mock(DocumentScannerCallback.class);
//        DocumentScannerPluginCallback pluginCallback = PluginCallbackDocScannerConverter
//                .convertToDomainPluginCallback(callback, TAG);
//
//        pluginCallback.onFirstSideRecognitionFinished();
//        verify(callback, times(1)).onFirstSideRecognitionFinished();
//
//        pluginCallback.onScannerStarted();
//        verify(callback, times(1)).onInitialized();
//
//        DocumentScannerResult result = Mockito.mock(DocumentScannerResult.class);
//        pluginCallback.onScanned(result);
//        verify(callback, times(1)).onDetected(result);
//
//        pluginCallback.onPluginUnavailable(
//                de.telekom.smartcredentials.documentscanner.utils.ScannerPluginUnavailable.CAMERA_PERMISSION_NEEDED);
//        verify(callback, times(1)).onScannerUnavailable(
//                de.telekom.smartcredentials.documentscanner.utils.ScannerPluginUnavailable.CAMERA_PERMISSION_NEEDED);
//
//        pluginCallback.onScannedFailed();
//        verify(callback, times(1)).onScannedFailed();
//
//        pluginCallback.onScannerStopped();
//        verify(callback, times(1)).onScannerStopped();
//
//        pluginCallback.onAutoFocusStarted(null);
//        verify(callback, times(1)).onAutoFocusStarted(null);
//
//        pluginCallback.onAutoFocusStopped(null);
//        verify(callback, times(1)).onAutoFocusStopped(null);
//
//        pluginCallback.onAutoFocusFailed();
//        verify(callback, times(1)).onAutofocusFailed();
//    }
//
//}