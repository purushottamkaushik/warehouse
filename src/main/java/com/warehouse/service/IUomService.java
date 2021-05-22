package com.warehouse.service;

import com.warehouse.customexception.UomNotFoundException;
import com.warehouse.model.Uom;

import java.util.List;

public interface IUomService {

    String saveUom(Uom uom);

    Uom getOneUom(Integer id) throws UomNotFoundException;

    List<Uom> getAllUoms();

    void deleteUom(Integer id);

    void updateUom(Uom uom);

    boolean isUomModelExist(String uomModel);

    boolean isUomModelExistForEdit(String uomModel, Integer id);

    List<Object[]> getUomTypeCount();
}
