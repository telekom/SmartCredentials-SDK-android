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

import android.content.Context;
import android.security.KeyPairGeneratorSpec;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Calendar;

import javax.security.auth.x500.X500Principal;

import de.telekom.smartcredentials.core.exceptions.EncryptionException;

import static de.telekom.smartcredentials.security.utils.Constants.ANDROID_KEY_STORE;
import static de.telekom.smartcredentials.security.utils.Constants.KEY_ALGORITHM_RSA;

public class KeyPairGeneratorWrapper {

    private static final String X500_NAME = "CN=Sample Name, O=Android Authority";

    private final String mAlias;

    public KeyPairGeneratorWrapper(String alias) {
        mAlias = alias;
    }

    String getAlias() {
        return mAlias;
    }

    void generateEncryptionKeyPairWithRSA(Context context, String metaAlias) throws EncryptionException {
        try {
            KeyPairGeneratorSpec spec = generateKeyPairGeneratorSpec(context, metaAlias);
            KeyPairGenerator generator = KeyPairGenerator.getInstance(KEY_ALGORITHM_RSA, ANDROID_KEY_STORE);
            generator.initialize(spec);
            generator.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) {
            throw new EncryptionException(e);
        }
    }

    private KeyPairGeneratorSpec generateKeyPairGeneratorSpec(Context context, String metaAlias) {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.add(Calendar.YEAR, 1);

        return new KeyPairGeneratorSpec.Builder(context)
                .setAlias(metaAlias)
                .setSubject(new X500Principal(X500_NAME))
                .setSerialNumber(BigInteger.ONE)
                .setStartDate(start.getTime())
                .setEndDate(end.getTime())
                .build();
    }
}
