package com.warehouse.service;

import com.warehouse.customexception.WhUserTypeNotFoundException;
import com.warehouse.model.WhUserType;

import java.util.List;
import java.util.Map;

public interface IWhUserTypeService {

    Integer addWhUserType(WhUserType whUserType);

    void updateWhUserType(WhUserType whUserType);

    List<WhUserType> getAllWhUsers();

    WhUserType getOneWhUserType(Integer id) throws WhUserTypeNotFoundException;

    boolean validateWhUserTypeCode(String code);

    boolean validateWhUserTypeCode(String code, Integer id);

    boolean validateWhUserTypeEmail(String email);

    boolean validateWhUserTypeEmail(String email, Integer id);

    boolean validateWhUserTypeIdNumber(String idNumber);

    boolean validateWhUserTypeIdNumber(String idNumber, Integer id);

    void deleteWhUserType(Integer id) throws WhUserTypeNotFoundException;

    List<Object[]> getUserTypeCount();

    Map<Integer,String> getWhUserIdAndCodeByType(String type);

}
