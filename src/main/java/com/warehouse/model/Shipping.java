package com.warehouse.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "shipping_tab")
public class Shipping {


    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "ship_gen")
    @SequenceGenerator(name = "ship_gen" , sequenceName = "shippinggenerator")
    private Integer id;

    @Column(name = "shipping_code")
    private String shippingCode;

    @Column(name = "ship_ref_num_col")
    private String shipRefNum;

    @Column(name = "courier_ref_num_col")
    private String courierRefNum;

    @Column(name = "contact")
    private String contactDetails;



    // Behaves as OneToOne
    @ManyToOne
    @JoinColumn(name = "so_id_fk_col",unique = true)
    private SaleOrder so;


    @Column(  name = "ship_description_col")
    private String description;

    @Column(name = "bill_add_col")
    private String billToAddress;

    @Column(name = "ship_add_col")
    private String shipToAddress;


}
