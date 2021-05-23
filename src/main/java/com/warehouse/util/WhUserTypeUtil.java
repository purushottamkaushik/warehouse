package com.warehouse.util;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class WhUserTypeUtil {


    public void generatePieChart(String path, List<Object[]> list) {

        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Object[] ob : list) {
            dataset.setValue(ob[0].toString(), Double.parseDouble(ob[1].toString()));
        }
        // Creating chart
//      JFreeChart chart =  ChartFactory.createPieChart("ShipmentType Mode","Modes","Count",dataset);
        JFreeChart chart = ChartFactory.createPieChart("WareHouse User Type", dataset);
        // Save chart

        try {
            ChartUtils.saveChartAsJPEG(new File(path + "WareHouseUserTypePie.jpeg"),
                    chart,
                    300, 300
            );
            System.out.println("Chart Successfully Saved");
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    public void generateBarChart(String path, List<Object[]> list) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Object[] ob : list) {
            dataset.setValue(Double.parseDouble(ob[1].toString()), ob[0].toString(), "");
        }

        JFreeChart chart = ChartFactory.createBarChart("WareHouse User Type", "", "", dataset);
        try {
            ChartUtils.saveChartAsJPEG(new File(path + "wareHouseUserTypeBar.jpeg"), chart, 300, 300);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
