///*
// * Developed by Lucian Iacob in Cluj-Napoca.
// * Project: SmartCredentials_aOS
// * Email: lucian.iacob@endava.com
// * Last modified 16/11/18 15:46.
// * Copyright (c) Deutsche Telekom, 2018. All rights reserved.
// */
//
//package de.telekom.smartcredentials.core.callback;
//
//import org.junit.Test;
//import org.mockito.Mockito;
//
//import java.util.Collections;
//import java.util.List;
//
//import de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable;
//import de.telekom.smartcredentials.core.plugins.callbacks.ScannerPluginCallback;
//
//import static de.telekom.smartcredentials.core.plugins.callbacks.BaseScannerPluginCallback.TAG;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
///**
// * Created by Lucian Iacob on November 16, 2018.
// */
//@SuppressWarnings("unchecked")
//public class PluginCallbackCameraConverterTest {
//
//    @Test
//    public void convertToDomainPluginCallbackCreatesPluginCallbackThatCallsScannerCallbackMethods() {
//        ScannerCallback callback = Mockito.mock(ScannerCallback.class);
//        ScannerPluginCallback pluginCallback = PluginCallbackCameraConverter
//                .convertToDomainPluginCallback(callback, TAG);
//
//        pluginCallback.onPluginUnavailable(ScannerPluginUnavailable.CAMERA_PERMISSION_NEEDED);
//        verify(callback, times(1)).
//                onScannerUnavailable(ScannerPluginUnavailable.CAMERA_PERMISSION_NEEDED);
//
//        pluginCallback.onScannerStarted();
//        verify(callback, times(1)).onInitialized();
//
//        List<String> result = Collections.singletonList("QR DETECTED!!");
//        pluginCallback.onScanned(result);
//        verify(callback, times(1)).onDetected(result);
//    }
//
//    @Test
//    public void convertToDomainPluginCallbackChecksIfScannerCallbackIsNullAndDoesNotThrowExceptions() {
//        ScannerPluginCallback pluginCallback = PluginCallbackCameraConverter
//                .convertToDomainPluginCallback(null, TAG);
//
//        pluginCallback.onScannerStarted();
//
//        pluginCallback.onPluginUnavailable(ScannerPluginUnavailable.CAMERA_PERMISSION_NEEDED);
//
//        String result = "QR DETECTED!!";
//        pluginCallback.onScanned(Collections.singletonList(result));
//    }
//
//}