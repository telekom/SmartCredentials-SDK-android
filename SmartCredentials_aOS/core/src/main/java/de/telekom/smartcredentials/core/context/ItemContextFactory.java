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

package de.telekom.smartcredentials.core.context;

import android.support.annotation.NonNull;

import de.telekom.smartcredentials.core.itemdatamodel.ItemType;
import de.telekom.smartcredentials.core.itemdatamodel.ItemTypeFactory;

@SuppressWarnings("WeakerAccess")
public class ItemContextFactory {

    private ItemContextFactory() {
        // required empty constructor
    }

    /**
     * Creates an {@link ItemContext} for storing an encrypted item of given type in sensitive storage.
     *
     * @param type type of the item to be stored
     * @return {@link ItemContext} containing necessary information for storing an item
     */
    @NonNull
    public static ItemContext createEncryptedSensitiveItemContext(String type) {
        ItemType itemType = ItemTypeFactory.createSensitiveType(type);
        return new ItemContext(itemType, true);
    }

    /**
     * Creates an {@link ItemContext} for storing a non-encrypted item of given type in sensitive storage.
     *
     * @param type type of the item to be stored
     * @return {@link ItemContext} containing necessary information for storing an item
     */
    @NonNull
    public static ItemContext createNonEncryptedSensitiveItemContext(String type) {
        ItemType itemType = ItemTypeFactory.createSensitiveType(type);
        return new ItemContext(itemType, false);
    }

    /**
     * Creates an {@link ItemContext} for storing an encrypted item of given type in non-sensitive storage.
     *
     * @param type type of the item to be stored
     * @return {@link ItemContext} containing necessary information for storing an item
     */
    @NonNull
    public static ItemContext createEncryptedNonSensitiveItemContext(String type) {
        ItemType itemType = ItemTypeFactory.createNonSensitiveType(type);
        return new ItemContext(itemType, true);
    }

    /**
     * Creates an {@link ItemContext} for storing an non-encrypted item of given type in non-sensitive storage.
     *
     * @param type type of the item to be stored
     * @return {@link ItemContext} containing necessary information for storing an item
     */
    @NonNull
    public static ItemContext createNonEncryptedNonSensitiveItemContext(String type) {
        ItemType itemType = ItemTypeFactory.createNonSensitiveType(type);
        return new ItemContext(itemType, false);
    }

    public static ItemContext getItemContext(boolean isSensitive, boolean isEncrypted, String type){
        ItemType itemType;

        if(isSensitive){
            itemType = ItemTypeFactory.createSensitiveType(type);
        } else {
            itemType = ItemTypeFactory.createNonSensitiveType(type);
        }

        if(isEncrypted){
            return new ItemContext(itemType, true);
        } else {
            return new ItemContext(itemType, false);
        }
    }
}
