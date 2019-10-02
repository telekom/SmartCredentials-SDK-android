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

package de.telekom.smartcredentials.core.actions;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import de.telekom.smartcredentials.core.ModelGenerator;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;

import static de.telekom.smartcredentials.core.actions.ActionItemToJSON.KEY_CHANNEL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
public class ActionItemToJSONTest {

    @Test
    public void executeActionReturnsTheJSONObjectOnCompleteCallback() throws JSONException {
        JSONObject data = new JSONObject();
        data.put(KEY_CHANNEL, ShareChannel.NONE.getName());

        ActionItemToJSON action = new ActionItemToJSON("12", "12", data);

        Context context = Mockito.mock(Context.class);
        ItemEnvelope itemEnvelope = ModelGenerator.generateItemEnvelope();
        ExecutionCallback callback = Mockito.mock(ExecutionCallback.class);

        action.execute(context, itemEnvelope.toItemDomainModel("5555"), callback);

        verify(callback).onComplete(any());
    }
}