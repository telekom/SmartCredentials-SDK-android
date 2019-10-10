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

package de.telekom.smartcredentials.networking.request.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

class TrustManagerProvider {

    private static final String X_509 = "X.509";
    private static final String CERTIFICATE_KEY_STORE_ALIAS = "ca";

    static X509TrustManager getTrustManager(InputStream inputStream) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(getTrustManagerKeyStore(inputStream));

        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        return (X509TrustManager) trustManagers[0];
    }

    private static KeyStore getTrustManagerKeyStore(InputStream inputStream) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);
        keyStore.setCertificateEntry(CERTIFICATE_KEY_STORE_ALIAS, getCertificate(inputStream));
        return keyStore;
    }

    private static Certificate getCertificate(InputStream inputStream) throws CertificateException, IOException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance(X_509);
        Certificate certificate = certificateFactory.generateCertificate(inputStream);
        inputStream.close();

        return certificate;
    }
}
