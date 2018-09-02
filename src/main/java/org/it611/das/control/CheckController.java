package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.it611.das.service.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

@Controller
public class CheckController {

    @Autowired
    private CheckService authenticationService;

    @RequestMapping(value = "/check/degreeCertificate/{id}", method = RequestMethod.GET)
    public ModelAndView degreeCertificateAuth(@PathVariable String id) throws IOException, NoSuchAlgorithmException, ProposalException, NoSuchMethodException, InstantiationException, InvalidKeySpecException, InvalidArgumentException, InvocationTargetException, IllegalAccessException, CryptoException, NoSuchProviderException, TransactionException, ClassNotFoundException {

        JSONObject result = authenticationService.selectDegreeCertificationDetailById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("result", result);
        modelAndView.setViewName("compare_certificateAssertDetail");
        return modelAndView;
    }

    @RequestMapping(value = "/check/drivingLicence/{id}", method = RequestMethod.GET)
    public ModelAndView drivingLicenceAuth(@PathVariable String id) throws IOException, NoSuchAlgorithmException, ProposalException, NoSuchMethodException, InstantiationException, InvalidKeySpecException, InvalidArgumentException, InvocationTargetException, IllegalAccessException, CryptoException, NoSuchProviderException, TransactionException, ClassNotFoundException {

        JSONObject result = authenticationService.selectDrivingLicenceDetailById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("result", result);
        modelAndView.setViewName("compare_drivingLicenceAssertDetail");
        return modelAndView;
    }


    @RequestMapping(value = "/check/syxxzl/{id}", method = RequestMethod.GET)
    public ModelAndView syxxzlAuth(@PathVariable String id) throws IOException, NoSuchAlgorithmException, ProposalException, NoSuchMethodException, InstantiationException, InvalidKeySpecException, InvalidArgumentException, InvocationTargetException, IllegalAccessException, CryptoException, NoSuchProviderException, TransactionException, ClassNotFoundException {

        JSONObject result = authenticationService.selectSyxxzlDetailById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("result", result);
        modelAndView.setViewName("compare_syxxzlAssertDetail");
        return modelAndView;
    }

    @RequestMapping(value = "/check/audio/{id}", method = RequestMethod.GET)
    public ModelAndView audioAuth(@PathVariable String id) throws IOException, NoSuchAlgorithmException, ProposalException, NoSuchMethodException, InstantiationException, InvalidKeySpecException, InvalidArgumentException, InvocationTargetException, IllegalAccessException, CryptoException, NoSuchProviderException, TransactionException, ClassNotFoundException {

        JSONObject result = authenticationService.selectAudioDetailById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("result", result);
        modelAndView.setViewName("compare_musicAssetDetail");
        return modelAndView;
    }

    @RequestMapping(value = "/check/video/{id}", method = RequestMethod.GET)
    public ModelAndView videoAuth(@PathVariable String id) throws IOException, NoSuchAlgorithmException, ProposalException, NoSuchMethodException, InstantiationException, InvalidKeySpecException, InvalidArgumentException, InvocationTargetException, IllegalAccessException, CryptoException, NoSuchProviderException, TransactionException, ClassNotFoundException {

        JSONObject result = authenticationService.selectVideoDetailById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("result", result);
        modelAndView.setViewName("compare_videoAssetDetail");
        return modelAndView;
    }

    @RequestMapping(value = "/check/image/{id}", method = RequestMethod.GET)
    public ModelAndView imageAuth(@PathVariable String id) throws IOException, NoSuchAlgorithmException, ProposalException, NoSuchMethodException, InstantiationException, InvalidKeySpecException, InvalidArgumentException, InvocationTargetException, IllegalAccessException, CryptoException, NoSuchProviderException, TransactionException, ClassNotFoundException {

        JSONObject result = authenticationService.selectImageDetailById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("result", result);
        modelAndView.setViewName("compare_photoAssertDetail");
        return modelAndView;
    }



}
