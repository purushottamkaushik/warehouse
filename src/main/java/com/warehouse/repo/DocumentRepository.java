package com.warehouse.repo;

import com.warehouse.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface DocumentRepository extends JpaRepository<Document,Integer> {

    @Query("SELECT docId,docName FROM Document")
    List<Object[]> getDocumentIdAndName();
}
