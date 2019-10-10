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

package de.telekom.smartcredentials.otp.converters;

import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import de.telekom.smartcredentials.core.model.item.ContentType;
import de.telekom.smartcredentials.core.model.item.ItemDomainData;
import de.telekom.smartcredentials.core.model.item.ItemDomainMetadata;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.core.model.otp.OTPTokenKey;
import de.telekom.smartcredentials.core.model.otp.OTPTokenKeyExtras;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;

public class UriToItemDomainModelConverter {

    private static final String TAG = UriToItemDomainModelConverter.class.getSimpleName();

    private final OTPUriParser mOTPUriParser;

    public UriToItemDomainModelConverter(OTPUriParser otpUriParser) {
        mOTPUriParser = otpUriParser;
    }

    public ItemDomainModel parseOTPUri(String itemId, String userId, Uri uri) {
        if (!mOTPUriParser.isTokenUriValid(uri)) {
            return null;
        }
        mOTPUriParser.extractOTPItemProperties(uri);

        ItemDomainModel itemDomainModel = new ItemDomainModel();
        itemDomainModel.setId(itemId);

        ItemDomainMetadata metadata = new ItemDomainMetadata(true, false, false);
        metadata.setItemType(mOTPUriParser.mOTPType.getDesc());
        metadata.setContentType(ContentType.SENSITIVE);
        metadata.setUserId(userId);
        itemDomainModel.setMetadata(metadata);

        ItemDomainData data = new ItemDomainData();
        try {
            data.setIdentifier(getIdentifier().toString());
            data.setPrivateData(getPrivateData().toString());
        } catch (JSONException e) {
            return null;
        }
        itemDomainModel.setData(data);

        return itemDomainModel;
    }

    JSONObject getIdentifier() throws JSONException {
        JSONObject identifier = new JSONObject();
        try {
            identifier.put(OTPTokenKeyExtras.ISSUER, mOTPUriParser.mIssuer);
            identifier.put(OTPTokenKeyExtras.USER_LABEL, mOTPUriParser.mUserLabel);
            identifier.put(OTPTokenKeyExtras.IMAGE, mOTPUriParser.mImage);
        } catch (JSONException e) {
            ApiLoggerResolver.logError(TAG, "Could not create identifier data for OTP item: " + e.getMessage());
            throw e;
        }
        return identifier;
    }

    JSONObject getPrivateData() throws JSONException {
        JSONObject privateData = new JSONObject();
        try {
            privateData.put(OTPTokenKey.KEY, mOTPUriParser.mSecret);
            privateData.put(OTPTokenKey.COUNTER, mOTPUriParser.mCounter);
            privateData.put(OTPTokenKey.DIGITS_COUNT, mOTPUriParser.mDigits);
            privateData.put(OTPTokenKey.ALGORITHM, mOTPUriParser.mAlgorithm);
            privateData.put(OTPTokenKey.VALIDITY_PERIOD, mOTPUriParser.mPeriod);
        } catch (JSONException e) {
            ApiLoggerResolver.logError(TAG, "Could not create privateData for OTP item: " + e.getMessage());
            throw e;
        }
        return privateData;
    }
}
