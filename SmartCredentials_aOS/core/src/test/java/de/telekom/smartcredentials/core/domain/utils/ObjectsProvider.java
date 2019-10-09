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

package de.telekom.smartcredentials.core.domain.utils;

import org.json.JSONException;
import org.json.JSONObject;

import de.telekom.smartcredentials.core.model.item.ContentType;
import de.telekom.smartcredentials.core.model.item.ItemDomainData;
import de.telekom.smartcredentials.core.model.item.ItemDomainMetadata;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;

/**
 * Created by Lucian Iacob on November 07, 2018.
 */
public class ObjectsProvider {

    public static ItemDomainModel provideEncryptedItemDomainModel() {
        return new ItemDomainModel()
                .setMetadata(provideEncryptedMetadata())
                .setData(provideData());
    }

    public static ItemDomainModel provideUnEncryptedItemDomainModel() {
        return new ItemDomainModel()
                .setData(provideData())
                .setMetadata(provideUnEncryptedMetadata());
    }

    public static JSONObject provideJsonObjectWithPair(String key, String value) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(key, value);
        return jsonObject;
    }

    private static ItemDomainMetadata provideUnEncryptedMetadata() {
        return new ItemDomainMetadata(false)
                .setContentType(ContentType.NON_SENSITIVE)
                .setItemType("voucher");
    }

    private static ItemDomainData provideData() {
        return new ItemDomainData()
                .setPrivateData("private mData to be stored")
                .setIdentifier("model identifier");
    }

    private static ItemDomainMetadata provideEncryptedMetadata() {
        return new ItemDomainMetadata(true)
                .setContentType(ContentType.NON_SENSITIVE)
                .setItemType("voucher");
    }
}
