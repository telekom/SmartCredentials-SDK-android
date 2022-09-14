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

import java.util.List;

import de.telekom.smartcredentials.core.rootdetector.RootDetectionConstantsSet;
import de.telekom.smartcredentials.core.rootdetector.RootDetectionOption;

import static de.telekom.smartcredentials.core.rootdetector.RootDetectionConstants.getJSONConstantsList;

public class RootManagementApplicationStrategy extends RootDetectionStrategy {

    public RootManagementApplicationStrategy(Context context, RootDetectionOptionListener listener) {
        super(context, RootDetectionOption.ROOT_MANAGEMENT_APPLICATIONS_EXISTS, listener);
    }

    @Override
    public boolean check() {
        List<String> generalRootApplicationsPackagesList =
                getJSONConstantsList(mContext, RootDetectionConstantsSet.GENERAL_ROOT_APPLICATIONS_PACKAGES);
        return deliverResult(existsPackageInstalled(generalRootApplicationsPackagesList));
    }
}
