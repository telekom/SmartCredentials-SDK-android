/*
 * Copyright (c) 2022 Telekom Deutschland AG
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

package de.telekom.smartcredentials.eid.commands;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import de.telekom.smartcredentials.eid.commands.types.EidCommandType;

/**
 * Created by axel.nennker@telekom.de at 03/09/2022
 */
public class SetCardCommand extends SmartEidCommand {

    public static class SimulatorFile {
        @SerializedName("fileId")
        @Expose
        private String mFileId;
        @SerializedName("shortFileId")
        @Expose
        private String mShortFileId;
        @SerializedName("content")
        @Expose
        private String mContent;
        public SimulatorFile(String fileId, String shortFileId, String content) {
            mFileId = fileId;
            mShortFileId = shortFileId;
            mContent = content;
        }

        public String getFileId() {
            return mFileId;
        }

        public String getShortFileId() {
            return mShortFileId;
        }

        public String getContent() {
            return mContent;
        }
    }

    static class Simulator {
        @SerializedName("files")
        @Expose
        private final SimulatorFile[] mFiles;
        public Simulator(SimulatorFile[] files) {
            mFiles = files;
        }

        public SimulatorFile[] getFiles() {
            return mFiles;
        }
    }

    @SerializedName("simulator")
    @Expose
    private final Simulator mSimulator;

    @SerializedName("name")
    @Expose
    private final String mReaderName;

    public SetCardCommand(@NonNull String readerName, SimulatorFile[] files) {
        super(EidCommandType.SET_CARD.getCommandType());
        mReaderName = readerName;
        mSimulator = new Simulator(files);
    }

    public String getReaderName() {
        return mReaderName;
    }

    public Simulator getSimulator() {
        return mSimulator;
    }
}
