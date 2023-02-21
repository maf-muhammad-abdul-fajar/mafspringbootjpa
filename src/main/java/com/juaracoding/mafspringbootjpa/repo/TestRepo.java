package com.juaracoding.mafspringbootjpa.repo;


/*
Created By IntelliJ IDEA 2022.2.3 (Community Edition)
Build #IU-222.4345.14, built on October 5, 2022
@Author Asus a.k.a. muhammad abdul fajar
Java Developer
Created on 2/18/2023 10:46 AM
@Last Modified 2/18/2023 10:46 AM
Version 1.0
*/
import com.juaracoding.mafspringbootjpa.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepo extends JpaRepository<Test,Long> {

}