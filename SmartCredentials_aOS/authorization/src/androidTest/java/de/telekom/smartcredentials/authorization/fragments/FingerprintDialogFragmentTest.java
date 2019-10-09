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

package de.telekom.smartcredentials.authorization.fragments;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Build;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.telekom.smartcredentials.authorization.R;
import de.telekom.smartcredentials.authorization.fingerprint.FingerprintLayoutAction;
import de.telekom.smartcredentials.authorization.fragments.presenters.FingerprintPresenter;
import de.telekom.smartcredentials.authorization.fragments.testutils.FragmentTestRule;
import de.telekom.smartcredentials.core.plugins.callbacks.AuthorizationPluginCallback;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.anyIntent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static de.telekom.smartcredentials.authorization.fragments.testutils.CustomMatcher.withDrawableId;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = Build.VERSION_CODES.M)
public class FingerprintDialogFragmentTest {

    private static final String ON_SUCCESS = "on_success";
    private static final String ON_AUTH_UNAVAILABLE = "on_auth_unavailable";
    private static final String ON_FAILED = "on_failed";

    private AuthorizationPluginCallbackImpl mPluginCallback;
    private FingerprintDialogFragment mFingerprintDialogFragment;
    private Activity mActivity;

    @Rule
    public FragmentTestRule mFingerprintDialogFragmentTestRule;

    @Before
    public void setUp() {
        mPluginCallback = new AuthorizationPluginCallbackImpl();
        mFingerprintDialogFragment = FingerprintDialogFragment.getInstance(mPluginCallback);
        mFingerprintDialogFragment.mPresenter = new FingerprintPresenter(null);
        mFingerprintDialogFragmentTestRule = new FragmentTestRule(mFingerprintDialogFragment);
        mActivity = mFingerprintDialogFragmentTestRule.launchActivity(null);
    }

    @Test
    public void fragmentCanBeDisplayed() {
        onView(withText(mActivity.getString(R.string.sc_plugin_string_fingerprint_sign_in))).check(matches(isDisplayed()));
        onView(withId(R.id.sc_dialog_layout_fingerprint)).check(matches(isDisplayed()));
        onView(withId(R.id.sc_layout_fingerprint)).check(matches(isDisplayed()));

        onView(withId(R.id.sc_text_fingerprint_description))
                .check(matches(allOf(
                        withText(mActivity.getString(R.string.sc_plugin_string_fingerprint_description)),
                        isDisplayed()
                )));

        onView(withId(R.id.sc_image_fingerprint_icon))
                .check(matches(allOf(
                        withContentDescription(R.string.sc_plugin_string_description_fingerprint_icon),
                        withDrawableId(R.drawable.sc_ic_dialog_fingerprint),
                        isDisplayed()
                )));

        onView(withId(R.id.sc_text_fingerprint_status))
                .check(matches(allOf(
                        withText(mActivity.getString(R.string.sc_plugin_string_fingerprint_hint)),
                        isDisplayed()
                )));

        onView(withId(R.id.sc_button_cancel))
                .check(matches(allOf(
                        withText(mActivity.getString(R.string.sc_plugin_string_cancel)),
                        isEnabled(),
                        isDisplayed()
                )));


        onView(withId(R.id.sc_button_auth))
                .check(matches(allOf(
                        withText(mActivity.getString(R.string.sc_plugin_string_use_pin)),
                        isEnabled(),
                        isDisplayed()
                )));
    }

    @Test
    public void fragmentIsDismissedWhenCancelButtonIsClicked() {
        onView(withId(R.id.sc_layout_fingerprint)).check(matches(isDisplayed()));
        assertTrue(mFingerprintDialogFragment.isVisible());
        onView(withId(R.id.sc_button_cancel)).perform(click());
        assertFalse(mFingerprintDialogFragment.isVisible());
    }

    @Test
    public void fragmentIsDismissedWhenBackButtonIsClicked() {
        onView(withId(R.id.sc_layout_fingerprint)).check(matches(isDisplayed()));
        assertTrue(mFingerprintDialogFragment.isVisible());
        onView(isRoot()).perform(pressBack());
        assertFalse(mFingerprintDialogFragment.isVisible());
    }

