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
import de.telekom.smartcredentials.core.rootdetector.RootDetectionNative;

import static de.telekom.smartcredentials.core.rootdetector.RootDetectionConstants.convertArrayListToStringArray;
import static de.telekom.smartcredentials.core.rootdetector.RootDetectionConstants.getJSONConstantsList;

public class RootNativeStrategy extends RootDetectionStrategy {

    public RootNativeStrategy(Context context) {
        super(context);
    }

    @Override
    public boolean check() {
        if (!canLoadNativeLibrary()) {
            return false;
        }

        String[] suPathsArray = convertArrayListToStringArray(getJSONConstantsList(mContext, RootDetectionConstantsSet.SU_PATHS));
        String superUserFileName = getJSONConstantsList(mContext, RootDetectionConstantsSet.SUPER_USER_FILENAME).get(0);

        String[] paths = new String[suPathsArray.length];
        for (int i = 0; i < paths.length; i++) {
            paths[i] = suPathsArray[i] + superUserFileName;
        }

        RootDetectionNative rootDetectionNative = new RootDetectionNative();
        return rootDetectionNative.checkRooted(paths) > 0;
    }

    private boolean canLoadNativeLibrary() {
        return new RootDetectionNative().isLibraryLoaded();
    }
}
