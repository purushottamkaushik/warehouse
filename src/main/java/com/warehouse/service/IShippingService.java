package com.warehouse.service;

import com.warehouse.model.Shipping;

import java.util.List;

public interface IShippingService {

    Integer saveShipping(Shipping shipping);
    Shipping getOneShipping(Integer id);

    List<Shipping> getAllShippings();

    void updateShippingStatus(Integer id ,String status);






}
