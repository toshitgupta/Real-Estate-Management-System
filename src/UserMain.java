import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserMain extends JPanel {
    public static final String ADD = "AddUser";
    public static final String DELETE = "DeleteUser";
    public static final String MOD = "ManageUser";
    UserMain(){
        setLayout(null);
        JButton addUser = new JButton(Resources.addPplIcon);
        addUser.setBounds(40, 35, Resources.addPplIcon.getIconWidth(), Resources.addPplIcon.getIconHeight());
        addUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.mainWindow.add(new AddUser(), ADD);
                Main.navigate(ADD);
            }
        });

        JButton removeUser = new JButton(Resources.removePplIcon);
        removeUser.setBounds(310, 35, Resources.removePplIcon.getIconWidth(),
                Resources.removePplIcon.getIconHeight());
        removeUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.mainWindow.add(new Delete(), DELETE);
                Main.navigate(DELETE);
            }
        });

        JButton modUser = new JButton(Resources.modPplIcon);
        modUser.setBounds(175, 240, Resources.modPplIcon.getIconWidth(), Resources.modPplIcon.getIconHeight());
        modUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.mainWindow.add(new Manage().f, MOD);
                Main.navigate(MOD);
            }
        });

        JButton back = new JButton(Resources.backImageIcon);
        back.setBounds(5,5, Resources.backImageIcon.getIconWidth(), Resources.backImageIcon.getIconHeight());
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Main.usrManager.saveUsers() == 0){
                    JOptionPane.showMessageDialog(back.getParent(), "Users saved Successfully",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(back.getParent(), "Users couldn't be saved!!",
                            "Error!!", JOptionPane.WARNING_MESSAGE);
                }
                Main.mainWindow.remove(back.getParent());
                Main.navigate("AdminMain");
            }
        });

        add(addUser); add(removeUser); add(modUser); add(back);
        setSize(640, 480);
    }
}
