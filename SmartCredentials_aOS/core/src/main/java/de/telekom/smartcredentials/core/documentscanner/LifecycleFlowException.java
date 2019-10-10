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

/*
 * Created by Lucian Iacob on 6/29/18 2:06 PM.
 * lucian.iacob@endava.com
 * Deutsche Telekom - All Rights Reserved - ©
 */

package de.telekom.smartcredentials.core.documentscanner;

public class LifecycleFlowException extends RuntimeException {

    public static final String ON_PAUSE_NOT_CALLED = "Cannot stop view that has not been paused. " +
            "Please call onPause() method first. State is: ";
    public static final String ON_STOP_NOT_CALLED = "It is not allowed to call onDestroy() method on " +
            "view that is not stopped. Please call onStop() first. State is: ";
    public static final String ON_RESUME_NOT_CALLED = "Cannot call onPause() on view that has not " +
            "been resumed. Please make sure that your view has been resumed with onResume() method. State is: ";
    public static final String ON_START_NOT_CALLED = "Cannot resume view that has not been started. " +
            "Please call onStart() first. State is ";
    public static final String ON_CREATE_NOT_CALLED = "Cannot start view that has not been created. " +
            "Please call onCreate() first. State is ";
    public static final String ON_CREATE_ALREADY_CALLED = "It is not allowed to call onCreate() on " +
            "already created view. State is: ";
    public static final String ON_START_NOT_CALLED_SWAP_RECOGNIZERS = "Method swapRecognizer() must " +
            "be called after calling onStart() on recognizerView";

    public LifecycleFlowException(String message, RecognizerState recognizerState) {
        super(message + recognizerState.name());
    }
}
