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

package de.telekom.smartcredentials.authorization.fragments.testutils;

import androidx.test.rule.ActivityTestRule;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import de.telekom.smartcredentials.authorization.R;
import de.telekom.smartcredentials.authorization.fragments.TestActivity;

public class FragmentTestRule extends ActivityTestRule<TestActivity> {

    private final Fragment mFragment;

    public FragmentTestRule(Fragment fragment) {
        super(TestActivity.class, true, false);
        mFragment = fragment;
    }

    @Override
    protected void afterActivityLaunched() {
        super.afterActivityLaunched();
        getActivity().runOnUiThread(() -> {
            if (mFragment instanceof DialogFragment) {
                ((DialogFragment) mFragment).show(getActivity().getSupportFragmentManager(), "");
                return;
            }

            FragmentManager manager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.sc_plugin_id_container, mFragment);
            transaction.commitAllowingStateLoss();
        });
    }
}