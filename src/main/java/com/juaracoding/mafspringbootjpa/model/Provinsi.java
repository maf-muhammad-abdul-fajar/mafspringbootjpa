package com.juaracoding.mafspringbootjpa.model;

/*
Created By IntelliJ IDEA 2022.2.3 (Community Edition)
Build #IU-222.4345.14, built on October 5, 2022
@Author Asus a.k.a. muhammad abdul fajar
Java Developer
Created on 2/19/2023 9:43 PM
@Last Modified 2/19/2023 9:43 PM
Version 1.0
*/

import javax.persistence.*;
import java.util.Date;

@Entity
@Table (name = "MstProvinsi")
public class Provinsi {

    @Id
    @Column(name = "IDProvinsi")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProvinsi;

    @Column(name = "NamaProvinsi",nullable = false,length = 40,unique = true)
    private String namaProvinsi;

    @Column(name = "Singkatan", nullable = false, length = 20,unique = true)
    private String singkatan;

    @Column(name = "Latitude", length = 20, nullable = false)
    private String lat;

    @Column(name = "Longitude" , length = 20, nullable = false)
    private String lon;

    @Column(name = "NamaPemimpin",length = 50)
    private String namaPemimpin;

    @Column(name = "CreatedBy",nullable = false)
    private String createdBy = "1";

    @Column(name = "CreatedDate",nullable = false)
    private Date createdDate = new Date();

    @Column(name = "ModifiedBy",nullable = true)
    private String modifiedBy ;

    @Column(name = "ModifiedDate",nullable = true)
    private Date modifiedDate;

    @Column(name = "IsActive",nullable = false)
    private boolean isActive = true;


    public Long getIdProvinsi() {
        return idProvinsi;
    }

    public void setIdProvinsi(Long idProvinsi) {
        this.idProvinsi = idProvinsi;
    }

    public String getNamaProvinsi() {
        return namaProvinsi;
    }

    public void setNamaProvinsi(String namaProvinsi) {
        this.namaProvinsi = namaProvinsi;
    }

    public String getSingkatan() {
        return singkatan;
    }

    public void setSingkatan(String singkatan) {
        this.singkatan = singkatan;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getNamaPemimpin() {
        return namaPemimpin;
    }

    public void setNamaPemimpin(String namaPemimpin) {
        this.namaPemimpin = namaPemimpin;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

