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

package de.telekom.smartcredentials.core.filter;

import androidx.annotation.NonNull;

import de.telekom.smartcredentials.core.itemdatamodel.ItemTypeFactory;

@SuppressWarnings("WeakerAccess")
public class SmartCredentialsFilterFactory {

    private SmartCredentialsFilterFactory() {
        // required empty constructor
    }

    /**
     * Create a SmartCredentialsFilter for getting summary or detailed information
     * for a non-sensitive data with specific id.
     *
     * @param id id of the voucher
     * @return SmartCredentialsFilter for filtering api results
     */
    @NonNull
    public static SmartCredentialsFilter createNonSensitiveItemFilter(String id, String type) {
        return new SmartCredentialsFilter(id, ItemTypeFactory.createNonSensitiveType(type));
    }

    /**
     * Create a SmartCredentialsFilter for retrieving all non-sensitive data.
     *
     * @return SmartCredentialsFilter for filtering api results
     */
    @NonNull
    public static SmartCredentialsFilter createNonSensitiveItemFilter(String type) {
        return new SmartCredentialsFilter(ItemTypeFactory.createNonSensitiveType(type));
    }

    /**
     * Create a SmartCredentialsFilter for retrieving sensitive data by id.
     *
     * @return SmartCredentialsFilter for filtering api results
     */
    @NonNull
    public static SmartCredentialsFilter createSensitiveItemFilter(String id, String type) {
        return new SmartCredentialsFilter(id, ItemTypeFactory.createSensitiveType(type));
    }

    /**
     * Create a SmartCredentialsFilter for retrieving all sensitive data.
     *
     * @return SmartCredentialsFilter for filtering api results
     */
    @NonNull
    public static SmartCredentialsFilter createSensitiveItemFilter(String type) {
        return new SmartCredentialsFilter(ItemTypeFactory.createSensitiveType(type));
    }
}
