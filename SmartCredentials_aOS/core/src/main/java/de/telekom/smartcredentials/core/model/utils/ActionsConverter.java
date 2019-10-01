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

package de.telekom.smartcredentials.core.model.utils;

import org.json.JSONException;
import org.json.JSONObject;

import de.telekom.smartcredentials.core.actions.SmartCredentialsAction;
import de.telekom.smartcredentials.core.model.item.ItemDomainAction;

/**
 * Created by Lucian Iacob on January 18, 2019.
 */
public class ActionsConverter {

    public static SmartCredentialsAction toSmartCredentialsAction(ItemDomainAction domainAction)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, JSONException {
        String className = domainAction.getClassName();
        Class<?> actionClass = Class.forName(className);

        SmartCredentialsAction smartCredentialsAction = (SmartCredentialsAction) actionClass.newInstance();
        smartCredentialsAction.setId(domainAction.getId());
        smartCredentialsAction.setName(domainAction.getName());

        if (domainAction.getData() != null) {
            JSONObject data = new JSONObject(domainAction.getData());
            smartCredentialsAction.setData(data);
        }

        return smartCredentialsAction;
    }

}
