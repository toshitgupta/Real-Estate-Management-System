import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarGraph extends JPanel {
    ChartPanel chartPanel;
    BarGraph() {
        String url = "jdbc:mysql://localhost:3306/RealEstate";
        String username = "root";
        String password = "vimaln";
        String query = "select a_id, count(d_id) from agent NATURAL JOIN deal group by a_id order by count(d_id) " +
                "desc limit 10";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String category = resultSet.getString("a_id");
                int value = resultSet.getInt("count(d_id)");
                dataset.addValue(value, "Value", category);
            }

            JFreeChart chart = ChartFactory.createBarChart(
                    "My Dynamic Bar Chart",
                    "Agent_id",
                    "Number_of_deals",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false
            );

            chartPanel = new ChartPanel(chart);
            setLayout(new BorderLayout());
            add(chartPanel, BorderLayout.CENTER);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new BarGraph());
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
