package com.juaracoding.mafspringbootjpa.controller;


/*
Created By IntelliJ IDEA 2022.2.3 (Community Edition)
Build #IU-222.4345.14, built on October 5, 2022
@Author Asus a.k.a. muhammad abdul fajar
Java Developer
Created on 2/18/2023 10:39 AM
@Last Modified 2/18/2023 10:39 AM
Version 1.0
*/

import com.juaracoding.mafspringbootjpa.dto.CategoryProductDTO;
import com.juaracoding.mafspringbootjpa.handler.ResourceNotFoundException;
import com.juaracoding.mafspringbootjpa.handler.ResponseHandler;
import com.juaracoding.mafspringbootjpa.model.CategoryProduct;
import com.juaracoding.mafspringbootjpa.service.CategoryProductService;
import com.juaracoding.mafspringbootjpa.utils.ConstantMessage;
import com.juaracoding.mafspringbootjpa.utils.CsvReader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/mgmnt")
public class categoryProductController {

    private CategoryProductService categoryProductService;

    @Autowired
    private ModelMapper modelMapper;

    private Map<String,Object> objectMapper = new HashMap<String,Object>();
    List<CategoryProduct> lsCPUpload = new ArrayList<CategoryProduct>();

    @Autowired
    public categoryProductController(CategoryProductService categoryProductService) {
        this.categoryProductService = categoryProductService;
    }

    @PostMapping("/v1/s")
    public ResponseEntity<Object> saveCategory(@Valid
                                               @RequestBody CategoryProduct categoryProduct
    ){

        categoryProductService.saveDataCategory(categoryProduct);

        return new ResponseHandler().
                generateResponse(ConstantMessage.SUCCESS_FIND_BY, HttpStatus.OK,null,null,null);
        //FI01001 --> F = FAILED , I = INSERT, 01 = MODUL, 001 = LENGTH NAME MAX 40
        //FI01002 --> F = FAILED , I = INSERT, 01 = MODUL, 002 = LENGTH DESC MAX 500
        //FI01003 --> F = FAILED , I = INSERT, 01 = MODUL, 003 = DATETIME FORMAT
        //SI01001 --> S = SUCCESS , I = INSERT , 01 = MODUL,

    }

    @PostMapping("/v1/sl")
    public ResponseEntity<Object> saveCategoryList(@Valid
                                                   @RequestBody List<CategoryProduct> listCategoryProduct
    ){

        categoryProductService.saveAllCategory(listCategoryProduct);

        return new ResponseHandler().
                generateResponse(ConstantMessage.SUCCESS_SAVE, HttpStatus.CREATED,null,null,null);

    }
    @PostMapping("/v1/upl/batch")
    public ResponseEntity<Object> uploadCsvMaster(@Valid
                                                  @RequestParam("fileDemo")
                                                  @RequestHeader  MultipartFile multipartFile
    ) throws Exception {


        try{
            if(CsvReader.isCsv(multipartFile))
            {
                List<CategoryProduct> lsCP =  categoryProductService.saveUploadFile(
                        csvToCategoryProduct(
                                multipartFile.getInputStream()));

                if(lsCP.size()==0)
                {
                    throw new ResourceNotFoundException(ConstantMessage.ERROR_UPLOAD_CSV+" -- "+multipartFile.getOriginalFilename());
                }
            }
            else
            {
                throw new ResourceNotFoundException(ConstantMessage.ERROR_NOT_CSV_FILE+" -- "+multipartFile.getOriginalFilename());
            }
        }catch (Exception e)
        {
            throw new Exception(ConstantMessage.ERROR_UPLOAD_CSV+multipartFile.getOriginalFilename());
        }
        return new ResponseHandler().generateResponse(ConstantMessage.SUCCESS_SAVE,
                HttpStatus.CREATED,null,null,null);


    }

    @PutMapping("/v1/sl/{id}")
    public ResponseEntity<Object> updateCategoryById(@Valid
                                                     @RequestBody CategoryProduct categoryProduct,
                                                     @PathVariable Long id
    ) throws Exception {

        categoryProductService.updateCategory(categoryProduct,id);

        return new ResponseHandler().
                generateResponse(ConstantMessage.SUCCESS_FIND_BY, HttpStatus.OK,null,null,null);
    }

