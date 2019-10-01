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

package de.telekom.smartcredentials.core.wispr;

public enum WisprVersion {

    WISPR_1("WISPR_1"),
    WISPR_1_FON("WISPR_1_FON"),
    WISPR_1_WITH_PROXY("WISPR_1_WITH_PROXY"),
    WISPR_1_WITH_POLLING("WISPR_1_WITH_POLLING"),
    WISPR_2("WISPR_2"),
    WISPR_2_WITH_POLLING("WISPR_2_WITH_POLLING"),
    WISPR_2_WITH_PROXY("WISPR_2_WITH_PROXY"),
    WISPR_2_WITH_EAP("WISPR_2_WITH_EAP"),
    WISPR_2_WITH_EAP_AND_POLLING("WISPR_2_WITH_EAP_AND_POLLING");

    private final String text;

    WisprVersion(String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
