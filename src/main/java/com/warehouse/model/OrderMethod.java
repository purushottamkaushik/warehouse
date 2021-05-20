package com.warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "order_method")
public class OrderMethod {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "order_method_gen")
    @SequenceGenerator(name = "order_method_gen",sequenceName = "ordermethodsequence")
    private Integer orderMethodId;

    @Column(name = "order_mode")
    private String orderMode;

    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "order_type")
    private String orderType;

    @ElementCollection // To create child table
    @CollectionTable(name = "order_accept_tab",   // Name of the child table
            joinColumns = @JoinColumn(name = "om_id_col") // Foreign key column Name
    )
    @Column(name = "order_method_accept") // Primary key column name in parent table
    private Set<String> orderAccept;

    @Column(name = "description")
    private String description;

}
