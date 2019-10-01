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

package de.telekom.smartcredentials.authentication.parser;

import android.os.Bundle;
import android.os.Parcel;
import android.util.Base64;

/**
 * Created by Lucian Iacob on March 27, 2019.
 */
public class BundleTransformer {

    public static Bundle decodeFromString(final String encodedExtras) {
        if (encodedExtras == null) {
            return null;
        }
        byte[] bytes = Base64.decode(encodedExtras, 0);
        Bundle bundle = new Bundle();
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0);
        bundle.readFromParcel(parcel);
        return bundle;
    }

    public static String encodeToString(final Bundle extras) {
        if (extras == null) {
            return null;
        }
        final Parcel parcel = Parcel.obtain();
        extras.writeToParcel(parcel, 0);
        final byte[] bytes = parcel.marshall();
        return Base64.encodeToString(bytes, 0, bytes.length, 0);
    }
}
