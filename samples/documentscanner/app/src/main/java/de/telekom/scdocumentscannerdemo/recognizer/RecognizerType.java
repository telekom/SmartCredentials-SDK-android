package de.telekom.scdocumentscannerdemo.recognizer;

/**
 * Created by gabriel.blaj@endava.com at 9/16/2020
 */
public enum RecognizerType {

    ID_COMBINED_RECOGNIZER("ID Combined Recognizer"),
    ID_SIMPLE_RECOGNIZER("ID Simple Recognizer"),
    DOCUMENT_FACE_RECOGNIZER("Document Face Recognizer"),
    ID_BARCODE_RECOGNIZER("ID Barcode Recognizer"),
    MRTD_COMBINED_RECOGNIZER("MRTD Combined Recognizer"),
    MRTD_SIMPLE_RECOGNIZER("MRTD Simple Recognizer"),
    PASSPORT_RECOGNIZER("Passport Recognizer"),
    USDL_COMBINED_RECOGNIZER("USDL Combined Recognizer"),
    USDL_SIMPLE_RECOGNIZER("USDL Simple Recognizer"),
    VISA_RECOGNIZER("Visa Recognizer");

    private final String desc;

    RecognizerType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
