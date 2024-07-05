import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class NewProperty{
    JPanel f;
    NewProperty(int mode, String[] values){
        this.f = new JPanel(null);
        JLabel header = new JLabel("Enter New Property");
        JLabel name = new JLabel("Property Name : ");
        JTextField prop_name = new JTextField();
        JLabel streetName = new JLabel("Street Name : ");
        JTextField street_name = new JTextField();
        JLabel locality = new JLabel("Locality : ");
        JTextField new_locality = new JTextField();
        JLabel constructionYear = new JLabel("Construction Year : ");
        JTextField construction_year = new JTextField();
        JButton submit = new JButton("Submit");
        JLabel bedRooms = new JLabel("Bed rooms : ");
        JTextField bed_rooms = new JTextField();
        JLabel status = new JLabel("Property status : ");
        JTextField prop_status = new JTextField();
        JLabel squareFeet = new JLabel("Square feet : ");
        JTextField sq_ft = new JTextField();
        JLabel price = new JLabel("Property price : ");
        JTextField prop_price = new JTextField();
        JButton back = new NaviButton(Resources.backImageIcon, AgentMainMenu.NEW_PROPERTY, f);


        header.setFont(new Font("Courier", Font.PLAIN, 15));
        prop_name.setFont(new Font("Courier", Font.PLAIN, 17));
        street_name.setFont(new Font("Courier", Font.PLAIN, 17));
        new_locality.setFont(new Font("Courier", Font.PLAIN, 17));
        construction_year.setFont(new Font("Courier", Font.PLAIN, 17));
        bed_rooms.setFont(new Font("Courier", Font.PLAIN, 17));
        prop_status.setFont(new Font("Courier", Font.PLAIN, 17));
        sq_ft.setFont(new Font("Courier", Font.PLAIN, 17));
        prop_price.setFont(new Font("Courier", Font.PLAIN, 17));
        name.setFont(new Font("Courier", Font.PLAIN, 17));
        streetName.setFont(new Font("Courier", Font.PLAIN, 17));
        locality.setFont(new Font("Courier", Font.PLAIN, 17));
        constructionYear.setFont(new Font("Courier", Font.PLAIN, 17));
        bedRooms.setFont(new Font("Courier", Font.PLAIN, 17));
        status.setFont(new Font("Courier", Font.PLAIN, 17));
        squareFeet.setFont(new Font("Courier", Font.PLAIN, 17));
        price.setFont(new Font("Courier", Font.PLAIN, 17));
        submit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        header.setBounds(240,20,150,56);
        name.setBounds(50,80,150,20);
        locality.setBounds(50,160,150,20);
        streetName.setBounds(50,120,150,20);
        constructionYear.setBounds(50,200,150,20);
        bedRooms.setBounds(50,240,150,20);
        status.setBounds(50,280,150,20);
        squareFeet.setBounds(50,320,150,20);
        price.setBounds(50,360,150,20);
        prop_name.setBounds(200,80,230,25);
        street_name.setBounds(200,120,230,25);
        new_locality.setBounds(200,160,230,25); 
        construction_year.setBounds(200,200,230,25);
        bed_rooms.setBounds(200,240,230,25);
        prop_status.setBounds(200,280,230,25);
        sq_ft.setBounds(200,320,230,25);
        prop_price.setBounds(200,360,230,25);
        submit.setBounds(250,400,100,40);
        back.setLocation(30, 30);

        prop_name.setText(values[0]);
        prop_name.setEnabled(false);
        if(mode == 1){
            street_name.setText(values[1]);
            new_locality.setText(values[2]);
            construction_year.setText(values[3]);
            street_name.setEnabled(false);
            new_locality.setEnabled(false);
            construction_year.setEnabled(false);
        }

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    SQLInterface sqli = new SQLInterface();
                    if(mode == 0){
                        sqli.insertValue("property", "p_id", new String[]{
                                prop_name.getText(), street_name.getText(), new_locality.getText(),
                                construction_year.getText()});
                    }
                    String p_id = sqli.fetchData("Property", "prop_name", prop_name.getText())[1][0];
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                    LocalDateTime now = LocalDateTime.now();
                    sqli.insertValue("Status", new String[]{p_id, bed_rooms.getText(),
                            prop_status.getText(), sq_ft.getText(), prop_price.getText(), dtf.format(now)});
                    JOptionPane.showMessageDialog(f, "Property Added Successfully",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                }catch (SQLException ex){
                    JOptionPane.showMessageDialog(f, ex, "SQLError", JOptionPane.ERROR_MESSAGE);
                }
                finally {
                    Main.mainWindow.remove(f);
                    Main.navigate("AgentMain");
                }
            }
        });

        f.add(header);
        f.add(prop_name);
        f.add(street_name);
        f.add(new_locality);
        f.add(construction_year);
        f.add(bed_rooms);
        f.add(prop_status);
        f.add(sq_ft);
        f.add(prop_price);
        f.add(name);
        f.add(streetName);
        f.add(locality);
        f.add(constructionYear);
        f.add(submit);
        f.add(bedRooms);
        f.add(status);
        f.add(squareFeet);
        f.add(price);
        f.setBounds(0,0,640,480);
        f.setLayout(null);
    }
    public static void main(String[] args){
        JFrame frame = new JFrame("New Property Details");
        frame.add(new NewProperty(0, new String[]{"Testing Testing"}).f);
        frame.setSize(640,480);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}