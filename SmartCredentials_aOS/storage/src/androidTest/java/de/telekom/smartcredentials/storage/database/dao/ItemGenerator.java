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

package de.telekom.smartcredentials.storage.database.dao;

import java.util.ArrayList;
import java.util.List;

import de.telekom.smartcredentials.core.model.item.ItemDomainAction;
import de.telekom.smartcredentials.storage.database.models.Item;
import de.telekom.smartcredentials.storage.database.models.ItemPrivateData;

public class ItemGenerator {

    public static final String mId1 = "12345";
    public static final String mUserId1 = "9898";
    static final String mRandomId = "54321";
    static final String mRandomType = "INEXISTENT_TYPE";
    private static final String mId2 = "11111";
    private static final String mIdentifier1 = "IDENTIFIER";
    private static final String mInfo1 = "ITEM_PRIVATE_INFO";
    private static final String mInfo2 = "OTHER_ITEM_PRIVATE_INFO";
    private static final String mType1 = "ITEM_TYPE";
    private static final String mType2 = "ITEM_TYPE_OTHER";
    private static final String mUserId2 = "8787";
    private static final List<ItemDomainAction> mActionList;

    public static Item generateItem() {
        Item item = new Item();
        item.setUid(mId1);
        item.setActionList(mActionList);
        item.setIdentifier(mIdentifier1);
        item.setType(mType1);
        item.setUserId(mUserId1);
        item.setSecuredData(true);
        item.setAutoLockEnabled(false);
        item.setLocked(false);
        return item;
    }

    static {
        mActionList = new ArrayList<>();
        ItemDomainAction itemDomainAction =
                new ItemDomainAction().setClassName(mRandomType).setActionId(mRandomId);
        mActionList.add(itemDomainAction);
    }

    static Item generateOtherItem() {
        Item item = new Item();
        item.setUid(mId2);
        item.setType(mType2);
        item.setUserId(mUserId2);
        return item;
    }

    static ItemPrivateData generateItemPrivateData() {
        ItemPrivateData privateItem = new ItemPrivateData(mId1);
        privateItem.setInfo(mInfo1);

        return privateItem;
    }

    static ItemPrivateData generateOtherItemPrivateData() {
        ItemPrivateData privateItem = new ItemPrivateData(mId2);
        privateItem.setInfo(mInfo2);

        return privateItem;
    }
}
