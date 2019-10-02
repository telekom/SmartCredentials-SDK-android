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

public enum RootDetectionConstantsSet {
    GENERAL_ROOT_APPLICATIONS_PACKAGES,
    DANGEROUS_APPLICATIONS_PACKAGES,
    SU_PATHS,
    PATHS_THAT_SHOULD_NOT_BE_WRITABLE,
    SUPER_USER_FILENAME,
    BUSY_BOX_FILENAME,
    READ_WRITE_OPTION,
    TEST_KEYS_TAG,
    WHICH_FILENAME,
    SYSTEM_PROPS_COMMAND,
    MOUNT_COMMAND,
    DANGEROUS_SYSTEM_PROPERTIES_MAP
}
