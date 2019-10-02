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

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.KeyGenerator;

class KeyGeneratorWrapper {

    private final String mAlgorithm;
    private final String mProvider;

    KeyGeneratorWrapper(String algorithm, String provider) {
        mAlgorithm = algorithm;
        mProvider = provider;
    }

    KeyGenerator initGenerator(AlgorithmParameterSpec specs) throws NoSuchProviderException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyGenerator generator = KeyGenerator.getInstance(mAlgorithm, mProvider);
        generator.init(specs);
        return generator;
    }
}