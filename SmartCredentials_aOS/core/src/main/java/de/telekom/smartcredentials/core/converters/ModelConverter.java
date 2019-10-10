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

package de.telekom.smartcredentials.core.converters;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.telekom.smartcredentials.core.context.ItemContext;
import de.telekom.smartcredentials.core.actions.SmartCredentialsAction;
import de.telekom.smartcredentials.core.model.item.ContentType;
import de.telekom.smartcredentials.core.model.item.ItemDomainAction;
import de.telekom.smartcredentials.core.model.item.ItemDomainData;
import de.telekom.smartcredentials.core.model.item.ItemDomainMetadata;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.core.model.utils.ActionsConverter;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import de.telekom.smartcredentials.core.itemdatamodel.ItemMetadata;
import de.telekom.smartcredentials.core.itemdatamodel.ItemPrivateData;
import de.telekom.smartcredentials.core.itemdatamodel.ItemType;
import de.telekom.smartcredentials.core.itemdatamodel.ItemTypeFactory;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.responses.EnvelopeException;
import de.telekom.smartcredentials.core.responses.EnvelopeExceptionReason;

public class ModelConverter {

    private static final String TAG = "ModelConverter";

    /**
     * Converts an {@link ItemDomainModel} to an {@link ItemEnvelope}.
     */
    public static ItemEnvelope toItemEnvelope(ItemDomainModel itemDomainModel) throws JSONException, EnvelopeException {
        if (itemDomainModel == null) {
            return null;
        }
        ItemEnvelope itemEnvelope = new ItemEnvelope();

        ItemDomainMetadata domainMetadata = itemDomainModel.getMetadata();
        itemEnvelope.setItemId(itemDomainModel.getUid());
        if (domainMetadata != null) {
            itemEnvelope.setUserId(domainMetadata.getUserId());
            if (itemDomainModel.getData() != null && itemDomainModel.getData().getIdentifier() != null) {
                itemEnvelope.setIdentifier(new JSONObject(itemDomainModel.getData().getIdentifier()));
            }

            ItemType itemType = domainMetadata.getContentType() == ContentType.NON_SENSITIVE
                    ? ItemTypeFactory.createNonSensitiveType(domainMetadata.getItemType())
                    : ItemTypeFactory.createSensitiveType(domainMetadata.getItemType());
            itemEnvelope.setItemType(itemType);

            boolean isDataEncrypted = domainMetadata.isDataEncrypted();
            ItemMetadata metadata = new ItemMetadata(isDataEncrypted);
            metadata.setActionList(toSmartCredentialsActionList(domainMetadata.getActionList()));
            metadata.setAutoLockState(domainMetadata.isAutoLockEnabled());
            metadata.setLockState(domainMetadata.isLocked());

            if (itemDomainModel.getData() != null && itemDomainModel.getData().getPrivateData() != null) {
                JSONObject privateData = new JSONObject(itemDomainModel.getData().getPrivateData());
                ItemPrivateData itemPrivateData = new ItemPrivateData(privateData);
                metadata.setPrivateData(itemPrivateData);
            }
            itemEnvelope.setItemMetadata(metadata);
        }

        return itemEnvelope;
    }

    /**
     * Converts an {@link ItemEnvelope} to an {@link ItemDomainModel} with unencrypted metadata.
     */
    public static ItemDomainModel toItemDomainModel(ItemEnvelope itemEnvelope) {
        checkNotNull(itemEnvelope.getItemMetadata(), EnvelopeExceptionReason.NO_METADATA_EXCEPTION_MSG);

        ItemDomainMetadata metadata = new ItemDomainMetadata(false);
        metadata.setItemType(itemEnvelope.getItemType().getDesc())
                .setContentType(itemEnvelope.getItemType().getContentType())
                .setActionList(toDomainActionList(itemEnvelope.getActionList()))
                .setAutoLockState(itemEnvelope.isAutoLockEnabled())
                .setLockState(itemEnvelope.isLocked());

        ItemDomainData data = new ItemDomainData();
        data.setIdentifier(itemEnvelope.getIdentifier() != null
                ? itemEnvelope.getIdentifier().toString() : null);
        data.setPrivateData(itemEnvelope.getDetails() != null
                ? itemEnvelope.getDetails().toString() : null);

        return new ItemDomainModel()
                .setId(itemEnvelope.getItemId())
                .setMetadata(metadata)
                .setData(data);
    }

