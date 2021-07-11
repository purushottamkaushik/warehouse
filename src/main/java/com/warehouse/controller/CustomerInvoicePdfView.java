package com.warehouse.controller;

import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.warehouse.model.SaleDtl;
import com.warehouse.model.SaleOrder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class CustomerInvoicePdfView extends AbstractPdfView {
    @Override
    protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
        super.buildPdfMetadata(model, document, request);
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> map, Document document, PdfWriter pdfWriter,
                                    HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        Paragraph heading = new Paragraph("Customer Invoice ", new Font(Font.TIMES_ROMAN, 12, Font.BOLD, Color.BLUE));
        heading.setAlignment(Element.ALIGN_CENTER);
        heading.setSpacingAfter(25.0f);

        SaleOrder so = (SaleOrder) map.get("so");
        List<SaleDtl> saleDtlList = (List<SaleDtl>) map.get("list");

        double finalCost = 0.0;
        for (SaleDtl saleDtl : saleDtlList) {
            finalCost += saleDtl.getQty() * saleDtl.getPart().getPartBaseCost();
        }
        document.add(heading);

        Font tableFont = new Font(Font.TIMES_ROMAN, 12, Font.BOLD, Color.WHITE);
        PdfPTable table = new PdfPTable(4);

        table.addCell(generateCellData("Customer Code", tableFont));
        table.addCell(generateCellData(so.getCustomer().getUserCode(), tableFont));

        table.addCell(generateCellData("Order Code", tableFont));
        table.addCell(generateCellData(so.getOrderCode(), tableFont));

        table.addCell(generateCellData("Final Cost", tableFont));
        table.addCell(generateCellData(String.valueOf(finalCost), tableFont));

        table.addCell(generateCellData("Shipment Type", tableFont));
        table.addCell(generateCellData(so.getSt().getShipmentCode(), tableFont));


        document.add(table);

        Paragraph detailText = new Paragraph(new Phrase("Item Details", new Font(Font.TIMES_ROMAN, 10, Font.BOLD, Color.GREEN)));
        detailText.setAlignment(Element.ALIGN_CENTER);
        document.add(detailText);
        Font detailsTableFont = new Font(Font.TIMES_ROMAN,12,Font.BOLD,Color.WHITE);

        PdfPTable detailParentTab = new PdfPTable(4);
        detailParentTab.setSpacingBefore(5.5f);
        detailParentTab.addCell(generateCellData("Item Code",detailsTableFont));
        detailParentTab.addCell(generateCellData("Base Cost",detailsTableFont));
        detailParentTab.addCell(generateCellData("Quantity",detailsTableFont));
        detailParentTab.addCell(generateCellData("Item Value",detailsTableFont));

        document.add(detailParentTab);
        PdfPTable detailChildTab = new PdfPTable(4);
        for (SaleDtl saleDtl : saleDtlList){
            detailChildTab.addCell(generateCellData(saleDtl.getPart().getPartCode(),detailsTableFont));
            detailChildTab.addCell(generateCellData(String.valueOf(saleDtl.getPart().getPartBaseCost()),detailsTableFont));
            detailChildTab.addCell(generateCellData(String.valueOf(saleDtl.getQty()),detailsTableFont));
            double totalvalue = saleDtl.getQty() * saleDtl.getPart().getPartBaseCost();
            detailChildTab.addCell(generateCellData(String.valueOf(totalvalue),detailsTableFont));
        }
        document.add(detailChildTab);

        Paragraph totalCostParagraph = new Paragraph("Total Cost : " + finalCost + " Rs");
        totalCostParagraph.setAlignment(Element.ALIGN_CENTER);
        totalCostParagraph.setSpacingBefore(10.0f);
        document.add(totalCostParagraph);
        httpServletResponse.addHeader("Content-Disposition", "attachment;filename=CustomerInvoice.pdf");

    }

    private PdfPCell generateCellData(String input, Font font) {
        PdfPCell pCell = new PdfPCell(new Phrase(input, font));
        pCell.setBackgroundColor(Color.BLACK);
        pCell.setBorder(Rectangle.BOTTOM);
        return pCell;
    }
}
