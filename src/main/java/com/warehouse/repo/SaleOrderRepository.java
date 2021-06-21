package com.warehouse.repo;

import com.warehouse.model.SaleOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleOrderRepository extends JpaRepository<SaleOrder,Integer> {
}
