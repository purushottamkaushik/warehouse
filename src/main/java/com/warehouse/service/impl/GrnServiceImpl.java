package com.warehouse.service.impl;

import com.warehouse.customexception.DataNotFoundException;
import com.warehouse.customexception.GrnNotFoundException;
import com.warehouse.model.Grn;
import com.warehouse.model.GrnDetail;
import com.warehouse.repo.GrnDetailsRepository;
import com.warehouse.repo.GrnRepository;
import com.warehouse.service.IGrnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class GrnServiceImpl implements IGrnService {

    @Autowired
    private GrnRepository grnRepository;

    @Autowired
    private GrnDetailsRepository grnDetailsRepository;


    @Override
    public Integer addGrn(Grn grn) {
        return grnRepository.save(grn).getId();
    }

    @Override
    public Grn getOneGrn(Integer id) {
        Optional<Grn> grn = grnRepository.findById(id);
        if (grn.isPresent()){
            return grn.get();
        } else {
            throw  new DataNotFoundException("Grn with id '" +id+ "' doesnt exists");
        }
    }

    @Override
    public List<Grn> getAllGrns() {
        return grnRepository.findAll();
    }

    @Override
    @Transactional
    public void updateGrnDetailsById(Integer id, String status) {
        boolean existsById = grnDetailsRepository.existsById(id);
        if (existsById){
            grnDetailsRepository.updateGrnDetailsById(id,status);
        } else {
            throw new GrnNotFoundException();
        }
    }
}
