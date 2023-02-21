package com.juaracoding.mafspringbootjpa.repo;

/*
Created By IntelliJ IDEA 2022.2.3 (Community Edition)
Build #IU-222.4345.14, built on October 5, 2022
@Author Asus a.k.a. muhammad abdul fajar
Java Developer
Created on 2/18/2023 10:29 AM
@Last Modified 2/18/2023 10:29 AM
Version 1.0
*/
import com.juaracoding.mafspringbootjpa.model.Provinsi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProvinsiRepo extends JpaRepository<Provinsi,Long> {

//    List<Provinsi> findByNamaProvinsi(String value);
}


