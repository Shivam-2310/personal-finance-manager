package com.shivam.personal_finance_manager.service;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Service
public class ChartService {

    public JFreeChart createSpendingCategoryPieChart(Map<String, BigDecimal> categorySpending) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        for (Map.Entry<String, BigDecimal> entry : categorySpending.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue().doubleValue());
        }

        return ChartFactory.createPieChart(
                "Spending by Category",
                dataset,
                true,  // legend
                true,  // tooltips
                false  // urls
        );
    }

    public JFreeChart createMonthlySpendingBarChart(Map<LocalDate, BigDecimal> dailySpending) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dailySpending.forEach((date, amount) -> {
            dataset.addValue(amount.doubleValue(), "Spending", date.toString());
        });

        return ChartFactory.createBarChart(
                "Daily Spending Trend",
                "Date",
                "Amount",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
    }

    public byte[] generateChartImage(JFreeChart chart, int width, int height) throws IOException {
        BufferedImage image = chart.createBufferedImage(width, height);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
}
