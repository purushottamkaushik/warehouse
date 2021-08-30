package com.warehouse.model;

import com.warehouse.consts.UserMode;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "users_tab")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    private UserMode mode = UserMode.DISABLED;

    @ManyToMany(fetch = FetchType.EAGER )
    private Set<Role> roles;


}
