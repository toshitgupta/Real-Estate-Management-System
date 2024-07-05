import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;

public class Manage{
    JPanel f;
    Manage(){
        this.f = new JPanel(null);
        f.setLayout(null);
        f.setBounds(0,0,640,480);
        //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton back = new NaviButton(Resources.backImageIcon, "UserMain", f);
        back.setLocation(10,10);
        back.setFocusable(false);
        back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        f.add(back);

        JLabel username = new JLabel("Enter username : ");
        username.setBounds(10,40,120,100);
        f.add(username);
        JTextField userInput = new JTextField();
        userInput.setBounds(170,75,150,30);
        f.add(userInput);

        JButton click = new JButton("SHOW");
        click.setBounds(350,75,80,30);
        click.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        click.setFocusable(false);
        f.add(click);

        String item[] = {"--ENTER NEW ROLE--","ADMIN","AGENT","RE OFFICIER"};
        JComboBox jcb = new JComboBox<>(item);
        jcb.setBounds(10,240,170,30);
        jcb.setVisible(false);
        jcb.setEnabled(false);
        f.add(jcb);

        JButton submit = new JButton("MODIFY");
        submit.setBounds(245,300,100,40);
        submit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        submit.setFocusable(false);
        submit.setVisible(false);
        submit.setEnabled(false);
        f.add(submit);

        JLabel user = new JLabel();
        JLabel type = new JLabel();
        user.setBounds(30, 100, 120, 100);
        type.setBounds(130, 100, 120, 100);
        f.add(user);f.add(type);

        click.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                //1.Make sure username entered is present in file
                if (userInput.getText().equals("")) {
                    JOptionPane.showMessageDialog(
                            userInput.getParent(),
                            "Username Field is Empty!!!\n" +
                                    "Please Enter a valid Username.",
                            "Invalid UserName",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
                //2.Display user details username and role
                //3.PopUp like username not entered or username is does not exists
                else{
                    String[] details = Main.usrManager.getDetails(userInput.getText());
                    if(details.length == 0){
                        JOptionPane.showMessageDialog(userInput.getParent(), "User doesn't exist!!",
                                "Error!!", JOptionPane.WARNING_MESSAGE);
                    }
                    else{
                        user.setText(details[0]);
                        type.setText(details[1]);
                        jcb.setVisible(true);jcb.setEnabled(true);
                        submit.setVisible(true);submit.setEnabled(true);
                    }
                }
            }
        });

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                /*
                1.Read a file and verify entered username
                2.PopUp where ever its necessary
                    1.Username is not present
                    2.Please enter username
                    3.Role is "--ENTER NEW ROLE--"
                3.Modify given data
                */
                if (jcb.getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(
                            user.getParent(),
                            "Role not Selected!!\n" +
                                    "Please Select a Role.",
                            "Invalid Role",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
                else{
                    Main.usrManager.modify(user.getText(), jcb.getSelectedIndex() - 1);
                    JOptionPane.showMessageDialog(user.getParent(), "User Modified Successfully",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    Main.mainWindow.remove(f);
                    Main.mainWindow.add(new Manage().f, UserMain.MOD);
                    Main.navigate(UserMain.MOD);
                }
            }
        });
        //f.setVisible(true);
    }
    public static void main(String[] args) {
        new Manage();
        JFrame frame = new JFrame("ManageUsers");
        frame.add(new Manage().f);
        frame.setLayout(null);
        frame.setSize(640, 480);
        frame.setVisible(true);
    }
}