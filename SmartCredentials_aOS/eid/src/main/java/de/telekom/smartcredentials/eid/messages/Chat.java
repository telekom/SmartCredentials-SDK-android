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

package de.telekom.smartcredentials.eid.messages;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex.Graur@endava.com at 11/11/2019
 */
public class Chat {

    @SerializedName("effective")
    @Expose
    private List<String> mEffective = new ArrayList<>();
    @SerializedName("optional")
    @Expose
    private List<String> mOptional = new ArrayList<>();
    @SerializedName("required")
    @Expose
    private List<String> mRequired = new ArrayList<>();

    public Chat() {

    }

    public List<String> getEffective() {
        return mEffective;
    }

    public void setEffective(List<String> mEffective) {
        this.mEffective = mEffective;
    }

    public List<String> getOptional() {
        return mOptional;
    }

    public void setOptional(List<String> mOptional) {
        this.mOptional = mOptional;
    }

    public List<String> getRequired() {
        return mRequired;
    }

    public void setRequired(List<String> mRequired) {
        this.mRequired = mRequired;
    }

    @NonNull
    @Override
    public String toString() {
        return "Chat{" +
                "mEffective=" + mEffective +
                ", mOptional=" + mOptional +
                ", mRequired=" + mRequired +
                '}';
    }
}
