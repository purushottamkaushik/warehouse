package com.warehouse.model;

import com.warehouse.consts.RoleType;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "roles_tab")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING) // Used to store enum values as string in database
    private RoleType role;

}
