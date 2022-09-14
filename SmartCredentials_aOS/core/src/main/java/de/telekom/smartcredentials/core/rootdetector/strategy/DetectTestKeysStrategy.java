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

import de.telekom.smartcredentials.core.rootdetector.RootDetectionConstantsSet;
import de.telekom.smartcredentials.core.rootdetector.RootDetectionOption;

import static de.telekom.smartcredentials.core.rootdetector.RootDetectionConstants.getJSONConstantsList;

public class DetectTestKeysStrategy extends RootDetectionStrategy {

    public DetectTestKeysStrategy(Context context, RootDetectionOptionListener listener) {
        super(context, RootDetectionOption.DETECT_TEST_KEYS, listener);
    }

    @Override
    public boolean check() {
        String testKeysTag = getJSONConstantsList(mContext, RootDetectionConstantsSet.TEST_KEYS_TAG).get(0);
        String buildTags = android.os.Build.TAGS;
        return deliverResult(buildTags != null && buildTags.contains(testKeysTag));
    }
}
