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

package de.telekom.smartcredentials.core;

import java.util.ArrayList;
import java.util.List;

import de.telekom.smartcredentials.core.model.item.ContentType;
import de.telekom.smartcredentials.core.model.item.ItemDomainAction;
import de.telekom.smartcredentials.core.model.item.ItemDomainData;
import de.telekom.smartcredentials.core.model.item.ItemDomainMetadata;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;

public class DomainModelGenerator {

    public static final String ID = "12345";
    public static final String GENERAL_APP_NAME = "MyApp";
    public static final String USER_ITEM_TYPE = "SENSITIVE";
    public static final String GENERAL_ITEM_TYPE = "NON_SENSITIVE";
    public static final String IDENTIFIER = "{\"random_key\":\"identifier\"}";
    public static final String PRIVATE_DATA = "{\"random_key\":\"private data\"}";
    public static final boolean IS_ENCRYPTED = false;
    public static final String CLASS_NAME = "action_class";
    public static final String CLASS_NAME_2 = "second_class";

    public static ItemDomainModel generateModelWithUnConvertibleJson() {
        return new ItemDomainModel()
                .setData(new ItemDomainData()
                        .setIdentifier("Some unconvertible json"))
                .setMetadata(new ItemDomainMetadata(true));
    }

    public static List<ItemDomainModel> generateModelList() {
        List<ItemDomainModel> itemDomainModelList = new ArrayList<>();
        itemDomainModelList.add(generateModelWithUnConvertibleJson());
        return itemDomainModelList;
    }

    public static ItemDomainModel generateEmptyModel() {
        return new ItemDomainModel();
    }

    public static ItemDomainModel generateModelWithEmptyData() {
        return new ItemDomainModel().setMetadata(new ItemDomainMetadata(IS_ENCRYPTED));
    }

    public static ItemDomainModel generateModelWithEmptyIdentifier(boolean isEncrypted) {
        return new ItemDomainModel().setMetadata(new ItemDomainMetadata(isEncrypted)).setData(new ItemDomainData());
    }

    public static ItemDomainModel generateModel() {
        ItemDomainData data = new ItemDomainData()
                .setIdentifier(IDENTIFIER)
                .setPrivateData(PRIVATE_DATA);

        ItemDomainMetadata metadata = new ItemDomainMetadata(IS_ENCRYPTED)
                .setContentType(ContentType.SENSITIVE)
                .setActionList(getActionList())
                .setItemType(USER_ITEM_TYPE);

        return new ItemDomainModel()
                .setId(ID)
                .setMetadata(metadata)
                .setData(data);
    }

    private static List<ItemDomainAction> getActionList() {
        List<ItemDomainAction> itemDomainActions = new ArrayList<>();

        itemDomainActions.add(new ItemDomainAction().setClassName(CLASS_NAME));
        itemDomainActions.add(new ItemDomainAction().setClassName(CLASS_NAME_2));

        return itemDomainActions;
    }
}
