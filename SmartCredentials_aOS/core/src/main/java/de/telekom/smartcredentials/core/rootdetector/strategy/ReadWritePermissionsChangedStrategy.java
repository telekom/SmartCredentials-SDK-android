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

import de.telekom.smartcredentials.core.rootdetector.CommandStreamScanner;
import de.telekom.smartcredentials.core.rootdetector.RootDetectionConstantsSet;

import static de.telekom.smartcredentials.core.rootdetector.RootDetectionConstants.convertArrayListToStringArray;
import static de.telekom.smartcredentials.core.rootdetector.RootDetectionConstants.getJSONConstantsList;

public class ReadWritePermissionsChangedStrategy extends RootDetectionStrategy {

    private static final String COMMA_DELIMITER = ",";

    public ReadWritePermissionsChangedStrategy(Context context) {
        super(context);
    }

    @Override
    public boolean check() {
        String mountCommand = getJSONConstantsList(mContext, RootDetectionConstantsSet.MOUNT_COMMAND).get(0);
        String[] lines = CommandStreamScanner.getCommand(mountCommand);
        if (lines == null || lines.length == 0) {
            return false;
        }

        for (String line : lines) {
            String[] args = line.split(" ");

            if (args.length < 4) {
                continue;
            }

            String mountPoint = args[1];
            String mountOptions = args[3];

            String[] pathsThatShouldNotBeWritable =
                    convertArrayListToStringArray(getJSONConstantsList(mContext, RootDetectionConstantsSet.PATHS_THAT_SHOULD_NOT_BE_WRITABLE));
            String readWriteOption = getJSONConstantsList(mContext, RootDetectionConstantsSet.READ_WRITE_OPTION).get(0);

            for (String pathToCheck : pathsThatShouldNotBeWritable) {
                if (mountPoint.equalsIgnoreCase(pathToCheck)) {
                    for (String option : mountOptions.split(COMMA_DELIMITER)) {
                        if (option.equalsIgnoreCase(readWriteOption)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}
