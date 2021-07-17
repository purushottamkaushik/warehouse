package com.warehouse.repo;

import com.warehouse.model.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingRepository extends JpaRepository<Shipping , Integer> {
}
