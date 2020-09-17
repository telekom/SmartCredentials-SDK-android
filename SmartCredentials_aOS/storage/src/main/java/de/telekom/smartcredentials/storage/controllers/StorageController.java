/*
 * Copyright (c) 2019 Telekom Deutschland AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.telekom.smartcredentials.storage.controllers;

import androidx.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsFeatureSet;
import de.telekom.smartcredentials.core.context.ItemContext;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.exceptions.EncryptionException;
import de.telekom.smartcredentials.core.exceptions.InvalidAlgorithmException;
import de.telekom.smartcredentials.core.filter.SmartCredentialsFilter;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.model.DomainModelException;
import de.telekom.smartcredentials.core.model.EncryptionAlgorithm;
import de.telekom.smartcredentials.core.model.item.ContentType;
import de.telekom.smartcredentials.core.model.item.ItemDomainMetadata;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.core.repositories.Repository;
import de.telekom.smartcredentials.core.responses.EnvelopeException;
import de.telekom.smartcredentials.core.responses.EnvelopeExceptionReason;
import de.telekom.smartcredentials.core.responses.FeatureNotSupportedThrowable;
import de.telekom.smartcredentials.core.responses.RootedThrowable;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.core.responses.SmartCredentialsResponse;
import de.telekom.smartcredentials.core.storage.ItemNotFoundException;
import de.telekom.smartcredentials.core.storage.SecurityCompromisedObserver;
import de.telekom.smartcredentials.core.storage.TokenRequest;
import de.telekom.smartcredentials.core.strategies.EncryptionStrategy;
import de.telekom.smartcredentials.storage.domain.converters.ModelConverter;
import de.telekom.smartcredentials.storage.exceptions.RepositoryException;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static de.telekom.smartcredentials.core.controllers.CoreController.UID_EXCEPTION_MESSAGE;
import static de.telekom.smartcredentials.core.model.ModelValidator.checkParamNotNull;
import static de.telekom.smartcredentials.core.model.ModelValidator.getValidatedMetadata;

public class StorageController implements StorageApi, SecurityCompromisedObserver {

    private final CoreController mCoreController;
    private final Repository mRepository;
    private final EncryptionStrategy mEncryptionStrategy;
    private final Gson mGson;
    private final CompositeDisposable mCompositeDisposable;

    public StorageController(CoreController coreController, Repository repository, EncryptionStrategy encryptionStrategy, Gson gson) {
        mCoreController = coreController;
        mRepository = repository;
        mEncryptionStrategy = encryptionStrategy;
        mGson = gson;
        mCompositeDisposable = new CompositeDisposable();
        mCoreController.attach(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<Integer> putItem(ItemDomainModel itemDomainModel) throws EncryptionException {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "putItem");
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.STORAGE)) {
            String errorMessage = SmartCredentialsFeatureSet.STORAGE.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        validateItemDomainModel(itemDomainModel);
        ItemDomainMetadata metadata = getValidatedMetadata(itemDomainModel);
        if (metadata.isDataEncrypted()) {
            itemDomainModel.encryptData(mEncryptionStrategy, isSensitive(metadata));
        }
        return new SmartCredentialsResponse<>(mRepository.saveData(itemDomainModel));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<Integer> putItem(ItemDomainModel itemDomainModel, TokenRequest tokenRequest) throws EncryptionException {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "putItem");
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.STORAGE)) {
            String errorMessage = SmartCredentialsFeatureSet.STORAGE.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        validateItemDomainModel(itemDomainModel);
        return new SmartCredentialsResponse<>(savePartiallyEncData(ModelConverter.toItemDomainModel(itemDomainModel, tokenRequest)));
    }

    /**
     * {@inheritDoc}
     */
    private int savePartiallyEncData(ItemDomainModel itemDomainModel) throws EncryptionException {
        ItemDomainMetadata metadata = getValidatedMetadata(itemDomainModel);
        if (metadata.isDataEncrypted()) {
            itemDomainModel.partiallyEncrypt(mEncryptionStrategy, isSensitive(metadata));
        }
        return mRepository.saveData(itemDomainModel);
    }

    /**
     * {@inheritDoc}
     */
    private int updateItem(ItemDomainModel itemDomainModel) throws EncryptionException {
        validateItemDomainModel(itemDomainModel);
        ItemDomainMetadata metadata = getValidatedMetadata(itemDomainModel);
        if (metadata.isDataEncrypted()) {
            itemDomainModel.encryptData(mEncryptionStrategy, isSensitive(metadata));
        }
        return mRepository.updateItem(itemDomainModel);
    }

    private List<ItemDomainModel> retrieveItemsFilteredByType(ItemDomainModel itemDomainModel) throws EncryptionException {
        List<ItemDomainModel> itemDomainModelList = mRepository.retrieveItemsFilteredByType(itemDomainModel);
        if (itemDomainModelList != null) {
            return decrypt(itemDomainModelList);
        }
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<ItemDomainModel> retrieveItemSummaryByUniqueIdAndType(ItemDomainModel itemDomainModel) throws EncryptionException {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "retrieveItemSummaryByUniqueIdAndType");
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.STORAGE)) {
            String errorMessage = SmartCredentialsFeatureSet.STORAGE.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        ItemDomainModel retrievedItemDomainModel = mRepository.retrieveFilteredItemSummaryByUniqueIdAndType(itemDomainModel);

        if (retrievedItemDomainModel != null) {
            return new SmartCredentialsResponse<>(decrypt(retrievedItemDomainModel));
        }
        return new SmartCredentialsResponse<>(new ItemNotFoundException());
    }

    private ItemDomainModel retrieveItemDetailsByUniqueIdAndType(ItemDomainModel itemDomainModel) throws EncryptionException {
        ItemDomainModel retrievedItemDomainModel = mRepository.retrieveFilteredItemDetailsByUniqueIdAndType(itemDomainModel);

        if (retrievedItemDomainModel != null) {
            return decrypt(retrievedItemDomainModel);
        }

        return null;
    }

    private void clearStorage() {
        mCompositeDisposable.add(Observable.defer(() -> Observable.just(mRepository.deleteAllData()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(count -> ApiLoggerResolver.logEvent(String.format(Locale.GERMANY, "Deleted %d rows", count)),
                        throwable -> ApiLoggerResolver.logError(StorageController.class.getSimpleName(),
                                "Failed to clear storage")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<TokenRequest> retrieveTokenRequest(ItemDomainModel itemDomainModel) {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "retrieveTokenRequest");
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.STORAGE)) {
            String errorMessage = SmartCredentialsFeatureSet.STORAGE.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        itemDomainModel.setMetadata(itemDomainModel.getMetadata().setContentType(ContentType.SENSITIVE));
        ItemDomainModel encryptedDataItemDomainModel = mRepository.retrieveFilteredItemDetailsByUniqueIdAndType(itemDomainModel);
        return new SmartCredentialsResponse<>(ModelConverter.toTokenRequest(encryptedDataItemDomainModel, mGson, mEncryptionStrategy, isSensitive(itemDomainModel.getMetadata())));
    }

    private List<ItemDomainModel> decrypt(List<ItemDomainModel> itemDomainModelList) throws EncryptionException {
        for (ItemDomainModel item : itemDomainModelList) {
            ItemDomainMetadata metadata = getValidatedMetadata(item);
            if (metadata.isDataEncrypted()) {
                try {
                    item.decryptData(mEncryptionStrategy);
                } catch (InvalidAlgorithmException exception) {
                    item.decryptData(mEncryptionStrategy, EncryptionAlgorithm.RSA_2048);
                    updateItem(new ItemDomainModel(item));
                }
            }
        }
        return itemDomainModelList;
    }

    private ItemDomainModel decrypt(ItemDomainModel itemDomainModel) throws EncryptionException {
        if (getValidatedMetadata(itemDomainModel).isDataEncrypted()) {
            try {
                return itemDomainModel.decryptData(mEncryptionStrategy);
            } catch (InvalidAlgorithmException ex) {
                itemDomainModel.decryptData(mEncryptionStrategy, EncryptionAlgorithm.RSA_2048);
                updateItem(new ItemDomainModel(itemDomainModel));
                return itemDomainModel;
            }
        }
        return itemDomainModel;
    }

    private void validateItemDomainModel(ItemDomainModel itemDomainModel) {
        if (TextUtils.isEmpty(itemDomainModel.getUid())) {
            throw new DomainModelException(UID_EXCEPTION_MESSAGE);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<List<ItemEnvelope>> getAllItemsByItemType(@NonNull SmartCredentialsFilter smartCredentialsFilter) {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "getAllItemsByItemType");
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.STORAGE)) {
            String errorMessage = SmartCredentialsFeatureSet.STORAGE.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        try {
            List<ItemDomainModel> filteredItems = retrieveItemsFilteredByType(smartCredentialsFilter.toItemDomainModel(mCoreController.getUserId()));

            if (filteredItems != null) {
                return new SmartCredentialsResponse<>(de.telekom.smartcredentials.core.converters.ModelConverter.toItemEnvelopeList(filteredItems));
            } else {
                return new SmartCredentialsResponse<>(new ItemNotFoundException());
            }
        } catch (DomainModelException e) {
            return new SmartCredentialsResponse<>(new EnvelopeException(EnvelopeExceptionReason.map(e.getMessage())));
        } catch (EnvelopeException | RepositoryException | JSONException | EncryptionException e) {
            return new SmartCredentialsResponse<>(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<ItemEnvelope> getItemSummaryById(@NonNull SmartCredentialsFilter smartCredentialsFilter) {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "getItemSummaryById");
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.STORAGE)) {
            String errorMessage = SmartCredentialsFeatureSet.STORAGE.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        try {
            SmartCredentialsApiResponse<ItemDomainModel> response = retrieveItemSummaryByUniqueIdAndType(smartCredentialsFilter.toItemDomainModel(mCoreController.getUserId()));
            if (response.isSuccessful()) {
                ItemDomainModel filteredItem = response.getData();
                return new SmartCredentialsResponse<>(de.telekom.smartcredentials.core.converters.ModelConverter.toItemEnvelope(filteredItem));
            } else {
                return new SmartCredentialsResponse<>(response.getError());
            }
        } catch (DomainModelException e) {
            return new SmartCredentialsResponse<>(new EnvelopeException(EnvelopeExceptionReason.map(e.getMessage())));
        } catch (EnvelopeException | RepositoryException | JSONException | EncryptionException e) {
            return new SmartCredentialsResponse<>(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<ItemEnvelope> getItemDetailsById(@NonNull SmartCredentialsFilter smartCredentialsFilter) {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "getItemDetailsById");
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.STORAGE)) {
            String errorMessage = SmartCredentialsFeatureSet.STORAGE.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        try {
            ItemDomainModel filteredItem = retrieveItemDetailsByUniqueIdAndType(smartCredentialsFilter.toItemDomainModel(mCoreController.getUserId()));

            if (filteredItem != null) {
                return new SmartCredentialsResponse<>(de.telekom.smartcredentials.core.converters.ModelConverter.toItemEnvelope(filteredItem));
            } else {
                return new SmartCredentialsResponse<>(new ItemNotFoundException());
            }

        } catch (DomainModelException e) {
            return new SmartCredentialsResponse<>(new EnvelopeException(EnvelopeExceptionReason.map(e.getMessage())));
        } catch (EnvelopeException | RepositoryException | JSONException | EncryptionException e) {
            return new SmartCredentialsResponse<>(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<Integer> putItem(ItemEnvelope itemEnvelope, ItemContext itemContext) {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "putItem");
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.STORAGE)) {
            String errorMessage = SmartCredentialsFeatureSet.STORAGE.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        try {
            ItemDomainModel itemDomainModel = de.telekom.smartcredentials.core.converters.ModelConverter.toItemDomainModel(itemEnvelope, itemContext, mCoreController.getUserId());
            int addedItemsCount = putItem(itemDomainModel).getData();
            return new SmartCredentialsResponse<>(addedItemsCount);
        } catch (DomainModelException e) {
            return new SmartCredentialsResponse<>(new EnvelopeException(EnvelopeExceptionReason.map(e.getMessage())));
        } catch (EnvelopeException | RepositoryException | EncryptionException e) {
            return new SmartCredentialsResponse<>(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<Integer> updateItem(ItemEnvelope itemEnvelope, ItemContext itemContext) {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "updateItem");
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.STORAGE)) {
            String errorMessage = SmartCredentialsFeatureSet.STORAGE.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        try {
            ItemDomainModel itemDomainModel = de.telekom.smartcredentials.core.converters.ModelConverter.toItemDomainModel(itemEnvelope, itemContext, mCoreController.getUserId());
            int affectedRows = updateItem(itemDomainModel);
            return new SmartCredentialsResponse<>(affectedRows);
        } catch (DomainModelException e) {
            return new SmartCredentialsResponse<>(new EnvelopeException(EnvelopeExceptionReason.map(e.getMessage())));
        } catch (EnvelopeException | EncryptionException ex) {
            return new SmartCredentialsResponse<>(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<Integer> deleteItem(@NonNull SmartCredentialsFilter smartCredentialsFilter) {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "deleteItem");
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.STORAGE)) {
            String errorMessage = SmartCredentialsFeatureSet.STORAGE.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        try {
            int count = mRepository.deleteItem(smartCredentialsFilter.toItemDomainModel(mCoreController.getUserId()));
            return new SmartCredentialsResponse<>(count);
        } catch (DomainModelException e) {
            return new SmartCredentialsResponse<>(new EnvelopeException(EnvelopeExceptionReason.map(e.getMessage())));
        } catch (EnvelopeException | RepositoryException ex) {
            return new SmartCredentialsResponse<>(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<Integer> deleteItemsByType(@NonNull SmartCredentialsFilter smartCredentialsFilter) {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "deleteItemsByType");
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.STORAGE)) {
            String errorMessage = SmartCredentialsFeatureSet.STORAGE.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        try {
            int count = mRepository.deleteItemsByType(smartCredentialsFilter.toItemDomainModel(mCoreController.getUserId()));
            return new SmartCredentialsResponse<>(count);
        } catch (DomainModelException e) {
            return new SmartCredentialsResponse<>(new EnvelopeException(EnvelopeExceptionReason.map(e.getMessage())));
        } catch (EnvelopeException | RepositoryException ex) {
            return new SmartCredentialsResponse<>(ex);
        }
    }

    @Override
    public void update() {
        clearStorage();
    }

    public void detach() {
        mCompositeDisposable.clear();
        mCoreController.detach(this);
    }

    private boolean isSensitive(ItemDomainMetadata metadata) {
        checkParamNotNull(metadata);
        return metadata.getContentType() == ContentType.SENSITIVE;
    }
}
