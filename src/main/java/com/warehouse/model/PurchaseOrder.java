package com.warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "po_tab")
public class PurchaseOrder {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "po_gen")
    @SequenceGenerator(name = "po_gen",sequenceName = "po_gen_sequence")
    private Integer id ;

    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "ref_num")
    private String refNumber;


    @Column(name = "quality_check")
    private String qualityCheck; // this will behave as a blob data

    @Column(name = "status")
    private String status;

    @Column(name = "description")
    private String description;

    // Integrations
    @ManyToOne
    @JoinColumn(name = "st_id_fk")
    private ShipmentType st ;

    @ManyToOne
    @JoinColumn(name = "wh_id_fk")
    private WhUserType vendor;

}
