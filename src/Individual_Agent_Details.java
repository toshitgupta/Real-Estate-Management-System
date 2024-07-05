import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class Individual_Agent_Details extends JPanel implements ActionListener {
    JLabel agent_name_label;
    JLabel agent_id_label;
    JLabel email_label;
    JLabel phone_number_label;
    JLabel deal_details_label;
    DefaultTableModel deal_tableModel;
    JTable deal_JTable;
    JScrollPane deal_JScrollPane;
    JLabel deal_count_label;
    JLabel avg_deal_price_label;
    JLabel avg_market_time;     //avg amount of time a property sold by this agent was in the market available(i.e. for rent/sale)
    JButton back_button;
    Individual_Agent_Details(int a_id) throws SQLException {
        //super("Agent Details");
        this.setSize(640, 480);
        this.setLayout(null);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);   //with this you can make the JFrame go back
                                                                    // when the close button is pressed

        deal_tableModel = new DefaultTableModel();
        try {
            String query = "SELECT * FROM deal WHERE a_id = " + a_id;
            new SQLInterface().fetchData(query, deal_tableModel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        back_button = new JButton("Back");
        back_button.setBounds(0, 0, 90, 30);
        back_button.addActionListener(this);
        back_button.setFocusable(false);

        String[] details = new SQLInterface().fetchData("agent", "a_id", ""+a_id)[1];

        agent_name_label = new JLabel("Name : " + details[1]);
        agent_name_label.setBounds(30, 50, 300, 15);

        agent_id_label = new JLabel("Agent ID : ".concat(details[0]));
        agent_id_label.setBounds(30, 80, 300, 15);

        email_label = new JLabel("Email : ".concat(details[2]));
        email_label.setBounds(30, 110, 300, 15);

        phone_number_label = new JLabel("Contact Number : ".concat(details[3]));
        phone_number_label.setBounds(30, 140, 300, 15);

        deal_details_label = new JLabel("The Details of the deals conducted by "
                .concat(details[1])
                .concat(" are as follows : "));
        deal_details_label.setBounds(30, 180, 550, 15);

        deal_JTable = new JTable(deal_tableModel);

        deal_JScrollPane = new JScrollPane(deal_JTable);
        deal_JScrollPane.setBounds(20, 205, 600, 70);
        deal_JScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        deal_JScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        deal_count_label = new JLabel("Number of Deals Completed : "
                .concat(String.valueOf(deal_tableModel.getRowCount())));
        deal_count_label.setBounds(30, 290, 300, 15);

        avg_deal_price_label = new JLabel("Average Price of Deals : Rs. "
                .concat(CalculateAvgDealPrice(deal_tableModel)));
        avg_deal_price_label.setBounds(30, 320, 300, 15);

        avg_market_time = new JLabel("Average time the property was on market : "
                .concat(String.valueOf(CalculateAvgMarketTime(deal_tableModel)))
                .concat(" Days"));
        avg_market_time.setBounds(30, 350, 600, 15);

        this.add(back_button);
        this.add(agent_name_label);
        this.add(agent_id_label);
        this.add(email_label);
        this.add(phone_number_label);
        this.add(deal_details_label);
        this.add(deal_JScrollPane);
        this.add(deal_count_label);
        this.add(avg_deal_price_label);
        this.add(avg_market_time);
        //this.setLocationRelativeTo(previous_frame);
        this.setVisible(true);
    }
    public String CalculateAvgDealPrice (DefaultTableModel deal_tableModel) {
        if(deal_tableModel.getRowCount() == 0) return "0";
        float total_amount = 0;
        for (int i = 0; i < deal_tableModel.getRowCount(); i++) {
            total_amount += Float.parseFloat(deal_tableModel.getValueAt(i, deal_tableModel.findColumn("value")).toString()) * 100000;
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);
        return decimalFormat.format(total_amount/deal_tableModel.getRowCount());
    }
    public int CalculateAvgMarketTime (DefaultTableModel deal_tableModel) {
        if(deal_tableModel.getRowCount() == 0) return 0;
        int total_time = 0;
        for (int i = 0; i < deal_tableModel.getRowCount(); i++) {
            total_time += Integer.parseInt(deal_tableModel.getValueAt(i, deal_tableModel.findColumn("time_taken")).toString());
        }
        return total_time/deal_tableModel.getRowCount();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if ((actionEvent.getSource() == back_button)) {
            Main.navigate("SearchAgent");
        }
    }
}