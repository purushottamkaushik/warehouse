package com.warehouse.view;

import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.warehouse.model.PurchaseDtl;
import com.warehouse.model.PurchaseOrder;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class VendorInvoicePdfView extends AbstractPdfView {
    @Override
    protected void buildPdfDocument(Map<String, Object> map, Document document,
                                    PdfWriter pdfWriter, HttpServletRequest httpServletRequest,
                                    HttpServletResponse response) throws Exception {

        PurchaseOrder po = (PurchaseOrder) map.get("po");
        List<PurchaseDtl> list = (List<PurchaseDtl>) map.get("list");

        // Calculating final cost of the generated PO Bill (Invoice)
        double finalCost = 0.0;
        int totalQty = 0 ;
        for(PurchaseDtl dtl : list) {
            totalQty += dtl.getQty();
            finalCost+= dtl.getQty() * dtl.getPart().getPartBaseCost();
        }

        /***
         * Adding one Image
         */
        Image img = Image.getInstance("https://s3-ap-southeast-1.amazonaws.com/tv-prod/member/photo/745494-large.jpg");
        //set width and height
        img.scaleAbsolute(250, 60);
        //set alignment
        img.setAlignment(Element.ALIGN_CENTER);
        //add to document
        document.add(img);

        /***
         * PDF by default OPEN in browser,
         * Download instead of display file
         */
        // download file with your own name
        response.addHeader("Content-Disposition", "attachment;filename=vendorinvoice.pdf");

        /**
         * One HEADING
         * space before/after
         */
        Font titleFont = new Font(Font.TIMES_ROMAN,30,Font.BOLD,Color.RED);
        Paragraph title = new Paragraph("VENDOR INVOICE",titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(25.0f);
        title.setSpacingBefore(20.0f);
        document.add(title);


        /**
         * Table#1 Display Vendor Code, Order Code, Final Cost, Shipment
         */
        Font tableHead = new Font(Font.TIMES_ROMAN,12,Font.BOLD, Color.WHITE);
        PdfPTable table1 = new PdfPTable(4);
        table1.setSpacingAfter(4.0f);
        table1.setHorizontalAlignment(Element.ALIGN_CENTER);


        table1.addCell(getCellData("VENDOR CODE", tableHead));
        table1.addCell(po.getVendor().getUserCode());
        table1.addCell(getCellData("ORDER CODE",tableHead));
        table1.addCell(po.getOrderCode());

        PdfPTable table2 = new PdfPTable(4);
        table2.setSpacingAfter(4.0f);
        table2.addCell(getCellData("FINAL COST",tableHead));
        table2.addCell(String.valueOf(finalCost));
        table2.addCell(getCellData("SHIPMENT CODE",tableHead));
        table2.addCell(po.getSt().getShipmentCode());


        document.add(table1);
        document.add(table2);


        /**
         * Table#2 Display Every PoDtl : id, code, cost, qty , lineToal
         */

        //PdfPTable child = new PdfPTable(4);
        PdfPTable child = new PdfPTable(new float[] {2.50f,1.5f,1.0f,2.0f});
        child.setSpacingBefore(20.0f);
        child.setSpacingAfter(20.0f);
        child.addCell(getCellData("CODE",tableHead));
        child.addCell(getCellData("BASE COST",tableHead));
        child.addCell(getCellData("QTY",tableHead));
        child.addCell(getCellData("LINE VALUE",tableHead));

        for(PurchaseDtl dtl:list) {
            child.addCell(dtl.getPart().getPartCode());
            child.addCell(dtl.getPart().getPartBaseCost().toString());
            child.addCell(dtl.getQty().toString());
            child.addCell(String.valueOf(dtl.getPart().getPartBaseCost()*dtl.getQty()));

        }
        document.add(child);

        String totalValueText = "Total Cost is " + finalCost + " Rs";
        Paragraph totalValParagraph = new Paragraph(totalValueText );
        totalValParagraph.setAlignment(Element.ALIGN_RIGHT);
        totalValParagraph.setIndentationRight(0.25f);
        document.add(totalValParagraph);

        /***
         * Description
         *  */
        Paragraph description = new Paragraph(
                "**** CURRENT ORDER CONTAINS "+totalQty+" PARTS ****",
                new Font(Font.TIMES_ROMAN, 14,Font.BOLDITALIC,Color.RED)
        );
        description.setSpacingBefore(10.0f);
        description.setAlignment(Element.ALIGN_CENTER);
        document.add(description);
    }
    /**
     * Common method for String with background color set
     */
    private PdfPCell getCellData(String input, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(input,font));
        cell.setBackgroundColor(Color.BLACK);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
}
