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

package de.telekom.smartcredentials.core.api;

import java.security.Key;
import java.security.PublicKey;

import de.telekom.smartcredentials.core.exceptions.EncryptionException;
import de.telekom.smartcredentials.core.model.EncryptionAlgorithm;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.core.security.CipherWrapper;
import de.telekom.smartcredentials.core.security.KeyStoreKeyProvider;
import de.telekom.smartcredentials.core.strategies.EncryptionStrategy;

public interface SecurityApi {

    /**
     * Returns a {@link PublicKey} for a given alias.
     *
     * @param alias of the public key
     * @return the pubic key
     */
    SmartCredentialsApiResponse<PublicKey> getPublicKey(String alias);

    /**
     * Encrypts a given {@link String} using a supported {@link EncryptionAlgorithm} and an alias
     * corresponding to a public key.
     *
     * @param toEncrypt represents the {@link String} to be encrypted
     * @param alias     of the public key
     * @param algorithm used for the encryption
     * @return the encrypted {@link String}
     */
    SmartCredentialsApiResponse<String> encrypt(String toEncrypt, String alias, EncryptionAlgorithm algorithm);

    /**
     * Method used to fetch the {@link CipherWrapper}.
     *
     * @param mTransformation used by the cipher
     * @param mOpMode         operation mode of the cipher
     * @param mKey            of the cipher
     * @return an instance of {@link CipherWrapper}
     */
    CipherWrapper getCipherWrapper(String mTransformation, int mOpMode, Key mKey);

    /**
     * Method used to fetch the {@link KeyStoreKeyProvider}.
     *
     * @return an instance of {@link KeyStoreKeyProvider}
     */
    KeyStoreKeyProvider getKeyStoreKeyProvider();

    /**
     * Method used to calculate the checksun of an otp.
     *
     * @param otp    used for calculating the checksum
     * @param digits count fo the otp value
     * @return the checksum of the otp
     */
    int calculateChecksum(long otp, int digits);

    /**
     * Method used to generate the HMAC of a given {@link byte[]} content.
     *
     * @param crypto   represents the HMAC algorithm used
     * @param keyBytes used in the HMAC algorithm
     * @param text     to be encrypted
     * @return the result of the HMAC encryption
     * @throws EncryptionException is the exception thrown is something went wrong in the process
     *                             of encrypting
     */
    byte[] hmac_sha(String crypto, byte[] keyBytes, byte[] text) throws EncryptionException;

    /**
     * Method used to fetch the {@link EncryptionStrategy}.
     *
     * @return an instance of {@link EncryptionStrategy}
     */
    EncryptionStrategy getEncryptionStrategy();
}
