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

package de.telekom.smartcredentials.camera.ocr.presenters;

import android.os.AsyncTask;

import java.util.Arrays;
import java.util.List;

import de.telekom.smartcredentials.camera.ocr.TextParser;
import de.telekom.smartcredentials.core.plugins.callbacks.ScannerPluginCallback;

public class ParserAsyncTask extends AsyncTask<String, Void, List<String>> {

    private final ScannerPluginCallback mPluginCallback;
    private final TextParser mTextParser;

    static ParserAsyncTask getInstance(ScannerPluginCallback pluginCallback, TextParser textParser) {
        return new ParserAsyncTask(pluginCallback, textParser);
    }

    private ParserAsyncTask(ScannerPluginCallback pluginCallback, TextParser textParser) {
        mPluginCallback = pluginCallback;
        mTextParser = textParser;
    }

    @Override
    protected List<String> doInBackground(String[] textArray) {
        List<String> values = Arrays.asList(textArray);

        if (mTextParser != null) {
            return mTextParser.parse(values);
        }
        return values;
    }

    @Override
    protected void onPostExecute(List<String> parsedText) {
        notifyScannedResultsReady(mPluginCallback, parsedText);
    }

    static void notifyScannedResultsReady(ScannerPluginCallback pluginCallback, List<String> parsedText) {
        if (pluginCallback != null) {
            pluginCallback.onScanned(parsedText);
        }
    }
}
