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
    private long docId ;

    @Column(name = "doc_name")
    private String docName;

    @Column(name = "doc_data")
    @Lob
    private byte[] docData; // this will behave as a blob data
}
