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

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alex.Graur@endava.com at 11/11/2019
 */
public class Result {

    @SerializedName("major")
    @Expose
    private String mMajor;
    @SerializedName("minor")
    @Expose
    private String mMinor;
    @SerializedName("language")
    @Expose
    private String mLanguage;
    @SerializedName("description")
    @Expose
    private String mDescription;
    @SerializedName("message")
    @Expose
    private String mMessage;
    @SerializedName("reason")
    @Expose
    private String mReason;

    public String getMajor() {
        return mMajor;
    }

    public void setMajor(String major) {
        this.mMajor = major;
    }

    public String getMinor() {
        return mMinor;
    }

    public void setMinor(String mMinor) {
        this.mMinor = mMinor;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setmLanguage(String language) {
        this.mLanguage = language;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }

    public String getReason() {
        return mReason;
    }

    public void setReason(String reason) {
        this.mReason = reason;
    }

    @NonNull
    @Override
    public String toString() {
        return "Result{" +
                "mMajor='" + mMajor + '\'' +
                ", mMinor='" + mMinor + '\'' +
                ", mLanguage='" + mLanguage + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mMessage='" + mMessage + '\'' +
                ", mReason='" + mReason + '\'' +
                '}';
    }
}
