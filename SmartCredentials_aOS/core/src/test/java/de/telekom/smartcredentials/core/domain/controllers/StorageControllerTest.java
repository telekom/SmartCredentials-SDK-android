///*
// * Developed by Lucian Iacob in Cluj-Napoca.
// * Project: SmartCredentials_aOS
// * Email: lucian.iacob@endava.com
// * Last modified 16/11/18 15:20.
// * Copyright (c) Deutsche Telekom, 2018. All rights reserved.
// */
//
//package de.telekom.smartcredentials.core.domain.controllers;
//
//import com.google.gson.Gson;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import de.telekom.smartcredentials.core.exceptions.EncryptionException;
//import de.telekom.smartcredentials.core.exceptions.InvalidAlgorithmException;
//import de.telekom.smartcredentials.core.model.DomainModelException;
//import de.telekom.smartcredentials.core.model.EncryptionAlgorithm;
//import de.telekom.smartcredentials.core.model.item.ContentType;
//import de.telekom.smartcredentials.core.model.item.ItemDomainMetadata;
//import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
//import de.telekom.smartcredentials.core.model.token.TokenRequest;
//import de.telekom.smartcredentials.core.repositories.Repository;
//import de.telekom.smartcredentials.core.strategies.EncryptionStrategy;
//import de.telekom.smartcredentials.core.domain.utils.MocksProvider;
//import de.telekom.smartcredentials.core.domain.utils.ObjectsProvider;
//
//import static de.telekom.smartcredentials.core.model.ModelValidator.NO_METADATA_EXCEPTION_MSG;
//import static de.telekom.smartcredentials.core.model.ModelValidator.NULL_PARAMETER_EXCEPTION_MSG;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//public class StorageControllerTest {
//
//    private Repository mRepository;
//    private EncryptionStrategy mEncryptionStrategy;
//    private StorageController mStorageController;
//    private ItemDomainModel mEncryptedItemDomainModel;
//    private ItemDomainModel mUnencryptedItemDomainModel;
//
//    @Rule
//    public ExpectedException mExpectedException = ExpectedException.none();
//
//    @Before
//    public void setUp() {
//        mRepository = MocksProvider.provideRepository();
//        mEncryptionStrategy = MocksProvider.provideEncryptionStrategy();
//        Gson gson = ObjectsProvider.provideGson();
//
//        mStorageController = new StorageController(mRepository, mEncryptionStrategy, gson);
//
//        mEncryptedItemDomainModel = ObjectsProvider.provideEncryptedItemDomainModel();
//        mUnencryptedItemDomainModel = ObjectsProvider.provideUnEncryptedItemDomainModel();
//    }
//
//    @Test
//    public void saveDataShouldSaveEncryptedData() throws EncryptionException {
//        String encryptedIdentifier = mEncryptedItemDomainModel.getData().getIdentifier();
//        String encryptedPrivateData = mEncryptedItemDomainModel.getData().getPrivateData();
//
//        mStorageController.saveData(mEncryptedItemDomainModel);
//
//        verify(mEncryptionStrategy, times(1))
//                .encrypt(encryptedIdentifier, mRepository.getAlias(mEncryptedItemDomainModel.getMetadata()));
//        verify(mEncryptionStrategy, times(1))
//                .encrypt(encryptedPrivateData, mRepository.getAlias(mEncryptedItemDomainModel.getMetadata()));
//        verify(mRepository, times(1))
//                .saveData(mEncryptedItemDomainModel);
//    }
//
//    @Test
//    public void saveDataShouldSaveNotEncryptedData() throws EncryptionException {
//        String encryptedIdentifier = mUnencryptedItemDomainModel.getData().getIdentifier();
//        String encryptedPrivateData = mUnencryptedItemDomainModel.getData().getPrivateData();
//
//        mStorageController.saveData(mUnencryptedItemDomainModel);
//
//        verify(mEncryptionStrategy, never()).encrypt(encryptedIdentifier,
//                mRepository.getAlias(mEncryptedItemDomainModel.getMetadata()));
//        verify(mEncryptionStrategy, never()).encrypt(encryptedPrivateData,
//                mRepository.getAlias(mEncryptedItemDomainModel.getMetadata()));
//        verify(mRepository, times(1))
//                .saveData(mUnencryptedItemDomainModel);
//    }
//
//    @Test
//    public void saveDataThrowsEncryptionException() throws EncryptionException {
//        String expectedMessage = "could not encrypt text";
//        ItemDomainModel itemDomainModel = MocksProvider.provideItemDomainModel();
//        ItemDomainMetadata itemDomainMetadata = MocksProvider.provideItemDomainMetadata();
//        when(itemDomainMetadata.isDataEncrypted()).thenReturn(true);
//        when(itemDomainModel.encryptData(mEncryptionStrategy, mRepository.getAlias(itemDomainMetadata)))
//                .thenThrow(new EncryptionException(expectedMessage));
//        when(itemDomainModel.getMetadata()).thenReturn(itemDomainMetadata);
//
//        mExpectedException.expect(EncryptionException.class);
//        mExpectedException.expectMessage(expectedMessage);
//
//        mStorageController.saveData(itemDomainModel);
//    }
//
//    @Test
//    public void saveDataDoesNotThrowEncryptedExceptionWhenDataIsNotEncrypted() throws EncryptionException {
//        String expectedMessage = "could not encrypt text";
//        ItemDomainModel itemDomainModel = MocksProvider.provideItemDomainModel();
//        ItemDomainMetadata itemDomainMetadata = MocksProvider.provideItemDomainMetadata();
//        when(itemDomainMetadata.isDataEncrypted()).thenReturn(false);
//        when(itemDomainModel.encryptData(mEncryptionStrategy, mRepository.getAlias(itemDomainMetadata)))
//                .thenThrow(new EncryptionException(expectedMessage));
//        when(itemDomainModel.getMetadata()).thenReturn(itemDomainMetadata);
//
//        mStorageController.saveData(itemDomainModel);
//    }
//
//    @Test
//    public void saveDataThrowsExceptionWhenItemDomainModelIsNotValidated() throws EncryptionException {
//        mExpectedException.expect(DomainModelException.class);
//        mExpectedException.expectMessage(NULL_PARAMETER_EXCEPTION_MSG);
//
//        mStorageController.saveData(null);
//    }
//
//    @Test
//    public void saveDataThrowsExceptionWhenItemDomainMetadataIsNotValidated() throws EncryptionException {
//        mExpectedException.expect(DomainModelException.class);
//        mExpectedException.expectMessage(NO_METADATA_EXCEPTION_MSG);
//
//        mStorageController.saveData(new ItemDomainModel());
//    }
//
//    @Test
//    public void retrieveFilteredItemsReturnsEmptyListWhenNoItemInRepository() throws EncryptionException {
//        List<ItemDomainModel> itemDomainModelList =
//                mStorageController.retrieveItemsFilteredByType(mEncryptedItemDomainModel);
//
//        assertEquals(itemDomainModelList.size(), 0);
//    }
//
//    @Test
//    public void retrieveFilteredItemsReturnsNumberOfItemsInRepository() throws EncryptionException {
//        List<ItemDomainModel> itemDomainModels = new ArrayList<>();
//        itemDomainModels.add(mEncryptedItemDomainModel);
//        when(mRepository.retrieveItemsFilteredByType(mEncryptedItemDomainModel)).thenReturn(itemDomainModels);
//
//        List<ItemDomainModel> itemDomainModelList =
//                mStorageController.retrieveItemsFilteredByType(mEncryptedItemDomainModel);
//
//        assertEquals(itemDomainModelList.size(), 1);
//    }
//
//    @Test
//    public void retrieveFilteredItemsReturnsEmptyListOfItemsWhenNoItemsInRepository() throws EncryptionException {
//        when(mRepository.retrieveItemsFilteredByType(mEncryptedItemDomainModel)).thenReturn(new ArrayList<>());
//
//        List<ItemDomainModel> itemDomainModelList =
//                mStorageController.retrieveItemsFilteredByType(mEncryptedItemDomainModel);
//
//        assertEquals(itemDomainModelList.size(), 0);
//    }
//
//    @Test
//    public void retrieveFilteredItemsReturnsNullWhenRepositoryReturnsNull() throws EncryptionException {
//        when(mRepository.retrieveItemsFilteredByType(mEncryptedItemDomainModel)).thenReturn(null);
//
//        List<ItemDomainModel> itemDomainModelList =
//                mStorageController.retrieveItemsFilteredByType(mEncryptedItemDomainModel);
//
//        assertNull(itemDomainModelList);
//    }
//
//    @Test
//    public void retrieveFilteredItemsReturnsDecryptedItemsWhenEncrypted() throws EncryptionException {
//        String encryptedIdentifier = mEncryptedItemDomainModel.getData().getIdentifier();
//        String decryptedIdentifier = "decryptedIdentifier";
//
//        List<ItemDomainModel> itemDomainModels = new ArrayList<>();
//        itemDomainModels.add(mEncryptedItemDomainModel);
//
//        when(mRepository.retrieveItemsFilteredByType(mEncryptedItemDomainModel)).thenReturn(itemDomainModels);
//        when(mEncryptionStrategy.decrypt(mEncryptedItemDomainModel.getData().getIdentifier(),
//                mRepository.getAlias(mEncryptedItemDomainModel.getMetadata()))).thenReturn(decryptedIdentifier);
//
//        List<ItemDomainModel> itemDomainModelList =
//                mStorageController.retrieveItemsFilteredByType(mEncryptedItemDomainModel);
//
//        assertNotNull(itemDomainModelList);
//        assertEquals(itemDomainModelList.size(), 1);
//        assertNotEquals(itemDomainModelList.get(0).getData().getIdentifier(), encryptedIdentifier);
//        assertEquals(itemDomainModelList.get(0).getData().getIdentifier(), decryptedIdentifier);
//    }
//
//    @Test
//    public void retrieveFilteredItemsShouldReturnDecryptedItemsIfInitiallyEncrypted() throws EncryptionException {
//        String encryptedIdentifier = mEncryptedItemDomainModel.getData().getIdentifier();
//        String decryptedIdentifier = "decryptedIdentifier";
//
//        List<ItemDomainModel> encryptedItems = new ArrayList<>();
//        encryptedItems.add(mEncryptedItemDomainModel);
//
//        when(mRepository.retrieveItemsFilteredByType(mUnencryptedItemDomainModel)).thenReturn(encryptedItems);
//        when(mEncryptionStrategy.decrypt(mEncryptedItemDomainModel.getData().getIdentifier(),
//                mRepository.getAlias(mEncryptedItemDomainModel.getMetadata()))).thenReturn(decryptedIdentifier);
//
//        List<ItemDomainModel> itemDomainModelList =
//                mStorageController.retrieveItemsFilteredByType(mUnencryptedItemDomainModel);
//
//        assertNotNull(itemDomainModelList);
//        assertEquals(itemDomainModelList.size(), 1);
//        assertNotEquals(itemDomainModelList.get(0).getData().getIdentifier(), encryptedIdentifier);
//        assertEquals(itemDomainModelList.get(0).getData().getIdentifier(), decryptedIdentifier);
//    }
//
//    @Test
//    public void retrieveFilteredItemsShouldDecryptWithRsaIfInvalidAlgorithmExceptionIsThrown()
//            throws EncryptionException {
//        String encryptedIdentifier = mEncryptedItemDomainModel.getData().getIdentifier();
//        String encryptedPrivateData = mEncryptedItemDomainModel.getData().getPrivateData();
//        String decryptedIdentifier = "decryptedIdentifier";
//        String decryptedPrivateData = "decryptedPrivateData";
//        List<ItemDomainModel> encryptedItems = new ArrayList<>();
//        encryptedItems.add(mEncryptedItemDomainModel);
//        String alias = mRepository.getAlias(mEncryptedItemDomainModel.getMetadata());
//
//        when(mRepository.retrieveItemsFilteredByType(mEncryptedItemDomainModel))
//                .thenReturn(encryptedItems);
//        when(mEncryptionStrategy.decrypt(encryptedIdentifier, alias))
//                .thenThrow(new InvalidAlgorithmException(""));
//        when(mEncryptionStrategy.decrypt(encryptedIdentifier, alias, EncryptionAlgorithm.RSA_2048))
//                .thenReturn(decryptedIdentifier);
//        when(mEncryptionStrategy.decrypt(encryptedPrivateData, alias, EncryptionAlgorithm.RSA_2048))
//                .thenReturn(decryptedPrivateData);
//
//        List<ItemDomainModel> returnedItems =
//                mStorageController.retrieveItemsFilteredByType(mEncryptedItemDomainModel);
//
//        verify(mEncryptionStrategy, times(1))
//                .decrypt(encryptedIdentifier, alias, EncryptionAlgorithm.RSA_2048);
//        verify(mEncryptionStrategy, times(1))
//                .decrypt(encryptedPrivateData, alias, EncryptionAlgorithm.RSA_2048);
//        verify(mEncryptionStrategy, times(1))
//                .encrypt(decryptedIdentifier, alias);
//        verify(mEncryptionStrategy, times(1))
//                .encrypt(decryptedPrivateData, alias);
//        verify(mRepository, times(1))
//                .updateItem(any(ItemDomainModel.class));
//        assertEquals(returnedItems.size(), 1);
//    }
//
//    @Test
//    public void retrieveFilteredItemsReturnsSameIdentifierWhenNotEncrypted() throws EncryptionException {
//        String identifier = mUnencryptedItemDomainModel.getData().getIdentifier();
//        String decryptedIdentifier = "decryptedIdentifier";
//
//        List<ItemDomainModel> unencryptedItems = new ArrayList<>();
//        unencryptedItems.add(mUnencryptedItemDomainModel);
//
//        when(mRepository.retrieveItemsFilteredByType(mUnencryptedItemDomainModel)).thenReturn(unencryptedItems);
//        when(mEncryptionStrategy.decrypt(mUnencryptedItemDomainModel.getData().getIdentifier(),
//                mRepository.getAlias(mEncryptedItemDomainModel.getMetadata()))).thenReturn(decryptedIdentifier);
//
//        List<ItemDomainModel> itemDomainModelList =
//                mStorageController.retrieveItemsFilteredByType(mUnencryptedItemDomainModel);
//
//        assertNotNull(itemDomainModelList);
//        assertEquals(itemDomainModelList.size(), 1);
//        assertEquals(itemDomainModelList.get(0).getData().getIdentifier(), identifier);
//        assertNotEquals(itemDomainModelList.get(0).getData().getIdentifier(), decryptedIdentifier);
//    }
//
//    @Test
//    public void retrieveFilteredItemsShouldNotReturnDecryptedItemsIfNotInitiallyEncrypted()
//            throws EncryptionException {
//        String identifier = mUnencryptedItemDomainModel.getData().getIdentifier();
//        String decryptedIdentifier = "decryptedIdentifier";
//
//        List<ItemDomainModel> itemDomainModels = new ArrayList<>();
//        itemDomainModels.add(mUnencryptedItemDomainModel);
//
//        when(mRepository.retrieveItemsFilteredByType(mEncryptedItemDomainModel)).thenReturn(itemDomainModels);
//        when(mEncryptionStrategy.decrypt(mUnencryptedItemDomainModel.getData().getIdentifier(),
//                mRepository.getAlias(mEncryptedItemDomainModel.getMetadata()))).thenReturn(decryptedIdentifier);
//
//        List<ItemDomainModel> itemDomainModelList =
//                mStorageController.retrieveItemsFilteredByType(mEncryptedItemDomainModel);
//
//        assertNotNull(itemDomainModelList);
//        assertEquals(itemDomainModelList.size(), 1);
//        assertEquals(itemDomainModelList.get(0).getData().getIdentifier(), identifier);
//        assertNotEquals(itemDomainModelList.get(0).getData().getIdentifier(), decryptedIdentifier);
//    }
//
//    @Test
//    public void retrieveFilteredItemsThrowsEncryptionException() throws EncryptionException {
//        List<ItemDomainModel> itemDomainModelList = new ArrayList<>();
//        ItemDomainModel retrievedItem = MocksProvider.provideItemDomainModel();
//        ItemDomainMetadata itemDomainMetadata = MocksProvider.provideItemDomainMetadata();
//        when(itemDomainMetadata.isDataEncrypted()).thenReturn(true);
//        when(retrievedItem.getMetadata()).thenReturn(itemDomainMetadata);
//        itemDomainModelList.add(retrievedItem);
//
//        ItemDomainModel retrievedItem2 = MocksProvider.provideItemDomainModel();
//        ItemDomainMetadata itemDomainMetadata2 = MocksProvider.provideItemDomainMetadata();
//        when(itemDomainMetadata2.isDataEncrypted()).thenReturn(false);
//        when(retrievedItem2.getMetadata()).thenReturn(itemDomainMetadata2);
//        itemDomainModelList.add(retrievedItem2);
//
//        String expectedMessage = "could not decrypt text";
//        ItemDomainModel itemDomainModel = MocksProvider.provideItemDomainModel();
//        when(mRepository.retrieveItemsFilteredByType(itemDomainModel)).thenReturn(itemDomainModelList);
//
//        when(retrievedItem.decryptData(mEncryptionStrategy, mRepository.getAlias(itemDomainMetadata)))
//                .thenThrow(new EncryptionException(expectedMessage));
//
//        mExpectedException.expect(EncryptionException.class);
//        mExpectedException.expectMessage(expectedMessage);
//
//        mStorageController.retrieveItemsFilteredByType(itemDomainModel);
//    }
//
//    @Test
//    public void retrieveFilteredItemsDoesNotThrowEncryptionException() throws EncryptionException {
//        List<ItemDomainModel> itemDomainModelList = new ArrayList<>();
//        ItemDomainModel retrievedItem = MocksProvider.provideItemDomainModel();
//        ItemDomainMetadata itemDomainMetadata = MocksProvider.provideItemDomainMetadata();
//        when(itemDomainMetadata.isDataEncrypted()).thenReturn(false);
//        when(retrievedItem.getMetadata()).thenReturn(itemDomainMetadata);
//        itemDomainModelList.add(retrievedItem);
//
//        ItemDomainModel retrievedItem2 = MocksProvider.provideItemDomainModel();
//        ItemDomainMetadata itemDomainMetadata2 = MocksProvider.provideItemDomainMetadata();
//        when(itemDomainMetadata2.isDataEncrypted()).thenReturn(false);
//        when(retrievedItem2.getMetadata()).thenReturn(itemDomainMetadata2);
//        itemDomainModelList.add(retrievedItem2);
//
//        String expectedMessage = "could not decrypt text";
//        ItemDomainModel itemDomainModel = MocksProvider.provideItemDomainModel();
//        when(mRepository.retrieveItemsFilteredByType(itemDomainModel)).thenReturn(itemDomainModelList);
//
//        when(retrievedItem.decryptData(mEncryptionStrategy, mRepository.getAlias(itemDomainMetadata)))
//                .thenThrow(new EncryptionException(expectedMessage));
//
//        mStorageController.retrieveItemsFilteredByType(itemDomainModel);
//    }
//
//    @Test
//    public void retrieveFilteredItemSummaryReturnsNullWhenNoItemInRepository() throws EncryptionException {
//        assertNull(mStorageController.retrieveItemSummaryByUniqueIdAndType(mEncryptedItemDomainModel));
//    }
//
//    @Test
//    public void retrieveFilteredItemSummaryReturnsDecryptedItemIfInitiallyEncrypted() throws EncryptionException {
//        String decryptedIdentifier = "decryptedIdentifier";
//
//        when(mRepository.retrieveFilteredItemSummaryByUniqueIdAndType(mEncryptedItemDomainModel)).
//                thenReturn(mEncryptedItemDomainModel);
//        when(mEncryptionStrategy.decrypt(mEncryptedItemDomainModel.getData().getIdentifier(),
//                mRepository.getAlias(mEncryptedItemDomainModel.getMetadata()))).thenReturn(decryptedIdentifier);
//
//
//        ItemDomainModel domainModel = mStorageController.retrieveItemSummaryByUniqueIdAndType(mEncryptedItemDomainModel);
//
//        assertNotNull(domainModel);
//        assertEquals(domainModel.getData().getIdentifier(), decryptedIdentifier);
//    }
//
//    @Test
//    public void retrieveFilteredItemSummaryShouldDecryptWithRsaIfInvalidAlgorithmExceptionIsThrown()
//            throws EncryptionException {
//        String encryptedIdentifier = mEncryptedItemDomainModel.getData().getIdentifier();
//        String decryptedIdentifier = "identifier";
//        String alias = mRepository.getAlias(mEncryptedItemDomainModel.getMetadata());
//
//        when(mRepository.retrieveFilteredItemSummaryByUniqueIdAndType(mEncryptedItemDomainModel)).
//                thenReturn(mEncryptedItemDomainModel);
//        when(mEncryptionStrategy.decrypt(encryptedIdentifier, alias))
//                .thenThrow(new InvalidAlgorithmException(""));
//        when(mEncryptionStrategy.decrypt(encryptedIdentifier, alias, EncryptionAlgorithm.RSA_2048))
//                .thenReturn(decryptedIdentifier);
//
//        ItemDomainModel domainModel = mStorageController.retrieveItemSummaryByUniqueIdAndType(mEncryptedItemDomainModel);
//
//        verify(mEncryptionStrategy, times(1))
//                .decrypt(encryptedIdentifier, alias, EncryptionAlgorithm.RSA_2048);
//        verify(mEncryptionStrategy, times(1))
//                .encrypt(decryptedIdentifier, alias);
//        verify(mRepository, times(1))
//                .updateItem(any(ItemDomainModel.class));
//        assertNotNull(domainModel);
//        assertEquals(domainModel.getData().getIdentifier(), decryptedIdentifier);
//    }
//
//    @Test
//    public void retrieveFilteredItemSummaryShouldNotDecryptItemIfInitiallyNotEncrypted() throws EncryptionException {
//        String decryptedIdentifier = "decryptedIdentifier";
//
//        when(mRepository.retrieveFilteredItemSummaryByUniqueIdAndType(mEncryptedItemDomainModel)).
//                thenReturn(mUnencryptedItemDomainModel);
//        when(mEncryptionStrategy.decrypt(mUnencryptedItemDomainModel.getData().getIdentifier(),
//                mRepository.getAlias(mEncryptedItemDomainModel.getMetadata()))).thenReturn(decryptedIdentifier);
//
//        ItemDomainModel domainModel =
//                mStorageController.retrieveItemSummaryByUniqueIdAndType(mEncryptedItemDomainModel);
//
//        assertNotNull(domainModel);
//        assertEquals(domainModel.getData().getIdentifier(), mUnencryptedItemDomainModel.getData().getIdentifier());
//    }
//
//    @Test
//    public void retrieveItemSummaryByUniqueIdAndTypeThrowsEncryptionException() throws EncryptionException {
//        ItemDomainModel retrievedItem = MocksProvider.provideItemDomainModel();
//        ItemDomainMetadata itemDomainMetadata = MocksProvider.provideItemDomainMetadata();
//        when(itemDomainMetadata.isDataEncrypted()).thenReturn(true);
//        when(retrievedItem.getMetadata()).thenReturn(itemDomainMetadata);
//
//        String expectedMessage = "could not decrypt text";
//        ItemDomainModel itemDomainModel = MocksProvider.provideItemDomainModel();
//        when(mRepository.retrieveFilteredItemSummaryByUniqueIdAndType(itemDomainModel)).thenReturn(retrievedItem);
//
//        when(retrievedItem.decryptData(mEncryptionStrategy, mRepository.getAlias(itemDomainMetadata)))
//                .thenThrow(new EncryptionException(expectedMessage));
//
//        mExpectedException.expect(EncryptionException.class);
//        mExpectedException.expectMessage(expectedMessage);
//
//        mStorageController.retrieveItemSummaryByUniqueIdAndType(itemDomainModel);
//    }
//
//    @Test
//    public void retrieveItemSummaryByUniqueIdAndTypeDoesNotThrowEncryptionException() throws EncryptionException {
//        ItemDomainModel retrievedItem = MocksProvider.provideItemDomainModel();
//        ItemDomainMetadata itemDomainMetadata = MocksProvider.provideItemDomainMetadata();
//        when(itemDomainMetadata.isDataEncrypted()).thenReturn(false);
//        when(retrievedItem.getMetadata()).thenReturn(itemDomainMetadata);
//
//        String expectedMessage = "could not decrypt text";
//        ItemDomainModel itemDomainModel = MocksProvider.provideItemDomainModel();
//        when(mRepository.retrieveFilteredItemSummaryByUniqueIdAndType(itemDomainModel)).thenReturn(retrievedItem);
//
//        when(retrievedItem.decryptData(mEncryptionStrategy, mRepository.getAlias(itemDomainMetadata)))
//                .thenThrow(new EncryptionException(expectedMessage));
//
//        mStorageController.retrieveItemSummaryByUniqueIdAndType(itemDomainModel);
//    }
//
//    @Test
//    public void retrieveFilteredItemDetailsReturnsNullWhenNoItemInRepository() throws EncryptionException {
//        assertNull(mStorageController.retrieveItemDetailsByUniqueIdAndType(mEncryptedItemDomainModel));
//    }
//
//    @Test
//    public void retrieveFilteredItemDetailsReturnsDecryptedItemIfInitiallyEncrypted() throws EncryptionException {
//        String decryptedIdentifier = "decryptedIdentifier";
//
//        when(mRepository.retrieveFilteredItemDetailsByUniqueIdAndType(mEncryptedItemDomainModel)).
//                thenReturn(mEncryptedItemDomainModel);
//        when(mEncryptionStrategy.decrypt(mEncryptedItemDomainModel.getData().getIdentifier(),
//                mRepository.getAlias(mEncryptedItemDomainModel.getMetadata()))).thenReturn(decryptedIdentifier);
//
//        ItemDomainModel domainModel =
//                mStorageController.retrieveItemDetailsByUniqueIdAndType(mEncryptedItemDomainModel);
//
//        assertNotNull(domainModel);
//        assertEquals(domainModel.getData().getIdentifier(), decryptedIdentifier);
//    }
//
//    @Test
//    public void retrieveFilteredItemDetailsShouldNotDecryptItemIfInitiallyNotEncrypted() throws EncryptionException {
//        String decryptedIdentifier = "decryptedIdentifier";
//
//        when(mRepository.retrieveFilteredItemDetailsByUniqueIdAndType(mEncryptedItemDomainModel)).
//                thenReturn(mUnencryptedItemDomainModel);
//        when(mEncryptionStrategy.decrypt(mUnencryptedItemDomainModel.getData().getIdentifier(),
//                mRepository.getAlias(mEncryptedItemDomainModel.getMetadata()))).thenReturn(decryptedIdentifier);
//
//
//        ItemDomainModel domainModel =
//                mStorageController.retrieveItemDetailsByUniqueIdAndType(mEncryptedItemDomainModel);
//
//        assertNotNull(domainModel);
//        assertEquals(domainModel.getData().getIdentifier(), mUnencryptedItemDomainModel.getData().getIdentifier());
//    }
//
//    @Test
//    public void retrieveTokenRequestCallsRetrieveFilteredItemDetailsByUniqueIdFromSensitiveRepository() throws Exception {
//        when(mRepository.retrieveFilteredItemDetailsByUniqueIdAndType(mEncryptedItemDomainModel)).
//                thenReturn(mEncryptedItemDomainModel);
//
//        TokenRequest request = mStorageController.retrieveTokenRequest(mEncryptedItemDomainModel);
//
//        assertNotNull(request);
//        assertEquals(request.getEncryptedModel(), mEncryptedItemDomainModel.getData().getPrivateData());
//    }
//
//    @Test
//    public void retrieveTokenRequestSetsContentTypeSensitiveBeforeRequestingItem() throws Exception {
//        assertEquals(mEncryptedItemDomainModel.getMetadata().getContentType(), ContentType.NON_SENSITIVE);
//
//        when(mRepository.retrieveFilteredItemDetailsByUniqueIdAndType(mEncryptedItemDomainModel)).
//                thenReturn(mEncryptedItemDomainModel);
//
//        TokenRequest request = mStorageController.retrieveTokenRequest(mEncryptedItemDomainModel);
//
//        assertEquals(mEncryptedItemDomainModel.getMetadata().getContentType(), ContentType.SENSITIVE);
//        assertNotNull(request);
//        assertEquals(request.getEncryptedModel(), mEncryptedItemDomainModel.getData().getPrivateData());
//    }
//
//    @Test
//    public void onSecurityCompromisedShouldDeleteAllRepositoryData() {
//        mStorageController.clearStorage();
//
//        verify(mRepository, times(1)).deleteAllData();
//    }
//
//    @Test
//    public void deleteItem() {
//        mStorageController.deleteItem(mEncryptedItemDomainModel);
//
//        verify(mRepository, times(1)).deleteItem(mEncryptedItemDomainModel);
//    }
//
//    @Test
//    public void deleteItemByType() {
//        mStorageController.deleteItemsByType(mEncryptedItemDomainModel);
//
//        verify(mRepository, times(1)).deleteItemsByType(mEncryptedItemDomainModel);
//    }
//
//    @Test
//    public void updateItemShouldUpdateEncryptedItem() throws EncryptionException {
//        String encryptedIdentifier = mEncryptedItemDomainModel.getData().getIdentifier();
//        String encryptedPrivateData = mEncryptedItemDomainModel.getData().getPrivateData();
//
//        mStorageController.updateItem(mEncryptedItemDomainModel);
//
//        verify(mEncryptionStrategy, times(1)).encrypt(encryptedIdentifier,
//                mRepository.getAlias(mEncryptedItemDomainModel.getMetadata()));
//        verify(mEncryptionStrategy, times(1)).encrypt(encryptedPrivateData,
//                mRepository.getAlias(mEncryptedItemDomainModel.getMetadata()));
//        verify(mRepository, times(1)).updateItem(mEncryptedItemDomainModel);
//    }
//
//    @Test
//    public void updateItemShouldUpdateNotEncryptedItem() throws EncryptionException {
//        String encryptedIdentifier = mUnencryptedItemDomainModel.getData().getIdentifier();
//        String encryptedPrivateData = mUnencryptedItemDomainModel.getData().getPrivateData();
//
//        mStorageController.updateItem(mUnencryptedItemDomainModel);
//
//        verify(mEncryptionStrategy, never()).encrypt(encryptedIdentifier,
//                mRepository.getAlias(mEncryptedItemDomainModel.getMetadata()));
//        verify(mEncryptionStrategy, never()).encrypt(encryptedPrivateData,
//                mRepository.getAlias(mEncryptedItemDomainModel.getMetadata()));
//        verify(mRepository, times(1)).updateItem(mUnencryptedItemDomainModel);
//    }
//
//    @Test
//    public void updateItemThrowsExceptionWhenItemDomainModelIsNotValidated() throws EncryptionException {
//        mExpectedException.expect(DomainModelException.class);
//        mExpectedException.expectMessage(NULL_PARAMETER_EXCEPTION_MSG);
//
//        mStorageController.updateItem(null);
//    }
//
//    @Test
//    public void updateItemThrowsExceptionWhenItemDomainMetadataIsNotValidated() throws EncryptionException {
//        mExpectedException.expect(DomainModelException.class);
//        mExpectedException.expectMessage(NO_METADATA_EXCEPTION_MSG);
//
//        mStorageController.updateItem(new ItemDomainModel());
//    }
//
//    @Test
//    public void updateItemThrowsEncryptionException() throws EncryptionException {
//        String expectedMessage = "could not encrypt text";
//        ItemDomainModel itemDomainModel = MocksProvider.provideItemDomainModel();
//        ItemDomainMetadata itemDomainMetadata = MocksProvider.provideItemDomainMetadata();
//        when(itemDomainMetadata.isDataEncrypted()).thenReturn(true);
//        when(itemDomainModel.encryptData(mEncryptionStrategy, mRepository.getAlias(itemDomainMetadata)))
//                .thenThrow(new EncryptionException(expectedMessage));
//        when(itemDomainModel.getMetadata()).thenReturn(itemDomainMetadata);
//        mExpectedException.expect(EncryptionException.class);
//        mExpectedException.expectMessage(expectedMessage);
//
//        mStorageController.updateItem(itemDomainModel);
//    }
//
//    @Test
//    public void updateItemNeverThrowsEncryptionExceptionIfNotEncrypted() throws EncryptionException {
//        String expectedMessage = "could not encrypt text";
//        ItemDomainModel itemDomainModel = MocksProvider.provideItemDomainModel();
//        ItemDomainMetadata itemDomainMetadata = MocksProvider.provideItemDomainMetadata();
//        when(itemDomainMetadata.isDataEncrypted()).thenReturn(false);
//        when(itemDomainModel.encryptData(mEncryptionStrategy, mRepository.getAlias(itemDomainMetadata)))
//                .thenThrow(new EncryptionException(expectedMessage));
//        when(itemDomainModel.getMetadata()).thenReturn(itemDomainMetadata);
//
//        mStorageController.updateItem(itemDomainModel);
//    }
//}