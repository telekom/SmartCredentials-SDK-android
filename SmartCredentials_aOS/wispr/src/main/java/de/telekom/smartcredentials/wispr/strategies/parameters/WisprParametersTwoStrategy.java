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

package de.telekom.smartcredentials.wispr.strategies.parameters;

import java.util.Map;

import de.telekom.smartcredentials.wispr.WisprConstants;

public class WisprParametersTwoStrategy extends WisprParametersStrategy {

    public WisprParametersTwoStrategy(Map<String, String> parameters) {
        super(parameters);
        mParameters.put(WisprConstants.WISPR_VERSION_PARAM, "2.0");
    }
}
