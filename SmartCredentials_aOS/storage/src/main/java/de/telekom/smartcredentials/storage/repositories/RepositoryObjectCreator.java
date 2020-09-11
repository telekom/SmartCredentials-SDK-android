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

package de.telekom.smartcredentials.storage.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;

import com.google.gson.Gson;

import de.telekom.smartcredentials.core.di.Provides;
import de.telekom.smartcredentials.storage.database.AppDatabase;
import de.telekom.smartcredentials.storage.prefs.PreferencesManager;
import de.telekom.smartcredentials.storage.prefs.SharedPreferencesRepoFiveFourteen;
import de.telekom.smartcredentials.storage.prefs.SharedPreferencesRepoFourTwo;

/**
 * Created by Lucian Iacob on November 05, 2018.
 */
public class RepositoryObjectCreator {

    private static final String SHARED_PREFS_NAME = "SmartCredentialsPreferences";

    private final Context mContext;
    private final AppDatabase mAppDatabase;
    private final Gson mGson;

    public RepositoryObjectCreator(final Context context, final Gson gson) {
        mContext = context;
        mGson = gson;
        mAppDatabase = AppDatabase.getDatabase(context);
    }

    @Provides
    @NonNull
    public SensitiveDataRepository provideSensitiveDataRepo() {
        return new SensitiveDataRepository(provideSharedPreferencesRepoFiveFourteen(),
                provideSharedPreferencesRepoFourTwo());
    }

    @Provides
    @NonNull
    public NonSensitiveDataRepository provideNonSensitiveDataRepo() {
        return new NonSensitiveDataRepository(mAppDatabase.getItemsDao(), mAppDatabase.getPrivateDataDao());
    }

    @Provides
    @NonNull
    private SharedPreferencesRepoFiveFourteen provideSharedPreferencesRepoFiveFourteen() {
        return new SharedPreferencesRepoFiveFourteen(mGson, providePreferenceManager());
    }

    private SharedPreferencesRepoFourTwo provideSharedPreferencesRepoFourTwo() {
        return new SharedPreferencesRepoFourTwo(mGson, providePreferenceManager());
    }

    @Provides
    @NonNull
    private PreferencesManager providePreferenceManager() {
        return new PreferencesManager(provideSharedPreferences());
    }

    @Provides
    @NonNull
    private SharedPreferences provideSharedPreferences() {
        return mContext.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }
}
