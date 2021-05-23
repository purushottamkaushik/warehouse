package com.warehouse.service.impl;

import com.warehouse.customexception.WhUserTypeNotFoundException;
import com.warehouse.model.WhUserType;
import com.warehouse.repo.WhUserTypeRepository;
import com.warehouse.service.IWhUserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WhUserTypeServiceImpl implements IWhUserTypeService {


    @Autowired
    private WhUserTypeRepository repo;

    @Override
    public Integer addWhUserType(WhUserType whUserType) {
        return repo.save(whUserType).getId();
    }

    @Override
    public void updateWhUserType(WhUserType whUserType) {
        repo.save(whUserType);
    }

    @Override
    public List<WhUserType> getAllWhUsers() {
        return repo.findAll();
    }

    @Override
    public WhUserType getOneWhUserType(Integer id) throws WhUserTypeNotFoundException {
        return repo.findById(id).orElseThrow(() -> {
            throw new WhUserTypeNotFoundException("WareHouse User with userId '" + id + "' not found");
        });
    }

    @Override
    public boolean validateWhUserTypeCode(String code) {
        return repo.isCodeExists(code) > 0;
    }

    @Override
    public boolean validateWhUserTypeCode(String code, Integer id) {
        return repo.isCodeExists(code, id) > 0;
    }

    @Override
    public boolean validateWhUserTypeEmail(String email) {
        return repo.isEmailExists(email) > 0;
    }

    @Override
    public boolean validateWhUserTypeEmail(String email, Integer id) {
        return repo.isEmailExists(email, id) > 0;
    }

    @Override
    public boolean validateWhUserTypeIdNumber(String idNumber) {
        return repo.isIdNumberExists(idNumber) > 0;
    }

    @Override
    public boolean validateWhUserTypeIdNumber(String idNumber, Integer id) {
        return repo.isIdNumberExists(idNumber, id) > 0;
    }

    @Override
    public void deleteWhUserType(Integer id) throws WhUserTypeNotFoundException {
        repo.delete(getOneWhUserType(id));
    }

    @Override
    public List<Object[]> getUserTypeCount() {
        return repo.getCountUserType();
    }
}
