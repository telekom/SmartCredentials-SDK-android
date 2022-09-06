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

import java.util.Arrays;
import java.util.Locale;

import de.persosim.simulator.tlv.Asn1;
import de.persosim.simulator.tlv.Asn1DateWrapper;
import de.persosim.simulator.tlv.Asn1DocumentType;
import de.persosim.simulator.tlv.Asn1PrintableStringWrapper;
import de.persosim.simulator.tlv.Asn1Utf8StringWrapper;
import de.persosim.simulator.tlv.ConstructedTlvDataObject;
import de.persosim.simulator.tlv.PrimitiveTlvDataObject;
import de.persosim.simulator.tlv.TlvConstants;
import de.persosim.simulator.tlv.TlvTag;
import de.persosim.simulator.utils.HexString;
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
        private SimulatorFile[] mFiles;
        public Simulator(SimulatorFile[] files) {
            mFiles = files;
        }

        public SimulatorFile[] getFiles() {
            return mFiles;
        }

        void setFile(@NonNull String fileId, @NonNull String shortFileId, @NonNull String content) {
            boolean found = false;
            for (SimulatorFile simulatorFile : mFiles) {
                if (fileId.equals(simulatorFile.mFileId) && shortFileId.equals(simulatorFile.mShortFileId)) {
                    simulatorFile.mContent = content;
                    found = true;
                }
            }
            if (!found) {
                // not very efficient. refactor to list?
                SimulatorFile simulatorFile = new SimulatorFile(fileId, shortFileId, content);
                mFiles = Arrays.copyOf(mFiles, mFiles.length + 1);
                mFiles[mFiles.length - 1] = simulatorFile;
            }

        }

        // documentType e.g. "ID"
        // DocumentType ::= [APPLICATION 1] ICAOString (SIZE (2))
        public void setDocumentType(String documentType) {
            ConstructedTlvDataObject constructedTlvDataObject = Asn1DocumentType.getInstance().encode(new TlvTag((byte) 0x61), documentType);
            String content = HexString.encode(constructedTlvDataObject.toByteArray()).toLowerCase(Locale.ROOT);
            setFile("0101", "01", content );
        }

        // GivenNames ::= [APPLICATION 4] UTF8String
        public void setGivenNames(@NonNull  String givenNames) {
            ConstructedTlvDataObject constructedTlvDataObject =
                    Asn1Utf8StringWrapper.getInstance().encode(new TlvTag((byte) 0x64), givenNames);
            String content = HexString.encode(constructedTlvDataObject.toByteArray()).toLowerCase(Locale.ROOT);
            setFile("0104", "04", content );
        }

        // GivenNames ::= [APPLICATION 4] UTF8String
        public void setFamilyNames(@NonNull  String familyNames) {
            ConstructedTlvDataObject constructedTlvDataObject =
                    Asn1Utf8StringWrapper.getInstance().encode(new TlvTag((byte) 0x65), familyNames);
            String content = HexString.encode(constructedTlvDataObject.toByteArray()).toLowerCase(Locale.ROOT);
            setFile("0105", "05", content );
        }

        // NomDePlume ::= [APPLICATION 6] UTF8String
        public void setNomDePlume(@NonNull  String nomDePlume) {
            ConstructedTlvDataObject constructedTlvDataObject =
                    Asn1Utf8StringWrapper.getInstance().encode(new TlvTag((byte) 0x66), nomDePlume);
            String content = HexString.encode(constructedTlvDataObject.toByteArray()).toLowerCase(Locale.ROOT);
            setFile("0106", "06", content );
        }

        // AcademicTitle ::= [APPLICATION 7] UTF8String
        public void setAcademicTitle(@NonNull  String academicTitle) {
            ConstructedTlvDataObject constructedTlvDataObject =
                    Asn1Utf8StringWrapper.getInstance().encode(new TlvTag((byte) 0x67), academicTitle);
            String content = HexString.encode(constructedTlvDataObject.toByteArray()).toLowerCase(Locale.ROOT);
            setFile("0107", "07", content );
        }

        // BirthName ::= [APPLICATION 13] UTF8String
        public void setBirthName(@NonNull  String birthName) {
            ConstructedTlvDataObject constructedTlvDataObject =
                    Asn1Utf8StringWrapper.getInstance().encode(new TlvTag((byte) 0x6d), birthName);
            String content = HexString.encode(constructedTlvDataObject.toByteArray()).toLowerCase(Locale.ROOT);
            setFile("010d", "0d", content );
        }

        // DateOfBirth ::= [APPLICATION 8] Date
        // Date ::= NumericString (SIZE (8)) -- YYYYMMDD
        public void setBirthDate(String birthDate) {
            ConstructedTlvDataObject constructedTlvDataObject = Asn1DateWrapper.getInstance().encode(new TlvTag((byte) 0x68), birthDate);
            String content = HexString.encode(constructedTlvDataObject.toByteArray()).toLowerCase(Locale.ROOT);
            setFile("0108", "08", content );
        }

        // DateOfExpiry ::= [APPLICATION 3] Date
        // Date ::= NumericString (SIZE (8)) -- YYYYMMDD
        public void setDateOfExpiry(String dateOfExpiry) {
            ConstructedTlvDataObject constructedTlvDataObject = Asn1DateWrapper.getInstance().encode(new TlvTag((byte) 0x63), dateOfExpiry);
            String content = HexString.encode(constructedTlvDataObject.toByteArray()).toLowerCase(Locale.ROOT);
            setFile("0103", "03", content );
        }

        // DateOfIssuance ::= {APPLICATION 15] Date
        // Date ::= NumericString (SIZE (8)) -- YYYYMMDD
        public void setDateOfIssuance(String dateOfIssuance) {
            ConstructedTlvDataObject constructedTlvDataObject = Asn1DateWrapper.getInstance().encode(new TlvTag((byte) 0x6f), dateOfIssuance);
            String content = HexString.encode(constructedTlvDataObject.toByteArray()).toLowerCase(Locale.ROOT);
            setFile("010F", "0F", content );
        }

        public void setMunicipality(String municipality) {
            byte[] decoded = HexString.toByteArray(municipality);
            PrimitiveTlvDataObject primitiveTlvDataObject =
                    new PrimitiveTlvDataObject(new TlvTag(Asn1.UNIVERSAL_OCTET_STRING), decoded);
            ConstructedTlvDataObject constructedTlvDataObject = new ConstructedTlvDataObject(new TlvTag((byte) 0x72));
            constructedTlvDataObject.addTlvDataObject(primitiveTlvDataObject);
            String content = HexString.encode(constructedTlvDataObject.toByteArray()).toLowerCase(Locale.ROOT);
            setFile("0112", "12", content );
        }

        ConstructedTlvDataObject createAddressCTDO(
                String streetAndHousenumber,
                String cityname,
                String zipcode,
                String country)
        {
            ConstructedTlvDataObject streetCTDO = Asn1Utf8StringWrapper.getInstance().encode(TlvConstants.TAG_AA, streetAndHousenumber);
            ConstructedTlvDataObject cityCTDO = Asn1Utf8StringWrapper.getInstance().encode(TlvConstants.TAG_AB, cityname);

            ConstructedTlvDataObject countryCTDO = Asn1PrintableStringWrapper.getInstance().encode(TlvConstants.TAG_AD, country);
            ConstructedTlvDataObject zipcodeCTDO = Asn1PrintableStringWrapper.getInstance().encode(TlvConstants.TAG_AE, zipcode);

            ConstructedTlvDataObject sequenceCTDO = new ConstructedTlvDataObject(new TlvTag((byte) 0x30));
            sequenceCTDO.addTlvDataObject(streetCTDO);
            sequenceCTDO.addTlvDataObject(cityCTDO);
            sequenceCTDO.addTlvDataObject(countryCTDO);
            sequenceCTDO.addTlvDataObject(zipcodeCTDO);

            ConstructedTlvDataObject addressCTDO = new ConstructedTlvDataObject(new TlvTag((byte) 0x71));
            addressCTDO.addTlvDataObject(sequenceCTDO);

            return addressCTDO;
        }

        public void setPlaceOfResidence(String streetAndHousenumber,
                                        String cityname,
                                        String zipcode,
                                        String country) {
            ConstructedTlvDataObject addressCTDO = createAddressCTDO(streetAndHousenumber, cityname, zipcode,country);
            String content = HexString.encode(addressCTDO.toByteArray()).toLowerCase(Locale.ROOT);
            setFile("0111", "11", content );
        }

        public void setPlaceOfBirth(String streetAndHousenumber,
                                        String cityname,
                                        String zipcode,
                                        String country) {
            ConstructedTlvDataObject addressCTDO = createAddressCTDO(streetAndHousenumber, cityname, zipcode,country);
            String content = HexString.encode(addressCTDO.toByteArray()).toLowerCase(Locale.ROOT);
            setFile("0109", "09", content );
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

    public SetCardCommand(@NonNull String readerName) {
        this(readerName, new SimulatorFile[]{});
    }

    public String getReaderName() {
        return mReaderName;
    }

    public Simulator getSimulator() {
        return mSimulator;
    }

    public void setFirstNames(@NonNull String firstnames) {
        mSimulator.setGivenNames(firstnames);
    }

    public void setBirthName(@NonNull String birthName) {
        mSimulator.setBirthName(birthName);
    }

    public void setAcademicTitle(@NonNull String academicTitle) {
        mSimulator.setAcademicTitle(academicTitle);
    }

    public void setNomDePlume(@NonNull String nomDePlume) {
        mSimulator.setNomDePlume(nomDePlume);
    }

    public void setFamilyNames(@NonNull String familyNames) {
        mSimulator.setFamilyNames(familyNames);
    }

    public void setDocumentType(@NonNull String documentType) {
        mSimulator.setDocumentType(documentType);
    }
    
    public void setBirthDate(@NonNull String birthDate) {
        mSimulator.setBirthDate(birthDate);
    }

    public void setDateOfExpiry(@NonNull String dateOfExpiry) {
        mSimulator.setDateOfExpiry(dateOfExpiry);
    }

    public void setDateOfIssuance(@NonNull String dateOfIssuance) {
        mSimulator.setDateOfIssuance(dateOfIssuance);
    }

    public void setMunicipality(String municipality) {
        mSimulator.setMunicipality(municipality);
    }

    public void setPlaceOfResidence(String streetAndHousenumber,
                                    String cityname,
                                    String zipcode,
                                    String country) {
        mSimulator.setPlaceOfResidence(streetAndHousenumber, cityname, zipcode,country);
    }

    public void setPlaceOfBirth(String streetAndHousenumber,
                                String cityname,
                                String zipcode,
                                String country) {
        mSimulator.setPlaceOfBirth(streetAndHousenumber, cityname, zipcode,country);
    }
}
