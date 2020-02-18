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

package de.telekom.smartcredentials.eid.messages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alex.Graur@endava.com at 2/10/2020
 */
public class Description {

    @SerializedName("issuerName")
    @Expose
    private String mIssuerName;
    @SerializedName("issuerUrl")
    @Expose
    private String mIssuerUrl;
    @SerializedName("subjectName")
    @Expose
    private String mSubjectName;
    @SerializedName("subjectUrl")
    @Expose
    private String mSubjectUrl;
    @SerializedName("termsOfUsage")
    @Expose
    private String mTermsOfUsage;
    @SerializedName("purpose")
    @Expose
    private String mPurpose;

    public String getIssuerName() {
        return mIssuerName;
    }

    public void setIssuerName(String issuerName) {
        this.mIssuerName = issuerName;
    }

    public String getIssuerUrl() {
        return mIssuerUrl;
    }

    public void setIssuerUrl(String issuerUrl) {
        this.mIssuerUrl = issuerUrl;
    }

    public String getSubjectName() {
        return mSubjectName;
    }

    public void setSubjectName(String subjectName) {
        this.mSubjectName = subjectName;
    }

    public String getSubjectUrl() {
        return mSubjectUrl;
    }

    public void setSubjectUrl(String subjectUrl) {
        this.mSubjectUrl = subjectUrl;
    }

    public String getTermsOfUsage() {
        return mTermsOfUsage;
    }

    public void setTermsOfUsage(String termsOfUsage) {
        this.mTermsOfUsage = termsOfUsage;
    }

    public String getPurpose() {
        return mPurpose;
    }

    public void setPurpose(String purpose) {
        this.mPurpose = purpose;
    }
}
