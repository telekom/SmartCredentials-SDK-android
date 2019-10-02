///*
// * Developed by Lucian Iacob in Cluj-Napoca.
// * Project: SmartCredentials_aOS
// * Email: lucian.iacob@endava.com
// * Last modified 07/11/18 14:02.
// * Copyright (c) Deutsche Telekom, 2018. All rights reserved.
// */
//
//package de.telekom.smartcredentials.core.controllers.callbackimplementations;
//
//import org.json.JSONObject;
//import org.junit.Before;
//import org.junit.Test;
//
//import de.telekom.smartcredentials.core.controllers.FlowController;
//import de.telekom.smartcredentials.core.plugins.callbacks.AuthenticationPluginCallback;
//import de.telekom.smartcredentials.core.domain.utils.MocksProvider;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//
//@SuppressWarnings("unchecked")
//public class UserAuthorizedPluginCallbackTest {
//
//    private UserAuthorizedPluginCallback mUserAuthorizedPluginCallback;
//    private AuthenticationPluginCallback mAuthenticationPluginCallback;
//    private FlowController mFlowController;
//
//    @Before
//    public void setUp() {
//        JSONObject jsonObject = new JSONObject();
//        mAuthenticationPluginCallback = MocksProvider.provideAuthPluginCallback();
//        mFlowController = MocksProvider.provideFlowController();
//        mUserAuthorizedPluginCallback = new UserAuthorizedPluginCallback(
//                jsonObject, mAuthenticationPluginCallback, mFlowController);
//    }
//
//    @Test
//    public void getUserAuthorizedPluginCallbackCallsPluginCallbackOnSuccessWhenNewItemIsSaved() {
//        mUserAuthorizedPluginCallback.onAuthorized();
//
//        verify(mFlowController).getToken(any(), any());
//    }
//
//    @Test
//    public void getUserAuthorizedPluginCallbackCallsInitialPluginCallbackOnFailedWhenRefreshPluginCallbackOnFailedIsCalled() {
//        String errorMessage = "error";
//
//        mUserAuthorizedPluginCallback.onFailed(errorMessage);
//
//        verify(mAuthenticationPluginCallback).onFailed(errorMessage);
//    }
//
//    @Test
//    public void getUserAuthorizedPluginCallbackCallsInitialPluginCallbackOnPluginUnavailableWhenRefreshPluginCallbackOnPluginUnavailableIsCalled() {
//        String errorMessage = "error";
//
//        mUserAuthorizedPluginCallback.onPluginUnavailable(errorMessage);
//
//        verify(mAuthenticationPluginCallback).onPluginUnavailable(errorMessage);
//    }
//}