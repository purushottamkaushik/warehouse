package com.warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "so_dtl_tab")
public class SaleDtl {

    @Id
    @Column(name = "so_dtl_id")
    @GeneratedValue(generator = "so_dtl_gen")
    @SequenceGenerator(name = "so_dtl_gen",sequenceName = "saleOrderDetailSequence")
    private Integer id;

    @Column(name = "so_dtl_qty")
    private Integer qty;

    // Many Part are connected to one Sale detail
    // We are not allowing repeated item to added to the sale detail
    @ManyToOne
    @JoinColumn(name = "part_id_fk")
    private Part part;

    // Many SaleOrder  details are connected to one Sale order
    @ManyToOne
    @JoinColumn(name = "po_id_fk")
    private SaleOrder so;

}
