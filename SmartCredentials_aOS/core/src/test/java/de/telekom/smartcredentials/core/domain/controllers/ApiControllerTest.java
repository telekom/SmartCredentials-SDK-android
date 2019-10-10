///*
// * Developed by Lucian Iacob in Cluj-Napoca.
// * Project: SmartCredentials_aOS
// * Email: lucian.iacob@endava.com
// * Last modified 16/11/18 15:14.
// * Copyright (c) Deutsche Telekom, 2018. All rights reserved.
// */
//
//package de.telekom.smartcredentials.core.domain.controllers;
//
//import android.os.Looper;
//import android.text.TextUtils;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//import org.junit.runner.RunWith;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//
//import de.telekom.smartcredentials.core.controllers.callbackimplementations.ScannerPluginCallbackForOTP;
//import de.telekom.smartcredentials.core.exceptions.EncryptionException;
//import de.telekom.smartcredentials.core.exceptions.UserNotSetException;
//import de.telekom.smartcredentials.core.model.DomainModelException;
//import de.telekom.smartcredentials.core.model.EncryptionAlgorithm;
//import de.telekom.smartcredentials.core.model.item.ItemDomainMetadata;
//import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
//import de.telekom.smartcredentials.core.model.otp.OTPType;
//import de.telekom.smartcredentials.core.model.request.RequestParams;
//import de.telekom.smartcredentials.core.model.token.TokenRequest;
//import de.telekom.smartcredentials.core.plugins.SecurityLeakCheckWrapper;
//import de.telekom.smartcredentials.core.plugins.callbacks.AuthenticationPluginCallback;
//import de.telekom.smartcredentials.core.plugins.callbacks.AuthorizationPluginCallback;
//import de.telekom.smartcredentials.core.plugins.callbacks.DocumentScannerPluginCallback;
//import de.telekom.smartcredentials.core.plugins.callbacks.OTPScannerPluginCallback;
//import de.telekom.smartcredentials.core.plugins.callbacks.ScannerPluginCallback;
//import de.telekom.smartcredentials.core.plugins.callbacks.ServicePluginCallback;
//import de.telekom.smartcredentials.persistence.domain.task.TaskManager;
//import de.telekom.smartcredentials.core.domain.utils.MocksProvider;
//
//import static de.telekom.smartcredentials.core.controllers.CoreController.UID_EXCEPTION_MESSAGE;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.verifyZeroInteractions;
//import static org.mockito.Mockito.when;
//
///**
// * Created by Lucian Iacob on November 16, 2018.
// */
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({TextUtils.class, Looper.class})
//public class ApiControllerTest {
//
//    private StorageController mStorageController;
//    private PluginController mPluginController;
//    private FlowController mFlowController;
//    private CreationController mCreationController;
//    private ScannerPluginCallbackForOTP mScannerPluginCallbackForOtp;
//    private SecurityLeakCheckWrapper mSecurityLeakCheckWrapper;
//    private TaskManager mTaskManager;
//    private ApiController mApiController;
//    private ItemDomainModel mItemDomainModel;
//    private ServicePluginCallback mServicePluginCallback;
//    private Looper mMainThreadLooper;
//
//    @Rule
//    public ExpectedException mExpectedException = ExpectedException.none();
//
//    @Before
//    public void setup() {
//        PowerMockito.mockStatic(TextUtils.class);
//        PowerMockito.mockStatic(Looper.class);
//
//        mStorageController = MocksProvider.provideStorageController();
//        mPluginController = MocksProvider.providePluginController();
//        mFlowController = MocksProvider.provideFlowController();
//        mCreationController = MocksProvider.provideCreationController();
//        mScannerPluginCallbackForOtp = MocksProvider.provideScannerPluginCallbackForOtp();
//        mSecurityLeakCheckWrapper = MocksProvider.provideSecurityLeackCheckWrapper();
//        mTaskManager = MocksProvider.provideTaskManager();
//        mItemDomainModel = MocksProvider.provideItemDomainModel();
//
//        mApiController = new ApiController(mStorageController,
//                mPluginController,
//                mFlowController,
//                mCreationController,
//                mScannerPluginCallbackForOtp,
//                mSecurityLeakCheckWrapper,
//                mTaskManager,
//                true);
//
//        mItemDomainModel = MocksProvider.provideItemDomainModel();
//        mServicePluginCallback = MocksProvider.provideServicePluginCallback();
//        mMainThreadLooper = MocksProvider.provideMainThreadLooper();
//
//        when(Looper.getMainLooper()).thenReturn(mMainThreadLooper);
//    }
//
//    @Test
//    public void putItemCallsSaveDataOnStorageController() throws EncryptionException {
//        PowerMockito.when(TextUtils.isEmpty(mItemDomainModel.getUid())).thenReturn(false);
//
//        mApiController.putItem(mItemDomainModel);
//
//        verify(mStorageController, times(1)).saveData(mItemDomainModel);
//    }
//
//    @Test
//    public void putItemThrowsExceptionWhenUidIsEmpty() throws EncryptionException {
//        PowerMockito.when(TextUtils.isEmpty(mItemDomainModel.getUid())).thenReturn(true);
//        mExpectedException.expect(DomainModelException.class);
//        mExpectedException.expectMessage(UID_EXCEPTION_MESSAGE);
//
//        mApiController.putItem(mItemDomainModel);
//    }
//
//    @Test
//    public void getAllItemsCallsMethodOnStorageController() throws EncryptionException {
//        mApiController.getAllItemsByItemType(mItemDomainModel);
//
//        verify(mStorageController, times(1)).retrieveItemsFilteredByType(mItemDomainModel);
//    }
//
//    @Test
//    public void getItemSummaryCallsMethodOnStorageController() throws EncryptionException {
//        mApiController.retrieveItemSummaryByUniqueIdAndType(mItemDomainModel);
//
//        verify(mStorageController, times(1)).retrieveItemSummaryByUniqueIdAndType(mItemDomainModel);
//    }
//
//    @Test
//    public void getItemDetailsCallsMethodOnStorageController() throws EncryptionException {
//        mApiController.getItemDetailsByUniqueIdAndType(mItemDomainModel);
//
//        verify(mStorageController, times(1)).retrieveItemDetailsByUniqueIdAndType(mItemDomainModel);
//    }
//
//    @Test
//    public void setUserIdThrowsExceptionIsUserIdIsEmpty() {
//        String userId = "";
//        PowerMockito.when(TextUtils.isEmpty(userId)).thenReturn(true);
//        mExpectedException.expect(UserNotSetException.class);
//
//        mApiController.setUserId(userId);
//    }
//
//    @Test
//    public void isSecurityCompromisedCallsMethodOnSecurityLeakCheckWrapper() {
//        mApiController.isSecurityCompromised();
//
//        verify(mSecurityLeakCheckWrapper, times(1)).isSecurityCompromised();
//    }
//
//    @Test
//    public void isSecurityLeakDetectedCallsMethodOnSecurityLeakCheckWrapper() {
//        mApiController.isSecurityLeakDetected();
//
//        verify(mSecurityLeakCheckWrapper, times(1)).isSecurityCompromised();
//    }
//
//    @Test
//    public void isSecurityLeakDetectedDontCallMethodOnSecurityLeakCheckWrapper() {
//        mApiController.mRootCheckerEnabled = false;
//
//        mApiController.isSecurityCompromised();
//
//        verifyZeroInteractions(mSecurityLeakCheckWrapper);
//    }
//
//    @Test
//    public void handleSecurityCompromisedExecutesStorageControllerMethodOnCurrentThreadIfCurrentlyNotOnMainThread() {
//        when(mMainThreadLooper.getThread()).thenReturn(new Thread());
//
//        mApiController.handleSecurityCompromised();
//
//        verify(mStorageController, times(1)).clearStorage();
//    }
//
//    @Test
//    public void handleSecurityCompromisedExecutesSecurityLeaksHandlerTaskMethodOnNewThreadIfCurrentlyOnMainThread() {
//        when(mMainThreadLooper.getThread()).thenReturn(Thread.currentThread());
//
//        mApiController.handleSecurityCompromised();
//
//        verify(mTaskManager, times(1)).executeSecurityLeaksHandlerTask(mApiController);
//    }
//
//    @Test
//    public void onSecurityCompromisedCallsMethodOnStorageController() {
//        mApiController.handleSecurityCompromised();
//
//        verify(mStorageController, times(1)).clearStorage();
//    }
//
//    @Test
//    public void deleteItemCallsMethodOnStorageController() {
//        mApiController.deleteItem(mItemDomainModel);
//
//        verify(mStorageController, times(1)).deleteItem(mItemDomainModel);
//    }
//
//    @Test
//    public void deleteItemByTypeCallsMethodOnStorageController() {
//        mApiController.deleteItemsByType(mItemDomainModel);
//
//        verify(mStorageController, times(1)).deleteItemsByType(mItemDomainModel);
//    }
//
//    @Test
//    public void authorizeCallsMethodOnPluginController() {
//        AuthorizationPluginCallback callback = MocksProvider.provideAuthorizationPluginCallback();
//
//        mApiController.getAuthorizationTool(callback);
//
//        verify(mPluginController, times(1)).getAuthorizationPlugin(callback);
//    }
//
//    @Test
//    public void getFingerprintAuthorizationPluginCallsMethodOnPluginController() {
//        AuthorizationPluginCallback callback = MocksProvider.provideAuthorizationPluginCallback();
//
//        mApiController.getFingerprintAuthorizationPlugin(callback);
//
//        verify(mPluginController, times(1)).getFingerprintAuthorizationPlugin(callback);
//    }
//
//    @Test
//    public void getBiometricAuthorizationPresenterCallsMethodOnPluginController() {
//        AuthorizationPluginCallback callback = MocksProvider.provideAuthorizationPluginCallback();
//
//        mApiController.getBiometricAuthorizationPresenter(callback);
//
//        verify(mPluginController, times(1)).getBiometricAuthorizationPlugin(callback);
//    }
//
//    @Test
//    public void authorizeLogInCallsMethodOnPluginController() {
//        AuthenticationPluginCallback pluginCallback = MocksProvider.provideAuthPluginCallback();
//
//        mApiController.getLogInWithAuthorizationTool(pluginCallback, null);
//
//        verify(mPluginController, times(1)).getAuthorizationPlugin(any());
//    }
//
//    @Test
//    public void getBarcodeScannerCallsMethodOnFlowController() {
//        ScannerPluginCallback pluginCallback = MocksProvider.provideScannerPluginCallback();
//        int barcodeFormat = 10;
//
//        mApiController.getBarcodeScanner(pluginCallback, barcodeFormat);
//
//        verify(mPluginController, times(1)).
//                getBarcodeScannerPlugin(pluginCallback, barcodeFormat);
//    }
//
//    @Test
//    public void getTextScannerCallsMethodOnFlowController() {
//        ScannerPluginCallback pluginCallback = MocksProvider.provideScannerPluginCallback();
//
//        mApiController.getTextScanner(pluginCallback);
//
//        verify(mPluginController, times(1)).getTextScannerPlugin(pluginCallback);
//    }
//
//    @Test
//    public void getOTPCallsMethodsOnFlowController() {
//        TokenRequest tokenRequest = MocksProvider.provideTokenRequest();
//        ItemDomainModel hotpItemDomainModel = new ItemDomainModel();
//        ItemDomainMetadata itemDomainMetadata = new ItemDomainMetadata(true);
//        itemDomainMetadata.setItemType(OTPType.HOTP.getDesc());
//        hotpItemDomainModel.setMetadata(itemDomainMetadata);
//        when(tokenRequest.getOtpType()).thenReturn(OTPType.HOTP);
//
//        mApiController.getOTPHandler(tokenRequest);
//
//        verify(mFlowController, times(1)).getOTPHandler(OTPType.HOTP);
//    }
//
//    @Test
//    public void updateItemCallsUpdateItemOnStorageController() throws EncryptionException {
//        PowerMockito.when(TextUtils.isEmpty(mItemDomainModel.getUid())).thenReturn(false);
//
//        mApiController.updateItem(mItemDomainModel);
//
//        verify(mStorageController, times(1)).updateItem(mItemDomainModel);
//    }
//
//    @Test
//    public void updateItemThrowsExceptionWhenUidIsEmpty() throws EncryptionException {
//        PowerMockito.when(TextUtils.isEmpty(mItemDomainModel.getUid())).thenReturn(true);
//        mExpectedException.expect(DomainModelException.class);
//        mExpectedException.expectMessage(UID_EXCEPTION_MESSAGE);
//
//        mApiController.updateItem(mItemDomainModel);
//    }
//
//    @SuppressWarnings("unchecked")
//    @Test
//    public void getQROTPScannerCallsMethodOnDefaultFlowController() {
//        OTPScannerPluginCallback otpScannerPluginCallback = MocksProvider.provideOtpScannerPluginCallback();
//        String itemId = "someID";
//        String userId = "someUserID";
//        int barcodeFormat = 0;
//        mApiController.getQROTPScanner(itemId, userId, otpScannerPluginCallback, barcodeFormat);
//
//        verify(mScannerPluginCallbackForOtp).getInstance(itemId, userId, otpScannerPluginCallback);
//        verify(mPluginController, times(1)).
//                getBarcodeScannerPlugin(mScannerPluginCallbackForOtp, barcodeFormat);
//    }
//
//    @Test
//    public void callServiceCallsMethodOnStorageController() {
//        RequestParams requestParams = MocksProvider.provideRequestParams();
//
//        mApiController.callService(requestParams, mServicePluginCallback);
//
//        verify(mFlowController, times(1)).
//                callService(requestParams, mServicePluginCallback);
//    }
//
//    @Test
//    public void getDocumentScannerCallsMethodOnDefaultFlowController() {
//        Object config = new Object();
//        DocumentScannerPluginCallback documentScannerPluginCallback =
//                MocksProvider.provideDocumentScannerPluginCallback();
//
//        mApiController.getDocumentScanner(config, documentScannerPluginCallback);
//
//        verify(mPluginController, times(1))
//                .getDocumentScanner(config, documentScannerPluginCallback);
//    }
//
//    @Test
//    public void getPublicKeyCallsMethodOnCreationController() throws EncryptionException {
//        String alias = "test";
//
//        mApiController.getPublicKey(alias);
//
//        verify(mCreationController, times(1)).createPublicKey(alias);
//    }
//
//    @Test
//    public void getPublicKeyThrowsEncryptionExceptionWhenCreationControllerThrowsEncryptionException()
//            throws EncryptionException {
//        String alias = "test";
//        String errMessage = "Error";
//        when(mCreationController.createPublicKey(alias)).thenThrow(new EncryptionException(errMessage));
//        mExpectedException.expect(EncryptionException.class);
//        mExpectedException.expectMessage(errMessage);
//
//        mApiController.getPublicKey(alias);
//    }
//
//    @Test
//    public void encryptThrowsEncryptionExceptionWhenCreationControllerThrowsEncryptionException()
//            throws EncryptionException {
//        String alias = "test";
//        String errMessage = "Error";
//        String toEncrypt = "toEncrypt";
//        when(mCreationController.encrypt(toEncrypt, alias, EncryptionAlgorithm.AES_256))
//                .thenThrow(new EncryptionException(errMessage));
//
//        mExpectedException.expect(EncryptionException.class);
//        mExpectedException.expectMessage(errMessage);
//
//        mApiController.encrypt(toEncrypt, alias, EncryptionAlgorithm.AES_256);
//    }
//
//    @Test
//    public void encryptCallsMethodOnCreationController() throws EncryptionException {
//        String alias = "test";
//        String toEncrypt = "toEncrypt";
//
//        mApiController.encrypt(toEncrypt, alias, EncryptionAlgorithm.RSA_2048);
//
//        verify(mCreationController, times(1))
//                .encrypt(toEncrypt, alias, EncryptionAlgorithm.RSA_2048);
//    }
//}