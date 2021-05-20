package com.warehouse.service.impl;

import com.warehouse.customexception.UomNotFoundException;
import com.warehouse.model.Uom;
import com.warehouse.repo.UomRepo;
import com.warehouse.service.IUomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UomServiceImpl implements IUomService {

    @Autowired
    private UomRepo repo;

    @Override
    public String saveUom(Uom uom) {
       Uom uom1 = repo.save(uom);
       return uom1.getUomId().toString();
    }

    @Override
    public Uom getOneUom(Integer id) throws UomNotFoundException{
//      Optional<Uom> uom = repo.findById(id);
//      if (uom.isPresent()) {
//          return uom.get();
//      } else {
//          throw new UomNotFoundException("Uom with uomId"  + id + " not found");
//      }

        return repo.findById(id).orElseThrow(()->{
            throw new UomNotFoundException("Uom With '" + id+ "' not found");
        });
    }

    @Override
    public List<Uom> getAllUoms() {
        return repo.findAll();
    }

    @Override
    public void deleteUom(Integer id) throws UomNotFoundException {
            repo.delete(getOneUom(id));
    }

    @Override
    public void updateUom(Uom uom){
        repo.save(uom);
    }

    @Override
    public boolean isUomModelExist(String uomModel) {
        return repo.getUomModelCount(uomModel)>0;
    }

    @Override
    public boolean isUomModelExistForEdit(String uomModel, Integer id) {
        return repo.getUomModelCountForEdit(uomModel, id)>0;
    }



}
