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

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import de.telekom.smartcredentials.core.model.item.ItemDomainModel;

/**
 * Created by Lucian Iacob on December 10, 2018.
 */
public abstract class SmartCredentialsAction implements Serializable {

    private String action_id;
    private String module_name;
    private JSONObject action_data;

    protected SmartCredentialsAction() {
        // required empty constructor
    }

    /**
     * Constructor for setting the action properties
     *
     * @param id   the id for this action
     * @param name the name of the module which will handle the action
     * @param data a {@link JSONObject} containing all necessary information for executing this action
     */
    protected SmartCredentialsAction(String id, String name, JSONObject data) {
        action_id = id;
        module_name = name;
        action_data = data;
    }

    /**
     * Method that handles the execution of current action
     *
     * @param callback {@link ExecutionCallback} abstract class used to communicate with the caller
     */
    public abstract void execute(Context context, ItemDomainModel itemDomainModel, ExecutionCallback callback);

    public String getId() {
        return action_id;
    }

    public void setId(String id) {
        action_id = id;
    }

    public String getName() {
        return module_name;
    }

    public void setName(String name) {
        module_name = name;
    }

    public JSONObject getData() {
        return action_data;
    }

    public void setData(JSONObject data) {
        action_data = data;
    }

    private void readObject(ObjectInputStream aInputStream) throws IOException, JSONException {
        action_id = aInputStream.readUTF();
        module_name = aInputStream.readUTF();
        action_data = new JSONObject(aInputStream.readUTF());
    }

    private void writeObject(ObjectOutputStream aOutputStream) throws IOException {
        aOutputStream.writeUTF(action_id);
        aOutputStream.writeUTF(module_name);
        aOutputStream.writeUTF(action_data.toString());
    }
}
