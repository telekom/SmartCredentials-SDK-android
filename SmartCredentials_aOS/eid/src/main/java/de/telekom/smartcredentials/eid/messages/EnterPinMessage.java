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

/**
 * Created by Alex.Graur@endava.com at 2/10/2020
 */
public class EnterPinMessage extends SmartEidMessage {

    @SerializedName("error")
    @Expose
    private String mError;
    @SerializedName("reader")
    @Expose
    private Reader mReader;

    public Reader getReader() {
        return mReader;
    }

    public void setReader(Reader reader) {
        this.mReader = reader;
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
        return "EnterPinMessage{" +
                "mError='" + mError + '\'' +
                ", mReader=" + mReader +
                '}';
    }
}
