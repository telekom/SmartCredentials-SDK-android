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

import de.telekom.smartcredentials.core.documentparser.callbacks.ParsingCompletionCallback;
import de.telekom.smartcredentials.core.documentparser.configuration.DocumentParserConfiguration;
import de.telekom.smartcredentials.core.documentparser.model.DocumentType;

/**
 * Created by Alex.Graur@endava.com at 9/10/2020
 */
public interface DocumentParserApi {

    @SuppressWarnings("unused")
    void initialize(DocumentParserConfiguration configuration);

    @SuppressWarnings("unused")
    void startParsingActivity(Context context, DocumentType documentType, ParsingCompletionCallback callback);
}
