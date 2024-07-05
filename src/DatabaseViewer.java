import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

class CustomDefaultTableModel extends DefaultTableModel{

    public String relation; //Required to know which SQL Table gets updated
    public JPanel parent; //Required to set the parent of dialog box.
    @Override
    public boolean isCellEditable(int row, int column) {
        //Making primary key uneditable(Necessary to identify rows for other updates)
        return column != 0;
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        /*
            Calling UpdateTable() from SQLInterface to update the relation.
            Called before setValueAt() to ensure that incorrect updates are not reflected in the model
        */
        try {
            new SQLInterface().UpdateTable(relation, getColumnName(0), (String) getValueAt(row, 0),
                    getColumnName(column), (String) aValue);
            super.setValueAt(aValue, row, column);
        } catch (SQLException e) {
            //Creates error message dialog box
            String error = "Error!! " + e;
            JOptionPane.showMessageDialog(parent, error, "Error!!", JOptionPane.ERROR_MESSAGE);
        }
    }
}
class TablePane extends JPanel{
    public CustomDefaultTableModel model;
    public String relation;
    TablePane(String name, int width, int height) throws SQLException {
        //Layout won't make a difference here
        setLayout(null);

        relation = name;
        model = new CustomDefaultTableModel();

        loadData();

        //Creating JTable with JScrollPane
        JTable relationTable = new JTable(model);
        JScrollPane scroll = new JScrollPane(relationTable);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setSize(width, height);
        add(scroll);
        scroll.setVisible(true);
    }

    public void loadData(){
        //Populating the model by calling fetchData() from SQLInterface. Any error reflected in the model itself
        try {
            String query = "Select * from " + relation;
            model.relation = relation;
            model.parent = this;
            new SQLInterface().fetchData(query, model);
        } catch (SQLException e){
            model.addRow(new String[]{"Error", String.valueOf(e)});
        }
    }
}

class DatabaseViewer extends JPanel {
    DatabaseViewer(){
        JTabbedPane relations = new JTabbedPane();
        /*
            Obtaining all the tables in the database and adding a panel corresponding to each in
            JTabbedPane relations.
        */
        try {
            for(String table:new SQLInterface().showTables()){
                relations.add(table, new TablePane(table, 620, 375));
            }
        } catch (SQLException e) {
            //Dialog box to show error(Exception thrown by showTables() in SQLInterface
            String error = "Error!! " + e;
            JOptionPane.showMessageDialog(null, error, "Error!!",
                    JOptionPane.ERROR_MESSAGE);
        }
        relations.setBounds(0, 40,630, 400);
        add(relations);relations.setVisible(true);
        setSize(640, 480);

        //Creating back button with backImageIcon and adding action listener to go back to Admin_Main
        JButton back = new JButton(Resources.backImageIcon);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Component jp : relations.getComponents()){
                    ((TablePane) jp).loadData();
                }
                Main.navigate("AdminMain");
            }
        });
        back.setBounds(0, 0, Resources.backImageIcon.getIconWidth(), Resources.backImageIcon.getIconHeight());

        //Creating Save button
        add(back);
        setLayout(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        FlatMacDarkLaf.setup();
        new DatabaseViewer();
    }
}
