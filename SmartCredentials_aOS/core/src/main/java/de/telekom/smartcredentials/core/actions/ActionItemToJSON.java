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

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;

import de.telekom.smartcredentials.core.converters.ModelConverter;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelopeParser;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;

@SuppressWarnings("unused")
public class ActionItemToJSON extends SmartCredentialsAction {

    public static final String KEY_CHANNEL = "share_channel";
    private static final String CLIP_DATA_LABEL = "simple text";
    private static final String INTENT_SET_TYPE = "text/plain";
    private static final String JSON_TAG = "JSON";

    private ItemEnvelope mItemEnvelope;

    public ActionItemToJSON() {
        // required empty constructor
    }

    public ActionItemToJSON(String id, String name, JSONObject data) {
        super(id, name, data);
    }

    @Override
    public void execute(Context context, ItemDomainModel itemDomainModel, ExecutionCallback callback) {
        try {
            mItemEnvelope = ModelConverter.toItemEnvelope(itemDomainModel);
        } catch (JSONException e) {
            ApiLoggerResolver.logError(getClass().getSimpleName(), "Could not convert ItemDomain to ItemEnvelope");
        }
        try {
            String shareChannel = getData().getString(KEY_CHANNEL);
            String jsonRepresentation = createTextForSharing(mItemEnvelope);
            if (shareChannel.equals(ShareChannel.DEFAULT_SYSTEM.getName())) {
                defaultShare(context, mItemEnvelope);
                callback.onComplete(new ActionShareResponse(jsonRepresentation, ShareChannel.DEFAULT_SYSTEM));
            } else if (shareChannel.equals(ShareChannel.COPY_TO_CLIPBOARD.getName())) {
                copyToClipboard(context, mItemEnvelope);
                callback.onComplete(new ActionShareResponse(jsonRepresentation, ShareChannel.COPY_TO_CLIPBOARD));
            } else if (shareChannel.equals(ShareChannel.NONE.getName())) {
                callback.onComplete(new ActionShareResponse(jsonRepresentation, ShareChannel.NONE));
            }
        } catch (JSONException e) {
            ApiLoggerResolver.logError(getClass().getSimpleName(), "Could not retrieve the share channel");
        }
    }

    /**
     * Copies to clipboard the JSON representation of the item envelope.
     */
    private void copyToClipboard(Context context, ItemEnvelope itemEnvelope) throws JSONException {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(CLIP_DATA_LABEL, createTextForSharing(itemEnvelope));

        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
        }
    }

    /**
     * Copies to clipboard the JSON representation of the item envelope.
     */
    private void defaultShare(Context context, ItemEnvelope itemEnvelope) throws JSONException {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(INTENT_SET_TYPE);
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, itemEnvelope.getItemType().getDesc() + " " + JSON_TAG);
        intent.putExtra(android.content.Intent.EXTRA_TEXT, createTextForSharing(itemEnvelope));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * Returns the JSON representation of the item envelope as a String.
     */
    private String createTextForSharing(ItemEnvelope itemEnvelope) throws JSONException {
        ItemEnvelopeParser parser = new ItemEnvelopeParser(itemEnvelope);
        JSONObject jsonItem = parser.generateJSON();

        return readableJsonFormat(jsonItem.toString()) + "\n" + getTimeStamp();
    }

    /**
     * Returns the system time as a String.
     */
    private String getTimeStamp() {
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    /**
     * Converts the JSON representation to a readable format.
     */
    private String readableJsonFormat(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            return jsonObject.toString(4);
        } catch (JSONException e) {
            ApiLoggerResolver.logError(getClass().getSimpleName(), "Could not format the JSON object");
            return null;
        }
    }

}
