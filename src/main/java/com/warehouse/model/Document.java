package com.warehouse.model;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "doc_tab")
public class Document {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "doc_gen")
    @SequenceGenerator(name = "doc_gen",sequenceName = "doc_gen_sequence")
    private Integer docId ;

    @Column(name = "doc_name",nullable = false,unique = true)
    private String docName;

    @Column(name = "doc_data" ,nullable = false,unique = true)
    @Lob
    private byte[] docData; // this will behave as a blob data
}
