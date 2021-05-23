package com.warehouse.view;

import com.warehouse.model.WhUserType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class WhUserTypeExcelView extends AbstractXlsxView {
    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        httpServletResponse.addHeader("Content-Disposition", "attachments;filename=WhUserType.xlsx");
        List<WhUserType> list = (List<WhUserType>) map.get("list");
        Sheet sheet = workbook.createSheet();
        addHeader(sheet);
        addBody(sheet, list);
    }

    private void addBody(Sheet sheet, List<WhUserType> list) {

        int rowIndex = 1;
        for (WhUserType wh : list) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(wh.getId());
            row.createCell(1).setCellValue(wh.getUserType());
            row.createCell(2).setCellValue(wh.getUserCode());
            row.createCell(3).setCellValue(wh.getUserFor());
            row.createCell(4).setCellValue(wh.getUserEmail());
            row.createCell(5).setCellValue(wh.getUserContact());
            row.createCell(6).setCellValue(wh.getUserIdType());
            row.createCell(7).setCellValue(wh.getIfOther());
            row.createCell(8).setCellValue(wh.getUserIdNum());
        }
    }

    private void addHeader(Sheet sheet) {
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("ID");
        row.createCell(1).setCellValue("Typ");
        row.createCell(2).setCellValue("code");
        row.createCell(3).setCellValue("FOR");
        row.createCell(4).setCellValue("Email");
        row.createCell(5).setCellValue("Contact");
        row.createCell(6).setCellValue("ID type");
        row.createCell(7).setCellValue("OTHER");
        row.createCell(8).setCellValue("USERID NUM");

    }
}
