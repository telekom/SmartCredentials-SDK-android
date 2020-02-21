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

package de.telekom.smartcredentials.persistentlogging.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.persistentlogging.files.FileGenerator;
import de.telekom.smartcredentials.persistentlogging.files.FileGeneratorFactory;

/**
 * Created by Alex.Graur@endava.com at 2/19/2020
 */
public class LogWriter {

    private static final String TAG = "LogWriter";
    private static final String ERROR_CREATE_FILE = "Failed to create file %s";
    private static final String ERROR_CREATE_DIRECTORY = "Failed to create directory %s";
    private static final String ERROR_WRITE_LOG = "Failed to write log in file %s";

    private final FileGeneratorFactory mFileGeneratorFactory;

    public LogWriter() {
        mFileGeneratorFactory = new FileGeneratorFactory();
    }

    public void write(File directoryFile, String filename, boolean appendDate,
                      String extension, String log) {
        boolean directoryExists = directoryFile.exists();
        boolean directoryCreated = false;

        if (!directoryExists) {
            directoryCreated = directoryFile.mkdir();
        }

        if (directoryExists || directoryCreated) {
            FileGenerator fileGenerator = mFileGeneratorFactory.getFileGenerator(appendDate);
            File logFile = fileGenerator.generateFile(directoryFile, filename, extension);
            boolean fileExists = logFile.exists();
            boolean fileCreated = false;

            if (!fileExists) {
                try {
                    fileCreated = logFile.createNewFile();
                } catch (IOException e) {
                    ApiLoggerResolver.logError(TAG, String.format(ERROR_CREATE_FILE, filename));
                }
            }

            if (fileExists || fileCreated) {
                try {
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(logFile, true));
                    bufferedWriter.append(log);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    bufferedWriter.close();
                } catch (IOException e) {
                    ApiLoggerResolver.logError(TAG, String.format(ERROR_WRITE_LOG, filename));
                }
            }
        } else {
            ApiLoggerResolver.logError(TAG, String.format(ERROR_CREATE_DIRECTORY, directoryFile.getName()));
        }
    }
}