    @GetMapping("/v1/f")
    public ResponseEntity<Object> findAll(){

        List<CategoryProduct> ls = categoryProductService.findAllCategory();

        if(ls.size()==0)
        {
            return new ResponseHandler().
                    generateResponse(ConstantMessage.WARNING_DATA_EMPTY,
                            HttpStatus.NOT_FOUND,
                            categoryProductService.findAllCategory(),
                            null,
                            null);
        }

        return new ResponseHandler().
                generateResponse(ConstantMessage.SUCCESS_FIND_BY,
                        HttpStatus.OK,
                        ls,
                        null,
                        null);


    }

    @GetMapping("/v1/fp/{size}/{page}/{sort}")
    public ResponseEntity<Object> findAllPagination(
            @PathVariable("size") Integer sizez,
            @PathVariable("page") Integer pagez,
            @PathVariable("sort") String sortz
    ){

        Pageable pageable = null;
        if(sortz.equalsIgnoreCase("desc"))
        {
            pageable = PageRequest.of(pagez,sizez, Sort.by("idCategoryProduct").descending());
        }
        else
        {
            pageable = PageRequest.of(pagez,sizez);
        }
        Page<CategoryProduct> page = categoryProductService.findAllCategoryByPage(pageable);

        return new ResponseHandler().
                generateResponse(ConstantMessage.SUCCESS_FIND_BY,
                        HttpStatus.OK,
                        page,
                        null,
                        null);


    }


    /*
       PAGINATION SORTING DEFAULT by ID TANPA PARAMETER SORT BY YANG SPESIFIK DAN RESPONSE SUDAH MENGGUNAKAN LIST
       Sudah ada validasi semisal data kosong dan informasi di response lebih dinamis
       Problem :
          1. Data content sudah lebih baik namun informasi Paging tidak diikutsertakan, sehingga menghambat front end nantinya
          2. belum menggunakan DTO data content untuk response masih belum rapih
    */
    @GetMapping("/v1/fp2/{size}/{page}/{sort}")
    public ResponseEntity<Object> findAllPagination2(
            @PathVariable("size") Integer sizez,
            @PathVariable("page") Integer pagez,
            @PathVariable("sort") String sortz
    ){

        Pageable pageable = null;
        if(sortz.equalsIgnoreCase("desc"))
        {
            pageable = PageRequest.of(pagez,sizez, Sort.by("idCategoryProduct").descending());
        }
        else
        {
            pageable = PageRequest.of(pagez,sizez);

        }
        Page<CategoryProduct> page = categoryProductService.findAllCategoryByPage(pageable);
        List<CategoryProduct> listCategoryProduct = page.getContent();
        if(listCategoryProduct.size()==0)
        {
            return new ResponseHandler().
                    generateResponse(ConstantMessage.WARNING_DATA_EMPTY,
                            HttpStatus.NOT_FOUND,
                            null,
                            null,
                            null);
        }

        return new ResponseHandler().
                generateResponse(ConstantMessage.SUCCESS_FIND_BY,
                        HttpStatus.OK,
                        listCategoryProduct,
                        null,
                        null);
    }

    /*
       PAGINATION DENGAN SORT BY DAN RESPONSE SUDAH MENGGUNAKAN LIST NAMUN VALIDASI SORT BY MASIH DI EMBED DI METHOD REQUEST
       Problem :
       1. Data content sudah lebih baik namun Data Paging tidak diikutsertakan, sehingga menghambat front end nantinya
       2. belum menggunakan DTO data content untuk response masih belum rapih
    */
    @GetMapping("/v1/fpsb1/{size}/{page}/{sort}/{sortby}")
    public ResponseEntity<Object> findPaginationSortBy1(
            @PathVariable("size") Integer sizez,
            @PathVariable("page") Integer pagez,
            @PathVariable("sort") String sortz,
            @PathVariable("sortby") String sortzBy
    ){

        Pageable pageable = null;
        String strSortBy = "";

        if(sortzBy.equalsIgnoreCase("id"))
        {
            strSortBy = "idCategoryProduct";
        }
        else if(sortzBy.equalsIgnoreCase("name"))
        {
            strSortBy = "nameCategoryProduct";
        }
        else if(sortzBy.equalsIgnoreCase("description"))
        {
            strSortBy = "strDescCategoryProduct";
        }
        else
        {
            strSortBy = "idCategoryProduct";
        }

        if(sortz.equalsIgnoreCase("desc"))
        {
            pageable = PageRequest.of(pagez,sizez, Sort.by(strSortBy).descending());
        }
        else
        {
            pageable = PageRequest.of(pagez,sizez, Sort.by(strSortBy).ascending());
        }

        Page<CategoryProduct> page = categoryProductService.findAllCategoryByPage(pageable);
        List<CategoryProduct> listCategoryProduct = page.getContent();

        if(listCategoryProduct.size()==0)
        {
            return new ResponseHandler().
                    generateResponse(ConstantMessage.WARNING_DATA_EMPTY,
                            HttpStatus.NOT_FOUND,
                            categoryProductService.findAllCategory(),
                            null,
                            null);
        }

        return new ResponseHandler().
                generateResponse(ConstantMessage.SUCCESS_FIND_BY,
                        HttpStatus.OK,
                        listCategoryProduct,
                        null,
                        null);
    }

