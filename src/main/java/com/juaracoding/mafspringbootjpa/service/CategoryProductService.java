package com.juaracoding.mafspringbootjpa.service;

/*
Created By IntelliJ IDEA 2022.2.3 (Community Edition)
Build #IU-222.4345.14, built on October 5, 2022
@Author Asus a.k.a. muhammad abdul fajar
Java Developer
Created on 2/18/2023 10:18 AM
@Last Modified 2/18/2023 10:18 AM
Version 1.0
*/

import com.juaracoding.mafspringbootjpa.handler.ResourceNotFoundException;
import com.juaracoding.mafspringbootjpa.model.CategoryProduct;
import com.juaracoding.mafspringbootjpa.repo.CategoryProductRepo;
import com.juaracoding.mafspringbootjpa.utils.ConstantMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOError;
import java.io.IOException;
import java.sql.SQLDataException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class CategoryProductService {
    private CategoryProductRepo categoryProductRepo;


    @Autowired
    public CategoryProductService(CategoryProductRepo categoryProductRepo) {
        this.categoryProductRepo = categoryProductRepo;
    }


    public void saveDataCategory(CategoryProduct categoryProduct){
        categoryProductRepo.save(categoryProduct);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveAllCategory(List<CategoryProduct> listCategoryProduct){
        categoryProductRepo.saveAll(listCategoryProduct);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<CategoryProduct> saveUploadFile(List<CategoryProduct> listCategoryProduct){
        return categoryProductRepo.saveAll(listCategoryProduct);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateCategory(CategoryProduct categoryProduct,Long id) throws  Exception
    {
        CategoryProduct cProduct = categoryProductRepo.findById(id).orElseThrow (
                ()->  new ResourceNotFoundException("Data tidak ditemukan")
        );

        /*
            SELECT * FROM MstCategoryProduct WHERE IDCategoryProduct = ?
            cProduct.getNameCategoryProduct();//ALAT ELEKTRONIK
            cProduct.getStrDescCategoryProduct();//seluruh peralatan yang disentuh nanti nyetrum
         */
        if(cProduct!=null){
            cProduct.setNameCategoryProduct(categoryProduct.getNameCategoryProduct());
            cProduct.setModifiedBy(categoryProduct.getModifiedBy());
            cProduct.setModifiedDate(new Date());
            cProduct.setStrDescCategoryProduct(categoryProduct.getStrDescCategoryProduct());
        }

    }

    public List<CategoryProduct> findAllCategory()
    {
        return categoryProductRepo.findAll();
        /*
            SELECT * FROM MstCategoryProduct
         */
    }
    public Page<CategoryProduct> findAllCategoryByPage(Pageable pageable)
    {
        return categoryProductRepo.findAll(pageable);
        /*
            SELECT * FROM MstCategoryProduct Page = ? , Sort = ? , Record = ?

            totalRecord = 100
            page = 0
            Record = 10
            data balikan = dari index ke 0 s.d index ke 9

            page = 1
            record = 10
            data balikan = dari index 10 s.d index 19

            page = 2
            record = 10
            data balikan = dari index 20 s.d index 19

         */
    }

    public Optional<CategoryProduct> findById(Long id)
    {
        return categoryProductRepo.findById(id);

        /*
            SELECT * FROM MstCategoryProduct WHERE IDCategoryProduct = ?
         */
    }
}


