package de.telekom.smartcredentials.eid;

public enum DataGroup {
    DG1("DG1", "Document Type", "0x0101", "0x01", "DocumentType"),
    DG2("DG2", "Issuing State, Region and Municipality", "0x0101", "0x01", "IssuingEntity"),
    DG3("DG3", "Date of Expiry", "0x0101", "0x01", "Date"),
    DG4("DG4", "Given Names", "0x0101", "0x01", "GivenNames"),
    DG5("DG5", "Family Names ", "0x0101", "0x01", "FamilyNames"),
    DG6("DG5", "Nom de Plume", "0x0101", "0x01", "NomDePlume"),
    DG7("DG7", "Academic Title", "0x0101", "0x01", "AcademicTitle"),
    DG8("DG8", "Date of Birth", "0x0101", "0x01", "DateOfBirth"),
    DG9("DG9", "Place of Birth", "0x0101", "0x01", "PlaceOfBirth"),
    DG10("DG10", "Nationality", "0x0101", "0x01", "Nationality"),
    DG11("DG11", "Sex", "0x0101", "0x01", "Sex"),
    DG13("DG13", "Birth Name ", "0x0101", "0x01", "BirthName"),
    DG15("DG15", "Date of Issuance", "0x0101", "0x01", "Date"),
    DG17("DG17", "Normal Place of Residence (multiple)", "0x0111", "0x11", "PlaceOfResidence"),
    DG18("DG18", "Municipality ID ", "0x0101", "0x01", "MunicipalityID"),
    DG19("DG19", "Residence Permit I", "0x0101", "0x01", "ResidencePermitI"),
    DG20("DG20", "Residence Permit II", "0x0101", "0x01", "ResidencePermitII");

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
