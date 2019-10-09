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

import java.util.HashMap;
import java.util.Locale;

import de.telekom.smartcredentials.core.exceptions.EncryptionException;

public class Base32String {

    private static final String SEPARATOR = "-";
    private static final Base32String INSTANCE =
            new Base32String(); // RFC 4648/3548

    private final char[] digits;
    private final int mask;
    private final int shift;
    private final HashMap<Character, Integer> charMap;

    static Base32String getInstance() {
        return INSTANCE;
    }

    private Base32String() {
        digits = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".toCharArray();
        mask = digits.length - 1;
        shift = Integer.numberOfTrailingZeros(digits.length);
        charMap = new HashMap<>();
        for (int i = 0; i < digits.length; i++) {
            charMap.put(digits[i], i);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public static byte[] decode(String encoded) throws DecodingException {
        return getInstance().decodeInternal(encoded);
    }

    public static String encode(byte[] data) throws EncryptionException {
        return getInstance().encodeInternal(data);
    }

    private byte[] decodeInternal(String encoded) throws DecodingException {
        encoded = encoded.trim().replaceAll(SEPARATOR, "").replaceAll(" ", "");

        // Remove padding. Note: the padding is used as hint to determine how many
        // bits to decode from the last incomplete chunk (which is commented out
        // below, so this may have been wrong to start with).
        encoded = encoded.replaceFirst("[=]*$", "");

        encoded = encoded.toUpperCase(Locale.US);
        if (encoded.length() == 0) {
            return new byte[0];
        }
        int encodedLength = encoded.length();
        int outLength = encodedLength * shift / 8;
        byte[] result = new byte[outLength];
        int buffer = 0;
        int next = 0;
        int bitsLeft = 0;
        for (char c : encoded.toCharArray()) {
            if (!charMap.containsKey(c)) {
                throw new DecodingException("Illegal character: " + c);
            }
            buffer <<= shift;
            buffer |= charMap.get(c) & mask;
            bitsLeft += shift;
            if (bitsLeft >= 8) {
                result[next++] = (byte) (buffer >> (bitsLeft - 8));
                bitsLeft -= 8;
            }
        }
        // We'll ignore leftover bits for now.
        //
        // if (next != outLength || bitsLeft >= shift) {
        //  throw new DecodingException("Bits left: " + bitsLeft);
        // }
        return result;
    }

    private String encodeInternal(byte[] data) throws EncryptionException {
        if (data.length == 0) {
            return "";
        }

        // shift is the number of bits per output character, so the length of the
        // output is the length of the input multiplied by 8/shift, rounded up.
        if (data.length >= (1 << 28)) {
            // The computation below will fail, so don't do it.
            throw new EncryptionException(EncryptionError.ENCODING_EXCEPTION_TEXT);
        }

        int outputLength = (data.length * 8 + shift - 1) / shift;
        StringBuilder result = new StringBuilder(outputLength);

        int buffer = data[0];
        int next = 1;
        int bitsLeft = 8;
        while (bitsLeft > 0 || next < data.length) {
            if (bitsLeft < shift) {
                if (next < data.length) {
                    buffer <<= 8;
                    buffer |= (data[next++] & 0xff);
                    bitsLeft += 8;
                } else {
                    int pad = shift - bitsLeft;
                    buffer <<= pad;
                    bitsLeft += pad;
                }
            }
            int index = mask & (buffer >> (bitsLeft - shift));
            bitsLeft -= shift;
            result.append(digits[index]);
        }
        return result.toString();
    }

    public static class DecodingException extends Exception {
        DecodingException(String message) {
            super(message);
        }
    }
}
