import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Office_Interface_Main extends JPanel implements ActionListener {
    JButton all_agent_details_button;
    JButton search_individual_agent_details_button;
    JButton register_Agent_button;
    JButton remove_Agent_button;
    JButton statistics_button;
    JButton settings_button;
    JButton logout_button;
    public static final String OFFICE_MAIN = "OfficeMain";
    public static final String SEARCH = "SearchAgent";
    public static final String ALL_AGENTS = "AllAgents";
    public static final String REGISTER = "RegisterAgent";
    public static final String REMOVE = "RemoveAgent";
    public static final String STATS = "Stats";
    Office_Interface_Main() {
        this.setSize(640, 480);
        this.setLayout(null);

        all_agent_details_button = new JButton(Resources.agentsIcon);

        all_agent_details_button.setFocusable(false);
        all_agent_details_button.addActionListener(this);

        search_individual_agent_details_button = new JButton(Resources.viewAgentsIcon);

        search_individual_agent_details_button.setFocusable(false);
        search_individual_agent_details_button.addActionListener(this);

        register_Agent_button = new JButton(Resources.addAgentIcon);
        register_Agent_button.setFocusable(false);
        register_Agent_button.addActionListener(this);

        remove_Agent_button = new JButton(Resources.removeAgentsIcon);
        remove_Agent_button.setFocusable(false);
        remove_Agent_button.addActionListener(this);

        statistics_button = new JButton(Resources.statsIcon);
        statistics_button.setFocusable(false);
        statistics_button.addActionListener(this);

        settings_button = new JButton(Resources.settingsIcon);
        //settings_button.setIcon();

        settings_button.setContentAreaFilled(false);
        settings_button.setFocusable(false);
        settings_button.addActionListener(this);

        logout_button = new JButton(Resources.logoutIcon);

        logout_button.setFocusable(false);
        logout_button.setContentAreaFilled(false);
        logout_button.addActionListener(this);

        /*
        all_agent_details_button.setBounds(7, 60, 200, 160);   //(250, 200) * 0.6 = (150, 120)
        search_individual_agent_details_button.setBounds(212, 60, 200, 160);
        register_Agent_button.setBounds(106, 230, 200, 160);
        remove_Agent_button.setBounds(311, 230, 200, 160);
        settings_button.setBounds(530, 5, 30, 30);    //(250, 200) * 0.2 = (50, 40)
        logout_button.setBounds(585, 5, 30, 30);
        statistics_button.setBounds(417, 60, 200, 160);
        */

        //Joke Interface
        all_agent_details_button.setBounds(10, 60, 200, 160);
        search_individual_agent_details_button.setBounds(420, 60, 200, 160);
        remove_Agent_button.setBounds(10, 230, 200, 160);
        statistics_button.setBounds(420, 230, 200, 160);
        register_Agent_button.setBounds(215, 190, 200, 160);
        settings_button.setBounds(245, 70, 30, 30);
        logout_button.setBounds(345, 70, 30, 30);
        //settings_button.setBounds(530, 5, 30, 30);
        //logout_button.setBounds(570, 5, 30, 30);
        String name = "Vaibhav Sonkusare";
        String text = "<HTML><BR>Welcome!! User " + name + "</HTML>";
        JLabel welcome = new JLabel(text, SwingConstants.RIGHT);
        welcome.setOpaque(false);
        welcome.setForeground(new Color(119, 255, 252));
        welcome.setFont(new Font("Helvetica", Font.BOLD, 20));
        welcome.setBounds(215, 80, 200, 120);
        add(welcome);

        this.add(all_agent_details_button);
        this.add(search_individual_agent_details_button);
        this.add(register_Agent_button);
        this.add(remove_Agent_button);
        this.add(statistics_button);
        this.add(settings_button);
        this.add(logout_button);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == all_agent_details_button) {
            Main.mainWindow.add(new All_Agent_Details(), ALL_AGENTS);
            Main.navigate(ALL_AGENTS);
        }
        if (actionEvent.getSource() == search_individual_agent_details_button) {
            Main.mainWindow.add(new Search_Agent_Details(), SEARCH);
            Main.navigate(SEARCH);
        }
        if (actionEvent.getSource() == register_Agent_button) {
            Main.mainWindow.add(new Register_New_Agent(), REGISTER);
            Main.navigate(REGISTER);
        }
        if (actionEvent.getSource() == remove_Agent_button) {
            Main.mainWindow.add(new Remove_Agent(), REMOVE);
            Main.navigate(REMOVE);
        }
        if (actionEvent.getSource() == statistics_button) {
            Main.mainWindow.add(new cardLayout(), STATS);
            Main.navigate(STATS);
        }
        if (actionEvent.getSource() == settings_button) {
            System.out.println("settings_button");
        }
        if (actionEvent.getSource() == logout_button) {
            int answer = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to Logout?",
                    "Logout Conformation",
                    JOptionPane.YES_NO_OPTION
            );
            if (answer == 0)
                Main.mainFrame.dispose();
        }
    }
}
