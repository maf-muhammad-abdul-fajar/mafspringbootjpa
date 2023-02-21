package com.juaracoding.mafspringbootjpa.model;

/*
Created By IntelliJ IDEA 2022.2.3 (Community Edition)
Build #IU-222.4345.14, built on October 5, 2022
@Author Asus a.k.a. muhammad abdul fajar
Java Developer
Created on 2/18/2023 1:38 PM
@Last Modified 2/18/2023 1:38 PM
Version 1.0
*/

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "MstTest")
public class Test {

    @Id
    @Column(name = "IDTest")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DateTest" , nullable = false)
    private LocalDate dateTest;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateTest() {
        return dateTest;
    }

    public void setDateTest(LocalDate dateTest) {
        this.dateTest = dateTest;
    }
}