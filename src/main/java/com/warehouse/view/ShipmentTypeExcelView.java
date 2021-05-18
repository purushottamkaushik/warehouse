package com.warehouse.view;


import com.warehouse.model.ShipmentType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ShipmentTypeExcelView extends AbstractXlsxView {


    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        httpServletResponse.addHeader("Content-Disposition","attachments;filename=Shipments.xlsx");
        List<ShipmentType> data = (List<ShipmentType>)map.get("list");

        Sheet sheet = workbook.createSheet();
        addHeader(sheet);
        addBody(sheet , data);
    }

    private void addHeader(Sheet sheet) {

       Row row =  sheet.createRow(0);
       row.createCell(0).setCellValue("ID");
       row.createCell(1).setCellValue("MODE");
       row.createCell(2).setCellValue("CODE");
       row.createCell(3).setCellValue("ENABLED");
       row.createCell(4).setCellValue("GRADE");
       row.createCell(4).setCellValue("DESCRIPTION");

    }
    private void addBody(Sheet sheet , List<ShipmentType> data) {
        int rowIndex = 1;
        for (ShipmentType shipmentType : data) {
            Row row = sheet.createRow(rowIndex++) ; // I
            row.createCell(0).setCellValue(shipmentType.getShipmentId());
            row.createCell(1).setCellValue(shipmentType.getShipmentMode());
            row.createCell(2).setCellValue(shipmentType.getShipmentCode());
            row.createCell(3).setCellValue(shipmentType.getEnableShipment());
            row.createCell(4).setCellValue(shipmentType.getShipmentGrade());
            row.createCell(4).setCellValue(shipmentType.getDescription());
        }
    }
}
