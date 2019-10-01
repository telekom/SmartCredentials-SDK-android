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

package de.telekom.smartcredentials.core.model;

import de.telekom.smartcredentials.core.model.item.ItemDomainMetadata;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;

import static de.telekom.smartcredentials.core.model.item.ItemDomainMetadata.KEY_SEPARATOR;

public class ModelValidator {

    public static final String NULL_PARAMETER_EXCEPTION_MSG = "Domain model cannot be null.";
    public static final String NO_METADATA_EXCEPTION_MSG = "Metadata cannot be null.";

    private ModelValidator() {
        // required empty constructor
    }

    public static void checkParamNotNull(ItemDomainModel itemDomainModel) {
        if (itemDomainModel == null) {
            throw new DomainModelException(NULL_PARAMETER_EXCEPTION_MSG);
        }
    }

    public static void checkParamNotNull(ItemDomainMetadata itemDomainMetadata) {
        if (itemDomainMetadata == null) {
            throw new DomainModelException(NO_METADATA_EXCEPTION_MSG);
        }
    }

    public static ItemDomainMetadata getValidatedMetadata(ItemDomainModel itemDomainModel) {
        checkParamNotNull(itemDomainModel);

        ItemDomainMetadata metadata = itemDomainModel.getMetadata();
        checkParamNotNull(metadata);
        return metadata;
    }

    public static String getValidatedUniqueKey(String prefix, String uid) {
        return prefix + KEY_SEPARATOR + uid;
    }
}
