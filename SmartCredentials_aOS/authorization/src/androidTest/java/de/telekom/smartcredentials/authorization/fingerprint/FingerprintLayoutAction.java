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

package de.telekom.smartcredentials.authorization.fingerprint;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import android.view.View;

import org.hamcrest.Matcher;

import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;

public class FingerprintLayoutAction implements ViewAction {

    private final Action mAction;

    public FingerprintLayoutAction(Action action) {
        mAction = action;
    }

    @Override
    public Matcher<View> getConstraints(){
        return isAssignableFrom(FingerprintLayout.class);
    }

    @Override
    public String getDescription(){
        return "performing " + mAction.mDesc + " action on FingerprintLayout view";
    }

    @Override
    public void perform(UiController uiController, View view){
        FingerprintLayout fingerprintLayout = (FingerprintLayout) view;
        switch (mAction) {
            case ON_AUTH:
                fingerprintLayout.mPresenter.onAuthenticated();
                break;
            case ON_FAILED:
                fingerprintLayout.mPresenter.onFailed();
                break;
            case NONE:
        }
    }

    public enum Action {
        ON_AUTH("onAuthenticated"),
        ON_FAILED("onRetrievingAuthInfoFailed"),
        NONE("none");

        final String mDesc;

        Action(String desc) {
            mDesc = desc;
        }
    }

}
