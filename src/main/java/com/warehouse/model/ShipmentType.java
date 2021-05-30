package com.warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shipment_type_tab")
public class ShipmentType {

    @Id
    @GeneratedValue(generator = "shipment_generator")
    @SequenceGenerator(name = "shipment_generator", sequenceName = "shipmentSequence")
    @Column(name = "id")
    private Integer id;

    @Column(name = "shipment_mode")
    private String shipmentMode;

    @Column(name = "shipment_code" , nullable = false, unique = true,length = 20)
    private String shipmentCode;

    @Column(name = "shipment_enabled")
    private String enableShipment;

    @Column(name = "shipment_grade")
    private String shipmentGrade;

    @Column(name = "description")
    private String description;
}
