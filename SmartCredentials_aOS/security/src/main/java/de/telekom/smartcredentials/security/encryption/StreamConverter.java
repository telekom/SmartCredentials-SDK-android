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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

class StreamConverter {

    static byte[] getOutputStreamBytes(byte[] toEncryptBytes, Cipher cipher) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);
        cipherOutputStream.write(toEncryptBytes);
        cipherOutputStream.close();

        return outputStream.toByteArray();
    }

    static byte[] getByteArrayInputStream(byte[] encryptedTextBytes, Cipher cipher) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(encryptedTextBytes);

        CipherInputStream cipherInputStream = new CipherInputStream(byteArrayInputStream, cipher);
        byte[] inputStreamBytes = getBytesFromCipherInputStream(cipherInputStream);
        cipherInputStream.close();

        return inputStreamBytes;
    }

    static byte[] append(byte[] prefix, byte[] suffix) {
        byte[] toReturn = new byte[prefix.length + suffix.length];
        System.arraycopy(prefix, 0, toReturn, 0, prefix.length);
        System.arraycopy(suffix, 0, toReturn, prefix.length, suffix.length);
        return toReturn;
    }

    private static byte[] getBytesFromCipherInputStream(CipherInputStream cipherInputStream) throws IOException {
        ArrayList<Byte> values = new ArrayList<>();
        int nextByte;
        while ((nextByte = cipherInputStream.read()) != -1) {
            values.add((byte) nextByte);
        }

        byte[] bytes = new byte[values.size()];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = values.get(i);
        }

        return bytes;
    }
}
