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

package de.telekom.smartcredentials.camera.views.presenters;

import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.ref.WeakReference;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SuppressWarnings("unchecked")
public class WeakViewClassResolverTest {

    private View mView;
    private WeakRefClassResolver mWeakRefClassResolver;

    @Before
    public void setUp() {
        mView = Mockito.mock(View.class);

        mWeakRefClassResolver = new WeakRefClassResolver<View>() {
            @Override
            public void onWeakRefResolved(View ref) {
                mView.bringToFront();
            }
        };
    }

    @Test
    public void executeDoesNotCallAnyOtherMethodWhenViewReferenceIsNull() {
        mWeakRefClassResolver.execute(new WeakReference<>(null));

        verify(mView, never()).bringToFront();
    }

    @Test
    public void executeCallsViewMethodWhenReferenceIsNotNull() {
        mWeakRefClassResolver.execute(new WeakReference<>(mView));

        verify(mView).bringToFront();
    }

}