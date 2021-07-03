package com.warehouse.service;

import com.warehouse.model.Grn;

import java.util.List;

public interface IGrnService {

    Integer addGrn(Grn grn);
    Grn getOneGrn(Integer id);
    List<Grn> getAllGrns();

    void updateGrnDetailsById(Integer id , String status);


}
