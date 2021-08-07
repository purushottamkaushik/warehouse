package com.warehouse.service.impl;

import com.warehouse.customexception.ShippingNotFoundException;
import com.warehouse.model.Shipping;
import com.warehouse.repo.ShippingDtlRepository;
import com.warehouse.repo.ShippingRepository;
import com.warehouse.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShippingImpl implements IShippingService {

    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private ShippingDtlRepository shippingDtlRepository;

    @Override
    public Integer saveShipping(Shipping shipping) {
        return shippingRepository.save(shipping).getId();
    }

    @Override
    public Shipping getOneShipping(Integer id) {
        return shippingRepository.findById(id).orElseThrow(() ->
                {
                    throw new  ShippingNotFoundException("Shipping with id " + id + " not found ");
                }
        );
    }

    @Override
    public List<Shipping> getAllShippings() {
        return shippingRepository.findAll();
    }
}
