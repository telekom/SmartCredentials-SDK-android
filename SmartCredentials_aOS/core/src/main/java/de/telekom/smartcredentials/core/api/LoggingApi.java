/*
 * Copyright (c) 2020 Telekom Deutschland AG
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

package de.telekom.smartcredentials.core.api;

import android.content.Context;

/**
 * Created by Alex.Graur@endava.com at 2/19/2020
 */
public interface LoggingApi {

    /**
     * Persist a log as a {@link String} in a {@link java.io.File} to external storage / SD card.
     *
     * @param directory  ontaining the log file
     * @param name       of the file
     * @param appendDate <code>true</code> if you want to append the current date to the file name,
     *                   <code>false</code> otherwise
     * @param extension  of the file
     * @param log        to be persisted in the file
     */
    @SuppressWarnings("unused")
    void persistLog(String directory, String name, boolean appendDate, String extension, String log);

    /**
     * Persist a log as a {@link String} in a {@link java.io.File} to internal storage.
     *
     * @param context    of the application
     * @param directory  containing the log file
     * @param name       of the file
     * @param appendDate <code>true</code> if you want to append the current date to the file name,
     *                   <code>false</code> otherwise
     * @param extension  of the file
     * @param log        to be persisted in the file
     */
    @SuppressWarnings("unused")
    void persistLog(Context context, String directory, String name, boolean appendDate, String extension, String log);
}