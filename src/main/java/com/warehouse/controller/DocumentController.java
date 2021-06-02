package com.warehouse.controller;

import com.warehouse.model.Document;
import com.warehouse.service.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.util.List;

@Controller
@RequestMapping("/doc")
public class DocumentController {

    @Autowired
    private IDocumentService service;

    @Autowired
    private HttpServletResponse response;

    @GetMapping("/register")

    public String docRegister(Model model) {
//     model.addAttribute("docId",System.currentTimeMillis());
        return "DocumentRegister";
    }

    @PostMapping("/upload")
    public String uploadDocument(@RequestParam Integer docId,
                               @RequestParam MultipartFile docObj) {
        try {
            Document doc = new Document();
            doc.setDocId(docId);
            if(docObj.getOriginalFilename() !=null && docObj.getOriginalFilename().length()>0) {
                doc.setDocName(docObj.getOriginalFilename());
            } else{
                throw new FileNotFoundException("File doesnt exists");
            }
            doc.setDocData(docObj.getBytes());
            service.saveDocument(doc);
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:all";
    }

    @GetMapping("/all")
    public String getAllDocs(Model model) {
        try {
            List<Object[]> list = service.getDocumentIdAndName();
            model.addAttribute("list",list);
        } catch (Exception e ){
            e.printStackTrace();
        }
        return "DocumentRegister";
    }

    @GetMapping("/delete")
    public  String deleteDocument(@RequestParam Integer id) {
        try {
            service.deleteDocumentById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  "redirect:all";

    }

    @GetMapping("/download")
    public void downloadDocument(@RequestParam Integer id) {
        try{
            Document document  = service.getDocumentById(id);
            response.setHeader("Content-Disposition","attachment;filename"+document.getDocName());
            FileCopyUtils.copy(document.getDocData() ,response.getOutputStream());
        } catch (Exception e){
            e.printStackTrace();

        }
    }

}
