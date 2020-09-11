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

import android.os.Build;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.telekom.smartcredentials.core.exceptions.EncryptionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class Base64EncryptionManagerRSATest {

    private final String mAlias = "KeyStoreKeyAlias";
    private Base64EncryptionManagerRSA mEncryptionManagerRSA;

    @Before
    public void setUp() {
        KeyPairGeneratorWrapper keyPairWrapper =
                new KeyPairGeneratorWrapper(InstrumentationRegistry.getContext(), mAlias);
        RSACipherManager rsaCipherManager = new RSACipherManager(keyPairWrapper);
        mEncryptionManagerRSA = new Base64EncryptionManagerRSA(rsaCipherManager);
    }

    @Test
    public void testEncryptDecryptShortText() throws EncryptionException {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            String toEncryptText = "test";
            String encrypted = mEncryptionManagerRSA.encrypt(toEncryptText, mAlias);
            assertNotNull(encrypted);
            assertNotEquals(toEncryptText, encrypted);

            String decrypted = mEncryptionManagerRSA.decrypt(encrypted, mAlias);
            assertNotNull(decrypted);
            assertNotEquals(decrypted, encrypted);
            assertEquals(toEncryptText, decrypted);
        }
    }

    @Test
    public void testEncryptDecryptLongText() throws EncryptionException {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            String toEncryptText = "Stronger unpacked felicity to of mistaken. Fanny at wrong table ye in. Be on easily cannot innate in lasted months on. " +
                    "Differed and and felicity steepest mrs age outweigh. Opinions learning likewise daughter now age outweigh. " +
                    "Raptures stanhill my greatest mistaken or exercise he on although. Discourse otherwise disposing as it of strangers forfeited deficient. " +
                    "Society excited by cottage private an it esteems. Fully begin on by wound an. Girl rich in do up or both. At declared in as rejoiced of together. " +
                    "He impression collecting delightful unpleasant by prosperous as on. End too talent she object mrs wanted remove giving.";
            String encrypted = mEncryptionManagerRSA.encrypt(toEncryptText, mAlias);
            assertNotNull(encrypted);
            assertNotEquals(toEncryptText, encrypted);

            String decrypted = mEncryptionManagerRSA.decrypt(encrypted, mAlias);
            assertNotNull(decrypted);
            assertNotEquals(decrypted, encrypted);
            assertEquals(toEncryptText, decrypted);
        }
    }
}