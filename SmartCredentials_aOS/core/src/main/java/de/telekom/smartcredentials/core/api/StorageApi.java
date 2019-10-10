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

package de.telekom.smartcredentials.core.api;

import android.support.annotation.NonNull;

import java.util.List;

import de.telekom.smartcredentials.core.context.ItemContext;
import de.telekom.smartcredentials.core.exceptions.EncryptionException;
import de.telekom.smartcredentials.core.filter.SmartCredentialsFilter;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.core.storage.TokenRequest;

/**
 * Created by Lucian Iacob on November 08, 2018.
 */
public interface StorageApi {

    /**
     * Retrieves all items based on the filtering information provided.
     *
     * @param smartCredentialsFilter - filter contains filtering information
     * @return SmartCredentialsApiResponse with the found list of items if request returns successful or with an error
     */
    @SuppressWarnings("unused")
    SmartCredentialsApiResponse<List<ItemEnvelope>> getAllItemsByItemType(@NonNull SmartCredentialsFilter smartCredentialsFilter);

    /**
     * Retrieves an items's summary info based on the filtering information provided.
     *
     * @param smartCredentialsFilter - filter contains filtering information
     * @return SmartCredentialsApiResponse with the found item if request returns successful or with an error
     */
    @SuppressWarnings("unused")
    SmartCredentialsApiResponse<ItemEnvelope> getItemSummaryById(@NonNull SmartCredentialsFilter smartCredentialsFilter);

    /**
     * Retrieves an items's detailed info (summary and private info) based on the filtering information provided.
     *
     * @param smartCredentialsFilter - filter contains filtering information
     * @return SmartCredentialsApiResponse with the found item if request returns successful or with an error
     */
    @SuppressWarnings("unused")
    SmartCredentialsApiResponse<ItemEnvelope> getItemDetailsById(@NonNull SmartCredentialsFilter smartCredentialsFilter);

    /**
     * Saves an ItemEnvelope according to details from ItemContext.
     *
     * @param itemEnvelope item to be saved
     * @param itemContext  details containing storage and encryption
     * @return SmartCredentialsApiResponse specifying the count of inserted items or returns an error if not
     */
    @SuppressWarnings("unused")
    SmartCredentialsApiResponse<Integer> putItem(ItemEnvelope itemEnvelope, ItemContext itemContext);

    /**
     * Saves an {@link ItemDomainModel} that contains a {@link TokenRequest}.
     *
     * @param itemDomainModel to be saved
     * @param tokenRequest    corresponding to the item
     * @return an integer representing the number of rows saved
     * @throws EncryptionException is the exception thrown is something went wrong in the process
     *                             of saving the item
     */
    @SuppressWarnings("UnusedReturnValue")
    SmartCredentialsApiResponse<Integer> putItem(ItemDomainModel itemDomainModel, TokenRequest tokenRequest) throws EncryptionException;

    /**
     * Saves an {@link ItemDomainModel}.
     *
     * @param itemDomainModel to be saved
     * @return an integer representing the number of rows saved
     * @throws EncryptionException is the exception thrown is something went wrong in the process
     *                             *                             of saving the item
     */
    SmartCredentialsApiResponse<Integer> putItem(ItemDomainModel itemDomainModel) throws EncryptionException;

    /**
     * Updates an ItemEnvelope based on its id
     *
     * @param itemEnvelope item to be updated
     * @param itemContext  details containing storing specifications
     * @return {@link SmartCredentialsApiResponse} containing an {@link Integer} specifying
     * the number of affected rows or an error if something wrong happened
     */
    SmartCredentialsApiResponse<Integer> updateItem(ItemEnvelope itemEnvelope, ItemContext itemContext);

    /**
     * Deletes an item based by information provided.
     *
     * @param smartCredentialsFilter - filter contains filtering information
     * @return SmartCredentialsApiResponse specifying the count of deleted items or returns an error if not
     */
    SmartCredentialsApiResponse<Integer> deleteItem(@NonNull SmartCredentialsFilter smartCredentialsFilter);

    /**
     * Deletes items based on item type information provided.
     *
     * @param smartCredentialsFilter - filter contains filtering information
     * @return SmartCredentialsApiResponse specifying the count of deleted items or returns an error if not
     */
    SmartCredentialsApiResponse<Integer> deleteItemsByType(@NonNull SmartCredentialsFilter smartCredentialsFilter);

    /**
     * Retrieves a {@link TokenRequest} corresponding to an {@link ItemDomainModel}.
     *
     * @param itemDomainModel corresponding to the token request
     * @return an instance of {@link TokenRequest}
     * @throws EncryptionException is the exception thrown is something went wrong in the process
     *                             of retrieving the token request
     */
    SmartCredentialsApiResponse<TokenRequest> retrieveTokenRequest(ItemDomainModel itemDomainModel) throws EncryptionException;

    /**
     * Retrieves an item summary by its id and type.
     *
     * @param itemDomainModel holding the id and type
     * @return an instance of {@link ItemDomainModel}
     * @throws EncryptionException is the exception thrown is something went wrong in the process
     *                             of retrieving the item summary
     */
    SmartCredentialsApiResponse<ItemDomainModel> retrieveItemSummaryByUniqueIdAndType(ItemDomainModel itemDomainModel) throws EncryptionException;
}
