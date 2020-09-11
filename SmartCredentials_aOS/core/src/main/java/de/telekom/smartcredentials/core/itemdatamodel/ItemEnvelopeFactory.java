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

package de.telekom.smartcredentials.core.itemdatamodel;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

import de.telekom.smartcredentials.core.actions.SmartCredentialsAction;

@SuppressWarnings("WeakerAccess")
public class ItemEnvelopeFactory {

    private static final String ACTION_LIST_NULL = "Actions list cannot be null.";

    private ItemEnvelopeFactory() {
        // required empty constructor
    }

    /**
     * Creates an {@link ItemEnvelope}.
     *
     * @param itemId     item id
     * @param identifier item identifier
     * @param details    item details
     * @param actions    list of actions associated with this item
     * @param isAutoLockEnabled the state of auto lock
     * @param isLocked the state of lock
     * @return {@link ItemEnvelope} - created item
     * @since 4.2.0
     */
    @SuppressWarnings("unused")
    public static ItemEnvelope createItemEnvelope(@NonNull String itemId,
                                                  @NonNull JSONObject identifier,
                                                  @NonNull JSONObject details,
                                                  @NonNull List<SmartCredentialsAction> actions,
                                                  @NonNull boolean isAutoLockEnabled,
                                                  @NonNull boolean isLocked) {
        Objects.requireNonNull(actions, ACTION_LIST_NULL);
        ItemPrivateData itemPrivateData = new ItemPrivateData(details);
        ItemMetadata itemMetadata = new ItemMetadata();
        itemMetadata.setPrivateData(itemPrivateData);
        itemMetadata.setAutoLockState(isAutoLockEnabled);
        itemMetadata.setLockState(isLocked);
        return new ItemEnvelope()
                .setItemId(itemId)
                .setIdentifier(identifier)
                .setItemMetadata(itemMetadata);
    }

    /**
     * Creates an {@link ItemEnvelope}.
     *
     * @param itemId     item id
     * @param identifier item identifier
     * @param details    item details
     * @param actions    list of actions associated with this item
     * @return {@link ItemEnvelope} - created item
     * @since 4.2.0
     */
    public static ItemEnvelope createItemEnvelope(@NonNull String itemId,
                                                  @NonNull JSONObject identifier,
                                                  @NonNull JSONObject details,
                                                  @NonNull List<SmartCredentialsAction> actions) {
        Objects.requireNonNull(actions, ACTION_LIST_NULL);
        ItemEnvelope itemEnvelope = createItemEnvelope(itemId, identifier, details);
        itemEnvelope.getItemMetadata().setActionList(actions);
        return itemEnvelope;
    }

    /**
     * Creates an {@link ItemEnvelope}.
     *
     * @param itemId     item id
     * @param identifier item identifier
     * @param details    item details
     * @param isAutoLockEnabled the state of auto lock
     * @param isLocked the state of lock
     * @return {@link ItemEnvelope} - created item
     * @since 4.2.0
     */
    @SuppressWarnings("unused")
    public static ItemEnvelope createItemEnvelope(@NonNull String itemId,
                                                  @NonNull JSONObject identifier,
                                                  @NonNull JSONObject details,
                                                  @NonNull boolean isAutoLockEnabled,
                                                  @NonNull boolean isLocked) {
        ItemMetadata itemMetadata = new ItemMetadata();
        ItemPrivateData itemPrivateData = new ItemPrivateData(details);
        itemMetadata.setPrivateData(itemPrivateData);
        itemMetadata.setAutoLockState(isAutoLockEnabled);
        itemMetadata.setLockState(isLocked);
        return new ItemEnvelope()
                .setItemId(itemId)
                .setIdentifier(identifier)
                .setItemMetadata(itemMetadata);
    }

    /**
     * Creates an {@link ItemEnvelope}.
     *
     * @param itemId     item id
     * @param identifier item identifier
     * @param details    item details
     * @return {@link ItemEnvelope} - created item
     */
    public static ItemEnvelope createItemEnvelope(@NonNull String itemId,
                                                  @NonNull JSONObject identifier,
                                                  @NonNull JSONObject details) {
        ItemPrivateData itemPrivateData = new ItemPrivateData(details);
        ItemMetadata itemMetadata = new ItemMetadata();
        itemMetadata.setPrivateData(itemPrivateData);
        return new ItemEnvelope()
                .setItemId(itemId)
                .setIdentifier(identifier)
                .setItemMetadata(itemMetadata);
    }

    /**
     * Creates an {@link ItemEnvelope}.
     *
     * @param itemId     item id
     * @param identifier item identifier
     * @return {@link ItemEnvelope} - created item
     */
    public static ItemEnvelope createItemEnvelope(@NonNull String itemId, @NonNull JSONObject identifier) {
        ItemMetadata itemMetadata = new ItemMetadata();
        return new ItemEnvelope()
                .setItemId(itemId)
                .setIdentifier(identifier)
                .setItemMetadata(itemMetadata);
    }

    /**
     * Creates an {@link ItemEnvelope}.
     * Don't use this method when performing CRUD operations on database.
     *
     * @param identifier item identifier
     * @return {@link ItemEnvelope} - created item
     * @deprecated as of version 4.1.0. Use {@link #createItemEnvelope(String, JSONObject)} or {@link #createItemEnvelope(String, JSONObject, JSONObject)} instead
     */
    public static ItemEnvelope createItemEnvelope(@NonNull JSONObject identifier) {
        ItemMetadata itemMetadata = new ItemMetadata();
        return new ItemEnvelope()
                .setIdentifier(identifier)
                .setItemMetadata(itemMetadata);
    }

    /**
     * Creates an {@link ItemEnvelope} from its JSON representation.
     *
     * @param json JSON representation of item
     * @return {@link ItemEnvelope} - created item
     * @throws JSONException when a required mapping is missing or the type is wrong
     * @see JSONObject
     * @since 4.3.0
     */
    public static ItemEnvelope from(@NonNull String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        SmartCredentialsJSONParser parser = new SmartCredentialsJSONParser(jsonObject);
        return parser.parse();
    }

}
