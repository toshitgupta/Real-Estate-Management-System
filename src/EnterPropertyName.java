import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class EnterPropertyName{
    JPanel f;
    private int phase = 1;
    private String op;
    EnterPropertyName(String operation){
        op = operation;
        DefaultTableModel model = new DefaultTableModel();
        this.f = new JPanel(null);
        JLabel header = new JLabel("Enter property name");
        JLabel nameLabel = new JLabel("Property name : ");
        JTextField prop_name = new JTextField();
        JButton submit = new JButton("Submit");
        JButton back = new NaviButton(Resources.backImageIcon, "AgentMain", this.f);
        header.setFont(new Font("Courier", Font.PLAIN, 15));
        prop_name.setFont(new Font("Courier", Font.PLAIN, 17));
        nameLabel.setFont(new Font("Courier", Font.PLAIN, 17));
        submit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JTable jt = new JTable(model);
        JScrollPane sp = new JScrollPane(jt);
        sp.setVisible(false);
        sp.setBounds(10,40,600,290);
        header.setBounds(240,40,150,56);
        nameLabel.setBounds(120,160,150,20);
        prop_name.setBounds(270,160,230,25);
        submit.setBounds(250,400,100,40);
        back.setLocation(10, 10);

        submit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                try{
                    if (phase == 1) {
                        String name;
                        name = prop_name.getText();
                        model.setNumRows(0);
                        model.setColumnCount(0);
                        String query = "select prop_name, street_name, locality, construction_year, " +
                                "bedrooms, status, sq_ft, price from " +
                                "property NATURAL JOIN status where prop_name like '%"+name+"%';";
                        //System.out.println(query);
                        new SQLInterface().fetchData(query, model);

                        if(isEmpty(jt) && operation.equals("New")) {
                            Main.mainWindow.add(new NewProperty(0, new String[]{name}).f,
                                    "AddProperty");
                            Main.navigate("AddProperty");
                        }
                        else {
                            header.setVisible(false);
                            prop_name.setVisible(false);
                            nameLabel.setVisible(false);
                            sp.setVisible(true);
                            phase = 2;
                        }
                    }else{
                        jt.setRowSelectionAllowed(true);
                        jt.setColumnSelectionAllowed(false);
                        int[] rows = jt.getSelectedRows();
                        System.out.println(Arrays.toString(rows));
                        if(rows.length == 0) {
                            JOptionPane.showMessageDialog(
                                    f, "You Have not selected any property",
                                    "Error property not selected", JOptionPane.ERROR_MESSAGE);
                        }else if(rows.length > 1) {
                            JOptionPane.showMessageDialog(f, "You Have selected multiple property",
                                    "Error multiple property selected", JOptionPane.ERROR_MESSAGE);
                        }else {
                            if(op.equals("Sale")) {
                                SQLInterface sqli = new SQLInterface();
                                String p_id = sqli.fetchData("Property", "prop_name",
                                        (String) model.getValueAt(rows[0], 0))[1][0];
                                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                                LocalDateTime now = LocalDateTime.now();
                                String timeTaken = sqli.fetchSingleValue("Select datediff(?, date_listed) " +
                                        "from status where p_id = ? and bedrooms = ?", new String[]{
                                                dtf.format(now), p_id, (String) model.getValueAt(rows[0], 4)
                                });
                                sqli.insertValue("deal", "d_id", new String[]{
                                        dtf.format(now), (String) (String) model.getValueAt(rows[0], 5),
                                        timeTaken, (String) (String) model.getValueAt(rows[0], 7),
                                        String.valueOf(AgentMainMenu.getA_id())
                                });
                                int c = JOptionPane.showConfirmDialog(f, "Do you want to delist ?",
                                        "Confirm Delist", JOptionPane.YES_NO_OPTION);
                                if (c == 0) {
                                    sqli.executeQuery("Delete from status where p_id = ? and" +
                                            " bedrooms = ?", new String[]{p_id,
                                            (String) model.getValueAt(rows[0], 4)});
                                    if(sqli.fetchSingleValue("select count(p_id) from status " +
                                            "where p_id = ?;", new String[]{p_id}).equals("0")){
                                        sqli.deleteRow("property", "p_id", p_id);
                                    }
                                }
                                Main.mainWindow.remove(f);
                                Main.navigate("AgentMain");
                            }else{
                                String[] vals = new String[4];
                                for(int i = 0; i < 4; i++){
                                    vals[i] = (String) model.getValueAt(rows[0], i);
                                }
                                Main.mainWindow.add(new NewProperty(1, vals).f,
                                        "AddProperty");
                                Main.navigate("AddProperty");
                            }
                        }
                    }
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(f, e, "SQLError", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        f.add(header);
        f.add(prop_name);
        f.add(nameLabel);
        f.add(submit);
        f.add(back);
        f.add(sp);
        f.setBounds(0,0,640,480);
        f.setLayout(null);
    }

    public static boolean isEmpty(JTable jTable) {
        if (jTable != null && jTable.getModel() != null) {
            return jTable.getModel().getRowCount() <= 0;
        }
        return true;
    }
    public static void main(String[] args){
        JFrame frame = new JFrame("Name and Go Button Frame");
        frame.add(new EnterPropertyName("New").f);
        frame.setSize(640,480);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}