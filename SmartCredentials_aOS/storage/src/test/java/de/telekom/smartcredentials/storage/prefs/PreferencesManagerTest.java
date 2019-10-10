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

package de.telekom.smartcredentials.storage.prefs;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.text.TextUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest(TextUtils.class)
public class PreferencesManagerTest {

    private static final String mKey = "testKey";
    private static final String mData = "testData";
    private SharedPreferences mSharedPreferences;
    private Map mMap;
    private String mOtherKey;
    private SharedPreferences.Editor mEditor;
    private PreferencesManager mPreferencesManager;

    @SuppressLint("CommitPrefEdits")
    @Before
    public void setUp() {
        PowerMockito.mockStatic(TextUtils.class);

        mMap = new HashMap<>();
        mMap.put(mKey, mData);
        mOtherKey = mKey + "1";
        mMap.put(mOtherKey, mData);

        mSharedPreferences = Mockito.mock(SharedPreferences.class);
        mEditor = Mockito.mock(SharedPreferences.Editor.class);

        mPreferencesManager = new PreferencesManager(mSharedPreferences);
        Mockito.when(mSharedPreferences.edit()).thenReturn(mEditor);
    }

    @Test
    public void saveDoesNothingWhenKeyAndDataIsNull() {
        mPreferencesManager.save(null, null);
        verify(mEditor, never()).putString(mKey, mData);
    }

    @Test
    public void saveDoesNothingWhenKeyIsNull() {
        mPreferencesManager.save(null, mData);
        verify(mEditor, never()).putString(mKey, mData);
    }

    @Test
    public void saveDoesNothingWhenDataIsNull() {
        mPreferencesManager.save(mKey, null);
        verify(mEditor, never()).putString(mKey, mData);
    }

    @Test
    public void saveCallsSharedPreferencesPutString() {
        assertTrue(mSharedPreferences.getAll().isEmpty());
        when(mEditor.putString(anyString(), anyString())).thenReturn(mEditor);
        mPreferencesManager.save(mKey, mData);
        verify(mEditor).putString(mKey, mData);
    }

    @Test
    public void getItemsCountReturnsTotalNumberOfItems() {
        int count = mPreferencesManager.getItemsCount();
        assertEquals(count, 0);

        when(mSharedPreferences.getAll()).thenReturn(mMap);
        count = mPreferencesManager.getItemsCount();
        assertEquals(count, mMap.size());

        when(mSharedPreferences.getAll()).thenReturn(null);
        count = mPreferencesManager.getItemsCount();
        assertEquals(count, 0);
    }

    @Test
    public void getItemsMatchingKeyReturnsEmptyLitWhenKeyIsNull() {
        List<String> items = mPreferencesManager.getItemsMatchingType(null);
        assertNotNull(items);
        assertTrue(items.isEmpty());
    }

    @Test
    public void getItemsMatchingKeyReturnsData() {
        Map map = new HashMap<>();
        String key = mKey + "1";
        map.put(key, mData);
        when(mSharedPreferences.getAll()).thenReturn(map);
        when(mSharedPreferences.getString(key, "")).thenReturn(mData);
        List<String> items = mPreferencesManager.getItemsMatchingType(mKey);
        assertNotNull(items);
        assertEquals(1, items.size());
        assertEquals(items.get(0), mData);
    }

    @Test
    public void getItemsMatchingKeyReturnsEmptyListWhenKeyIsEmpty() {
        when(mSharedPreferences.getAll()).thenReturn(mMap);
        PowerMockito.when(TextUtils.isEmpty(mKey)).thenReturn(true);
        when(mSharedPreferences.getString(mOtherKey, "")).thenReturn(mData);
        List<String> items = mPreferencesManager.getItemsMatchingType(mKey);
        assertNotNull(items);
        assertTrue(items.isEmpty());
    }

    @Test
    public void getItemsMatchingKeyReturnsEmptyListWhenKeyNotMatching() {
        String keyNotMatching = "1234";
        when(mSharedPreferences.getAll()).thenReturn(mMap);
        when(mSharedPreferences.getString(mKey, "")).thenReturn(mData);
        List<String> items = mPreferencesManager.getItemsMatchingType(keyNotMatching);
        assertNotNull(items);
        assertTrue(items.isEmpty());
    }

    @Test
    public void getItemReturnsNullWhenKeyIsNull() {
        String item = mPreferencesManager.getItem(null);
        assertNull(item);
    }

    @Test
    public void getItemReturnsData() {
        when(mSharedPreferences.contains(mKey)).thenReturn(true);
        when(mSharedPreferences.getString(mKey, "")).thenReturn(mData);
        String item = mPreferencesManager.getItem(mKey);
        assertNotNull(item);
        assertEquals(item, mData);
    }

    @Test
    public void getItemsReturnsNullWhenPrefsNotContainsKey() {
        when(mSharedPreferences.contains(mKey)).thenReturn(false);
        String item = mPreferencesManager.getItem(mKey);
        assertNull(item);
    }

    @Test
    public void clearAllCallsClearOnEditor() {
        when(mEditor.clear()).thenReturn(mEditor);
        mPreferencesManager.clearAll();
        verify(mEditor).clear();
    }

    @Test
    public void removeDoesNothingWhenKeyIsNull() {
        mPreferencesManager.remove(null);
        verify(mEditor, never()).remove(null);
    }

    @Test
    public void removeCallsEditorRemove() {
        when(mEditor.remove(mKey)).thenReturn(mEditor);

        mPreferencesManager.remove(mKey);
        verify(mEditor).remove(mKey);
    }

    @Test
    public void updateDoesNothingWhenKeyIsNull() {
        mPreferencesManager.update(null, mData);
        verify(mEditor, never()).putString(mKey, mData);
    }

    @Test
    public void updateCallsSharedPreferencesPutString() {
        when(mEditor.putString(anyString(), anyString())).thenReturn(mEditor);
        mPreferencesManager.update(mKey, mData);
        verify(mEditor).putString(mKey, mData);
    }
}