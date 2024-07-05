import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import org.jfree.chart.ChartPanel;
import java.awt.*;
import org.jfree.chart.ChartFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class AgentPieChart implements ActionListener, KeyListener {
    JTextField yearInput;
    JPanel f;
    ChartPanel cp;
    PieChart p;
    JButton b;
    AgentPieChart(){
        f = new JPanel();
        f.setSize(640,480);
        f.setLayout(null);

        JLabel label = new JLabel("Enter year : ");
        label.setBounds(10,10,200,35);
        f.add(label);

        yearInput = new JTextField();
        yearInput.setBounds(100,15,200,30);
        f.add(yearInput);
        yearInput.addKeyListener(this);

        b = new JButton("SHOW");
        b.setBounds(310,15,100,30);
        f.add(b);
        b.addActionListener(this);
        b.addKeyListener(this);

    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Shivam");
        frame.add(new AgentPieChart().f);
        frame.setLayout(null);
        frame.setSize(640, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public String getYear(){
        return new AgentPieChart().yearInput.getText();
    }
    @Override
    public void actionPerformed(ActionEvent ae){
        if (ae.getSource() == b) {
            if(cp != null) f.remove(cp);
            this.p = new PieChart(yearInput.getText());
            this.cp = p.frame;
            cp.setBounds(45,80,500,300);
            f.add(cp);
            f.repaint();
        }

        //p.getFrame();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyChar() == '\n') {
            System.out.println("thiafolds");
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getKeyChar() == '\n') {
            b.doClick();
            System.out.println("pressed");
        }
    }
}

class PieChart{
    ChartPanel frame;
    PieChart(String year){
        //SecondFrame sec = new SecondFrame();
        //String year = sec.getYear();
        DefaultPieDataset dataset = new DefaultPieDataset();
        try{
            String url = "jdbc:mysql://localhost:3306/RealEstate";
            String username = "root";
            String password = "vimaln";
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("Current Database: " + con.getCatalog());
            String query = "SELECT COUNT(d_id) AS count, name" +
                    " FROM deal NATURAL JOIN agent" +
                    " WHERE YEAR(date) = " + year +
                    " GROUP BY (a_id)";
            ResultSet rs =  con.createStatement().executeQuery(query);
            while(rs.next()){
                dataset.setValue(rs.getString("name"), rs.getInt("count"));
            }
        } catch (Exception e) {
            System.out.println("You got an error : "+e.getMessage());
        }

        JFreeChart chart = ChartFactory.createPieChart("Pie Chart",dataset,true,true,false);
        chart.setBackgroundPaint(Color.white);

        frame = new ChartPanel(chart);
        frame.setVisible(true);
    }
    static String get_password() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(
                    "/home/vaibhav/Documents/DBMS_Lab/password.txt"
            ));
            String password = bufferedReader.readLine();
            bufferedReader.close();
            return password;
        }
        catch (IOException e) {
            System.out.println("Error!!! Password File not found/is empty.");
            throw new RuntimeException(e);
        }
    }

    /*public void getFrame(){
        JFrame f = new JFrame("Pie Chart");
        frame.setSize(640,480);
        f.add(frame);
        f.setSize(640,480);
        f.setLayout(null);
        f.setVisible(true);
    }*/
}