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
public class VersionInfo {

    @SerializedName("Name")
    @Expose
    private String mName;
    @SerializedName("Implementation-Title")
    @Expose
    private String mImplementationTitle;
    @SerializedName("Implementation-Vendor")
    @Expose
    private String mImplementationVendor;
    @SerializedName("Implementation-Version")
    @Expose
    private String mImplementationVersion;
    @SerializedName("Specification-Title")
    @Expose
    private String mSpecificationTitle;
    @SerializedName("Specification-Vendor")
    @Expose
    private String mSpecificationVendor;
    @SerializedName("Specification-Version")
    @Expose
    private String mSpecificationVersion;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getImplementationTitle() {
        return mImplementationTitle;
    }

    public void setImplementationTitle(String implementationTitle) {
        this.mImplementationTitle = implementationTitle;
    }

    public String getImplementationVendor() {
        return mImplementationVendor;
    }

    public void setImplementationVendor(String implementationVendor) {
        this.mImplementationVendor = implementationVendor;
    }

    public String getImplementationVersion() {
        return mImplementationVersion;
    }

    public void setImplementationVersion(String implementationVersion) {
        this.mImplementationVersion = implementationVersion;
    }

    public String getSpecificationTitle() {
        return mSpecificationTitle;
    }

    public void setSpecificationTitle(String specificationTitle) {
        this.mSpecificationTitle = specificationTitle;
    }

    public String getSpecificationVendor() {
        return mSpecificationVendor;
    }

    public void setSpecificationVendor(String specificationVendor) {
        this.mSpecificationVendor = specificationVendor;
    }

    public String getSpecificationVersion() {
        return mSpecificationVersion;
    }

    public void setSpecificationVersion(String specificationVersion) {
        this.mSpecificationVersion = specificationVersion;
    }
}
