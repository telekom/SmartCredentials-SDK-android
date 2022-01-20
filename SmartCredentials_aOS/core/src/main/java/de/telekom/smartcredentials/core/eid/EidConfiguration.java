/*
 * Copyright (c) 2021 Telekom Deutschland AG
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

package de.telekom.smartcredentials.core.eid;

public class EidConfiguration {

    private final String mProductionUrl;
    private final String mTestUrl;
    private final TlsConfiguration mTlsConfiguration;

    private EidConfiguration(ConfigurationBuilder builder) {
        mProductionUrl = builder.productionUrl;
        mTestUrl = builder.testUrl;
        mTlsConfiguration = builder.tlsConfiguration;
    }

    public String getProductionUrl() {
        return mProductionUrl;
    }

    public String getTestUrl() {
        return mTestUrl;
    }

    public TlsConfiguration getTlsConfiguration() {
        return mTlsConfiguration;
    }

    public static class ConfigurationBuilder {
        private final String productionUrl;
        private final String testUrl;
        private final TlsConfiguration tlsConfiguration;

        public ConfigurationBuilder(String productionUrl, String testUrl, TlsConfiguration tlsConfiguration) {
            this.productionUrl = productionUrl;
            this.testUrl = testUrl;
            this.tlsConfiguration = tlsConfiguration;
        }

        public EidConfiguration build() {
            return new EidConfiguration(this);
        }
    }
}
