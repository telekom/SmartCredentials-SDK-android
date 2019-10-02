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

package de.telekom.smartcredentials.storage.database.converters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import de.telekom.smartcredentials.core.model.item.ItemDomainAction;

/**
 * Created by Lucian Iacob on December 10, 2018.
 */
public class ActionListTypeConverter {

    @TypeConverter
    public static List<ItemDomainAction> serializedListToListOfActions(String serializedActions) {
        if (serializedActions == null) {
            return Collections.emptyList();
        }

        Type listOfActionsObject = new TypeToken<List<ItemDomainAction>>() {
        }.getType();


        return new Gson().fromJson(serializedActions, listOfActionsObject);
    }

    @TypeConverter
    public static String listOfActionsToString(List<ItemDomainAction> actionList) {
        if (actionList == null) {
            return null;
        }

        Type typeOfActions = new TypeToken<List<ItemDomainAction>>() {
        }.getType();

        return new Gson().toJson(actionList, typeOfActions);
    }

}
