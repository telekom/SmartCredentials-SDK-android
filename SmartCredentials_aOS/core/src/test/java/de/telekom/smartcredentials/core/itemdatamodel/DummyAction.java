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

package de.telekom.smartcredentials.core.itemdatamodel;

import android.content.Context;

import org.json.JSONObject;

import de.telekom.smartcredentials.core.actions.ExecutionCallback;
import de.telekom.smartcredentials.core.actions.SmartCredentialsAction;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;

/**
 * Created by Lucian Iacob on December 17, 2018.
 */
public class DummyAction extends SmartCredentialsAction {

    public DummyAction() {
        super();
    }

    public DummyAction(String id, String name, JSONObject data) {
        super(id, name, data);
    }

    @Override
    public void execute(Context context, ItemDomainModel itemDomainModel, ExecutionCallback callback) {

    }
}
