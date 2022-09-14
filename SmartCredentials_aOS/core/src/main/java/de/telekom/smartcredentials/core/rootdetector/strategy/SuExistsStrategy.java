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

import static de.telekom.smartcredentials.core.rootdetector.RootDetectionConstants.getJSONConstantsList;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.rootdetector.RootDetectionConstantsSet;
import de.telekom.smartcredentials.core.rootdetector.RootDetectionOption;

public class SuExistsStrategy extends RootDetectionStrategy {

    public SuExistsStrategy(Context context, RootDetectionOptionListener listener) {
        super(context, RootDetectionOption.SU_EXISTS, listener);
    }

    @Override
    public boolean check() {
        Process process = null;
        BufferedReader in = null;

        String whichFileName = getJSONConstantsList(mContext, RootDetectionConstantsSet.WHICH_FILENAME).get(0);
        String superUserFileName = getJSONConstantsList(mContext, RootDetectionConstantsSet.SUPER_USER_FILENAME).get(0);

        try {
            process = Runtime.getRuntime().exec(new String[]{whichFileName, superUserFileName});
            in = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.defaultCharset()));
            String line = in.readLine();
            return deliverResult(line != null);
        } catch (IOException ex) {
            return deliverResult(false);
        } finally {
            if (process != null) {
                process.destroy();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    ApiLoggerResolver.logError(TAG, e.getMessage());
                }
            }
        }
    }
}
