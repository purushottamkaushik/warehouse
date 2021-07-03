package com.warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "grn_dtl_tab")
public class GrnDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "order_code")
    private String partCode;

    @Column(name = "base_cost")
    private Double baseCost;

    @Column(name = "qty")
    private Integer qty;

    @Column(name = "status") // For Accept or reject Storage Purpose
    private String status;

    
}
