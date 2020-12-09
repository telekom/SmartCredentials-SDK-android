package de.telekom.scdocumentscannerdemo.repository;

public interface Repository {

    void storeCapture(int side, byte[] capture);

    byte[] retrieveCapture(int side);

    void storeName(String name);

    String retrieveName();

    void storeMrz(String mrz);

    String retrieveMrz();

    void clearData();
}
