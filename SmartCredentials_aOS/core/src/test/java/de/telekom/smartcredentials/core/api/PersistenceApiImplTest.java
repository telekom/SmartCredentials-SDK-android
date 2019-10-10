///*
// * Developed by Lucian Iacob in Cluj-Napoca.
// * Project: SmartCredentials_aOS
// * Email: lucian.iacob@endava.com
// * Last modified 18/01/19 13:26.
// * Copyright (c) Deutsche Telekom, 2019. All rights reserved.
// */
//
//package de.telekom.smartcredentials.core.api;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//
//import de.telekom.smartcredentials.core.DomainModelGenerator;
//import de.telekom.smartcredentials.core.blacklisting.FilesManager;
//import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsFeatureSet;
//import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsSystemPropertyMap;
//import de.telekom.smartcredentials.core.context.ItemContext;
//import de.telekom.smartcredentials.core.context.ItemContextFactory;
//import de.telekom.smartcredentials.core.converters.ModelConverter;
//import de.telekom.smartcredentials.core.di.ObjectGraphCreator;
//import de.telekom.smartcredentials.core.actions.ExecutionCallback;
//import de.telekom.smartcredentials.core.domain.controllers.ApiController;
//import de.telekom.smartcredentials.core.exceptions.EncryptionException;
//import de.telekom.smartcredentials.core.model.DomainModelException;
//import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
//import de.telekom.smartcredentials.core.filter.SmartCredentialsFilter;
//import de.telekom.smartcredentials.core.filter.SmartCredentialsFilterFactory;
//import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
//import de.telekom.smartcredentials.core.itemdatamodel.ItemMetadata;
//import de.telekom.smartcredentials.persistence.exceptions.RepoException;
//import de.telekom.smartcredentials.core.responses.EnvelopeException;
//import de.telekom.smartcredentials.core.responses.FeatureNotSupportedThrowable;
//import de.telekom.smartcredentials.core.responses.RootedThrowable;
//import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
//
//import static de.telekom.smartcredentials.core.model.ModelValidator.NULL_PARAMETER_EXCEPTION_MSG;
//import static junit.framework.Assert.assertFalse;
//import static junit.framework.Assert.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.doThrow;
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
//public class PersistenceApiImplTest {
//
//    private static final String mType = "type";
//
//    private ItemContext mItemContext;
//    private ItemEnvelope mItemEnvelopeNoMetadata;
//    private ItemEnvelope mItemEnvelopeNoTarget;
//    private ApiController mApiController;
//    private SmartCredentialsFilter mItemEnvelopeFilterNoMetadata;
//    private SmartCredentialsFilter mItemEnvelopeFilterNoTarget;
//    private SmartCredentialsFilter mVoucherFilter;
//    private StorageApi mSCPersistenceApi;
//
//    @Before
//    public void setup() {
//        PowerMockito.mockStatic(ObjectGraphCreator.class);
//        PowerMockito.mockStatic(FilesManager.class);
//        PowerMockito.mockStatic(SmartCredentialsSystemPropertyMap.class);
//
//        mApiController = Mockito.mock(ApiController.class);
//        mSCPersistenceApi = new SmartCredentialsPersistenceApiImpl(mApiController);
//
//        mVoucherFilter = SmartCredentialsFilterFactory.createNonSensitiveItemFilter(mType);
//        mItemContext = ItemContextFactory.createNonEncryptedNonSensitiveItemContext(mType);
//        mItemEnvelopeNoMetadata = new ItemEnvelope();
//        mItemEnvelopeNoTarget = new ItemEnvelope().setItemMetadata(new ItemMetadata(true));
//        mItemEnvelopeFilterNoMetadata = new SmartCredentialsFilter(new ItemEnvelope());
//        mItemEnvelopeFilterNoTarget = new SmartCredentialsFilter(new ItemEnvelope().setItemMetadata(new ItemMetadata(true)));
//        ObjectGraphCreator objectGraphCreator = Mockito.mock(ObjectGraphCreator.class);
//
//        when(ObjectGraphCreator.getInstance()).thenReturn(objectGraphCreator);
//        when(FilesManager.readDeviceBlackListJSON(any(), any())).thenReturn(new JSONObject());
//    }
//
//    @Test
//    public void updateItemReturnsUnsuccessfulResponseWhenDeviceIsRooted() {
//        when(mApiController.isSecurityCompromised()).thenReturn(true);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .updateItem(mItemEnvelopeNoMetadata, mItemContext);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof RootedThrowable);
//    }
//
//    @Test
//    public void updateItemReturnsUnsuccessfulResponseWhenDeviceIsBlacklisted() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        PowerMockito.when(SmartCredentialsSystemPropertyMap.isFeatureBlockedOnCurrentDevice(any(),
//                eq(SmartCredentialsFeatureSet.STORAGE))).thenReturn(true);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .updateItem(mItemEnvelopeNoMetadata, mItemContext);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof FeatureNotSupportedThrowable);
//    }
//
//    @PrepareForTest({ObjectGraphCreator.class, ModelConverter.class, FilesManager.class, SmartCredentialsSystemPropertyMap.class})
//    @Test
//    public void updateItemReturnsSuccessfulResponse() throws EncryptionException {
//        String userId = "1243";
//        ItemDomainModel mItemDomainModel = Mockito.mock(ItemDomainModel.class);
//        PowerMockito.mockStatic(ModelConverter.class);
//        PowerMockito.when(ModelConverter.toItemDomainModel(mItemEnvelopeNoTarget, mItemContext, userId))
//                .thenReturn(mItemDomainModel);
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        when(mApiController.getUserId()).thenReturn(userId);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .updateItem(mItemEnvelopeNoTarget, mItemContext);
//
//        assertTrue(smartCredentialsResponse.isSuccessful());
//        verify(mApiController, times(1)).updateItem(mItemDomainModel);
//    }
//
//    @Test
//    public void updateItemReturnsUnsuccessfulResponseWhenItemEnvelopeMetadataIsNull() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .updateItem(mItemEnvelopeNoMetadata, mItemContext);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof EnvelopeException);
//    }
//
//    @Test
//    public void updateItemReturnsUnsuccessfulResponseWhenDomainModelExceptionIsThrown() throws EncryptionException {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        when(mApiController.updateItem(any())).thenThrow(new DomainModelException(NULL_PARAMETER_EXCEPTION_MSG));
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .updateItem(mItemEnvelopeNoTarget, mItemContext);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof EnvelopeException);
//    }
//
//    @Test
//    public void updateItemReturnsUnsuccessfulResponseWhenEncryptionExceptionIsThrown() throws EncryptionException {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        when(mApiController.updateItem(any())).thenThrow(new EncryptionException(NULL_PARAMETER_EXCEPTION_MSG));
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .updateItem(mItemEnvelopeNoTarget, mItemContext);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof EncryptionException);
//    }
//
//    @Test
//    public void getAllItemsByItemTypeReturnsUnsuccessfulResponseIfDeviceIsRooted() {
//        when(mApiController.isSecurityCompromised()).thenReturn(true);
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .getAllItemsByItemType(mVoucherFilter);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof RootedThrowable);
//    }
//
//    @Test
//    public void getAllItemsByItemTypeReturnsUnsuccessfulResponseIfDeviceIsBlacklisted() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        PowerMockito.when(SmartCredentialsSystemPropertyMap.isFeatureBlockedOnCurrentDevice(any(),
//                eq(SmartCredentialsFeatureSet.STORAGE))).thenReturn(true);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .getAllItemsByItemType(mVoucherFilter);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof FeatureNotSupportedThrowable);
//    }
//
//    @Test
//    public void getAllItemsByItemTypeReturnsSuccessfulResponse() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .getAllItemsByItemType(mVoucherFilter);
//
//        assertTrue(smartCredentialsResponse.isSuccessful());
//    }
//
//    @Test
//    public void getAllItemsByItemTypeReturnsUnsuccessfulResponseWhenApiControllerResponseCannotBeConverted() throws EncryptionException {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        when(mApiController.getAllItemsByItemType(any()))
//                .thenReturn(DomainModelGenerator.generateModelList());
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .getAllItemsByItemType(mVoucherFilter);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof JSONException);
//    }
//
//    @Test
//    public void getAllItemsByItemTypeReturnsUnsuccessfulResponseWhenItemEnvelopeMetadataIsNull() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .getAllItemsByItemType(mItemEnvelopeFilterNoMetadata);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof EnvelopeException);
//    }
//
//    @Test
//    public void getAllItemsByItemTypeReturnsUnsuccessfulResponseWhenDomainModelExceptionIsThrown() throws EncryptionException {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        when(mApiController.getAllItemsByItemType(any()))
//                .thenThrow(new DomainModelException(NULL_PARAMETER_EXCEPTION_MSG));
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .getAllItemsByItemType(mVoucherFilter);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof EnvelopeException);
//    }
//
//    @Test
//    public void getAllItemsByItemTypeReturnsUnsuccessfulResponseWhenItemEnvelopeTargetIsNull() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .getAllItemsByItemType(mItemEnvelopeFilterNoTarget);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof EnvelopeException);
//    }
//
//    @Test
//    public void getAllItemsByItemTypeReturnsUnsuccessfulResponseWhenRepoExceptionIsThrown() throws EncryptionException {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        when(mApiController.getAllItemsByItemType(any())).thenThrow(new RepoException(new Throwable()));
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .getAllItemsByItemType(mVoucherFilter);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof RepoException);
//    }
//
//    @Test
//    public void getAllItemsByItemTypeReturnsUnsuccessfulResponseWhenEncryptionExceptionIsThrown() throws EncryptionException {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        when(mApiController.getAllItemsByItemType(any())).thenThrow(new EncryptionException(new Exception()));
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .getAllItemsByItemType(mVoucherFilter);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof EncryptionException);
//    }
//
//    @Test
//    public void getItemSummaryByIdReturnsUnsuccessfulResponseIfDeviceIsRooted() {
//        when(mApiController.isSecurityCompromised()).thenReturn(true);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .getItemSummaryById(mVoucherFilter);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof RootedThrowable);
//    }
//
//    @Test
//    public void getItemSummaryByIdReturnsUnsuccessfulResponseIfDeviceIsBlacklisted() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        PowerMockito.when(SmartCredentialsSystemPropertyMap.isFeatureBlockedOnCurrentDevice(any(),
//                eq(SmartCredentialsFeatureSet.STORAGE))).thenReturn(true);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .getItemSummaryById(mVoucherFilter);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof FeatureNotSupportedThrowable);
//    }
//
//    @Test
//    public void getItemSummaryByIdReturnsSuccessfulResponse() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .getItemSummaryById(mVoucherFilter);
//
//        assertTrue(smartCredentialsResponse.isSuccessful());
//    }
//
//    @Test
//    public void getItemSummaryByIdReturnsUnsuccessfulResponseWhenApiControllerResponseCannotBeConverted() throws EncryptionException {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        when(mApiController.retrieveItemSummaryByUniqueIdAndType(any()))
//                .thenReturn(DomainModelGenerator.generateModelWithUnConvertibleJson());
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .getItemSummaryById(mVoucherFilter);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof JSONException);
//    }
//
//    @Test
//    public void getItemSummaryByIdReturnsUnsuccessfulResponseWhenItemEnvelopeMetadataIsNull() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .getItemSummaryById(mItemEnvelopeFilterNoMetadata);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof EnvelopeException);
//    }
//
//    @Test
//    public void getItemSummaryByIdReturnsUnsuccessfulResponseWhenItemEnvelopeTargetIsNull() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .getItemSummaryById(mItemEnvelopeFilterNoTarget);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof EnvelopeException);
//    }
//
//    @Test
//    public void getItemSummaryByIdReturnsUnsuccessfulResponseWhenDomainModelExceptionIsThrown() throws EncryptionException {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        when(mApiController.retrieveItemSummaryByUniqueIdAndType(any()))
//                .thenThrow(new DomainModelException(NULL_PARAMETER_EXCEPTION_MSG));
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .getItemSummaryById(mVoucherFilter);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof EnvelopeException);
//    }
//
//    @Test
//    public void getItemSummaryByIdReturnsUnsuccessfulResponseWhenRepoExceptionIsThrown() throws EncryptionException {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        when(mApiController.retrieveItemSummaryByUniqueIdAndType(any())).thenThrow(new RepoException(new Throwable()));
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .getItemSummaryById(mVoucherFilter);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof RepoException);
//    }
//
//    @Test
//    public void getItemSummaryByIdReturnsUnsuccessfulResponseWhenEncryptionExceptionIsThrown() throws EncryptionException {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        when(mApiController.retrieveItemSummaryByUniqueIdAndType(any())).thenThrow(new EncryptionException(new Exception()));
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .getItemSummaryById(mVoucherFilter);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof EncryptionException);
//    }
//
//    @Test
//    public void getItemDetailsByIdReturnsUnsuccessfulResponseIfDeviceIsRooted() {
//        when(mApiController.isSecurityCompromised()).thenReturn(true);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .getItemDetailsById(mVoucherFilter);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof RootedThrowable);
//    }
//
//    @Test
//    public void getItemDetailsByIdReturnsUnsuccessfulResponseIfDeviceIsBlacklisted() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        PowerMockito.when(SmartCredentialsSystemPropertyMap.isFeatureBlockedOnCurrentDevice(any(),
//                eq(SmartCredentialsFeatureSet.STORAGE))).thenReturn(true);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .getItemDetailsById(mVoucherFilter);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof FeatureNotSupportedThrowable);
//    }
//
//
//    @Test
//    public void getItemDetailsByIdReturnsSuccessfulResponse() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .getItemDetailsById(mVoucherFilter);
//
//        assertTrue(smartCredentialsResponse.isSuccessful());
//    }
//
//    @Test
//    public void getItemDetailsByIdReturnsUnsuccessfulResponseWhenItemEnvelopeMetadataIsNull() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .getItemDetailsById(mItemEnvelopeFilterNoMetadata);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof EnvelopeException);
//    }
//
//    @Test
//    public void getItemDetailsByIdReturnsUnsuccessfulResponseWhenItemEnvelopeTargetIsNull() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .getItemDetailsById(mItemEnvelopeFilterNoTarget);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof EnvelopeException);
//    }
//
//    @Test
//    public void getItemDetailsByIdReturnsUnsuccessfulResponseWhenApiControllerResponseCannotBeConverted() throws EncryptionException {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        when(mApiController.getItemDetailsByUniqueIdAndType(any()))
//                .thenReturn(DomainModelGenerator.generateModelWithUnConvertibleJson());
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .getItemDetailsById(mVoucherFilter);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof JSONException);
//    }
//
//    @Test
//    public void getItemDetailsByIdReturnsUnsuccessfulResponseWhenRepoExceptionIsThrown() throws EncryptionException {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        when(mApiController.getItemDetailsByUniqueIdAndType(any())).thenThrow(new RepoException(new Throwable()));
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .getItemDetailsById(mVoucherFilter);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof RepoException);
//    }
//
//    @Test
//    public void getItemDetailsByIdReturnsUnsuccessfulResponseWhenDomainModelExceptionIsThrown() throws EncryptionException {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        when(mApiController.getItemDetailsByUniqueIdAndType(any())).thenThrow(new DomainModelException(NULL_PARAMETER_EXCEPTION_MSG));
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .getItemDetailsById(mVoucherFilter);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof EnvelopeException);
//    }
//
//    @Test
//    public void getItemDetailsByIdReturnsUnsuccessfulResponseWhenEncryptionExceptionIsThrown() throws EncryptionException {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        when(mApiController.getItemDetailsByUniqueIdAndType(any())).thenThrow(new EncryptionException(NULL_PARAMETER_EXCEPTION_MSG));
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .getItemDetailsById(mVoucherFilter);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof EncryptionException);
//    }
//
//    @Test
//    public void putItemReturnsUnsuccessfulResponseIfDeviceIsRooted() {
//        when(mApiController.isSecurityCompromised()).thenReturn(true);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .putItem(mItemEnvelopeNoMetadata, mItemContext);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof RootedThrowable);
//    }
//
//    @Test
//    public void putItemReturnsUnsuccessfulResponseIfDeviceIsBlacklisted() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        PowerMockito.when(SmartCredentialsSystemPropertyMap.isFeatureBlockedOnCurrentDevice(any(),
//                eq(SmartCredentialsFeatureSet.STORAGE))).thenReturn(true);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .putItem(mItemEnvelopeNoMetadata, mItemContext);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof FeatureNotSupportedThrowable);
//    }
//
//    @Test
//    public void putItemReturnsSuccessfulResponse() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .putItem(mItemEnvelopeNoTarget, mItemContext);
//
//        assertTrue(smartCredentialsResponse.isSuccessful());
//    }
//
//    @Test
//    public void putItemReturnsUnsuccessfulResponseWhenItemEnvelopeMetadataIsNull() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .putItem(mItemEnvelopeNoMetadata, mItemContext);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof EnvelopeException);
//    }
//
//    @Test
//    public void putItemReturnsUnsuccessfulResponseWhenRepoExceptionIsThrown() throws EncryptionException {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        doThrow(new RepoException(new Throwable())).when(mApiController).putItem(any());
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .putItem(mItemEnvelopeNoTarget, mItemContext);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof RepoException);
//    }
//
//    @Test
//    public void putIdReturnsUnsuccessfulResponseWhenDomainModelExceptionIsThrown() throws EncryptionException {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        when(mApiController.putItem(any())).thenThrow(new DomainModelException(NULL_PARAMETER_EXCEPTION_MSG));
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .putItem(mItemEnvelopeNoTarget, mItemContext);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof EnvelopeException);
//    }
//
//    @Test
//    public void putIdReturnsUnsuccessfulResponseWhenEncryptionExceptionIsThrown() throws EncryptionException {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        when(mApiController.putItem(any())).thenThrow(new EncryptionException(NULL_PARAMETER_EXCEPTION_MSG));
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .putItem(mItemEnvelopeNoTarget, mItemContext);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof EncryptionException);
//    }
//
//    @Test
//    public void deleteItemReturnsUnsuccessfulResponseIfDeviceIsRooted() {
//        when(mApiController.isSecurityCompromised()).thenReturn(true);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .deleteItem(mVoucherFilter);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof RootedThrowable);
//    }
//
//    @Test
//    public void deleteItemReturnsUnsuccessfulResponseIfDeviceIsBlacklisted() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        PowerMockito.when(SmartCredentialsSystemPropertyMap.isFeatureBlockedOnCurrentDevice(any(),
//                eq(SmartCredentialsFeatureSet.STORAGE))).thenReturn(true);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .deleteItem(mVoucherFilter);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof FeatureNotSupportedThrowable);
//    }
//
//    @Test
//    public void deleteItemReturnsUnsuccessfulResponseWhenItemEnvelopeMetadataIsNull() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .deleteItem(mItemEnvelopeFilterNoMetadata);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof EnvelopeException);
//    }
//
//    @Test
//    public void deleteItemReturnsUnsuccessfulResponseWhenItemEnvelopeTargetIsNull() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .deleteItem(mItemEnvelopeFilterNoTarget);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof EnvelopeException);
//    }
//
//    @Test
//    public void deleteItemReturnsSuccessfulResponse() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .deleteItem(mVoucherFilter);
//
//        assertTrue(smartCredentialsResponse.isSuccessful());
//    }
//
//    @Test
//    public void deleteItemReturnsUnsuccessfulResponseWhenDomainModelExceptionIsThrown() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        when(mApiController.deleteItem(any())).thenThrow(new DomainModelException(NULL_PARAMETER_EXCEPTION_MSG));
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .deleteItem(mVoucherFilter);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof EnvelopeException);
//    }
//
//    @Test
//    public void deleteItemReturnsUnsuccessfulResponseWhenRepoExceptionIsThrown() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        doThrow(new RepoException(new Throwable())).when(mApiController).deleteItem(any());
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .deleteItem(mVoucherFilter);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof RepoException);
//    }
//
//    @Test
//    public void deleteItemByTYpeReturnsUnsuccessfulResponseIfDeviceIsRooted() {
//        when(mApiController.isSecurityCompromised()).thenReturn(true);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .deleteItemsByType(mVoucherFilter);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof RootedThrowable);
//    }
//
//    @Test
//    public void deleteItemByTYpeReturnsUnsuccessfulResponseIfDeviceIsBlacklisted() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        PowerMockito.when(SmartCredentialsSystemPropertyMap.isFeatureBlockedOnCurrentDevice(any(),
//                eq(SmartCredentialsFeatureSet.STORAGE))).thenReturn(true);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .deleteItemsByType(mVoucherFilter);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof FeatureNotSupportedThrowable);
//    }
//
//    @Test
//    public void deleteItemByTypeReturnsSuccessfulResponse() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .deleteItemsByType(mVoucherFilter);
//
//        assertTrue(smartCredentialsResponse.isSuccessful());
//    }
//
//    @Test
//    public void deleteItemByTypeReturnsUnsuccessfulResponseWhenDomainModelExceptionIsThrown() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        when(mApiController.deleteItemsByType(any()))
//                .thenThrow(new DomainModelException(NULL_PARAMETER_EXCEPTION_MSG));
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .deleteItemsByType(mVoucherFilter);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof EnvelopeException);
//    }
//
//    @Test
//    public void deleteItemByTypeReturnsUnsuccessfulResponseWhenItemEnvelopeMetadataIsNull() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .deleteItemsByType(mItemEnvelopeFilterNoMetadata);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof EnvelopeException);
//    }
//
//    @Test
//    public void deleteItemByTypeReturnsUnsuccessfulResponseWhenItemEnvelopeTargetIsNull() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .deleteItemsByType(mItemEnvelopeFilterNoTarget);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof EnvelopeException);
//    }
//
//    @Test
//    public void deleteItemByTypeReturnsUnsuccessfulResponseWhenRepoExceptionIsThrown() {
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//        doThrow(new RepoException(new Throwable())).when(mApiController).deleteItemsByType(any());
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .deleteItemsByType(mVoucherFilter);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getError() instanceof RepoException);
//    }
//
//    @Test
//    public void isDeviceRootedReturnsSuccessfulResponseWithBooleanValueIfDeviceIsRooted() {
//        when(mApiController.isSecurityLeakDetected()).thenReturn(true);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi.isDeviceRooted();
//
//        assertTrue(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getData() instanceof Boolean);
//        assertTrue((Boolean) smartCredentialsResponse.getData());
//
//        when(mApiController.isSecurityLeakDetected()).thenReturn(false);
//
//        smartCredentialsResponse = mSCPersistenceApi.isDeviceRooted();
//
//        assertTrue(smartCredentialsResponse.isSuccessful());
//        assertTrue(smartCredentialsResponse.getData() instanceof Boolean);
//        assertFalse((Boolean) smartCredentialsResponse.getData());
//    }
//
//    @Test
//    public void executeReturnsUnsuccessfulResponse() {
//        when(mApiController.isSecurityCompromised()).thenReturn(true);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi.execute(null, null, null);
//
//        assertFalse(smartCredentialsResponse.isSuccessful());
//    }
//
//    @Test
//    @PrepareForTest({ObjectGraphCreator.class, ModelConverter.class, FilesManager.class, SmartCredentialsSystemPropertyMap.class})
//    public void executeReturnForwardsCallToApiController() {
//        ItemDomainModel itemDomainModel = Mockito.mock(ItemDomainModel.class);
//        ExecutionCallback callback = Mockito.mock(ExecutionCallback.class);
//        String actionId = "1";
//
//        PowerMockito.mockStatic(ModelConverter.class);
//        PowerMockito.when(ModelConverter.toItemDomainModel(mItemEnvelopeNoTarget))
//                .thenReturn(itemDomainModel);
//        when(mApiController.isSecurityCompromised()).thenReturn(false);
//
//        SmartCredentialsApiResponse smartCredentialsResponse = mSCPersistenceApi
//                .execute(mItemEnvelopeNoTarget, actionId, callback);
//
//        assertTrue(smartCredentialsResponse.isSuccessful());
//        verify(mApiController, times(1))
//                .execute(itemDomainModel, actionId, callback);
//    }
//}