    /*
       PAGINATION DENGAN SORT BY DAN RESPONSE SUDAH MENGGUNAKAN LIST SERTA VALIDASI SUDAH DIBUAT METHOD SENDIRI
       Problem :
       1. belum menggunakan DTO data content untuk response masih belum rapih
    */
    @GetMapping("/v1/fpsb2/{size}/{page}/{sort}/{sortby}")
    public ResponseEntity<Object> findPaginationSortBy2(
            @PathVariable("size") Integer sizez,
            @PathVariable("page") Integer pagez,
            @PathVariable("sort") String sortz,
            @PathVariable("sortby") String sortzBy
    ){

        Pageable pageable = filterPagination(pagez,sizez,sortz,sortzBy);
        Page<CategoryProduct> page = categoryProductService.findAllCategoryByPage(pageable);
        List<CategoryProduct> listCategoryProduct = page.getContent();

        if(listCategoryProduct.size()==0)
        {
            return new ResponseHandler().
                    generateResponse(ConstantMessage.WARNING_DATA_EMPTY,
                            HttpStatus.NOT_FOUND,
                            categoryProductService.findAllCategory(),
                            null,
                            null);
        }

        return new ResponseHandler().
                generateResponse(ConstantMessage.SUCCESS_FIND_BY,
                        HttpStatus.OK,
                        listCategoryProduct,
                        null,
                        null);
    }

    /*
       PAGINATION DENGAN SORT BY DAN RESPONSE SUDAH MENGGUNAKAN LIST SERTA VALIDASI SUDAH DIBUAT METHOD SENDIRI + DTO di embed di method request
       Problem :
       1. kurang rapih karena DTO nya masih diembed di method request
    */
    @GetMapping("/v1/fpsbd1/{size}/{page}/{sort}/{sortby}")
    public ResponseEntity<Object> findPaginationSortByDTO1(
            @PathVariable("size") Integer sizez,
            @PathVariable("page") Integer pagez,
            @PathVariable("sort") String sortz,
            @PathVariable("sortby") String sortzBy
    ){

        Pageable pageable = filterPagination(pagez,sizez,sortz,sortzBy);
        Page<CategoryProduct> page = categoryProductService.findAllCategoryByPage(pageable);
        List<CategoryProduct> listCategoryProduct = page.getContent();

        /*
            CATATAN PENTING!!
            VALIDASI INI DILETAKKAN DISINI AGAR TIDAK PERLU MELAKUKAN PROSES APAPUN (PROSES MAPPING ATAU TRANSFORM DTO) JIKA DATA YANG DICARI KOSONG
         */
        if(listCategoryProduct.size()==0)
        {
            return new ResponseHandler().
                    generateResponse(ConstantMessage.WARNING_DATA_EMPTY,
                            HttpStatus.NOT_FOUND,
                            null,
                            null,
                            null);
        }

        List<CategoryProductDTO> listCategoryProductDTO = modelMapper.map(listCategoryProduct, new TypeToken<List<CategoryProductDTO>>() {}.getType());
        objectMapper.put("data",listCategoryProductDTO);
        objectMapper.put("currentPage",page.getNumber());
        objectMapper.put("totalItems",page.getTotalElements());
        objectMapper.put("totalPages",page.getTotalPages());
        objectMapper.put("sort",page.getSort());
        objectMapper.put("numberOfElements",page.getNumberOfElements());

        return new ResponseHandler().
                generateResponse(ConstantMessage.SUCCESS_FIND_BY,
                        HttpStatus.OK,
                        objectMapper,
                        null,
                        null);
    }

