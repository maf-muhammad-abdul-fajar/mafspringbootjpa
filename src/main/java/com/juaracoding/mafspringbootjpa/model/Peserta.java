package com.juaracoding.mafspringbootjpa.model;

/*
Created By IntelliJ IDEA 2022.2.3 (Community Edition)
Build #IU-222.4345.14, built on October 5, 2022
@Author Asus a.k.a. muhammad abdul fajar
Java Developer
Created on 2/16/2023 6:17 AM
@Last Modified 2/16/2023 6:17 AM
Version 1.0
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
public class Peserta {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Perserta {
        private long id;
        private String nama;
        private String batch;
        private String alamat;
    }
}