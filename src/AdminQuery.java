import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class AdminQuery extends JPanel{
    JPanel f;
    AdminQuery(){
        this.f = this;
        JLabel header = new JLabel("ENTER QUERY");
        JButton back = new JButton(Resources.backImageIcon);
        JTextField sb = new JTextField();
        JButton sub = new JButton(Resources.runButton);
        sub.setContentAreaFilled(false);
        JButton resetButton = new JButton(Resources.resetButtonIcon);
        DefaultTableModel model = new DefaultTableModel();
        sub.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                try{
                    model.setNumRows(0);
                    model.setColumnCount(0);
                    new SQLInterface().fetchData(sb.getText(), model);
                }catch(SQLException e){
                    String error = "Error!! " + e;
                    JOptionPane.showMessageDialog(f, error, "Error!!",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setNumRows(0);
                model.setColumnCount(0);
                sb.setText("");
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setNumRows(0);
                model.setColumnCount(0);
                sb.setText("");
                Main.navigate("AdminMain");
            }
        });
        JTable jt = new JTable(model);
        JScrollPane sp = new JScrollPane(jt);
        sp.setBounds(0,150,620,290);
        f.add(sp);

        header.setFont(new Font("Serif", Font.BOLD, 20));
        sb.setFont(new Font("Courier", Font.PLAIN, 17));
        header.setBounds(225,30,200,30);
        sub.setBounds(490,103, Resources.runButton.getIconWidth(), Resources.runButton.getIconHeight());
        back.setBounds(30,30, Resources.backImageIcon.getIconWidth(), Resources.backImageIcon.getIconHeight());
        sb.setBounds(80, 100,400,35);
        resetButton.setBounds(530, 103, Resources.resetButtonIcon.getIconWidth(),
                Resources.resetButtonIcon.getIconHeight());

        f.add(sb);f.add(back);f.add(header);f.add(sub);f.add(resetButton);
        f.setSize(640,480);
        f.setLayout(null);f.setVisible(true);
    }
    public static void main(String[] args) {
        new AdminQuery();
    }
}
