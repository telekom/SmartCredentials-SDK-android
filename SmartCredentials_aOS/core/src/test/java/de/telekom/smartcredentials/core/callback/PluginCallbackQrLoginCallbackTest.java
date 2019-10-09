///*
// * Developed by Lucian Iacob in Cluj-Napoca.
// * Project: SmartCredentials_aOS
// * Email: lucian.iacob@endava.com
// * Last modified 16/11/18 16:25.
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
//import de.telekom.smartcredentials.core.model.token.AuthenticationTokenResponse;
//import de.telekom.smartcredentials.core.plugins.callbacks.AuthenticationPluginCallback;
//import de.telekom.smartcredentials.qrlogin.qr.TokenPluginError;
//
//import static de.telekom.smartcredentials.core.plugins.callbacks.BaseScannerPluginCallback.TAG;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
///**
// * Created by Lucian Iacob on November 16, 2018.
// */
//@SuppressWarnings("unchecked")
//public class PluginCallbackQrLoginCallbackTest {
//
//    @Test
//    public void convertToDomainPluginCallbackCreatesPluginCallbackThatCallsScannerCallbackMethods() {
//        AuthenticationCallback callback = Mockito.mock(AuthenticationCallback.class);
//        AuthenticationPluginCallback<AuthorizationPluginUnavailable, AuthorizationPluginError,
//                TokenPluginError> pluginCallback =
//                PluginCallbackQrLoginCallback.convertToDomainPluginCallback(callback, TAG);
//
//        TokenPluginError message = TokenPluginError.EMPTY_MESSAGE;
//        pluginCallback.onRetrievingAuthInfoFailed(message);
//        verify(callback, times(1)).onFailure(message);
//
//        pluginCallback.onPluginUnavailable(AuthorizationPluginUnavailable.FINGERPRINT_PERMISSION_MISSING);
//        verify(callback, times(1)).onUnavailable(
//                AuthorizationPluginUnavailable.FINGERPRINT_PERMISSION_MISSING);
//
//        AuthenticationTokenResponse tokenResponse = new AuthenticationTokenResponse("value", 1234);
//        pluginCallback.onAuthenticated(tokenResponse);
//        verify(callback, times(1)).onAuthenticated("value", 1234);
//
//        AuthorizationPluginError error = AuthorizationPluginError.AUTH_BECAME_UNAVAILABLE;
//        pluginCallback.onFailed(error);
//        verify(callback, times(1)).onFailure(error);
//    }
//
//    @Test
//    public void convertToDomainPluginCallbackChecksIfScannerCallbackIsNullAndDoesNotThrowExceptions() {
//        AuthenticationPluginCallback<AuthorizationPluginUnavailable, AuthorizationPluginError,
//                TokenPluginError> pluginCallback =
//                PluginCallbackQrLoginCallback.convertToDomainPluginCallback(null, TAG);
//
//        pluginCallback.onPluginUnavailable(AuthorizationPluginUnavailable.FINGERPRINT_PERMISSION_MISSING);
//
//        AuthenticationTokenResponse tokenResponse = new AuthenticationTokenResponse("value", 1234);
//        pluginCallback.onAuthenticated(tokenResponse);
//    }
//
//}
