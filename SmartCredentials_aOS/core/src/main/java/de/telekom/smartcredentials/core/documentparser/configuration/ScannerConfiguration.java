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

import de.telekom.smartcredentials.core.documentparser.model.BlurDetection;
import de.telekom.smartcredentials.core.documentparser.model.DarknessDetection;
import de.telekom.smartcredentials.core.documentparser.model.DetectionMode;
import de.telekom.smartcredentials.core.documentparser.model.GlareDetection;
import de.telekom.smartcredentials.core.documentparser.model.PhotocopyDetection;

/**
 * Created by gabriel.blaj@endava.com at 10/19/2020
 */
public class ScannerConfiguration {

    private DetectionMode mDetectionMode;
    private BlurDetection mBlurDetection;
    private GlareDetection mGlareDetection;
    private DarknessDetection mDarknessDetection;
    private PhotocopyDetection mPhotocopyDetection;

    ScannerConfiguration(){
        this.mDetectionMode = DetectionMode.MACHINE_LEARNING;
        this.mBlurDetection = BlurDetection.STRICT;
        this.mGlareDetection = GlareDetection.WHITE;
        this.mDarknessDetection = DarknessDetection.RELAXED;
        this.mPhotocopyDetection = PhotocopyDetection.BLACK_AND_WHITE;
    }

    private ScannerConfiguration(ConfigurationBuilder builder) {
        mDetectionMode = builder.detectionMode;
        mBlurDetection = builder.blurDetection;
        mGlareDetection = builder.glareDetection;
        mDarknessDetection = builder.darknessDetection;
        mPhotocopyDetection = builder.photocopyDetection;
    }

    public DetectionMode getDetectionMode() {
        return mDetectionMode;
    }

    public BlurDetection getBlurDetection() {
        return mBlurDetection;
    }

    public GlareDetection getGlareDetection() {
        return mGlareDetection;
    }

    public DarknessDetection getDarknessDetection() {
        return mDarknessDetection;
    }

    public PhotocopyDetection getPhotocopyDetection() {
        return mPhotocopyDetection;
    }

    public static class ConfigurationBuilder {
        private DetectionMode detectionMode;
        private BlurDetection blurDetection;
        private GlareDetection glareDetection;
        private DarknessDetection darknessDetection;
        private PhotocopyDetection photocopyDetection;

        public ConfigurationBuilder(DetectionMode detectionMode,
                                    BlurDetection blurDetection,
                                    GlareDetection glareDetection,
                                    DarknessDetection darknessDetection,
                                    PhotocopyDetection photocopyDetection) {
            this.detectionMode = detectionMode;
            this.blurDetection = blurDetection;
            this.glareDetection = glareDetection;
            this.darknessDetection = darknessDetection;
            this.photocopyDetection = photocopyDetection;
        }

        public ScannerConfiguration build() {
            return new ScannerConfiguration(this);
        }
    }
}
