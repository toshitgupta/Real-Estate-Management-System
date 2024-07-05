import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class All_Agent_Details extends JPanel {
    JLabel agent_detail_label;
    CustomDefaultTableModel agent_detail_tableModel;
    JTable agent_JTable;
    JScrollPane agent_JScrollPane;
    JLabel agent_count_label;
    DefaultTableModel deal_tableModel;
    JLabel total_price_volume_label;
    All_Agent_Details () {
        this.setSize(640, 480);
        this.setLayout(null);

        agent_detail_label = new JLabel("Details of all Registered Agents are as follows:");
        agent_detail_label.setBounds(30, 40, 500, 15);

        try {
            String query = "SELECT * FROM agent";
            agent_detail_tableModel = new CustomDefaultTableModel();
            agent_detail_tableModel.relation = "agent";
            agent_detail_tableModel.parent = this;
            new SQLInterface().fetchData(query, agent_detail_tableModel);

            agent_JTable = new JTable(agent_detail_tableModel);

            agent_JScrollPane = new JScrollPane(agent_JTable);
            agent_JScrollPane.setBounds(20, 65, 600, 300);
            agent_JScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            agent_JScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        agent_count_label = new JLabel("Total number of Agents = "
                .concat(String.valueOf(agent_detail_tableModel.getRowCount())));
        agent_count_label.setBounds(30, 380, 300, 15);

        try {
            deal_tableModel = new DefaultTableModel();
            String query = "SELECT * FROM deal";
            new SQLInterface().fetchData(query, deal_tableModel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        total_price_volume_label = new JLabel("Total Price Volume of property sold = "
                .concat(String.valueOf(CalculateTotalDealPrice(deal_tableModel))));
        total_price_volume_label.setBounds(30, 410, 500, 15);
        NaviButton back = new NaviButton(Resources.backImageIcon, Office_Interface_Main.OFFICE_MAIN, this);
        back.setLocation(10, 10);
        this.add(agent_detail_label);
        this.add(agent_JScrollPane);
        this.add(total_price_volume_label);
        this.setVisible(true);
    }

    public String CalculateTotalDealPrice (DefaultTableModel deal_tableModel) {
        if(deal_tableModel.getRowCount() == 0) return "0";
        float total_amount = 0;
        for (int i = 0; i < deal_tableModel.getRowCount(); i++) {
            total_amount += Float.parseFloat(deal_tableModel.getValueAt(i, deal_tableModel.findColumn("value")).toString()) * 100000;
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);
        return decimalFormat.format(total_amount);
    }
}
