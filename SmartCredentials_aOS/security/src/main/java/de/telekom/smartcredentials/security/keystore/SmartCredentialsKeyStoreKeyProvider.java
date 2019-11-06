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

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import de.telekom.smartcredentials.core.security.KeyStoreKeyProvider;
import de.telekom.smartcredentials.core.security.KeyStoreManagerException;
import de.telekom.smartcredentials.core.security.KeyStoreProviderException;

import static de.telekom.smartcredentials.security.utils.Constants.ANDROID_KEY_STORE;

public class SmartCredentialsKeyStoreKeyProvider implements KeyStoreKeyProvider {

    public static final int KEY_SIZE = 256;
    private static final String AES_ALGORITHM = "AES";

    private final Context mContext;
    private final KeyStoreManager mKeyStoreManager;

    public SmartCredentialsKeyStoreKeyProvider(Context context) {
        mContext = context;
        mKeyStoreManager = new KeyStoreManager();
    }

    @Override
    public SecretKey getKeyStoreSecretKey(String alias) throws KeyStoreManagerException, KeyStoreProviderException {
        generateKeyForAlias(alias);
        return mKeyStoreManager.getKeyStoreSecretKey(alias);
    }

    @Override
    public void deleteEntry(String keyAlias) throws KeyStoreException, KeyStoreManagerException {
        mKeyStoreManager.deleteEntry(keyAlias);
    }

    private void generateKeyForAlias(String alias) throws KeyStoreProviderException {
        try {
            if (mKeyStoreManager.checkKeyStoreContainsAlias(alias)) {
                return;
            }

            KeyGeneratorWrapper generatorWrapper = new KeyGeneratorWrapper(AES_ALGORITHM, ANDROID_KEY_STORE);
            KeyGenerator keyGenerator;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                keyGenerator = generatorWrapper.initGenerator(generateKeyGenParameterSpecAboveApi23(alias));
            } else {
                keyGenerator = generatorWrapper.initGenerator(generateKeyGenParameterSpecBelowApi23(alias));
            }

            keyGenerator.generateKey();

        } catch (KeyStoreManagerException | NoSuchAlgorithmException | NoSuchProviderException
                | InvalidAlgorithmParameterException e) {
            throw new KeyStoreProviderException(e);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private KeyGenParameterSpec generateKeyGenParameterSpecAboveApi23(String alias) {
        KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec
                .Builder(alias, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setUserAuthenticationRequired(false)
                .setRandomizedEncryptionRequired(false)
                .setKeySize(KEY_SIZE)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setInvalidatedByBiometricEnrollment(false);
        }
        if (Build.VERSION.SDK_INT >= 28) {
            builder.setIsStrongBoxBacked(false)
                    .setUnlockedDeviceRequired(false);
        }
        return builder.build();
    }

    private KeyPairGeneratorSpec generateKeyGenParameterSpecBelowApi23(String alias) {
        return new KeyPairGeneratorSpec.Builder(mContext)
                .setAlias(alias)
                .setKeySize(KEY_SIZE)
                .build();
    }
}
