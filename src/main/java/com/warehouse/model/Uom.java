package com.warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "uom_tab")
public class Uom {

    @Id
    @GeneratedValue(generator = "uom_gen")
    @SequenceGenerator(name = "uom_gen", sequenceName = "uomseq_gen")
    @Column(name = "uom_id")
    private Integer id;

    @Column(name = "uom_type")
    private String uomType;

    @Column(name = "uom_model")
    private String uomModel;

    @Column(name = "description")
    private String description;

}
