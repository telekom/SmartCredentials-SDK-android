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

import java.io.File;

import de.telekom.smartcredentials.core.rootdetector.RootDetectionConstantsSet;

import static de.telekom.smartcredentials.core.rootdetector.RootDetectionConstants.convertArrayListToStringArray;
import static de.telekom.smartcredentials.core.rootdetector.RootDetectionConstants.getJSONConstantsList;

public abstract class BinaryFilesStrategy extends RootDetectionStrategy {

    private String mFilename;

    public BinaryFilesStrategy(Context context, String filename) {
        super(context);
        mFilename = filename;
    }

    @Override
    public boolean check() {
        String[] suPathsArray = convertArrayListToStringArray(getJSONConstantsList(mContext, RootDetectionConstantsSet.SU_PATHS));
        for (String suPath : suPathsArray) {
            File suFile = new File(suPath + mFilename);
            if (suFile.exists()) {
                return true;
            }
        }

        return false;
    }
}
