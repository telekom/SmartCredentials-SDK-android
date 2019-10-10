///*
// * Developed by Lucian Iacob in Cluj-Napoca.
// * Project: SmartCredentials_aOS
// * Email: lucian.iacob@endava.com
// * Last modified 15/11/18 11:35.
// * Copyright (c) Deutsche Telekom, 2018. All rights reserved.
// */
//
//package de.telekom.smartcredentials.core.controllers.callbackimplementations;
//
//import android.net.Uri;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mockito;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import de.telekom.smartcredentials.core.domain.controllers.StorageController;
//import de.telekom.smartcredentials.core.domain.converters.UriToItemDomainModelConverter;
//import de.telekom.smartcredentials.core.exceptions.EncryptionException;
//import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
//import de.telekom.smartcredentials.core.plugins.callbacks.OTPScannerPluginCallback;
//import de.telekom.smartcredentials.core.domain.utils.MocksProvider;
//
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.doReturn;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.verify;
//import static org.powermock.api.mockito.PowerMockito.when;
//
//@SuppressWarnings("unchecked")
//public class ScannerPluginCallbackForOTPTest {
//
//    private ScannerPluginCallbackForOTP mScannerPluginCallbackForOTP;
//    private String mItemId = "itemID";
//    private String mUserId = "userId";
//    private OTPScannerPluginCallback mOTPScannerPluginCallback;
//    private UriToItemDomainModelConverter mUriToItemDomainModelConverter;
//    private StorageController mStorageController;
//
//    @Before
//    public void setUp() {
//        mOTPScannerPluginCallback = MocksProvider.provideOtpScannerPluginCallback();
//        mUriToItemDomainModelConverter = MocksProvider.provideUriToItemDomainModelConverter();
//        mStorageController = MocksProvider.provideStorageController();
//        mScannerPluginCallbackForOTP = new ScannerPluginCallbackForOTP(
//                mStorageController, mUriToItemDomainModelConverter);
//        mScannerPluginCallbackForOTP.getInstance(mItemId, mUserId, mOTPScannerPluginCallback);
//    }
//
//    @Test
//    public void onScannerStartedCallsMethodOnOTPScannerPluginCallback() {
//        mScannerPluginCallbackForOTP.onScannerStarted();
//
//        verify(mOTPScannerPluginCallback).onScannerStarted();
//    }
//
//    @Test
//    public void onScannedDoesNotCallOnFailedIfOTPItemWasParsed() throws EncryptionException {
//        ScannerPluginCallbackForOTP scannerPluginCallbackForOTPSpy =
//                Mockito.spy(mScannerPluginCallbackForOTP);
//        ItemDomainModel itemDomainModel = MocksProvider.provideItemDomainModel();
//        List<String> results = new ArrayList<>();
//        results.add("someUri");
//        doReturn(itemDomainModel).when(scannerPluginCallbackForOTPSpy).
//                getParsedModelFromScannerResults(results);
//        doNothing().when(scannerPluginCallbackForOTPSpy).saveParsedUri(itemDomainModel);
//
//        scannerPluginCallbackForOTPSpy.onScanned(results);
//
//        verify(mOTPScannerPluginCallback, never()).onParseFailed();
//    }
//
//    @Test
//    public void onScannedCallsOnParseFailedIfOTPItemWasNotFound() {
//        ScannerPluginCallbackForOTP scannerPluginCallbackForOTPSpy =
//                Mockito.spy(mScannerPluginCallbackForOTP);
//        List<String> results = new ArrayList<>();
//        results.add("someUri");
//        doReturn(null).when(scannerPluginCallbackForOTPSpy).
//                getParsedModelFromScannerResults(results);
//
//        scannerPluginCallbackForOTPSpy.onScanned(results);
//
//        verify(mOTPScannerPluginCallback).onParseFailed();
//    }
//
//    @Test
//    public void onScannedCallsOnEncryptionFailedIfOTPItemWasNotFound() throws EncryptionException {
//        ScannerPluginCallbackForOTP scannerPluginCallbackForOTPSpy =
//                Mockito.spy(mScannerPluginCallbackForOTP);
//        ItemDomainModel itemDomainModel = MocksProvider.provideItemDomainModel();
//        List<String> results = new ArrayList<>();
//        results.add("someUri");
//        doReturn(itemDomainModel).when(scannerPluginCallbackForOTPSpy).getParsedModelFromScannerResults(results);
//        doThrow(new EncryptionException("")).when(scannerPluginCallbackForOTPSpy).saveParsedUri(itemDomainModel);
//
//        scannerPluginCallbackForOTPSpy.onScanned(results);
//
//        verify(mOTPScannerPluginCallback).onEncryptionFailed();
//    }
//
//    @Test
//    public void parseUriCallsParseMethodOnModelConverter() {
//        Uri uri = MocksProvider.provideUri();
//
//        mScannerPluginCallbackForOTP.parseUri(uri);
//
//        verify(mUriToItemDomainModelConverter).parseOTPUri(mItemId, mUserId, uri);
//    }
//
//    @Test
//    public void saveParsedUriCallsSaveOnStorageControllerAndNotifiesSuccess() throws EncryptionException {
//        ItemDomainModel itemDomainModel = MocksProvider.provideItemDomainModel();
//        when(mStorageController.saveData(itemDomainModel)).thenReturn(1);
//
//        mScannerPluginCallbackForOTP.saveParsedUri(itemDomainModel);
//
//        verify(mStorageController).saveData(itemDomainModel);
//        verify(mOTPScannerPluginCallback).onScanned(itemDomainModel);
//    }
//
//    @Test
//    public void saveParsedUriCallsSaveOnStorageControllerAndNotifiesFailure() throws EncryptionException {
//        ItemDomainModel itemDomainModel = MocksProvider.provideItemDomainModel();
//        when(mStorageController.saveData(itemDomainModel)).thenReturn(0);
//
//        mScannerPluginCallbackForOTP.saveParsedUri(itemDomainModel);
//
//        verify(mStorageController).saveData(itemDomainModel);
//        verify(mOTPScannerPluginCallback).onSaveFailed();
//    }
//
//    @Test
//    public void onPluginUnavailableCallsMethodOnOTPScannerPluginCallback() {
//        mScannerPluginCallbackForOTP.onPluginUnavailable("");
//
//        verify(mOTPScannerPluginCallback).onPluginUnavailable("");
//    }
//
//}