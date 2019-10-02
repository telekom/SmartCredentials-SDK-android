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
//import de.telekom.smartcredentials.core.authorization.AuthorizationPluginError;
//import de.telekom.smartcredentials.core.authorization.AuthorizationPluginUnavailable;
//import de.telekom.smartcredentials.core.plugins.callbacks.AuthorizationPluginCallback;
//
//import static de.telekom.smartcredentials.core.plugins.callbacks.AuthorizationPluginCallback.TAG;
//import static org.mockito.Mockito.verify;
//
///**
// * Created by Lucian Iacob on November 16, 2018.
// */
//@SuppressWarnings("unchecked")
//public class PluginCallbackAuthorizationConverterTest {
//
//    @Test
//    public void testConvertToDomainPluginCallback() {
//        AuthorizationCallback callback = Mockito.mock(AuthorizationCallback.class);
//        AuthorizationPluginCallback pluginCallback = PluginCallbackAuthorizationConverter
//                .convertToDomainPluginCallback(callback, TAG);
//
//        pluginCallback.onAuthorized();
//        verify(callback).onAuthorized();
//
//        pluginCallback.onPluginUnavailable(AuthorizationPluginUnavailable.FINGERPRINT_PERMISSION_MISSING);
//        verify(callback).onUnavailable(AuthorizationPluginUnavailable.FINGERPRINT_PERMISSION_MISSING);
//
//        pluginCallback.onFailed(AuthorizationPluginError.DEVICE_NOT_SECURED);
//        verify(callback).onFailure(AuthorizationPluginError.DEVICE_NOT_SECURED);
//
//    }
//
//    @Test
//    public void convertToDomainPluginCallbackChecksIfAuthorizationCallbackIsNullAndDoesNotThrowExceptions() {
//        AuthorizationPluginCallback pluginCallback = PluginCallbackAuthorizationConverter
//                .convertToDomainPluginCallback(null, TAG);
//
//        pluginCallback.onAuthorized();
//
//        pluginCallback.onPluginUnavailable(AuthorizationPluginUnavailable.FINGERPRINT_PERMISSION_MISSING);
//        pluginCallback.onFailed(AuthorizationPluginError.DEVICE_NOT_SECURED);
//    }
//}