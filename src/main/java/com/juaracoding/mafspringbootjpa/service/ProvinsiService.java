package com.juaracoding.mafspringbootjpa.service;

/*
Created By IntelliJ IDEA 2022.2.3 (Community Edition)
Build #IU-222.4345.14, built on October 5, 2022
@Author Asus a.k.a. muhammad abdul fajar
Java Developer
Created on 2/18/2023 10:18 AM
@Last Modified 2/18/2023 10:18 AM
Version 1.0
*/

//import com.juaracoding.mafspringbootjpa.configuration.OtherConfig;
import com.juaracoding.mafspringbootjpa.handler.ResourceNotFoundException;
import com.juaracoding.mafspringbootjpa.handler.ResponseHandler;
import com.juaracoding.mafspringbootjpa.model.Provinsi;
import com.juaracoding.mafspringbootjpa.repo.ProvinsiRepo;
import com.juaracoding.mafspringbootjpa.utils.ConstantMessage;
import com.juaracoding.mafspringbootjpa.utils.LoggingFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@Service
@Transactional
public class ProvinsiService {
    private ProvinsiRepo provinsiRepo;
    private String [] strExceptionArr = new String[2];
    @Autowired
    public ProvinsiService(ProvinsiRepo provinsiRepo) {
        strExceptionArr[0] = "ProvinsiService";
        this.provinsiRepo = provinsiRepo;
    }


    public ResponseEntity<Object> saveProvinsi(Provinsi provinsi)
    {
        String strMessage = ConstantMessage.SUCCESS_SAVE;
        try
        {
            provinsiRepo.save(provinsi);
        }
        catch (Exception e)
        {
//            strExceptionArr[1]="saveProvinsi(Provinsi provinsi)";
//            LoggingFile.exceptionStringz(strExceptionArr,e, OtherConfig.getFlagLogging());
//            return new ResponseHandler().generateResponse(ConstantMessage.ERROR_SAVE_FAILED,
//                    HttpStatus.BAD_REQUEST,null,"FI02001",null);
        }

        return new ResponseHandler().generateResponse(strMessage,
                HttpStatus.CREATED,null,null,null);
    }

    public ResponseEntity<Object> uploadProvinsiBatch(List<Provinsi> provinsiList) {
        String strMessage = ConstantMessage.SUCCESS_SAVE;
        try {
            provinsiRepo.saveAll(provinsiList);
        } catch (Exception e) {
//            strExceptionArr[1] = "uploadProvinsiBatch(List<Provinsi> provinsiList)";
//            LoggingFile.exceptionStringz(strExceptionArr,e, OtherConfig.getFlagLogging());
//            return new ResponseHandler().generateResponse(ConstantMessage.ERROR_SAVE_FAILED,
//                    HttpStatus.BAD_REQUEST,null,"FI02001",null);
        }

        return new ResponseHandler().generateResponse(strMessage,
                HttpStatus.CREATED,null,null,null);
    }

    @Transactional(rollbackFor = {Exception.class, SQLException.class})
    public ResponseEntity<Object> saveUploadFile(List<Provinsi> listProvinsi,
                                                 MultipartFile multipartFile,
                                                 WebRequest request) throws ResourceNotFoundException {
        List<Provinsi> provinsiList = null;
        String strMessage = ConstantMessage.SUCCESS_SAVE;
        try
        {
            provinsiList = provinsiRepo.saveAll(listProvinsi);
            if(provinsiList.size()==0)
            {
                return new ResponseHandler().generateResponse(ConstantMessage.ERROR_EMPTY_FILE +" -- "+multipartFile.getOriginalFilename(),
                        HttpStatus.BAD_REQUEST,null,"FI01020",request);
            }
        }
        catch (Exception e)
        {
//            strMessage = e.getMessage();
//            strExceptionArr[1]="saveUploadFile(List<Provinsi> listProvinsi, MultipartFile multipartFile, WebRequest request) --- LINE 77";
//            LoggingFile.exceptionStringz(strExceptionArr,e, OtherConfig.getFlagLogging());
//            return new ResponseHandler().generateResponse(strMessage,
//                    HttpStatus.BAD_REQUEST,null,"FI01021",request);
        }

        return new ResponseHandler().generateResponse(strMessage,
                HttpStatus.CREATED,null,null,request);
    }
}

