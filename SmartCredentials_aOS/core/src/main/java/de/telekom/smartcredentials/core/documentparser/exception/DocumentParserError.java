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

package de.telekom.smartcredentials.core.documentparser.exception;

public enum DocumentParserError {
    INVALID_LICENSE("Invalid license."),
    INVALID_BRAND("Invalid brand."),
    INVALID_ENVIRONMENT("Invalid parsing environment."),
    INVALID_JWT("Invalid jwt."),
    INVALID_FCM_SENDER_ID("Invalid FCM sender ID."),
    INVALID_SCANNER_CONFIGURATION("Invalid Scanner Configuration."),
    INVALID_PARSER_CONFIGURATION("Invalid Document Parser Configuration"),
    INVALID_PARSING_COMPLETION_CALLBACK("Parsing Completion callback have not been initialized."),
    INVALID_PROCESSING_COMPLETION_CALLBACK("Processing Completion callback have not been initialized."),
    INVALID_OTHER_DOCUMENTS_CALLBACK("Other Documents callback have not been initialized.");

    private String mDesc;

    DocumentParserError(String desc) {
        this.mDesc = desc;
    }

    public String getDesc() {
        return mDesc;
    }
}
