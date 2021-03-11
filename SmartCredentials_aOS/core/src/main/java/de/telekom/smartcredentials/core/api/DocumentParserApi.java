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

import java.util.Map;

import de.telekom.smartcredentials.core.documentparser.callbacks.OtherDocumentsCallback;
import de.telekom.smartcredentials.core.documentparser.callbacks.ParsingCompletionCallback;
import de.telekom.smartcredentials.core.documentparser.callbacks.ProcessingCompletionCallback;
import de.telekom.smartcredentials.core.documentparser.configuration.DocumentParserConfiguration;

/**
 * Created by Alex.Graur@endava.com at 9/10/2020
 */
@SuppressWarnings("unused")
public interface DocumentParserApi {

    void initialize(DocumentParserConfiguration configuration);

    void setParsingCompletionListener(ParsingCompletionCallback callback);

    void setProcessingCompletionListener(ProcessingCompletionCallback callback);

    void setOtherDocumentsListener(OtherDocumentsCallback callback);

    void sendNotificationData(Context context, Map<String, String> notificationData);

    void pollData(Context context);

    void submitData(Context context);
    
    void retrySubmission(Context context);
}
