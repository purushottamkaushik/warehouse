package com.warehouse.service;

import com.warehouse.customexception.UomNotFoundException;
import com.warehouse.model.Uom;

import java.util.List;
import java.util.Map;

public interface IUomService {

    Integer saveUom(Uom uom);

    Uom getOneUom(Integer id) throws UomNotFoundException;

    List<Uom> getAllUoms();

    void deleteUom(Integer id);

    void updateUom(Uom uom);

    boolean isUomModelExist(String uomModel);

    boolean isUomModelExistForEdit(String uomModel, Integer id);

    List<Object[]> getUomTypeCount();

    Map<Integer,String> getUomIdAndModel();
}
