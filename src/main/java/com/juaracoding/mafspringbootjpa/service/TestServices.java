package com.juaracoding.mafspringbootjpa.service;

/*
Created By IntelliJ IDEA 2022.2.3 (Community Edition)
Build #IU-222.4345.14, built on October 5, 2022
@Author Asus a.k.a. muhammad abdul fajar
Java Developer
Created on 2/18/2023 10:45 AM
@Last Modified 2/18/2023 10:45 AM
Version 1.0
*/
import com.juaracoding.mafspringbootjpa.repo.TestRepo;
import com.juaracoding.mafspringbootjpa.model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TestServices {

    private TestRepo testRepo;


    @Autowired
    public TestServices(TestRepo testRepo) {
        this.testRepo = testRepo;
    }


    public void saveAllTest(List<Test> ls){

        testRepo.saveAll(ls);
    }
}
