package org.it611.das.service;

import com.alibaba.fastjson.JSONObject;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

public interface CheckService {

    JSONObject selectDegreeCertificationDetailById(String id) throws InvalidArgumentException, NoSuchAlgorithmException, IOException, NoSuchProviderException, TransactionException, ProposalException, CryptoException, InvalidKeySpecException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;
    JSONObject selectDrivingLicenceDetailById(String id) throws IOException, InvalidArgumentException, NoSuchAlgorithmException, NoSuchProviderException, TransactionException, ProposalException, CryptoException, InvalidKeySpecException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;
    JSONObject selectSyxxzlDetailById(String id) throws IOException, InvalidArgumentException, NoSuchAlgorithmException, NoSuchProviderException, TransactionException, ProposalException, CryptoException, InvalidKeySpecException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;
    JSONObject selectImageDetailById(String id) throws IOException, InvalidArgumentException, NoSuchAlgorithmException, NoSuchProviderException, TransactionException, ProposalException, CryptoException, InvalidKeySpecException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;
    JSONObject selectAudioDetailById(String id) throws IOException, NoSuchAlgorithmException, InvocationTargetException, NoSuchMethodException, InstantiationException, InvalidKeySpecException, CryptoException, InvalidArgumentException, IllegalAccessException, NoSuchProviderException, TransactionException, ClassNotFoundException, ProposalException;
    JSONObject selectVideoDetailById(String id) throws IOException, InvalidArgumentException, NoSuchAlgorithmException, NoSuchProviderException, TransactionException, ProposalException, CryptoException, InvalidKeySpecException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;

}
