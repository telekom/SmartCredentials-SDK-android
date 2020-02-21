/*
 * Copyright (c) 2020 Telekom Deutschland AG
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

package de.telekom.smartcredentials.storage.database.models.migration.fourfulltwo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alex.Graur@endava.com at 1/20/2020
 */
public class ObjectA {

    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("identifier")
    @Expose
    private String identifier;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("secured")
    @Expose
    private boolean secured;
    @SerializedName("user_id")
    @Expose
    private String userId;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSecured() {
        return secured;
    }

    public void setSecured(boolean secured) {
        this.secured = secured;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ObjectA{" +
                "uid='" + uid + '\'' +
                ", identifier='" + identifier + '\'' +
                ", type='" + type + '\'' +
                ", secured=" + secured +
                ", userId='" + userId + '\'' +
                '}';
    }
}
