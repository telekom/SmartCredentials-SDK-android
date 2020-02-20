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

package de.telekom.smartcredentials.persistentlogging.controllers;

import java.io.File;

import de.telekom.smartcredentials.core.api.LoggingApi;
import de.telekom.smartcredentials.persistentlogging.writer.LogWriter;

/**
 * Created by Alex.Graur@endava.com at 11/8/2019
 */
public class LoggingController implements LoggingApi {

    private final LogWriter mLogWriter;

    public LoggingController() {
        mLogWriter = new LogWriter();
    }

    @Override
    public void persistLog(File rootDirectory, String directory, String filename, boolean appendDate, String extension, String log) {
        File directoryFile = new File(rootDirectory, directory);
        mLogWriter.write(directoryFile, filename, appendDate, extension, log);
    }
}
