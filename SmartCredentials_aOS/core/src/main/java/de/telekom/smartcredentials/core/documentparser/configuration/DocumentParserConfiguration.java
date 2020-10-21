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

package de.telekom.smartcredentials.core.documentparser.configuration;

import de.telekom.smartcredentials.core.documentparser.model.Brand;
import de.telekom.smartcredentials.core.documentparser.model.ParsingEnvironment;

/**
 * Created by gabriel.blaj@endava.com at 10/19/2020
 */
public class DocumentParserConfiguration {

    private Brand mBrand;
    private ParsingEnvironment mEnvironment;
    private String mJwtToken;
    private ScannerConfiguration mScannerConfiguration;

    private DocumentParserConfiguration(ConfigurationBuilder builder) {
        mBrand = builder.brand;
        mEnvironment = builder.environment;
        mJwtToken = builder.jwtToken;
        mScannerConfiguration = builder.scannerConfiguration;
    }

    public Brand getBrand() {
        return mBrand;
    }

    public ParsingEnvironment getEnvironment() {
        return mEnvironment;
    }

    public String getJwtToken() {
        return mJwtToken;
    }

    public ScannerConfiguration getScannerConfiguration() {
        return mScannerConfiguration;
    }

    public static class ConfigurationBuilder {
        private Brand brand;
        private ParsingEnvironment environment;
        private String jwtToken;
        private ScannerConfiguration scannerConfiguration;

        public ConfigurationBuilder(Brand brand, ParsingEnvironment environment, String jwtToken) {
            this.brand = brand;
            this.environment = environment;
            this.jwtToken = jwtToken;
            this.scannerConfiguration = new ScannerConfiguration();
        }

        public ConfigurationBuilder setScannerConfiguration(ScannerConfiguration scannerConfiguration) {
            this.scannerConfiguration = scannerConfiguration;
            return this;
        }

        public DocumentParserConfiguration build() {
            return new DocumentParserConfiguration(this);
        }
    }
}