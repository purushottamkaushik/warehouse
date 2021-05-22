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
public class ShipmentTypeUtil {
    public void generatePieChart(String path, List<Object[]> list) {
        //1. Create Dataset
        DefaultPieDataset dataset = new DefaultPieDataset();
        // 2. Fill the data
        for (Object[] ob : list) {
            dataset.setValue(ob[0].toString(), Double.parseDouble(ob[1].toString()));
        }
        //3 Create chart
        JFreeChart chart = ChartFactory.createPieChart("Shipment Mode ", dataset);
        // 4 Save chart as image
        try {
            ChartUtils.saveChartAsJPEG(new File(path + "shipmentModePie.jpeg"), chart, 300, 300);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateBarChart(String path, List<Object[]> list) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Object[] ob : list) {
            dataset.setValue(Double.parseDouble(ob[1].toString()), ob[0].toString(), "");
        }
        JFreeChart chart = ChartFactory.createBarChart("Shipment Mode Bar Chart", "", "Count", dataset);
        try {
            ChartUtils.saveChartAsJPEG(new File(path + "shipmentModeBarChart.jpeg"), chart, 300, 300);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
