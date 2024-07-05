import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class cardLayout extends JPanel implements ActionListener {

    private JPanel mainPanel, panel1, panel2, panel3;
    private JComboBox<String> comboBox;
    private CardLayout cardLayout;
    
    public cardLayout() {
        // Set up the main panel with the card layout
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);
        
        // Set up the first panel
        
        // Set up the second panel
        panel2 = new JPanel();
        panel2.add(new JLabel("This is panel 2"));
        
        // Set up the third panel
        /*panel3 = new JPanel();
        panel3.add(new JLabel("This is panel 3"));*/
        
        // Add the panels to the main panel
        mainPanel.add(new BarGraph(), "Bar Graph");
        mainPanel.add(new AgentPieChart().f, "Pie Chart");
        mainPanel.add(new Performance_Scatter().Get_ChartPanel(), "Scatter Graph");
        
        // Set up the combo box
        comboBox = new JComboBox<String>(new String[] {"Bar Graph", "Pie Chart", "Scatter Graph"});
        comboBox.addActionListener(this);
        
        // Add the combo box and main panel to this panel
        NaviButton back = new NaviButton(Resources.backImageIcon, "OfficeMain", this);
        back.setPreferredSize(new Dimension(30, 30));
        mainPanel.setPreferredSize(new Dimension(600, 450));
        this.setLayout(new FlowLayout());
        this.add(comboBox);
        this.add(mainPanel);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == comboBox) {
            String selectedItem = (String) comboBox.getSelectedItem();
            switch (selectedItem) {
                case "Bar Graph":
                    cardLayout.show(mainPanel, "Bar Graph");
                    break;
                case "Pie Chart":
                    cardLayout.show(mainPanel, "Pie Chart");
                    break;
                case "Scatter Graph":
                    cardLayout.show(mainPanel, "Scatter Graph");
                    break;
            }
        }
    }

public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.getContentPane().add(new cardLayout());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(640, 480);
    frame.setVisible(true);
}

}