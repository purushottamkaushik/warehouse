package com.warehouse.controller;

import com.warehouse.model.Document;
import com.warehouse.service.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/doc")
public class DocumentController {

    @Autowired
    private IDocumentService service;


    @GetMapping("/register")

    public String docRegister(Model model)
    {   model.addAttribute("docId",System.currentTimeMillis());
        return "DocumentRegister";
    }

    @PostMapping("/upload")
    public String uploadDocument(@RequestParam long docId,
                               @RequestParam MultipartFile docObj) {
        try {
            Document doc = new Document();
            doc.setDocId(docId);
            doc.setDocName(docObj.getOriginalFilename());
            doc.setDocData(docObj.getBytes());
            service.saveDocument(doc);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:all";
    }

    @GetMapping("/all")
    public String getAllDocs(Model model) {
        try {
            List<Object[]> list = service.getDocumentIdAndName();
            model.addAttribute("docId",System.currentTimeMillis());
            model.addAttribute("list",list);
        } catch (Exception e ){
            e.printStackTrace();
        }
        return "DocumentRegister";
    }


}
