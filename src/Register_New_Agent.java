import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Register_New_Agent extends JPanel implements ActionListener {
    JLabel agent_name_label;
    JTextField agent_name_JTextField;
    JLabel agent_email_label;
    JTextField agent_email_JTextField;
    JLabel agent_phoneNo_label;
    JTextField agent_phoneNo_JTextField;
    JButton submit_button;
    Register_New_Agent () {
        this.setSize(640, 480);
        this.setLayout(null);

        agent_name_label = new JLabel("Agent Name : ");
        agent_name_label.setBounds(60, 80, 180, 15);
        NaviButton back = new NaviButton(Resources.backImageIcon, Office_Interface_Main.OFFICE_MAIN, this);
        back.setLocation(10, 10);
        agent_name_JTextField = new JTextField();
        agent_name_JTextField.setBounds(250, 73, 250, 30);

        agent_email_label = new JLabel("Agent Email : ");
        agent_email_label.setBounds(60, 140, 180, 15);

        agent_email_JTextField = new JTextField();
        agent_email_JTextField.setBounds(250, 133, 250, 30);

        agent_phoneNo_label = new JLabel("Agent Phone Number : ");
        agent_phoneNo_label.setBounds(60, 200, 180, 15);

        agent_phoneNo_JTextField = new JTextField();
        agent_phoneNo_JTextField.setBounds(250, 193, 250, 30);

        submit_button = new JButton("Submit");
        submit_button.setBounds(220, 270, 130, 30);
        submit_button.setFocusable(false);
        submit_button.addActionListener(this);

        this.add(agent_name_label);
        this.add(agent_name_JTextField);
        this.add(agent_email_label);
        this.add(agent_email_JTextField);
        this.add(agent_phoneNo_label);
        this.add(agent_phoneNo_JTextField);
        this.add(submit_button);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == submit_button) {
            String string = "Agent Name : " + agent_name_JTextField.getText() + "\n" +
                    "Agent Email : " + agent_email_JTextField.getText() + "\n" +
                    "Agent Phone Number: " + agent_phoneNo_JTextField.getText() + "\n";

            String[] responses = {"Confirm", "Edit"};

            int answer = JOptionPane.showOptionDialog(
                    this, string,
                    "Conform New Agent Details",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null, responses, 0);

            if (answer == 0) {
                //if answer = 0 ==> user chose option YES
                if (
                        agent_name_JTextField.getText().length() == 0 ||
                        agent_email_JTextField.getText().length() == 0 ||
                        agent_phoneNo_JTextField.getText().length() != 10
                ) {
                    if (agent_name_JTextField.getText().length() == 0) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Please enter a valid Name",
                                "Invalid Name",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }
                    if (agent_email_JTextField.getText().length() == 0) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Please enter a valid Email",
                                "Invalid Email",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }
                    if (agent_phoneNo_JTextField.getText().length() != 10) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Please enter a valid 10 digit phone number",
                                "Invalid Phone Number",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }
                }
                else {
                    try {
                        new SQLInterface().insertValue("Agent", "a_id",
                                new String[]{agent_name_JTextField.getText(), agent_email_JTextField.getText(),
                                agent_phoneNo_JTextField.getText()});
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(this,
                                e, null, JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }
}
