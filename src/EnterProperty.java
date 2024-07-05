import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

public class EnterProperty extends JPanel{

    EnterProperty() {
        DefaultTableModel model = new DefaultTableModel();
        JLabel header = new JLabel("ENTER PROPERTY NAME");
        JButton back = new NaviButton(Resources.backImageIcon, "AgentMain", this);
        JTextField sb = new JTextField();
        JButton sub = new JButton("Go");

        header.setFont(new Font("Serif", Font.BOLD, 15));
        sb.setFont(new Font("Courier", Font.PLAIN, 17));
        header.setBounds(200,30,200,30);
        sub.setBounds(450,120,80,50);
        back.setLocation(30,30);
        sb.setBounds(30,120,400,50);


        sub.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                try{
                    String name;
                    name = sb.getText();
                    model.setNumRows(0);
                    model.setColumnCount(0);
                    String query = "select prop_name, street_name, locality, bedrooms, status, sq_ft, price from " +
                            "property NATURAL JOIN status where prop_name like '%"+name+"%';";
                    System.out.println(query);
                    new SQLInterface().fetchData(query, model);
                    Main.mainFrame.add(new DisplayProperties(model), "DisplayProp");
                    Main.navigate("DisplayProp");
                }catch(Exception e){
                    System.out.println("You got an error : "+e.getMessage());
                }
            }
        });

        add(sb);
        add(back);
        add(header);
        add(sub);

        setSize(640, 480); // Set frame size to 480x640
        //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
    }

 public static void main(String[] args) {
        new EnterProperty();
    }

}
