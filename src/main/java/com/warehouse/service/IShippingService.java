package com.warehouse.service;

import com.warehouse.consts.ShippingStatus;
import com.warehouse.model.Shipping;

import java.net.Inet4Address;
import java.util.List;

public interface IShippingService {

    Integer saveShipping(Shipping shipping);
    Shipping getOneShipping(Integer id);

    List<Shipping> getAllShippings();

}