    /*
       PAGINATION DENGAN SORT BY DAN RESPONSE SUDAH MENGGUNAKAN LIST SERTA VALIDASI SUDAH DIBUAT METHOD SENDIRI + DTO sudah dibuat method sendiri
    */
    @GetMapping("/v1/fpsbd2/{size}/{page}/{sort}/{sortby}")
    public ResponseEntity<Object> findPaginationSortByDTO2(
            @PathVariable("size") Integer sizez,
            @PathVariable("page") Integer pagez,
            @PathVariable("sort") String sortz,
            @PathVariable("sortby") String sortzBy
    ){

        Pageable pageable = filterPagination(pagez,sizez,sortz,sortzBy);
        Page<CategoryProduct> page = categoryProductService.findAllCategoryByPage(pageable);
        List<CategoryProduct> listCategoryProduct = page.getContent();

        /*
            CATATAN PENTING!!
            VALIDASI INI DILETAKKAN DISINI AGAR TIDAK PERLU MELAKUKAN PROSES APAPUN (PROSES MAPPING ATAU TRANSFORM DTO) JIKA DATA YANG DICARI KOSONG
         */
        if(listCategoryProduct.size()==0)
        {
            return new ResponseHandler().
                    generateResponse(ConstantMessage.WARNING_DATA_EMPTY,
                            HttpStatus.NOT_FOUND,
                            null,
                            null,
                            null);
        }

        List<CategoryProduct> listCategoryProductDTO = modelMapper.map(listCategoryProduct, new TypeToken<List<CategoryProduct>>() {}.getType());
        Map<String, Object> mapResult = transformObject(objectMapper,listCategoryProductDTO,page);

        return new ResponseHandler().
                generateResponse(ConstantMessage.SUCCESS_FIND_BY,
                        HttpStatus.OK,
                        mapResult,
                        null,
                        null);
    }

    private Map<String,Object> transformObject(Map<String,Object> mapz, List ls, Page page)
    {
        mapz.put("data",ls);
        mapz.put("currentPage",page.getNumber());
        mapz.put("totalItems",page.getTotalElements());
        mapz.put("totalPages",page.getTotalPages());
        mapz.put("sort",page.getSort());
        mapz.put("numberOfElements",page.getNumberOfElements());

        return mapz;
    }

    private Pageable filterPagination(Integer page, Integer size, String sorts , String sortsBy)
    {
        Pageable pageable;
        String strSortBy = "";

        if(sortsBy.equalsIgnoreCase("id"))
        {
            strSortBy = "idCategoryProduct";
        }
        else if(sortsBy.equalsIgnoreCase("name"))
        {
            strSortBy = "nameCategoryProduct";
        }
        else if(sortsBy.equalsIgnoreCase("description"))
        {
            strSortBy = "strDescCategoryProduct";
        }
        else
        {
            strSortBy = "idCategoryProduct";
        }

        if(sorts.equalsIgnoreCase("desc"))
        {
            pageable = PageRequest.of(page,size, Sort.by(strSortBy).descending());
        }
        else
        {
            pageable = PageRequest.of(page,size, Sort.by(strSortBy).ascending());
        }

        return pageable;
    }

    public List<CategoryProduct> csvToCategoryProduct(InputStream inputStream) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        CSVParser csvParser = new CSVParser(bufferedReader,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().
                        withIgnoreHeaderCase().
                        withTrim()
        );
        lsCPUpload.clear();
        try {

            Iterable<CSVRecord> iterRecords = csvParser.getRecords();

            for (CSVRecord record : iterRecords) {
                CategoryProduct cProducts = new CategoryProduct();
                cProducts.setNameCategoryProduct(record.get("NameCategoryProduct"));
                cProducts.setStrDescCategoryProduct(record.get("DescCategoryProduct"));
                cProducts.setCreatedBy(Integer.parseInt(record.get("CreatedBy")));
                lsCPUpload.add(cProducts);
            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {

            if (!csvParser.isClosed()) {
                csvParser.close();
            }
            return lsCPUpload;
        }
    }
}

