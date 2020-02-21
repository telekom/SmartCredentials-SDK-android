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

package de.telekom.smartcredentials.eid.dispatcher;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.IsoDep;

/**
 * Created by Alex.Graur@endava.com at 11/8/2019
 */
@SuppressWarnings("unused")
public class ForegroundDispatcher {

    public static void enable(Activity activity) {
        Intent intent = new Intent(activity, activity.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(activity, 0, intent, 0);
        NfcAdapter mNfcAdapter = NfcAdapter.getDefaultAdapter(activity);
        IntentFilter[] filters = new IntentFilter[]{new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)};
        String[][] technologies = new String[][]{new String[]{IsoDep.class.getName()}};
        mNfcAdapter.enableForegroundDispatch(activity, pendingIntent, filters, technologies);
    }

    public static void disable(Activity activity) {
        NfcAdapter mNfcAdapter = NfcAdapter.getDefaultAdapter(activity);
        mNfcAdapter.disableForegroundDispatch(activity);
    }
}
