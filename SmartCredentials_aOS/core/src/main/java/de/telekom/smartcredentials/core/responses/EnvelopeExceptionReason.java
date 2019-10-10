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

package de.telekom.smartcredentials.core.responses;

import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.model.ModelValidator;
import de.telekom.smartcredentials.core.model.item.ItemDomainMetadata;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;

public enum EnvelopeExceptionReason {

    NO_METADATA_EXCEPTION_MSG(ModelValidator.NO_METADATA_EXCEPTION_MSG),
    NO_ITEM_TYPE_EXCEPTION_MSG("Envelope type cannot be null."),
    NO_IDENTIFIER_EXCEPTION_MSG("Envelope identifier cannot be null."),
    NO_DETAILS_EXCEPTION_MSG("Envelope details cannot be null."),
    UID_EXCEPTION_MESSAGE(CoreController.UID_EXCEPTION_MESSAGE),
    UNIQUE_KEY_TYPE_PREFIX_EXCEPTION_MESSAGE(ItemDomainMetadata.UNIQUE_KEY_TYPE_PREFIX_EXCEPTION_MESSAGE),
    UNIQUE_KEY_USER_ID_PREFIX_EXCEPTION_MESSAGE(ItemDomainMetadata.UNIQUE_KEY_USER_ID_PREFIX_EXCEPTION_MESSAGE),
    UNIQUE_KEY_EXCEPTION_MESSAGE(ItemDomainModel.UNIQUE_KEY_EXCEPTION_MESSAGE),
    NULL_PARAMETER_EXCEPTION_MSG(ModelValidator.NULL_PARAMETER_EXCEPTION_MSG),
    JSON_EXCEPTION("Envelope JSON property could not be created."),
    REQUEST_PARAMS_EXCEPTION("Request endpoint not set.");

    private final String mReason;

    EnvelopeExceptionReason(String reason) {
        mReason = reason;
    }

    public static EnvelopeExceptionReason map(String message) {
        if (message == null)
            return null;

        for (EnvelopeExceptionReason envelopeExceptionReason : EnvelopeExceptionReason.values()) {
            if (message.equals(envelopeExceptionReason.getReason())) {
                return envelopeExceptionReason;
            }
        }

        return null;
    }

    public String getReason() {
        return mReason;
    }
}
