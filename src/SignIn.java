import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SignIn extends JPanel implements ActionListener{
    JLabel header;
    JLabel user;
    JTextField username;
    JLabel pass;
    JPasswordField password;
    JButton submit;
    SignIn(){
        this.setSize(640,480);
        this.setLayout(null);
        
        header = new JLabel("Login");
        user = new JLabel("Enter username : ");
        username = new JTextField();
        pass = new JLabel("Enter password : ");
        password = new JPasswordField();
        submit = new JButton("Submit");

        submit.addActionListener(this);

        header.setFont(new Font("Courier", Font.BOLD, 45));
        username.setFont(new Font("Courier", Font.PLAIN, 17));
        password.setFont(new Font("Courier", Font.PLAIN, 17));
        user.setFont(new Font("Courier", Font.PLAIN, 17));
        pass.setFont(new Font("Courier", Font.PLAIN, 17));
        submit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        header.setBounds(240,40,150,56);
        user.setBounds(120,150,150,20);
        username.setBounds(270,150,230,25);
        pass.setBounds(120,210,150,20);
        password.setBounds(270,210,230,25);
        submit.setBounds(250,270,100,40);

        this.add(header);
        this.add(user);
        this.add(username);
        this.add(pass);
        this.add(password);
        this.add(submit);

    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == submit) {

            //if either of username or password field is empty
            if (username.getText().equals("") || password.getPassword().length == 0) {
                //if username is empty
                if(username.getText().equals("")){
                    JOptionPane.showMessageDialog(
                            header.getParent(),
                            "Username field is empty!\n" +
                                    "Please Enter a Valid Username.",
                            "Invalid Username",
                            JOptionPane.WARNING_MESSAGE
                    );
                }

                //if password is empty
                else if(password.getPassword().length == 0) {
                    JOptionPane.showMessageDialog(
                            header.getParent(),
                            "Password field is empty!\n" +
                                    "Please Enter your Password.",
                            "Invalid Password",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }
            else {
                //check if invalid username
                //if username not valid
                if (Main.usrManager.find(username.getText(), String.valueOf(password.getPassword())) == 10) {
                    JOptionPane.showMessageDialog(
                            header.getParent(),
                            "Given Username or password is Invalid!\n" +
                                    "Please Enter a Valid Username.",
                            "Invalid Username",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
                else {
                    String[] details = Main.usrManager.getDetails(username.getText());
                    Main.userName = details[0];
                    if(details[1].equals("Super User")) {
                        String[] superOptions = {"Agent", "Office", "Admin"};
                        int result = JOptionPane.showOptionDialog(
                                header.getParent(),
                                "Choose one of the Windows",
                                "Super Window Selector",
                                JOptionPane.YES_NO_CANCEL_OPTION,
                                JOptionPane.INFORMATION_MESSAGE,
                                null,
                                superOptions,
                                0
                        );
                        if (result == 0) {
                            Main.navigate("AgentMain");
                        } else if (result == 1) {
                            Main.navigate("OfficeMain");
                        }
                        else{
                            Main.navigate("AdminMain");
                        }
                    }else {
                        Main.navigate(details[1] + "Main");
                        System.out.println(details[1]);
                        if(details[1].equals("Agent"))
                            AgentMainMenu.setA_id(Integer.parseInt(details[0].substring(1)));
                    }
                    Main.mainWindow.remove(this);
                }
            }
        }
    }
    public static void main(String[] args){
        JFrame frame = new JFrame("SignIn");
        frame.add(new SignIn());
        frame.setSize(640,480);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}