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

package de.telekom.smartcredentials.core.itemdatamodel;

import android.os.Parcel;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;

import static de.telekom.smartcredentials.core.itemdatamodel.ModelGenerator.KEY_DETAILS;
import static de.telekom.smartcredentials.core.itemdatamodel.ModelGenerator.KEY_IDENTIFIER;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ItemEnvelopeAndroidTest {

    @Test
    public void putItemInParcelPutsAllFieldsCorrectly() throws JSONException {
        ItemEnvelope itemEnvelope = ModelGenerator.generateItemEnvelope();
        Parcel parcel = Parcel.obtain();

        itemEnvelope.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);

        ItemEnvelope createdFromParcel = (ItemEnvelope) ItemEnvelope.CREATOR.createFromParcel(parcel);

        assertEquals(itemEnvelope.getItemId(), createdFromParcel.getItemId());
        assertEquals(itemEnvelope.getUserId(), createdFromParcel.getUserId());
        assertEquals(itemEnvelope.getItemType().getContentType(), createdFromParcel.getItemType().getContentType());
        assertEquals(itemEnvelope.getItemType().getDesc(), createdFromParcel.getItemType().getDesc());
        assertEquals(itemEnvelope.getIdentifier().get(KEY_IDENTIFIER), createdFromParcel.getIdentifier().get(KEY_IDENTIFIER));
        assertEquals(itemEnvelope.getItemMetadata().isDataEncrypted(), createdFromParcel.getItemMetadata().isDataEncrypted());
        assertEquals(itemEnvelope.getDetails().get(KEY_DETAILS), createdFromParcel.getDetails().get(KEY_DETAILS));
        assertEquals(itemEnvelope.getItemMetadata().getActionList().get(0).getName(),
                createdFromParcel.getItemMetadata().getActionList().get(0).getName());
    }

}