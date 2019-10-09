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

package de.telekom.smartcredentials.core.model.item;

/**
 * Created by Lucian Iacob on December 10, 2018.
 */
public class ItemDomainAction {

    private String class_name;
    private String action_id;
    private String module_name;
    private String action_data;

    public ItemDomainAction setActionId(String actionId) {
        action_id = actionId;
        return this;
    }

    public ItemDomainAction setActionName(String actionName) {
        module_name = actionName;
        return this;
    }

    public ItemDomainAction setActionData(String actionData) {
        action_data = actionData;
        return this;
    }

    public String getClassName() {
        return class_name;
    }

    public ItemDomainAction setClassName(String className) {
        class_name = className;
        return this;
    }

    public String getId() {
        return action_id;
    }

    public String getName() {
        return module_name;
    }

    public String getData() {
        return action_data;
    }
}
