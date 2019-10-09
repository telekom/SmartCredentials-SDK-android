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

package de.telekom.smartcredentials.core.actions;

import java.util.List;

import de.telekom.smartcredentials.core.model.item.ItemDomainAction;

/**
 * Created by Lucian Iacob on January 16, 2019.
 */
public interface ActionSelector {

    String ACTION_NOT_PROCESSED = "Action could not be processed.";

    SmartCredentialsAction select(String actionId, List<ItemDomainAction> actionList);

}
