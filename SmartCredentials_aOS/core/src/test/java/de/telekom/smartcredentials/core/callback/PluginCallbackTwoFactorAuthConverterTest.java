///*
// * Developed by Lucian Iacob in Cluj-Napoca.
// * Project: SmartCredentials_aOS
// * Email: lucian.iacob@endava.com
// * Last modified 16/11/18 16:45.
// * Copyright (c) Deutsche Telekom, 2018. All rights reserved.
// */
//
//package de.telekom.smartcredentials.core.callback;
//
//import org.junit.Test;
//import org.mockito.Mockito;
//
//import de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable;
//import de.telekom.smartcredentials.core.model.item.ItemDomainMetadata;
//import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
//import de.telekom.smartcredentials.core.model.otp.OTPType;
//import de.telekom.smartcredentials.core.plugins.callbacks.OTPScannerPluginCallback;
//
//import static de.telekom.smartcredentials.core.plugins.callbacks.BaseScannerPluginCallback.TAG;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
///**
// * Created by Lucian Iacob on November 16, 2018.
// */
//@SuppressWarnings("unchecked")
//public class PluginCallbackTwoFactorAuthConverterTest {
//
//    @Test
//    public void convertToDomainPluginCallbackCreatesPluginCallbackThatCallsOTPImporterCallbackMethods() {
//        OTPImporterCallback callback = Mockito.mock(OTPImporterCallback.class);
//        OTPScannerPluginCallback pluginCallback = PluginCallbackTwoFactorAuthConverter
//                .convertToDomainPluginCallback(callback, TAG);
//
//        pluginCallback.onPluginUnavailable(ScannerPluginUnavailable.CAMERA_PERMISSION_NEEDED);
//        verify(callback, times(1))
//                .onScannerUnavailable(ScannerPluginUnavailable.CAMERA_PERMISSION_NEEDED);
//
//        pluginCallback.onScannerStarted();
//        verify(callback, times(1)).onInitialized();
//
//        ItemDomainModel itemDomainModel = Mockito.mock(ItemDomainModel.class);
//        ItemDomainMetadata metadata = Mockito.mock(ItemDomainMetadata.class);
//        when(itemDomainModel.getUid()).thenReturn("someUid");
//        when(metadata.getItemType()).thenReturn(OTPType.HOTP.getDesc());
//        when(itemDomainModel.getMetadata()).thenReturn(metadata);
//
//        pluginCallback.onScanned(itemDomainModel);
//        verify(callback, times(1)).onOTPItemImported(any());
//
//        pluginCallback.onSaveFailed();
//        verify(callback, times(1)).onOTPItemImportFailed(OTPImportFailed.OTP_ITEM_NOT_SAVED);
//
//        pluginCallback.onParseFailed();
//        verify(callback, times(1)).onOTPItemImportFailed(OTPImportFailed.OTP_ITEM_NOT_FOUND);
//
//        pluginCallback.onEncryptionFailed();
//        verify(callback, times(1)).onOTPItemImportFailed(OTPImportFailed.OTP_ITEM_NOT_ENCRYPTED);
//    }
//
//    @Test
//    public void convertToDomainPluginCallbackChecksIfOTPImporterCallbackIsNullAndDoesNotThrowExceptions() {
//        OTPScannerPluginCallback pluginCallback = PluginCallbackTwoFactorAuthConverter
//                .convertToDomainPluginCallback(null, TAG);
//
//        pluginCallback.onScannerStarted();
//
//        pluginCallback.onPluginUnavailable(ScannerPluginUnavailable.CAMERA_PERMISSION_NEEDED);
//
//        pluginCallback.onScanned(Mockito.mock(ItemDomainModel.class));
//
//        pluginCallback.onParseFailed();
//
//        pluginCallback.onSaveFailed();
//
//        pluginCallback.onEncryptionFailed();
//    }
//}