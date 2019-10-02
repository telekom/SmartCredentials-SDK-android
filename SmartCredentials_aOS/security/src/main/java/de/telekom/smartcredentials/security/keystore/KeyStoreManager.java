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

package de.telekom.smartcredentials.security.keystore;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.SecretKey;

import de.telekom.smartcredentials.core.security.KeyStoreManagerException;

import static de.telekom.smartcredentials.security.utils.Constants.ANDROID_KEY_STORE;

public class KeyStoreManager {

    private static final KeyStoreManager KEY_STORE_MANAGER_INSTANCE = new KeyStoreManager();

    private KeyStore mKeyStore;

    private KeyStoreManager() {
        // required empty constructor
    }

    public static KeyStoreManager getInstance() {
        return KEY_STORE_MANAGER_INSTANCE;
    }

    public KeyStore.PrivateKeyEntry getPrivateKeyEntry(String alias) throws KeyStoreManagerException {
        try {
            return (KeyStore.PrivateKeyEntry) getKeyStore().getEntry(alias, null);
        } catch (NoSuchAlgorithmException | UnrecoverableEntryException | KeyStoreException e) {
            throw new KeyStoreManagerException(e);
        }
    }

    public boolean checkKeyStoreContainsAlias(String alias) throws KeyStoreManagerException {
        try {
            return getKeyStore().containsAlias(alias);
        } catch (KeyStoreException e) {
            throw new KeyStoreManagerException(e);
        }
    }

    SecretKey getKeyStoreSecretKey(String alias) throws KeyStoreManagerException {
        try {
            return (SecretKey) getKeyStore().getKey(alias, null);
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new KeyStoreManagerException(e);
        }
    }

    void deleteEntry(String keyAlias) throws KeyStoreException, KeyStoreManagerException {
        getKeyStore().deleteEntry(keyAlias);
    }

    private void init() throws KeyStoreManagerException {
        try {
            mKeyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
            mKeyStore.load(null);
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new KeyStoreManagerException(e);
        }
    }

    private KeyStore getKeyStore() throws KeyStoreManagerException {
        if (mKeyStore == null) {
            init();
        }
        return mKeyStore;
    }
}
