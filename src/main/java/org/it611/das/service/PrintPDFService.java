package org.it611.das.service;



public interface PrintPDFService {

    String printDegreeCertification(String id, String ownerName) throws Exception;

    String printVideo(String id, String ownerName) throws Exception;

    String printAudio(String id, String ownerName) throws Exception;

    String printPhoto(String id, String ownerName) throws Exception;

    String printDrivingLicence(String id, String ownerName) throws Exception;

    String printSyxxzl(String id, String ownerName) throws Exception;

}
