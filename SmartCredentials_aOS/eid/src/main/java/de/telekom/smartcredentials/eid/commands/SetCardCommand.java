package de.telekom.smartcredentials.eid.commands;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import de.telekom.smartcredentials.eid.commands.builder.CommandBuilder;
import de.telekom.smartcredentials.eid.commands.types.EidCommandType;

public class SetCardCommand extends SmartEidCommand {

    @SerializedName("simulator")
    @Expose
    private Simulator mSimulator;
    @SerializedName("name")
    @Expose
    private String mName;

    private SetCardCommand(Builder builder) {
        super(EidCommandType.SET_CARD.getCommandType());
        this.mSimulator = builder.mSimulator;
        this.mName = builder.mName;
    }

    public Simulator getSimulator() {
        return mSimulator;
    }

    public String getName() {
        return mName;
    }

    public static class Builder implements CommandBuilder<SetCardCommand> {

        private final Simulator mSimulator;
        private String mName;

        public Builder() {
            this.mSimulator = new Simulator(new SimulatorFile[]{});
        }

        public Builder(Simulator simulator) {
            this.mSimulator = simulator;
        }

        public Builder setReaderName(String name) {
            this.mName = name;
            return this;
        }

        public Builder setIssuingEntity(String issuingEntity) {
            mSimulator.setIssuingEntity(issuingEntity);
            return this;
        }

        public Builder setFirstNames(@NonNull String firstnames) {
            mSimulator.setGivenNames(firstnames);
            return this;
        }

        public Builder setBirthName(@NonNull String birthName) {
            mSimulator.setBirthName(birthName);
            return this;
        }

        public Builder setAcademicTitle(@NonNull String academicTitle) {
            mSimulator.setAcademicTitle(academicTitle);
            return this;
        }

        public Builder setNomDePlume(@NonNull String nomDePlume) {
            mSimulator.setNomDePlume(nomDePlume);
            return this;
        }

        public Builder setFamilyNames(@NonNull String familyNames) {
            mSimulator.setFamilyNames(familyNames);
            return this;
        }

        public Builder setDocumentType(@NonNull String documentType) {
            mSimulator.setDocumentType(documentType);
            return this;
        }

        public Builder setBirthDate(@NonNull String birthDate) {
            mSimulator.setBirthDate(birthDate);
            return this;
        }

        public Builder setDateOfExpiry(@NonNull String dateOfExpiry) {
            mSimulator.setDateOfExpiry(dateOfExpiry);
            return this;
        }

        public Builder setDateOfIssuance(@NonNull String dateOfIssuance) {
            mSimulator.setDateOfIssuance(dateOfIssuance);
            return this;
        }

        public Builder setMunicipality(String municipality) {
            mSimulator.setMunicipality(municipality);
            return this;
        }

        public Builder setPlaceOfResidence(String streetAndHousenumber,
                                        String cityname,
                                        String zipcode,
                                        String country) {
            mSimulator.setPlaceOfResidence(streetAndHousenumber, cityname, zipcode, country);
            return this;
        }

        public Builder setPlaceOfBirth(String streetAndHousenumber,
                                    String cityname,
                                    String zipcode,
                                    String country) {
            mSimulator.setPlaceOfBirth(streetAndHousenumber, cityname, zipcode, country);
            return this;
        }

        public Builder setNationality(String nationality) {
            mSimulator.setNationality(nationality);
            return this;
        }

        public Builder setSex(String sex) {
            mSimulator.setSex(sex);
            return this;
        }

        public Builder setResidencePermitI(String residencePermitI) {
            mSimulator.setResidencePermitI(residencePermitI);
            return this;
        }

        public Builder setResidencePermitII(String residencePermitI) {
            mSimulator.setResidencePermitII(residencePermitI);
            return this;
        }

        @Override
        public SetCardCommand build() {
            return new SetCardCommand(this);
        }
    }
}
