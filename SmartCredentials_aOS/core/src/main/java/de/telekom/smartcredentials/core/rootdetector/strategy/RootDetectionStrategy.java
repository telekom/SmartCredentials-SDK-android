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
import android.content.pm.PackageManager;

import java.util.List;

import de.telekom.smartcredentials.core.rootdetector.RootDetectionOption;

public abstract class RootDetectionStrategy {

    protected static final String TAG = "RootDetectionStrategy";

    protected Context mContext;
    protected RootDetectionOption mOption;
    protected RootDetectionOptionListener mListener;

    public RootDetectionStrategy(Context context, RootDetectionOption option,
                                 RootDetectionOptionListener listener) {
        mContext = context;
        mOption = option;
        mListener = listener;
    }

    public abstract boolean check();

    public boolean deliverResult(boolean result) {
        if (mListener != null) {
            mListener.onOptionChecked(mOption, result);
        }

        return result;
    }

    /**
     * Check if one or more packages from list is installed in system.
     *
     * @param packages list of packages
     * @return true if at least one package is installed
     */
    boolean existsPackageInstalled(List<String> packages) {
        boolean result = false;
        PackageManager pm = mContext.getPackageManager();

        for (String packageName : packages) {
            try {
                pm.getPackageInfo(packageName, 0);
                result = true;
            } catch (PackageManager.NameNotFoundException e) {
                return false;
            }
        }
        return deliverResult(result);
    }
}
