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
public class UomUtil {

    public void generatePieChart(String path, List<Object[]> uomTypeCount) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Object[] ob : uomTypeCount) {
            dataset.setValue(ob[0].toString(), Double.parseDouble(ob[1].toString()));
        }

        JFreeChart chart =
                ChartFactory.createPieChart("Uom Type Pie Chart", dataset);
        try {
            ChartUtils.saveChartAsJPEG(new File(path + "uomTypePieChart.jpeg"), chart, 300, 300);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateBarChart(String path, List<Object[]> uomTypeCount) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Object[] ob : uomTypeCount) {
            dataset.setValue(Double.parseDouble(ob[1].toString()), ob[0].toString(), "");
        }
        JFreeChart chart =
                ChartFactory.createBarChart("Uom Type Bar Chart", "", "", dataset);
        try {
            ChartUtils.saveChartAsJPEG(new File(path + "uomTypeBarChart.jpeg"), chart, 300, 300);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
