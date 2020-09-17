/*
 * Copyright (c) 2020 Telekom Deutschland AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.telekom.smartcredentials.documentscanner.model.results;

import com.microblink.entities.recognizers.Recognizer;
import com.microblink.entities.recognizers.blinkid.mrtd.MrtdCombinedRecognizer;

import de.telekom.smartcredentials.core.model.DocumentScannerResult;
import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;

/**
 * Created by gabriel.blaj@endava.com at 9/15/2020
 */
public class MrtdCombinedRecognizerResult extends DocumentScannerResult {

    private MrtdCombinedRecognizer.Result result;

    public MrtdCombinedRecognizerResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    public MrtdCombinedRecognizer.Result getResultData() {
        return result;
    }

    public MrtdCombinedRecognizerResult setResult(MrtdCombinedRecognizer.Result result) {
        this.result = result;
        return this;
    }
}
