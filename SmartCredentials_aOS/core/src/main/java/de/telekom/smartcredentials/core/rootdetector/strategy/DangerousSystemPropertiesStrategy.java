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

package de.telekom.smartcredentials.core.rootdetector.strategy;

import android.content.Context;

import java.util.Map;

import de.telekom.smartcredentials.core.rootdetector.CommandStreamScanner;
import de.telekom.smartcredentials.core.rootdetector.RootDetectionConstantsSet;

import static de.telekom.smartcredentials.core.rootdetector.RootDetectionConstants.getJSONConstantsList;
import static de.telekom.smartcredentials.core.rootdetector.RootDetectionConstants.getJSONConstantsMap;

public class DangerousSystemPropertiesStrategy extends RootDetectionStrategy {

    public DangerousSystemPropertiesStrategy(Context context) {
        super(context);
    }

    @Override
    public boolean check() {
        String systemPropsCommand = getJSONConstantsList(mContext, RootDetectionConstantsSet.SYSTEM_PROPS_COMMAND).get(0);
        String[] systemProperties = CommandStreamScanner.getCommand(systemPropsCommand);
        if (systemProperties == null || systemProperties.length == 0) {
            return false;
        }

        Map<String, String> dangerousSystemPropsMap =
                getJSONConstantsMap(mContext, RootDetectionConstantsSet.DANGEROUS_SYSTEM_PROPERTIES_MAP);
        for (String systemProperty : systemProperties) {
            for (Map.Entry<String, String> dangerousSystemPropertyEntry : dangerousSystemPropsMap.entrySet()) {
                if (systemProperty.contains(dangerousSystemPropertyEntry.getKey())) {
                    String dangerousSystemPropertyValue = "[" + dangerousSystemPropertyEntry.getValue() + "]";
                    if (systemProperty.contains(dangerousSystemPropertyValue)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
