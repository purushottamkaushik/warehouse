package com.warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "shipping_dtl_tab")
public class ShippingDtl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "base_cost")
    private Double baseCost;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "status")
    private String status;

}
