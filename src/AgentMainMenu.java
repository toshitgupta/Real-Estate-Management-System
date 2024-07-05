import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class AgentMainMenu{
    JPanel f;
    public static final String NEW_PROPERTY = "NewProperty";
    private static final String RECORD_SALE = "RecordSale";
    private static int  a_id;

    AgentMainMenu(int a_id){
        AgentMainMenu.a_id = a_id;
        this.f = new JPanel(null);
        JLabel header = new JLabel("Agent's Menu");
        JButton GetPropertyDetails = new JButton(Resources.recordSaleIcon);
        JButton AddNewProperty = new JButton(Resources.newPropertyIcon);


        header.setFont(new Font("Courier", Font.PLAIN, 15));
        GetPropertyDetails.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        AddNewProperty.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        header.setBounds(250,40,150,56);
        GetPropertyDetails.setBounds(40,150,Resources.recordSaleIcon.getIconWidth(),
                Resources.recordSaleIcon.getIconHeight());
        AddNewProperty.setBounds(340,150,Resources.newPropertyIcon.getIconWidth(),
                Resources.newPropertyIcon.getIconHeight());

        GetPropertyDetails.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                try{
                    Main.mainWindow.add(new EnterPropertyName("Sale").f, RECORD_SALE);
                    Main.navigate(RECORD_SALE);
                }catch(Exception e){
                    System.out.println("You got an error : "+e.getMessage());
                }
            }
        });

        AddNewProperty.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                try{
                    Main.mainWindow.add(new EnterPropertyName("New").f, NEW_PROPERTY);
                    Main.navigate(NEW_PROPERTY);
                }catch(Exception e){
                    System.out.println("You got an error : "+e.getMessage());
                }
            }
        });

        f.add(header);
        f.add(GetPropertyDetails);
        f.add(AddNewProperty);
        f.setBounds(0,0,640,480);
        f.setLayout(null);
    }
    public static void main(String[] args){
        Frame frame = new JFrame("Agent's Main Menu");
        frame.add(new AgentMainMenu(1).f);
        frame.setSize(640,480);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public static int getA_id() {
        return a_id;
    }

    public static void setA_id(int a_id) {
        AgentMainMenu.a_id = a_id;
    }
}