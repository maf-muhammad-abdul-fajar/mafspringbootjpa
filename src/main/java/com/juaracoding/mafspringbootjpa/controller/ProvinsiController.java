package com.juaracoding.mafspringbootjpa.controller;

/*
Created By IntelliJ IDEA 2022.2.3 (Community Edition)
Build #IU-222.4345.14, built on October 5, 2022
@Author Asus a.k.a. muhammad abdul fajar
Java Developer
Created on 2/18/2023 10:39 AM
@Last Modified 2/18/2023 10:39 AM
Version 1.0
*/

//import com.juaracoding.mafspringbootjpa.configuration.OtherConfig;
import com.juaracoding.mafspringbootjpa.handler.ResourceNotFoundException;
import com.juaracoding.mafspringbootjpa.handler.ResponseHandler;

import com.juaracoding.mafspringbootjpa.model.Provinsi;
import com.juaracoding.mafspringbootjpa.service.ProvinsiService;
import com.juaracoding.mafspringbootjpa.utils.ConstantMessage;
import com.juaracoding.mafspringbootjpa.utils.CsvReader;
import com.juaracoding.mafspringbootjpa.utils.LoggingFile;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@RestController
@RequestMapping("api/provinsi/")
public class ProvinsiController {
    private ProvinsiService provinsiService;

    private String [] strExceptionArr = new String[2];

    @Autowired
    private ModelMapper modelMapper;

    private Map<String,Object> objectMapper = new HashMap<String,Object>();
    List<Provinsi> lsCPUpload = new ArrayList<Provinsi>();


    @Autowired
    public ProvinsiController(ProvinsiService provinsiService) {
        this.provinsiService = provinsiService;
    }

    @PostMapping("v1/sv")
    public ResponseEntity<Object> saveProvinsi(@Valid
                                               @RequestBody Provinsi provinsi)
    {
        return provinsiService.saveProvinsi(provinsi);
    }


    @PostMapping("/v1/upl/batch")
    public ResponseEntity<Object> uploadCsvMaster(@Valid
                                                  @RequestParam("fileDemo")
                                                  @RequestHeader MultipartFile multipartFile,
                                                  WebRequest request
    ) throws Exception {

        if(CsvReader.isCsv(multipartFile))
        {
            return provinsiService.saveUploadFile(
                    csvToProvinsi(
                            multipartFile.getInputStream()),multipartFile,request);
        }
        else
        {
            return new ResponseHandler().generateResponse(ConstantMessage.ERROR_NOT_CSV_FILE+" -- "+multipartFile.getOriginalFilename(),
                    HttpStatus.NOT_ACCEPTABLE,null,"FI01021",request);
        }

    }

    public List<Provinsi> csvToProvinsi(InputStream inputStream) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        CSVParser csvParser = new CSVParser(bufferedReader,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().
                        withIgnoreHeaderCase()
        );
        lsCPUpload = new ArrayList<Provinsi>();
        lsCPUpload.clear();
        int intCatchErrorRecord = 1;
        try {
            Iterable<CSVRecord> iterRecords = csvParser.getRecords();

            for (CSVRecord record : iterRecords) {
                Provinsi cProvinsi = new Provinsi();
                cProvinsi.setNamaProvinsi(record.get("NamaProvinsi"));
                cProvinsi.setSingkatan(record.get("Singkatan"));
                cProvinsi.setLat(record.get("Latitude"));
                cProvinsi.setLon(record.get("Longitude"));
                cProvinsi.setNamaPemimpin(record.get("NamaPemimpin"));
                cProvinsi.setCreatedBy(record.get("CreatedBy"));
                lsCPUpload.add(cProvinsi);
                intCatchErrorRecord++;
            }

        } catch (Exception ex) {
//            strExceptionArr[1]="csvToProvinsiInputStream inputStream) "+ex.getMessage()+" Error Record at Line "+intCatchErrorRecord;
//            LoggingFile.exceptionStringz(strExceptionArr,ex, OtherConfig.getFlagLogging());
//            throw new Exception(ex.getMessage()+" Error Record at Line "+intCatchErrorRecord);


        }
        finally {
            if (!csvParser.isClosed()) {
                csvParser.close();
            }
        }
        return lsCPUpload;
    }

}