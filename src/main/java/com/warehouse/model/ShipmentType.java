package com.warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shipment_type")
public class ShipmentType {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer shipmentId;

    @Column(name = "shipment_mode")
    private String shipmentMode;

    @Column(name = "shipment_code")
    private String shipmentCode;

    @Column(name = "shipment_enabled")
    private String enableShipment;

    @Column(name = "shipment_grade")
    private String shipmentGrade;

    @Column(name = "description")
    private String description;
}