    /**
     * Converts an {@link ItemEnvelope} to an {@link ItemDomainModel} with encrypted metadata.
     */
    public static ItemDomainModel toItemDomainModel(ItemEnvelope itemEnvelope, ItemContext itemContext, String userId) {
        checkNotNull(itemEnvelope.getItemMetadata(), EnvelopeExceptionReason.NO_METADATA_EXCEPTION_MSG);
        checkNotNull(itemContext.getItemType(), EnvelopeExceptionReason.NO_ITEM_TYPE_EXCEPTION_MSG);

        ItemDomainMetadata metadata = new ItemDomainMetadata(itemContext.isEncrypted());
        metadata.setUserId(userId)
                .setItemType(itemContext.getItemType().getDesc())
                .setContentType(itemContext.getItemType().getContentType())
                .setActionList(toDomainActionList(itemEnvelope.getActionList()))
                .setAutoLockState(itemEnvelope.isAutoLockEnabled())
                .setLockState(itemEnvelope.isLocked());

        ItemDomainData data = new ItemDomainData();
        data.setIdentifier(itemEnvelope.getIdentifier() != null
                ? itemEnvelope.getIdentifier().toString() : null);
        data.setPrivateData(itemEnvelope.getDetails() != null
                ? itemEnvelope.getDetails().toString() : null);

        return new ItemDomainModel()
                .setId(itemEnvelope.getItemId())
                .setMetadata(metadata)
                .setData(data);
    }

    /**
     * Converts a list of {@link ItemDomainModel} to a list of {@link ItemEnvelope}.
     */
    public static List<ItemEnvelope> toItemEnvelopeList(List<ItemDomainModel> itemDomainModelList) throws JSONException {
        if (itemDomainModelList == null) {
            return null;
        }

        List<ItemEnvelope> itemEnvelopeList = new ArrayList<>();
        for (ItemDomainModel itemDomainModel : itemDomainModelList) {
            itemEnvelopeList.add(toItemEnvelope(itemDomainModel));
        }

        return itemEnvelopeList;
    }

    /**
     * Converts a list of {@link ItemDomainAction} objects to a list of {@link SmartCredentialsAction}.
     */
    private static List<SmartCredentialsAction> toSmartCredentialsActionList(List<ItemDomainAction> actionList) {
        if (actionList == null) {
            return new ArrayList<>();
        }

        List<SmartCredentialsAction> apiActions = new ArrayList<>();

        for (ItemDomainAction domainAction : actionList) {
            try {
                SmartCredentialsAction apiAction = ActionsConverter.toSmartCredentialsAction(domainAction);
                apiActions.add(apiAction);
            } catch (ClassNotFoundException | JSONException | IllegalAccessException | InstantiationException ex) {
                ApiLoggerResolver.logError(TAG, ex.getMessage());
            }
        }

        return apiActions;
    }

    /**
     * Converts a list of {@link SmartCredentialsAction} to a list of {@link ItemDomainAction}.
     */
    private static List<ItemDomainAction> toDomainActionList(List<SmartCredentialsAction> apiActions) {
        List<ItemDomainAction> domainActions = new ArrayList<>();

        for (SmartCredentialsAction action : apiActions) {
            ItemDomainAction itemDomainAction = toDomainAction(action);
            domainActions.add(itemDomainAction);
        }

        return domainActions;
    }

    /**
     * Converts an {@link SmartCredentialsAction} to an {@link ItemDomainAction}.
     */
    private static ItemDomainAction toDomainAction(SmartCredentialsAction action) {
        return new ItemDomainAction()
                .setClassName(action.getClass().getName())
                .setActionId(action.getId())
                .setActionName(action.getName())
                .setActionData(action.getData() == null ? null : action.getData().toString());
    }

    private static void checkNotNull(Object o, EnvelopeExceptionReason msg) {
        if (o == null) {
            throw new EnvelopeException(msg);
        }
    }
}
