import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.invoke.VarHandle;
import java.sql.SQLException;

public class Remove_Agent extends JPanel implements ActionListener {
    JLabel enter_agent_id_label;
    JTextField input_agent_id_JTextField;
    JButton search_button;
    JPanel agent_details_panel;
    JLabel agent_id_label;
    JLabel agent_name_label;
    JLabel agent_email_label;
    JLabel agent_phoneNo_label;
    JButton conform_button;
    JButton back_button;
    Remove_Agent () {
        this.setSize(640, 480);
        this.setLayout(null);

        back_button = new JButton(Resources.backImageIcon);
        back_button.setBounds(10, 10, Resources.backImageIcon.getIconWidth(),
                Resources.backImageIcon.getIconHeight());
        back_button.setFocusable(false);
        back_button.addActionListener(this);

        enter_agent_id_label = new JLabel("Enter ID of Agent to be removed: ");
        enter_agent_id_label.setBounds(30, 80, 250, 30);

        input_agent_id_JTextField = new JTextField();
        input_agent_id_JTextField.setBounds(280, 80, 200, 30);

        search_button = new JButton("Search");
        search_button.setBounds(520, 80, 90, 30);
        search_button.setFocusable(false);
        search_button.addActionListener(this);

        agent_details_panel = new JPanel();
        agent_details_panel.setBounds(50, 150, 540, 180);
        agent_details_panel.setLayout(null);
        agent_details_panel.setVisible(false);

        agent_id_label = new JLabel();
        agent_id_label.setBounds(30, 10, 380, 20);

        agent_name_label = new JLabel();
        agent_name_label.setBounds(30, 50, 380, 20);

        agent_email_label = new JLabel();
        agent_email_label.setBounds(30, 90, 380, 20);

        agent_phoneNo_label = new JLabel();
        agent_phoneNo_label.setBounds(30, 130, 380, 20);

        agent_details_panel.add(agent_id_label);
        agent_details_panel.add(agent_name_label);
        agent_details_panel.add(agent_email_label);
        agent_details_panel.add(agent_phoneNo_label);

        conform_button = new JButton("Confirm");
        conform_button.setBounds(480, 350, 100, 30);
        conform_button.setFocusable(false);
        conform_button.addActionListener(this);
        conform_button.setVisible(false);

        this.add(back_button);
        this.add(enter_agent_id_label);
        this.add(input_agent_id_JTextField);
        this.add(search_button);
        this.add(agent_details_panel);
        this.add(conform_button);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == back_button) {
            Main.navigate(Office_Interface_Main.OFFICE_MAIN);
        }
        if (actionEvent.getSource() == search_button) {
            if (input_agent_id_JTextField.getText().length() == 0)  {
                JOptionPane.showMessageDialog(
                        this,
                        "Please Enter an Agent ID!!!",
                        "Empty Agent ID",
                        JOptionPane.WARNING_MESSAGE
                );
            }
            else if (DoAgentNotExists(Integer.parseInt(input_agent_id_JTextField.getText()))) {
                JOptionPane.showMessageDialog(
                        this,
                        "No Agent With Agent ID = ".concat(input_agent_id_JTextField.getText()),
                        "Invalid Agent ID",
                        JOptionPane.WARNING_MESSAGE
                );
            }
            else {
                FillAgentDetailPanel(Integer.parseInt(input_agent_id_JTextField.getText()));
            }
        }
        if (actionEvent.getSource() == conform_button)  {
            int answer = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to Delete Agent Details of Agent ID = "
                            .concat(input_agent_id_JTextField.getText()),
                    "Conform Delete Agent Details",
                    JOptionPane.YES_NO_OPTION
            );
            if (answer == 0) {
                //add code to delete here
                try {
                    SQLInterface sqli = new SQLInterface();
                    sqli.deleteRow("deal", "a_id", input_agent_id_JTextField.getText());
                    sqli.deleteRow("agent", "a_id", input_agent_id_JTextField.getText());
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(
                            this,
                            e,
                            "SQL Error",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
                //after delete informing dialog
                JOptionPane.showMessageDialog(
                        this,
                        "Agent Details have been Deleted",
                        "Delete Completed",
                        JOptionPane.INFORMATION_MESSAGE
                );
                //exit here
            }
        }
    }

    public void FillAgentDetailPanel (int a_id) {
        try {
            String[] row = new SQLInterface().fetchData("Agent", "a_id", String.valueOf(a_id))[1];
            agent_id_label.setText("Agent ID : ".concat(String.valueOf(a_id)));
            agent_name_label.setText("Agent Name : ".concat(row[1]));
            agent_email_label.setText("Agent Email : ".concat(row[2]));
            agent_phoneNo_label.setText("Agent Phone No : ".concat(row[3]));
            agent_details_panel.setVisible(true);
            conform_button.setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    e,
                    "SQL Error",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }
    public boolean DoAgentNotExists(int a_id) {
        //to check if a_id is valid
        if (a_id > 120) return true;
        return false;
    }
}
