package com.juaracoding.mafspringbootjpa.controller;

/*
Created By IntelliJ IDEA 2022.2.3 (Community Edition)
Build #IU-222.4345.14, built on October 5, 2022
@Author Asus a.k.a. muhammad abdul fajar
Java Developer
Created on 2/18/2023 10:44 AM
@Last Modified 2/18/2023 10:44 AM
Version 1.0
*/

import com.juaracoding.mafspringbootjpa.handler.ResponseHandler;
import com.juaracoding.mafspringbootjpa.model.Test;
import com.juaracoding.mafspringbootjpa.service.TestServices;
import com.juaracoding.mafspringbootjpa.utils.ConstantMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tests")
public class TestController {


    private TestServices testServices;

    @Autowired
    public TestController(TestServices testServices) {
        this.testServices = testServices;
    }

    @PostMapping("/v1/s")
    public ResponseEntity<Object> saveAll(
            @RequestBody List<Test> listTest
    ){

        testServices.saveAllTest(listTest);

        return new ResponseHandler().
                generateResponse(ConstantMessage.SUCCESS_FIND_BY, HttpStatus.OK,null,null,null);

    }
}
