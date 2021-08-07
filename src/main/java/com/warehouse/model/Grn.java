package com.warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "grn_tab")
public class Grn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "grn_code")
    private String grnCode;

    @Column(name = "grn_type")
    private String grnType;

    @Column(name = "grn_description")
    private String description;


    // One PurchaseOrder is connected to one GRN 1.....1 Mapping
    @ManyToOne
    @JoinColumn(name = "po_id_fk_col" , unique = true)
    PurchaseOrder po;

    @OneToMany(cascade = CascadeType.ALL) // Perform Operations on child when performed on Parent
    @Column(name = "grn_detail_fk")
    private Set<GrnDetail> grnDetails;




}
