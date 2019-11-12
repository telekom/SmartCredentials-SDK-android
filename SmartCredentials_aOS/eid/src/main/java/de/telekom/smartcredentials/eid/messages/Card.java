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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alex.Graur@endava.com at 11/11/2019
 */
public class Card {

    @SerializedName("inoperative")
    @Expose
    private boolean mInoperative;
    @SerializedName("deactivated")
    @Expose
    private boolean mDeactivated;
    @SerializedName("retryCounter")
    @Expose
    private float mRetryCounter;

    public Card() {

    }

    public boolean isInoperative() {
        return mInoperative;
    }

    public void setInoperative(boolean inoperative) {
        this.mInoperative = inoperative;
    }

    public boolean isDeactivated() {
        return mDeactivated;
    }

    public void setDeactivated(boolean deactivated) {
        this.mDeactivated = deactivated;
    }

    public float getRetryCounter() {
        return mRetryCounter;
    }

    public void setRetryCounter(float retryCounter) {
        this.mRetryCounter = retryCounter;
    }

    @Override
    public String toString() {
        return "Card{" +
                "mInoperative=" + mInoperative +
                ", mDeactivated=" + mDeactivated +
                ", mRetryCounter=" + mRetryCounter +
                '}';
    }
}
