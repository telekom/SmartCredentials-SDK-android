///*
// * Developed by Lucian Iacob in Cluj-Napoca.
// * Project: SmartCredentials_aOS
// * Email: lucian.iacob@endava.com
// * Last modified 18/01/19 12:37.
// * Copyright (c) Deutsche Telekom, 2019. All rights reserved.
// */
//
//package de.telekom.smartcredentials.core.controllers.defaultcontrollers;
//
//import org.json.JSONObject;
//import org.junit.Before;
//import org.junit.Test;
//
//import de.telekom.smartcredentials.core.handlers.ChannelHandler;
//import de.telekom.smartcredentials.core.handlers.ItemHandler;
//import de.telekom.smartcredentials.core.handlers.TokenHandler;
//import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
//import de.telekom.smartcredentials.core.model.otp.OTPType;
//import de.telekom.smartcredentials.core.model.request.RequestParams;
//import de.telekom.smartcredentials.core.plugins.callbacks.DocumentScannerPluginCallback;
//import de.telekom.smartcredentials.core.plugins.callbacks.ScannerPluginCallback;
//import de.telekom.smartcredentials.core.plugins.callbacks.ServicePluginCallback;
//import de.telekom.smartcredentials.core.plugins.callbacks.TokenPluginCallback;
//import de.telekom.smartcredentials.core.domain.utils.MocksProvider;
//
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
//public class DefaultFlowControllerTest {
//
//    private ChannelHandler        mChannelHandler;
//    private TokenHandler          mTokenHandler;
//    private DefaultFlowController mDefaultFlowController;
//    private ItemHandler           mItemHandler;
//
//    @Before
//    public void setUp() {
//        mChannelHandler = MocksProvider.provideChannelHandler();
//        mTokenHandler = MocksProvider.provideTokenHandler();
//        mItemHandler = MocksProvider.provideItemHandler();
//        mDefaultFlowController = new DefaultFlowController(mTokenHandler, mChannelHandler, mItemHandler);
//    }
//
//    @Test
//    public void getRefreshTokenCallsMethodOnTokenHandler() {
//        TokenPluginCallback pluginCallback = MocksProvider.provideTokenPluginCallback();
//        JSONObject reqParams = new JSONObject();
//
//        mDefaultFlowController.getToken(reqParams, pluginCallback);
//
//        verify(mTokenHandler).getToken(reqParams, pluginCallback);
//    }
//
//    @Test
//    public void getOTPHandlerCallsMethodOnTokenHandler() {
//        mDefaultFlowController.getOTPHandler(OTPType.HOTP);
//
//        verify(mTokenHandler).getOTPHandler(OTPType.HOTP);
//    }
//
//    @Test
//    public void getBarcodeScannerCallsMethodOnChannelHandler() {
//        ScannerPluginCallback callback = MocksProvider.provideScannerPluginCallback();
//        int format = 10;
//
//        mDefaultFlowController.getBarcodeScanner(callback, format);
//
//        verify(mChannelHandler).getBarcodeScanner(callback, format);
//    }
//
//    @Test
//    public void getTextScannerCallsMethodOnChannelHandler() {
//        ScannerPluginCallback callback = MocksProvider.provideScannerPluginCallback();
//
//        mDefaultFlowController.getTextScanner(callback);
//
//        verify(mChannelHandler).getTextScannerPlugin(callback);
//    }
//
//    @Test
//    public void callServiceCallsMethodOnChannelHandler() {
//        RequestParams requestParams = MocksProvider.provideRequestParams();
//        ServicePluginCallback callback = MocksProvider.provideServicePluginCallback();
//
//        mDefaultFlowController.callService(requestParams, callback);
//
//        verify(mChannelHandler).callService(requestParams, callback);
//    }
//
//    @Test
//    public void getDocumentScannerCallsMethodOnChannelHandler() {
//        Object config = new Object();
//        DocumentScannerPluginCallback callback = MocksProvider.provideDocumentScannerPluginCallback();
//
//        mDefaultFlowController.getDocumentScanner(config, callback);
//
//        verify(mChannelHandler, times(1)).getDocumentScanner(config, callback);
//    }
//
//    @Test
//    public void executeCallsMethodOnItemHandler() {
//        ItemDomainModel itemDomainModel = new ItemDomainModel();
//        String actionId = "1";
//        mDefaultFlowController.executeAction(itemDomainModel, actionId, null);
//
//        verify(mItemHandler, times(1)).execute(itemDomainModel, actionId, null);
//    }
//
//}