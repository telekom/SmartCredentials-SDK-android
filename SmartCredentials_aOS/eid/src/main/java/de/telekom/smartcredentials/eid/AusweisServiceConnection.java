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

package de.telekom.smartcredentials.eid;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.governikus.ausweisapp2.IAusweisApp2Sdk;

import de.telekom.smartcredentials.core.eid.EidMessageReceivedCallback;
import de.telekom.smartcredentials.core.eid.messages.EidMessage;
import de.telekom.smartcredentials.eid.callback.AusweisCallback;

/**
 * Created by Alex.Graur@endava.com at 11/8/2019
 */
public class AusweisServiceConnection implements ServiceConnection {

    private IAusweisApp2Sdk mSdk;
    private AusweisCallback mAusweisCallback;
    private EidMessageReceivedCallback mMessageReceivedCallback;

    public AusweisServiceConnection(AusweisCallback ausweisCallback, EidMessageReceivedCallback callback) {
        mAusweisCallback = ausweisCallback;
        mMessageReceivedCallback = callback;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mMessageReceivedCallback.onDebugged("onServiceConnected() was called");
        mSdk = IAusweisApp2Sdk.Stub.asInterface(service);

        if (mSdk != null) {
            try {
                mSdk.connectSdk(mAusweisCallback);
                mMessageReceivedCallback.onMessageReceived(new EidMessage(EidMessage.BINDED));
            } catch (RemoteException e) {
                mMessageReceivedCallback.onMessageReceived(new EidMessage(EidMessage.NOT_BINDED));
            }
        } else {
            mMessageReceivedCallback.onMessageReceived(new EidMessage(EidMessage.SDK_NOT_INITIALIZED));
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mSdk = null;
    }

    public IAusweisApp2Sdk getAusweisSdk() {
        return mSdk;
    }
}
