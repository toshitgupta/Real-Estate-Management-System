import javax.swing.*;
import java.awt.event.*;

public class AddUser extends JPanel implements ActionListener{
    JButton back;
    JLabel username;
    JTextField userInput;
    JLabel passwordLabel;
    JTextField passInput;
    JLabel confirm;
    JTextField confirmInput;
    String item[];
    JComboBox jcb;
    JButton submit;
    AddUser(){
        this.setLayout(null);
        this.setSize(640,480);

        back = new NaviButton(Resources.backImageIcon, "UserMain", this);
        back.setLocation(10, 10);

        username = new JLabel("Enter username : ");
        username.setBounds(10,40,120,100);
        this.add(username);
        userInput = new JTextField();
        userInput.setBounds(170,75,150,30);
        this.add(userInput);

        passwordLabel = new JLabel("Create password : ");
        passwordLabel.setBounds(10,90,140,100);
        this.add(passwordLabel);
        passInput = new JTextField();
        passInput.setBounds(170,125,150,30);
        this.add(passInput);

        confirm = new JLabel("Enter confirm password : ");
        confirm.setBounds(10,140,180,100);
        this.add(confirm);
        confirmInput = new JTextField();
        confirmInput.setBounds(170,175,150,30);
        this.add(confirmInput);

        item = new String[]{"--SELECT YOUR ROLE--", "ADMIN", "AGENT", "RE OFFICIER"};
        jcb = new JComboBox<>(item);
        jcb.setBounds(10,240,170,30);
        this.add(jcb);

        submit = new JButton("SUBMIT");
        submit.setBounds(245,300,100,40);
        this.add(submit);
        submit.addActionListener(this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == submit) {
            // true if all data is correct
            boolean pass = true;

            // username is empty
            if (userInput.getText().equals("")) {
                JOptionPane.showMessageDialog(
                        this,
                        "Username Field is Empty!!!\n" +
                                "Please Enter a valid Username.",
                        "Invalid UserName",
                        JOptionPane.WARNING_MESSAGE
                );
                pass = false;
            }
            //password is empty or less than 8 char long
            if ((passInput.getText().equals("") || passInput.getText().length() < 8) && pass) {
                //password is empty
                if (passInput.getText().equals("")) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Password Field is Empty!!!\n" +
                                    "Please Enter a valid password.",
                            "Invalid Password",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
                //less than 8 char long
                else if (passInput.getText().length() < 8) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Password length is too short!\n" +
                                    "Please Re-enter the password.",
                            "Invalid Password",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
                pass = false;
            }

            //check if both passwords are equal
            if (!passInput.getText().equals(confirmInput.getText()) && pass) {
                JOptionPane.showMessageDialog(
                        this,
                        "Confirm password DO NOT MATCH with original password!\n" +
                                "Please Re-enter the password.",
                        "Invalid Password",
                        JOptionPane.WARNING_MESSAGE
                );
                pass = false;
            }

            //if role not selected
            if (jcb.getSelectedItem().equals("--SELECT YOUR ROLE--") && pass) {
                JOptionPane.showMessageDialog(
                        this,
                        "Role not Selected!!\n" +
                                "Please Select a Role.",
                        "Invalid Role",
                        JOptionPane.WARNING_MESSAGE
                );
                pass = false;
            }

            //if pass == true then all conditions are ok
            if (pass) {
                if(Main.usrManager.addUser(userInput.getText(), passInput.getText(),
                        jcb.getSelectedIndex() - 1) == 0){
                    JOptionPane.showMessageDialog(this, "User Added Successfully",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    userInput.setText("");
                    passInput.setText("");
                    confirmInput.setText("");
                    jcb.setSelectedIndex(0);
                }
                else{
                    JOptionPane.showMessageDialog(this, "User already exists!!",
                            "Error!!", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Add User");
        frame.add(new AddUser());
        frame.setSize(640,480);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}