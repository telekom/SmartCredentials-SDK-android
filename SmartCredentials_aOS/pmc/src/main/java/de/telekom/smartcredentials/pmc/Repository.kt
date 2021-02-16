/*
 * Copyright (c) 2021 Telekom Deutschland AG
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

package de.telekom.smartcredentials.pmc

import de.telekom.smartcredentials.core.api.StorageApi
import de.telekom.smartcredentials.core.context.ItemContextFactory
import de.telekom.smartcredentials.core.filter.SmartCredentialsFilterFactory
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelopeFactory
import org.json.JSONObject

/**
 * Created by Alex.Graur@endava.com at 2/15/2021
 */
class Repository(private val storageApi: StorageApi) {

    companion object {
        const val POLICY_SCHEMA_TYPE = "policy_schema_type";
    }

    fun insertPolicySchema(response: PolicySchemaResponse) {
        val itemContext = ItemContextFactory.createEncryptedSensitiveItemContext(POLICY_SCHEMA_TYPE)
        val identifierJson = JSONObject()
        val detailsJson = JSONObject()
        val itemEnvelope = ItemEnvelopeFactory.createItemEnvelope("1", identifierJson, detailsJson)
        storageApi.putItem(itemEnvelope, itemContext);
    }

    fun fetchPolicySchema() {
        val filter = SmartCredentialsFilterFactory.createSensitiveItemFilter("1", POLICY_SCHEMA_TYPE)
//        return storageApi.getItemDetailsById(filter).data.identifier
    }
}