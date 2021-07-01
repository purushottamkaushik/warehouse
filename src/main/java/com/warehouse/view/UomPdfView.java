package com.warehouse.view;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.warehouse.model.Uom;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class UomPdfView extends AbstractPdfView {
    @Override
    protected void buildPdfMetadata(Map<String, Object> model,
                                    Document document,
                                    HttpServletRequest request) {
        HeaderFooter header = new HeaderFooter(new Phrase("Uom Details",new Font(Font.TIMES_ROMAN,22,Font.BOLD,new Color(153,153,102))),false);
        header.setAlignment(Element.ALIGN_CENTER);

        HeaderFooter footer = new HeaderFooter(new Phrase("GENERRATED BY Purushottam",new Font(Font.TIMES_ROMAN,22,Font.BOLD,new Color(153,153,102))),true);
        header.setAlignment(Element.ALIGN_RIGHT);

        document.setHeader(header);
        document.setFooter(footer);

    }

    @Override
    protected void buildPdfDocument(Map<String, Object> map,
                                    Document document,
                                    PdfWriter pdfWriter,
                                    HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse) throws Exception {

        Font titleFont = new Font(Font.TIMES_ROMAN,22,Font.BOLD,new Color(51, 51, 255));
        Paragraph p1 = new Paragraph("Uom Pdf View",titleFont);
        p1.setSpacingAfter(10.0f);
        p1.setAlignment(Element.ALIGN_CENTER);

        PdfPTable table = new PdfPTable(4);
        table.addCell("ID");
        table.addCell("TYPE");
        table.addCell("MODEL");
        table.addCell("DESC");

        List<Uom> uoms = (List<Uom>) map.get("list");
        for (Uom uom : uoms) {
            table.addCell(uom.getId().toString());
            table.addCell(uom.getUomType());
            table.addCell(uom.getUomModel());
            table.addCell(uom.getDescription());
        }

        document.add(p1);
        document.add(table);

    }
}
