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

package de.telekom.smartcredentials.core.rootdetector;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum RootDetectionOption {
    CHECK_SUPER_USER_BINARY_FILES,
    CHECK_BUSY_BOX_BINARY_FILES,
    DANGEROUS_APPLICATIONS_EXISTS,
    DANGEROUS_SYSTEM_PROPERTIES_EXISTS,
    DETECT_TEST_KEYS,
    READ_WRITE_PERMISSIONS_CHANGED,
    ROOT_MANAGEMENT_APPLICATIONS_EXISTS,
    ROOT_NATIVE_EXISTS,
    SU_EXISTS;

    public final static Set<RootDetectionOption> ALL = new HashSet<>(Arrays.asList(
            CHECK_SUPER_USER_BINARY_FILES,
            CHECK_BUSY_BOX_BINARY_FILES,
            DANGEROUS_APPLICATIONS_EXISTS,
            DANGEROUS_SYSTEM_PROPERTIES_EXISTS,
            DETECT_TEST_KEYS,
            READ_WRITE_PERMISSIONS_CHANGED,
            ROOT_MANAGEMENT_APPLICATIONS_EXISTS,
            ROOT_NATIVE_EXISTS,
            SU_EXISTS));

    @SuppressWarnings("unused")
    public final static Set<RootDetectionOption> NONE = new HashSet<>();
}
