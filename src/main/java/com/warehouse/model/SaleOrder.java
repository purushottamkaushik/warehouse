package com.warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity(name = "sale_order_tab")
public class SaleOrder {

    @Id
    @Column(name = "id")
    @GeneratedValue( generator = "so_gen")
    @SequenceGenerator(name = "so_gen",sequenceName = "saleOrderSequence")
    private Integer id;

    @Column(name = "sale_order_code")
    private String orderCode;

    @Column(name = "sale_ref_num")
    private String refNum;

    @Column(name = "sale_stock_mode")
    private String stockMode;

    @Column(name = "sale_stock_source")
    private String stockSource;

    @Column(name = "sale_status")
    private String status;

    @Column(name = "sale_description")
    private String description;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "st_id_fk_col")
    private ShipmentType st;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "wh_usr_fk_col")
    private WhUserType customer;


}
