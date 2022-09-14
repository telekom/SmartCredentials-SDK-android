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

import android.content.Context;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Contract;

import java.util.Set;

import de.telekom.smartcredentials.core.rootdetector.strategy.BusyBoxBinaryFilesStrategy;
import de.telekom.smartcredentials.core.rootdetector.strategy.DangerousApplicationsStrategy;
import de.telekom.smartcredentials.core.rootdetector.strategy.DangerousSystemPropertiesStrategy;
import de.telekom.smartcredentials.core.rootdetector.strategy.DefaultRootDetectionStrategy;
import de.telekom.smartcredentials.core.rootdetector.strategy.DetectTestKeysStrategy;
import de.telekom.smartcredentials.core.rootdetector.strategy.ReadWritePermissionsChangedStrategy;
import de.telekom.smartcredentials.core.rootdetector.strategy.RootDetectionOptionListener;
import de.telekom.smartcredentials.core.rootdetector.strategy.RootDetectionStrategy;
import de.telekom.smartcredentials.core.rootdetector.strategy.RootManagementApplicationStrategy;
import de.telekom.smartcredentials.core.rootdetector.strategy.RootNativeStrategy;
import de.telekom.smartcredentials.core.rootdetector.strategy.SuExistsStrategy;
import de.telekom.smartcredentials.core.rootdetector.strategy.SuperUserBinaryFilesStrategy;

public class RootDetectionController implements RootDetectionApi {

    private final Context mContext;

    public RootDetectionController(Context context) {
        mContext = context;
    }

    @Override
    public boolean isSecurityCompromised(Set<RootDetectionOption> rootDetectionOptions) {
        boolean isDeviceRooted = false;
        for (RootDetectionOption option : rootDetectionOptions) {
            isDeviceRooted |= provideRootDetectionStrategy(mContext, option, null).check();
        }
        return isDeviceRooted;
    }

    @Override
    public boolean isSecurityCompromised(Set<RootDetectionOption> rootDetectionOptions, RootDetectionOptionListener listener) {
        boolean isDeviceRooted = false;
        for (RootDetectionOption option : rootDetectionOptions) {
            isDeviceRooted |= provideRootDetectionStrategy(mContext, option, listener).check();
        }
        return isDeviceRooted;
    }

    @NonNull
    @Contract("_, _, _ -> new")
    private RootDetectionStrategy provideRootDetectionStrategy(Context context,
                                                               @NonNull RootDetectionOption option,
                                                               RootDetectionOptionListener listener) {
        switch (option) {
            case CHECK_BUSY_BOX_BINARY_FILES:
                return new BusyBoxBinaryFilesStrategy(context, listener);
            case CHECK_SUPER_USER_BINARY_FILES:
                return new SuperUserBinaryFilesStrategy(context, listener);
            case DANGEROUS_APPLICATIONS_EXISTS:
                return new DangerousApplicationsStrategy(context, listener);
            case DANGEROUS_SYSTEM_PROPERTIES_EXISTS:
                return new DangerousSystemPropertiesStrategy(context, listener);
            case DETECT_TEST_KEYS:
                return new DetectTestKeysStrategy(context, listener);
            case READ_WRITE_PERMISSIONS_CHANGED:
                return new ReadWritePermissionsChangedStrategy(context, listener);
            case ROOT_MANAGEMENT_APPLICATIONS_EXISTS:
                return new RootManagementApplicationStrategy(context, listener);
            case ROOT_NATIVE_EXISTS:
                return new RootNativeStrategy(context, listener);
            case SU_EXISTS:
                return new SuExistsStrategy(context, listener);
            default:
                return new DefaultRootDetectionStrategy(context, listener);
        }
    }
}
