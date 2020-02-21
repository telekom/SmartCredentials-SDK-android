/*
 * Copyright (c) 2020 Telekom Deutschland AG
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

package de.telekom.smartcredentials.security.repositories;

import de.telekom.smartcredentials.core.exceptions.EncryptionException;

/**
 * Created by Alex.Graur@endava.com at 1/22/2020
 */
public class RepositoryAliasNative {

    private static final String REPO_ALIAS_NOT_AVAILABLE = "repo_alias library not yet loaded.";

    private static boolean sIsLibraryLoaded;

    public static String getAliasSensitive() throws EncryptionException {
        if (!isLibraryLoaded()) {
            throw new EncryptionException(REPO_ALIAS_NOT_AVAILABLE);
        }
        return aliasSensitive();
    }

    public static String getAliasNonSensitive() throws EncryptionException {
        if (!isLibraryLoaded()) {
            throw new EncryptionException(REPO_ALIAS_NOT_AVAILABLE);
        }
        return aliasNonSensitive();
    }

    private static boolean isLibraryLoaded() {
        return sIsLibraryLoaded;
    }

    static {
        System.loadLibrary("repo_alias");
        sIsLibraryLoaded = true;
    }

    private static native String aliasSensitive();

    private static native String aliasNonSensitive();
}
