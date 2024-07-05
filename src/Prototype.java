import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class Prototype extends JFrame {
    private int width;
    private int height;
    private static ResultSet rs;
    public static DefaultTableModel model = new DefaultTableModel();
    public static JComboBox<String> jc;
    Prototype() {
        super("Table");
        width = 640;
        height = 480;
        jc = new JComboBox<>(getTables());
        JTable jt = new JTable();
        extractData((String) jc.getSelectedItem());
        jt.setModel(model);
        jt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane sp = new JScrollPane(jt);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setBounds(20, 50, 550, 360);
        JButton sel = new JButton("GO!!");
        sel.addActionListener(new SetTable());
        sel.setBounds(500, 20, 70, 20);
        jc.setBounds(150, 20, 200, 20);
        add(sp);add(sel);add(jc);
        setSize(width, height);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        new Prototype();
    }

    public static void extractData(String tableName) {
        try {
            model.setNumRows(0);
            model.setColumnCount(0);
            String url = "jdbc:mysql://localhost:3306/RealEstate";
            String username = "root";
            String password = "vimaln";
            Connection connection = DriverManager.getConnection(url, username, password);
            rs = connection.createStatement().executeQuery("Select * from " + tableName);
            ResultSetMetaData rsmd = rs.getMetaData();
            String[] columnNames = new String[rsmd.getColumnCount()];
            for (int i = 0; i < rsmd.getColumnCount(); i++){
                columnNames[i] = rsmd.getColumnName(i+1);
            }
            model.setColumnIdentifiers(columnNames);
            while (rs.next()){
                String[] row = new String[rsmd.getColumnCount()];
                for (int i = 0; i < rsmd.getColumnCount(); i++){
                    row[i] = rs.getString(i+1);
                }
                model.addRow(row);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    private static String[] getTables(){
        try {
            String url = "jdbc:mysql://localhost:3306/RealEstate";
            String username = "root";
            String password = "vimaln";
            Connection connection = DriverManager.getConnection(url, username, password);
            rs = connection.createStatement().executeQuery("Show Tables;");
            String[] tables = new String[5];
            int i = 0;
            while (rs.next()){
                tables[i++] = rs.getString(1);
            }
            return tables;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
}

class SetTable implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton) e.getSource();
        Prototype.extractData((String) Prototype.jc.getSelectedItem());
    }
}
