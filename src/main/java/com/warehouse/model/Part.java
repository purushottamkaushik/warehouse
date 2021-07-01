package com.warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "part_tab")
public class Part {

    @Id
    @GeneratedValue(generator = "partGen")
    @SequenceGenerator(name = "partGen",sequenceName = "partGenSeq")
    @Column(name = "id")
    private Integer id;

    @Column(name = "part_code")
    private String partCode;

    @Column(name = "part_width")
    private String partWidth;

    @Column(name = "part_height")
    private String partHeight;

    @Column(name = "part_length")
    private String partLength;

    @Column(name = "part_base_cost")
    private Double partBaseCost;

    @Column(name = "part_base_currency")
    private String partBaseCurrency;

    @Column(name = "description")
    private String description;


    @ManyToOne
    @JoinColumn(name = "uom_id_fk")
    private Uom uom;

    @ManyToOne
    @JoinColumn(name = "om_id_fk")
    private OrderMethod om;

}
