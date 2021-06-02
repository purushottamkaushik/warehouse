package com.warehouse.service;

import com.warehouse.customexception.OrderMethodNotFoundException;
import com.warehouse.model.Document;
import com.warehouse.model.OrderMethod;

import java.util.List;

public interface IDocumentService {

  void saveDocument(Document document);
  List<Object[]> getDocumentIdAndName();
  void deleteDocumentById(Integer id);

  Document getDocumentById(Integer id);
}
