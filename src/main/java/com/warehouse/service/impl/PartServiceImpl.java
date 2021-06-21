package com.warehouse.service.impl;

import com.warehouse.customexception.PartNotFoundException;
import com.warehouse.model.OrderMethod;
import com.warehouse.model.Part;
import com.warehouse.repo.PartRepository;
import com.warehouse.service.IPartService;
import com.warehouse.util.MyAppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PartServiceImpl implements IPartService {

    @Autowired
    private PartRepository repo;

    @Override
    public Integer savePart(Part part) throws PartNotFoundException, Exception {
        return  repo.save(part).getId();
    }

    @Override
    public void updatePart(Part part) {
        repo.save(part);
    }

    @Override
    public Part getOnePart(Integer id) throws PartNotFoundException {
        return repo.findById(id).orElseThrow(()->
                new PartNotFoundException("Part with id '" +id+ "' not found" )
                );
    }

    @Override
    public List<Part> getAllParts() {
        return repo.findAll();
    }

    @Override
    public Map<Integer, String> getPartIdAndCode() {
        List<Object[]> list = repo.getPartIdAndCode();
        return MyAppUtil.convertListToMap(list);
    }


}