    @Test
    public void testUserAuthorizationWithPinSucceeded() {
        onView(withId(R.id.sc_layout_fingerprint)).check(matches(isDisplayed()));
        assertTrue(mFingerprintDialogFragment.isVisible());

        Intent intent = new Intent();
        Instrumentation.ActivityResult intentResult = new Instrumentation.ActivityResult(Activity.RESULT_OK, intent);

        Intents.init();
        intending(anyIntent()).respondWith(intentResult);

        onView(withId(R.id.sc_button_auth)).check(matches(isDisplayed())).perform(click());
        assertEquals(ON_SUCCESS, mPluginCallback.mMessage);

        Intents.release();
    }

    @Test
    public void testUserAuthorizationWithPinFailsIsResultCodeIsNotOK() {
        onView(withId(R.id.sc_layout_fingerprint)).check(matches(isDisplayed()));
        assertTrue(mFingerprintDialogFragment.isVisible());

        Intent intent = new Intent();
        Instrumentation.ActivityResult intentResult = new Instrumentation.ActivityResult(0, intent);

        Intents.init();
        intending(anyIntent()).respondWith(intentResult);

        onView(withId(R.id.sc_button_auth)).check(matches(isDisplayed())).perform(click());
        assertEquals(ON_FAILED, mPluginCallback.mMessage);

        Intents.release();
    }

    @Test
    public void testUserAuthorizationWithFingerprintSucceeded() {
        onView(withId(R.id.sc_layout_fingerprint)).check(matches(isDisplayed()));
        assertTrue(mFingerprintDialogFragment.isVisible());
        onView(withId(R.id.sc_layout_fingerprint)).perform(new FingerprintLayoutAction(FingerprintLayoutAction.Action.ON_AUTH));

        onView(withId(R.id.sc_image_fingerprint_icon))
                .check(matches(allOf(
                        withContentDescription(R.string.sc_plugin_string_description_fingerprint_icon),
                        withDrawableId(R.drawable.sc_ic_dialog_fingerprint_success),
                        isDisplayed()
                )));

        onView(withId(R.id.sc_text_fingerprint_status))
                .check(matches(allOf(
                        withText(mActivity.getString(R.string.sc_plugin_string_fingerprint_success)),
                        isDisplayed()
                )));

        onView(withId(R.id.sc_button_cancel))
                .check(matches(allOf(
                        withText(mActivity.getString(R.string.sc_plugin_string_cancel)),
                        not(isEnabled()),
                        isDisplayed()
                )));


        onView(withId(R.id.sc_button_auth))
                .check(matches(allOf(
                        withText(mActivity.getString(R.string.sc_plugin_string_use_pin)),
                        not(isEnabled()),
                        isDisplayed()
                )));

        assertEquals(ON_SUCCESS, mPluginCallback.mMessage);
    }

    @Test
    public void testUserAuthorizationWithFingerprintFails() {
        onView(withId(R.id.sc_layout_fingerprint)).check(matches(isDisplayed()));
        assertTrue(mFingerprintDialogFragment.isVisible());
        onView(withId(R.id.sc_layout_fingerprint)).perform(new FingerprintLayoutAction(FingerprintLayoutAction.Action.ON_FAILED));

        onView(withId(R.id.sc_image_fingerprint_icon))
                .check(matches(allOf(
                        withContentDescription(R.string.sc_plugin_string_description_fingerprint_icon),
                        withDrawableId(R.drawable.sc_ic_dialog_fingerprint_error),
                        isDisplayed()
                )));

        onView(withId(R.id.sc_text_fingerprint_status))
                .check(matches(allOf(
                        withText(mActivity.getString(R.string.sc_plugin_string_fingerprint_not_recognized)),
                        isDisplayed()
                )));

        onView(withId(R.id.sc_button_cancel))
                .check(matches(allOf(
                        withText(mActivity.getString(R.string.sc_plugin_string_cancel)),
                        isEnabled(),
                        isDisplayed()
                )));


        onView(withId(R.id.sc_button_auth))
                .check(matches(allOf(
                        withText(mActivity.getString(R.string.sc_plugin_string_use_pin)),
                        isEnabled(),
                        isDisplayed()
                )));

        assertNull(mPluginCallback.mMessage);
    }

    private class AuthorizationPluginCallbackImpl extends AuthorizationPluginCallback {
        String mMessage;

        @Override
        public void onAuthorized() {
            mMessage = ON_SUCCESS;
        }

        @Override
        public void onPluginUnavailable(Object errorMessage) {
            mMessage = ON_AUTH_UNAVAILABLE;
        }

        @Override
        public void onFailed(Object message) {
            mMessage = ON_FAILED;
        }
    }
}