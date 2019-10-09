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

package de.telekom.smartcredentials.core.security;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

public abstract class CipherWrapper {

    protected Cipher mCipher;
    protected String mTransformation;
    protected int mOpMode;
    protected Key mKey;

    public CipherWrapper(String mTransformation, int mOpMode, Key mKey) {
        this.mTransformation = mTransformation;
        this.mOpMode = mOpMode;
        this.mKey = mKey;
    }

    public Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException {
        if (mCipher == null) {
            mCipher = createCipher();
        }
        return mCipher;
    }

    public int getOpMode() {
        return mOpMode;
    }

    private Cipher createCipher() throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException {
        Cipher cipher = Cipher.getInstance(mTransformation);
        cipher.init(mOpMode, mKey);
        return cipher;
    }
}
