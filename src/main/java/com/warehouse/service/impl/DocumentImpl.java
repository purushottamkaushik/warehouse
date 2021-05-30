package com.warehouse.service.impl;

import com.warehouse.model.Document;
import com.warehouse.repo.DocumentRepository;
import com.warehouse.service.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentImpl implements IDocumentService {

    @Autowired
    private DocumentRepository repository;

    @Override
    public void saveDocument(Document document) {
        repository.save(document);
    }

    @Override
    public List<Object[]> getDocumentIdAndName() {
        return repository.getDocumentIdAndName();
    }
}
