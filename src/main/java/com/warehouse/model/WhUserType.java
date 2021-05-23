package com.warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "wh_user_type_tab")
public class WhUserType {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "wh_user_gen")
    @SequenceGenerator(name = "wh_user_gen", sequenceName = "wh_usergen")
    private Integer id;
    @Column(name = "wh_user_type")
    private String userType;

    @Column(name = "wh_user_Code")
    private String userCode;

    @Column(name = "wh_user_For")
    private String userFor;

    @Column(name = "wh_user_email", unique = true)
    private String userEmail;

    @Column(name = "wh_user_contact")
    private String userContact;

    @Column(name = "wh_user_id_type")
    private String userIdType;

    @Column(name = "wh_user_other")
    private String ifOther;

    @Column(name = "wh_user_id_num")
    private String userIdNum;

}
