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

package de.telekom.smartcredentials.security.di;

import android.content.Context;

import de.telekom.smartcredentials.core.di.Provides;
import de.telekom.smartcredentials.security.encryption.AESCipherManager;
import de.telekom.smartcredentials.security.encryption.Base64EncryptionManagerAES;
import de.telekom.smartcredentials.security.encryption.Base64EncryptionManagerRSA;
import de.telekom.smartcredentials.security.encryption.EncryptionManager;
import de.telekom.smartcredentials.security.encryption.RSACipherManager;
import de.telekom.smartcredentials.security.keystore.SmartCredentialsKeyStoreKeyProvider;

/**
 * Created by Lucian Iacob on November 05, 2018.
 */
public class EncryptionObjectCreator {

    private final Context mContext;
    private final String mAlias;

    public EncryptionObjectCreator(Context context, String alias) {
        mContext = context;
        mAlias = alias;
    }

    @Provides
    public Base64EncryptionManagerRSA provideRsaEncryptionManager() {
        return new Base64EncryptionManagerRSA(provideRsaCipherManager());
    }

    @Provides
    public Base64EncryptionManagerAES provideAesEncryptionManager(Context context) {
        return new Base64EncryptionManagerAES(provideAesCipherManager(context));
    }

    @Provides
    public EncryptionManager provideEncryptionManagerBelowApi23() {
        return provideRsaEncryptionManager();
    }

    @Provides
    public EncryptionManager provideEncryptionManagerAboveApi23(Context context) {
        return provideAesEncryptionManager(context);
    }

    @Provides
    private AESCipherManager provideAesCipherManager(Context context) {
        return new AESCipherManager(provideKeyStoreKeyProvider(context), mAlias);
    }

    @Provides
    private SmartCredentialsKeyStoreKeyProvider provideKeyStoreKeyProvider(Context context) {
        return new SmartCredentialsKeyStoreKeyProvider(context);
    }

    @Provides
    private RSACipherManager provideRsaCipherManager() {
        return new RSACipherManager(mContext, mAlias);
    }
}
