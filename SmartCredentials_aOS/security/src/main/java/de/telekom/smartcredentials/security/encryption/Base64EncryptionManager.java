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

package de.telekom.smartcredentials.security.encryption;

import de.telekom.smartcredentials.core.exceptions.EncryptionException;

public class Base64EncryptionManager implements EncryptionManager {

    private final EncryptionManager mBase64EncryptionManagerBelowAPI23;
    private final EncryptionManager mBase64EncryptionManagerAboveAPI23;

    public Base64EncryptionManager(EncryptionManager base64EncryptionManagerBelowAPI23, EncryptionManager base64EncryptionManagerAboveAPI23) {
        mBase64EncryptionManagerBelowAPI23 = base64EncryptionManagerBelowAPI23;
        mBase64EncryptionManagerAboveAPI23 = base64EncryptionManagerAboveAPI23;
    }

    @Override
    public String encrypt(String toEncrypt) throws EncryptionException {
        EncryptionManager selectedEncryptionManager = getBase64EncryptionManager();
        return selectedEncryptionManager.encrypt(toEncrypt);
    }

    @Override
    public String encrypt(String toEncrypt, boolean isSensitive) throws EncryptionException {
        EncryptionManager selectedEncryptionManager = getBase64EncryptionManager();
        return selectedEncryptionManager.encrypt(toEncrypt, isSensitive);
    }

    @Override
    public String decrypt(String stringToDecrypt) throws EncryptionException {
        EncryptionManager selectedEncryptionManager = getBase64EncryptionManager();
        return selectedEncryptionManager.decrypt(stringToDecrypt);
    }

    private EncryptionManager getBase64EncryptionManager() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            return mBase64EncryptionManagerAboveAPI23;
        }
        return mBase64EncryptionManagerBelowAPI23;
    }
}