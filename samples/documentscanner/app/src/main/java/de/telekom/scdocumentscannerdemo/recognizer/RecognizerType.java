package de.telekom.scdocumentscannerdemo.recognizer;

/**
 * Created by gabriel.blaj@endava.com at 9/16/2020
 */
public enum RecognizerType {

    ID_COMBINED_RECOGNIZER("ID Combined Recognizer"),
    ID_SIMPLE_RECOGNIZER("ID Simple Recognizer"),
    ID_BARCODE_RECOGNIZER("ID Barcode Recognizer"),
    USDL_COMBINED_RECOGNIZER("USDL Combined Recognizer");

    private final String desc;

    RecognizerType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
