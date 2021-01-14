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
 * Created by Alex.Graur@endava.com at 11/19/2019
 */
@SuppressWarnings("unused")
public class ReaderMessage extends SmartEidMessage {

    @SerializedName("name")
    @Expose
    private String mName;
    @SerializedName("attached")
    @Expose
    private boolean mAttached;
    @SerializedName("keypad")
    @Expose
    private boolean mKeypad;
    @SerializedName("card")
    @Expose
    private Card mCard;

    public ReaderMessage() {

    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public boolean isAttached() {
        return mAttached;
    }

    public void setAttached(boolean attached) {
        this.mAttached = attached;
    }

    public boolean isKeypad() {
        return mKeypad;
    }

    public void setKeypad(boolean keypad) {
        this.mKeypad = keypad;
    }

    public Card getCard() {
        return mCard;
    }

    public void setCard(Card card) {
        this.mCard = card;
    }

    @NonNull
    @Override
    public String toString() {
        return "ReaderMessage{" +
                "mName='" + mName + '\'' +
                ", mAttached=" + mAttached +
                ", mKeypad=" + mKeypad +
                ", mCard=" + mCard +
                '}';
    }
}
