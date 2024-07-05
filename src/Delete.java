import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Delete extends JPanel implements ActionListener{
    boolean flag = true;
    JButton back;
    JLabel username;
    JTextField userInput;
    JButton show;
    JButton delete;
    JLabel user;
    JLabel type;
    Delete(){
        this.setLayout(null);
        this.setBounds(0,0,640,480);

        JButton back = new NaviButton(Resources.backImageIcon, "UserMain", this);
        back.setLocation(10,10);
        back.setFocusable(false);
        back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.add(back);

        username = new JLabel("Enter username : ");
        username.setBounds(10,40,120,100);
        this.add(username);
        userInput = new JTextField();
        userInput.setBounds(170,75,150,30);
        this.add(userInput);

        show = new JButton("SHOW");
        show.setBounds(350,75,80,30);
        show.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        show.setFocusable(false);
        show.addActionListener(this);
        this.add(show);

        delete = new JButton("DELETE");
        delete.setBounds(205,225,100,40);
        delete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        delete.setFocusable(false);
        delete.addActionListener(this);
        delete.setEnabled(false);
        delete.setVisible(false);
        this.add(delete);

        user = new JLabel();
        type = new JLabel();
        user.setBounds(30, 100, 120, 100);
        type.setBounds(130, 100, 120, 100);
        add(user);add(type);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == show) {
            /*
                1.Make sure username entered is present in file
                2.Display user details username and role
                3.PopUp like username not entered or username is does not exists
            */
            boolean pass = true;

            //if username field is empty
            if (userInput.getText().equals("") && pass) {
                JOptionPane.showMessageDialog(
                        this,
                        "Username Field is Empty!!!\n" +
                                "Please Enter a valid Username.",
                        "Invalid UserName",
                        JOptionPane.WARNING_MESSAGE
                );
                pass = false;
            }

            //if username is not in file or no such username

            if (pass) {
                String[] details = Main.usrManager.getDetails(userInput.getText());
                if (details.length == 0) {
                    JOptionPane.showMessageDialog(userInput.getParent(), "User doesn't exist!!",
                            "Error!!", JOptionPane.WARNING_MESSAGE);
                } else {
                    user.setText(details[0]);
                    type.setText(details[1]);
                    delete.setVisible(true); delete.setEnabled(true);
                }
            }
        }
        if (actionEvent.getSource() == delete) {
            /*
                1.Read a file and verify entered username and password
                2.Delete given data is valid
                3.PopUp where ever its necessary
                    1.Username is not present
                    2.Invalid Password
                    3.Please enter username
                    4.Please enter password
                4.For password you have show input dialog if username is present in file
                5.On "OK" click in dialog delete appropriate user entry from file
             */
            //asking for current user password
            String password = JOptionPane.showInputDialog(
                    this,
                    "Please Enter Your Password",
                    "Authorize User Deletion",
                    JOptionPane.PLAIN_MESSAGE
            );

            //code to verify pass
            if (Main.usrManager.find(Main.userName, password) == 0) {
                Main.usrManager.removeUser(user.getText());
                JOptionPane.showMessageDialog(
                        this,
                        "User Removed Successfully.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
                Main.mainWindow.remove(this);
                Main.mainWindow.add(new Delete(), UserMain.DELETE);
                Main.navigate(UserMain.DELETE);
            }
            else {
                JOptionPane.showMessageDialog(
                        this,
                        "Please Re-enter the password.",
                        "Invalid Password",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Delete Users");
        frame.add(new Delete());
        frame.setLayout(null);
        frame.setSize(640, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}