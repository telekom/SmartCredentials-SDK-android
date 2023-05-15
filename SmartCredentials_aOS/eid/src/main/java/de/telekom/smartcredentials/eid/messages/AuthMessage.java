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
public class AuthMessage extends SmartEidMessage {

    public static final String AUTH_MESSAGE_CANCELED = "The process has been cancelled.";

    @SerializedName("url")
    @Expose
    private String mUrl;
    @SerializedName("result")
    @Expose
    private Result mResult;
    @SerializedName("error")
    @Expose
    private String mError;

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public Result getResult() {
        return mResult;
    }

    public void setResult(Result result) {
        this.mResult = result;
    }

    public String getError() {
        return mError;
    }

    public void setError(String error) {
        this.mError = error;
    }

    @NonNull
    @Override
    public String toString() {
        return "AuthMessage{" +
                "mUrl='" + mUrl + '\'' +
                ", mResult=" + mResult +
                ", mError='" + mError + '\'' +
                '}';
    }
}
