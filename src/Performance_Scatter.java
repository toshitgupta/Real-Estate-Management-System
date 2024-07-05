import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import javax.swing.*;

import org.jfree.chart.*;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Performance_Scatter extends JPanel implements ChartMouseListener {
    ChartPanel chartPanel;
    public Performance_Scatter() {
        this.setSize(640, 480);

        XYDataset xyDataset = createDataset();
        JFreeChart scatter_plot_JFreeChart = ChartFactory.createScatterPlot(
                "Agent Performance Graph",
                "Number of Deals",
                "Average Value of Deals",
                xyDataset
        );

        XYPlot plot = (XYPlot) scatter_plot_JFreeChart.getPlot();
        //plot.setBackgroundPaint(new Color(119,255,252));

        plot.getRenderer().setDefaultItemLabelGenerator(new XYItemLabelGenerator() {
            @Override
            public String generateLabel(XYDataset xyDataset, int i, int i1) {
                return "a_id";
            }
        });

        chartPanel = new ChartPanel(scatter_plot_JFreeChart);
        //chartPanel.setSize(640, 480);
        this.add(chartPanel);
        this.setVisible(true);
    }
    public ChartPanel Get_ChartPanel()  {
        return chartPanel;
    }
    private XYDataset createDataset() {
        try {
            XYSeriesCollection dataset = new XYSeriesCollection();

            String url = "jdbc:mysql://localhost:3306/RealEstate";
            String username = "root";
            String password = "vimaln";
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Current Database: " + connection.getCatalog());
            ResultSet resultSet;

            XYSeries deal_count_xy_Series = new XYSeries("Average Value of Deal (in lakhs)");
            resultSet = connection.createStatement().executeQuery(
                    "SELECT a_id, COUNT(*) AS count, AVG(value) AS avg FROM deal GROUP BY (a_id)"
            );
            while (resultSet.next()) {
                deal_count_xy_Series.add(
                        resultSet.getInt("count"),
                        resultSet.getInt("avg")
                );
            }

            dataset.addSeries(deal_count_xy_Series);

            return dataset;
        } catch (SQLException e) {
            System.out.println("A SQL exception occurred!!!");
            throw new RuntimeException(e);
        }
    }
    static String get_password() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(
                    "/home/vaibhav/Documents/DBMS_Lab/password.txt"
            ));
            String password = bufferedReader.readLine();
            bufferedReader.close();
            return password;
        }
        catch (IOException e) {
            System.out.println("Error!!! Password File not found/is empty.");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void chartMouseClicked(ChartMouseEvent chartMouseEvent) {
        ChartEntity chartEntity = chartMouseEvent.getEntity();

        if (chartEntity instanceof XYItemEntity xyItemEntity) {

            int seriesIndex = xyItemEntity.getSeriesIndex();

            JOptionPane.showMessageDialog(null, seriesIndex);
            System.out.println(seriesIndex);
        }
    }

    @Override
    public void chartMouseMoved(ChartMouseEvent chartMouseEvent) {

    }
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(640, 480);
        frame.setContentPane(new Performance_Scatter().Get_ChartPanel());
        //frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}