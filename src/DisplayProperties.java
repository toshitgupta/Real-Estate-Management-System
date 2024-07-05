import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class DisplayProperties extends JPanel{
    String y[];
    String x[][];
    JPanel self = this;
    DisplayProperties(DefaultTableModel model_new){
        JLabel header = new JLabel("PROPERTY DETAILS");
        JButton back = new JButton(Resources.backImageIcon);
        JButton sub = new JButton("Go");

        JTable jt = new JTable(model_new);
        JScrollPane sp = new JScrollPane(jt);
        sp.setBounds(10,150,600,290);
        add(sp);

        header.setFont(new Font("Serif", Font.BOLD, 20));
        header.setBounds(225,30,200,30);
        back.setBounds(30,30,Resources.backImageIcon.getIconWidth(),Resources.backImageIcon.getIconHeight());
        sub.setBounds(500,30,100,35);

        int[] rows = jt.getSelectedRows();

        /*if(rows.length == 0) {
            sub.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showConfirmDialog(
                    f,
                    "You Have not selected any property",
                    "Error property not selected", getDefaultCloseOperation()
                    );
                    
                }
            });
        }

        else if(rows.length > 1) {
            sub.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showConfirmDialog(
                    f,
                    "You Have selected multiple property",
                    "Error multiple property selected",
                    getDefaultCloseOperation());
                    
                }
            });
        }

        else {
            sub.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showConfirmDialog(
                    f,
                    "Do you want to delist ?",
                    "Confirm Delist",
                    JOptionPane.YES_NO_OPTION);
                    
                }
            });
            System.out.println(rows);
        }*/

        sub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showConfirmDialog(
                sub,
                "Do you want to delist ?",
                "Confirm Delist",
                JOptionPane.YES_NO_OPTION);
                
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.navigate("EnterProperty");
                Main.mainWindow.remove(self);
            }
        });
        add(back);add(header);add(sub);
        setSize(640,480);
        setLayout(null);
    }

}
