package de.telekom.smartcredentials.eid;

public enum DataGroup {
    DG1("DG1", "Document Type", "0x0101", "0x01", "DocumentType"),
    DG2("DG2", "Issuing State, Region and Municipality", "0x0102", "0x02", "IssuingEntity"),
    DG3("DG3", "Date of Expiry", "0x0103", "0x03", "Date"),
    DG4("DG4", "Given Names", "0x0104", "0x04", "GivenNames"),
    DG5("DG5", "Family Names ", "0x0105", "0x05", "FamilyNames"),
    DG6("DG5", "Nom de Plume", "0x0106", "0x06", "NomDePlume"),
    DG7("DG7", "Academic Title", "0x0107", "0x07", "AcademicTitle"),
    DG8("DG8", "Date of Birth", "0x0108", "0x08", "DateOfBirth"),
    DG9("DG9", "Place of Birth", "0x0109", "0x09", "PlaceOfBirth"),
    DG10("DG10", "Nationality", "0x010A", "0x0A", "Nationality"),
    DG11("DG11", "Sex", "0x010B", "0x0B", "Sex"),
    DG13("DG13", "Birth Name ", "0x010D", "0x0D", "BirthName"),
    DG15("DG15", "Date of Issuance", "0x010F", "0x0F", "Date"),
    DG17("DG17", "Normal Place of Residence (multiple)", "0x0111", "0x11", "PlaceOfResidence"),
    DG18("DG18", "Municipality ID ", "0x0112", "0x12", "MunicipalityID"),
    DG19("DG19", "Residence Permit I", "0x0113", "0x13", "ResidencePermitI"),
    DG20("DG20", "Residence Permit II", "0x0114", "0x14", "ResidencePermitII");

    private final String mDg;
    private final String mContent;
    private final String mFileId;
    private final String mShortFileId;
    private final String mASN1Type;

    DataGroup(String dg, String content, String fileId, String shortFileId, String ASN1Type) {
        mDg = dg;
        mContent = content;
        mFileId = fileId;
        mShortFileId = shortFileId;
        mASN1Type = ASN1Type;
    }

    public String getDg() {
        return mDg;
    }

    public String getFileId() {
        return mFileId;
    }

    public String getShortFileId() {
        return mShortFileId;
    }

    public String getASN1Type() {
        return mASN1Type;
    }

    public String getContent() {
        return mContent;
    }
}
