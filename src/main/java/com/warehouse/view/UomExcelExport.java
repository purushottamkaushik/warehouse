package com.warehouse.view;

import com.warehouse.model.Uom;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class UomExcelExport extends AbstractXlsxView {
    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        httpServletResponse.addHeader("Content-Disposition", "attachments;filename=Uom.xlsx");
        List<Uom> uom = (List<Uom>) map.get("list");
        Sheet sheet = workbook.createSheet();
        addHeader(sheet);
        addBody(sheet, uom);
    }

    private void addHeader(Sheet sheet) {
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("Uom Id");
        row.createCell(1).setCellValue("Uom Type");
        row.createCell(2).setCellValue("Uom Model");
        row.createCell(3).setCellValue("Uom Description");
    }

    private void addBody(Sheet sheet, List<Uom> data) {

        int rowIndex = 1;
        for (Uom uom : data) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(uom.getId());
            row.createCell(1).setCellValue(uom.getUomType());
            row.createCell(2).setCellValue(uom.getUomModel());
            row.createCell(3).setCellValue(uom.getDescription());
        }

    }
}
