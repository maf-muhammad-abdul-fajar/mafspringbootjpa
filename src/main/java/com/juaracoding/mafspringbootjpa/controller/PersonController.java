package com.juaracoding.mafspringbootjpa.controller;

/*
Created By IntelliJ IDEA 2022.2.3 (Community Edition)
Build #IU-222.4345.14, built on October 5, 2022
@Author Asus a.k.a. muhammad abdul fajar
Java Developer
Created on 2/16/2023 7:36 PM
@Last Modified 2/16/2023 7:36 PM
Version 1.0
*/

import com.juaracoding.mafspringbootjpa.handler.ResponseHandler;
import com.juaracoding.mafspringbootjpa.model.Person;
import com.juaracoding.mafspringbootjpa.service.PersonService;
import com.juaracoding.mafspringbootjpa.utils.ConstantMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private PersonService personService;
    private String [] strExceptionArr = new String[2];

    @Autowired
    private ModelMapper modelMapper;

    private Map<String,Object> objectMapper = new HashMap<String,Object>();
    List<Person> lsCPUpload = new ArrayList<Person>();

    @Autowired
    public PersonController(PersonService personService) {
        strExceptionArr[0] = "PersonController";
        this.personService = personService;
    }

    @PostMapping("/v2/save")
    public ResponseEntity<Object> savePerson(@Valid
                                               @RequestBody Person person
    ){

        personService.savePerson(person);

        return new ResponseHandler().
                generateResponse(ConstantMessage.SUCCESS_FIND_BY, HttpStatus.OK,null,null,null);
    }


    @PostMapping("/v2/saveAll")
    public ResponseEntity<Object> savePersonList(@Valid
                                                   @RequestBody List<Person> listPerson
    ){

        personService.saveAllPerson(listPerson);

        return new ResponseHandler().
                generateResponse(ConstantMessage.SUCCESS_SAVE, HttpStatus.CREATED,null,null,null);

    }

}
