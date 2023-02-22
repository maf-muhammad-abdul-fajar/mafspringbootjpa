package com.juaracoding.mafspringbootjpa.service;

/*
Created By IntelliJ IDEA 2022.2.3 (Community Edition)
Build #IU-222.4345.14, built on October 5, 2022
@Author Asus a.k.a. muhammad abdul fajar
Java Developer
Created on 2/22/2023 12:08 PM
@Last Modified 2/22/2023 12:08 PM
Version 1.0
*/

import com.juaracoding.mafspringbootjpa.model.Person;
import com.juaracoding.mafspringbootjpa.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class PersonService{

    private PersonRepo personRepo;
    private String [] strExceptionArr = new String[2];


    @Autowired
    public PersonService(PersonRepo personRepo) {
        strExceptionArr[0] = "PersonService";
        this.personRepo = personRepo;
    }
    @Transactional(rollbackFor = Exception.class)
    public void savePerson(Person person){

        personRepo.save(person);

    }
    @Transactional(rollbackFor = Exception.class)
    public void saveAllPerson(List<Person> listPerson){
        personRepo.saveAll(listPerson);
    }
}