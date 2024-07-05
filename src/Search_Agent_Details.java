import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Stack;

public class Search_Agent_Details extends JPanel implements ActionListener, KeyListener {
    Individual_Agent_Details individual_agent_details;
    JLabel enter_agent_id_label;
    JTextField agent_id_input;
    JButton find;
    Search_Agent_Details() {
        super();
        this.setSize(640, 480);
        this.setLayout(null);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        enter_agent_id_label = new JLabel("Enter Agent ID: ");
        enter_agent_id_label.setBounds(20, 50, 150, 30);

        agent_id_input = new JTextField();
        agent_id_input.setBounds(180, 50, 150, 30);
        agent_id_input.addKeyListener(this);

        find = new JButton("Find");
        find.setBounds(115, 100, 100, 30);
        find.addActionListener(this);
        find.setFocusable(false);
        find.addKeyListener(this);
        NaviButton back = new NaviButton(Resources.backImageIcon, Office_Interface_Main.OFFICE_MAIN, this);
        back.setLocation(10, 10);
        this.add(enter_agent_id_label);
        this.add(agent_id_input);
        this.add(find);
        //this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    static boolean is_valid_a_id (int a_id) {
        //write code to check if an agent of given a_id exists or not?
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == find) {
            try {
                int a_id = Integer.parseInt(agent_id_input.getText());
                if (is_valid_a_id(a_id)) {
                    individual_agent_details = new Individual_Agent_Details(a_id);
                    Main.mainWindow.add(individual_agent_details, "Agent_" + a_id);
                    //individual_agent_details.setLocationRelativeTo(this);
                    Main.navigate("Agent_" + a_id);
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "There is no Agent with Agent ID (a_id) = " +
                                    agent_id_input.getText(),
                            "Agent Not Found!!!",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Agent ID = " +
                                agent_id_input.getText() +
                                "\nONLY POSITIVE NUMBERS ARE ALLOWED!!!",
                        "Invalid Input!!!",
                        JOptionPane.ERROR_MESSAGE
                );
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        if (keyEvent.getKeyChar() == '\n') {
            find.doClick();
            System.out.println("pressed");
        }
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}