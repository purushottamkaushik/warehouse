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
public class OrderMethodUtil {
    public void generatePieChart(List<Object[]> list , String path) {

        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Object[] ob : list) {
            dataset.setValue(ob[0].toString(),Double.parseDouble(ob[1].toString()));
        }
      // Creating chart
//      JFreeChart chart =  ChartFactory.createPieChart("ShipmentType Mode","Modes","Count",dataset);
        JFreeChart chart = ChartFactory.createPieChart("Shipment Type Mode",dataset);
      // Save chart

        try {
            ChartUtils.saveChartAsJPEG(new File(path + "orderModePie.jpeg"),
                    chart,
                    300,300
                    );
            System.out.println("Chart Successfully Saved");
        } catch (IOException e) {

            e.printStackTrace();
        }


    }

    public void generateBarChart(List<Object[]> list, String path) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Object[] ob : list) {
            dataset.setValue( Double.parseDouble(ob[1].toString()),ob[0].toString(),"");
        }

        JFreeChart chart = ChartFactory.createBarChart("Shipment Mode","","",dataset);
        try {
            ChartUtils.saveChartAsJPEG(new File(path+"orderModeBar.jpeg"),chart,300,300);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
