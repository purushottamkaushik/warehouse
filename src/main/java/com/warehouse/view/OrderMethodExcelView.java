package com.warehouse.view;

import com.warehouse.model.OrderMethod;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.criterion.Order;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class OrderMethodExcelView extends AbstractXlsxView {
    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        httpServletResponse.addHeader("Content-Disposition","attachments;filename=orderMethod.xlsx");

        List<OrderMethod> list =(List<OrderMethod>) map.get("list");

        Sheet sheet = workbook.createSheet();
        addHeader(sheet);
        addBody(sheet,list);
    }

    private void addBody(Sheet sheet, List<OrderMethod> list) {
         int rowIndex = 1;
         for (OrderMethod om : list) {
             Row row = sheet.createRow(rowIndex++);
             row.createCell(0).setCellValue(om.getId());
             row.createCell(1).setCellValue(om.getOrderMode());
             row.createCell(2).setCellValue(om.getOrderCode());
             row.createCell(3).setCellValue(om.getOrderType());
             row.createCell(4).setCellValue(om.getOrderAccept().toString());
             row.createCell(5).setCellValue(om.getDescription());
         }

    }

    private void addHeader(Sheet sheet) {
        Row row =  sheet.createRow(0);
        row.createCell(0).setCellValue("ID");
        row.createCell(1).setCellValue("MODE");
        row.createCell(2).setCellValue("CODE");
        row.createCell(3).setCellValue("TYPE");
        row.createCell(4).setCellValue("ACCEPT");
        row.createCell(5).setCellValue("DESCRIPTION");
    }
}
