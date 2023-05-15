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

package de.telekom.smartcredentials.eid.messages;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Alex.Graur@endava.com at 2/10/2020
 */
public class ApiLevelMessage extends SmartEidMessage {

    @SerializedName("msg")
    @Expose
    private String mMsg;
    @SerializedName("error")
    @Expose
    private String mError;
    @SerializedName("available")
    @Expose
    private List<Integer> mAvailable;
    @SerializedName("current")
    @Expose
    private int mCurrent;

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        this.mMsg = msg;
    }

    public String getError() {
        return mError;
    }

    public void setError(String error) {
        this.mError = error;
    }

    public List<Integer> getAvailable() {
        return mAvailable;
    }

    public void setAvailable(List<Integer> available) {
        this.mAvailable = available;
    }

    public int getCurrent() {
        return mCurrent;
    }

    public void setCurrent(int current) {
        this.mCurrent = current;
    }

    @NonNull
    @Override
    public String toString() {
        return "ApiLevelMessage{" +
                "mMsg='" + mMsg + '\'' +
                ", mError='" + mError + '\'' +
                ", mAvailable=" + mAvailable +
                ", mCurrent=" + mCurrent +
                '}';
    }
}
