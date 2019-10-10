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

package de.telekom.smartcredentials.storage.domain.converters;


import com.google.gson.Gson;

import de.telekom.smartcredentials.core.model.item.ItemDomainData;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.core.storage.TokenRequest;
import de.telekom.smartcredentials.core.strategies.EncryptionStrategy;
import de.telekom.smartcredentials.storage.domain.model.token.SmartCredentialsTokenRequest;

public class ModelConverter {

    public static SmartCredentialsTokenRequest toTokenRequest(ItemDomainModel itemDomainModel, Gson gson, EncryptionStrategy encryptionStrategy, String alias) {
        SmartCredentialsTokenRequest tokenRequest = new SmartCredentialsTokenRequest(gson,
                itemDomainModel.getMetadata().getItemType(),
                encryptionStrategy,
                alias);
        if (itemDomainModel.getData() != null) {
            tokenRequest.setEncryptedModel(itemDomainModel.getData().getPrivateData());
        }
        return tokenRequest;
    }

    public static ItemDomainModel toItemDomainModel(ItemDomainModel model, TokenRequest tokenRequest) {
        ItemDomainData itemDomainData = model.getData();
        itemDomainData.setPrivateData(tokenRequest.getEncryptedModel());
        model.setData(itemDomainData);

        return model;
    }
}
