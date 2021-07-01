package com.warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "po_dtl_tab")
public class PurchaseDtl {

    @Id
    @Column(name = "po_dtl_id")
    @GeneratedValue(generator = "po_gen")
    private Integer id;

    @Column(name = "po_dtl_qty")
    private Integer qty;

    // Many Part are connected to one purchase detail
    @ManyToOne
    @JoinColumn(name = "part_id_fk")
    private Part part;

    // Many Purchase details are connected to one purchase order
    @ManyToOne
    @JoinColumn(name = "po_id_fk")
    private PurchaseOrder po;


}
