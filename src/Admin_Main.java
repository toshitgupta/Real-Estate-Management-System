import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Admin_Main extends JPanel implements ActionListener {
    JButton write_query_button;
    JButton manage_user_button;
    JButton database_viewer_button;
    JButton logout_button;
    Admin_Main () {
        //Main.mainWindow.setTitle("HOME");
        this.setSize(640, 480);
        this.setLayout(null);

        write_query_button = new JButton(Resources.queryImgIcon);
        write_query_button.setBounds(55, 10, 250, 200);
        write_query_button.setFocusable(false);
        write_query_button.addActionListener(this);

        manage_user_button = new JButton(Resources.managePplIcon);
        manage_user_button.setBounds(320, 10, 250, 200);
        manage_user_button.setFocusable(false);
        manage_user_button.addActionListener(this);

        database_viewer_button = new JButton(Resources.DBCVIcon);
        database_viewer_button.setBounds(55, 225, 250, 200);
        database_viewer_button.setFocusable(false);
        database_viewer_button.addActionListener(this);

        logout_button = new JButton(Resources.logoutXLIcon);
        logout_button.setBounds(320, 225, 250, 200);
        logout_button.setFocusable(false);
        logout_button.addActionListener(this);

        this.add(write_query_button);
        this.add(manage_user_button);
        this.add(database_viewer_button);
        this.add(logout_button);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == write_query_button) {
            Main.navigate("AdminQuery");
        }
        if (actionEvent.getSource() == manage_user_button) {
            Main.mainWindow.add(new UserMain(), "UserMain");
            Main.navigate("UserMain");
        }
        if (actionEvent.getSource() == database_viewer_button) {
            Main.navigate("DatabaseViewer");
        }
        if (actionEvent.getSource() == logout_button) {
            int answer = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to Logout?",
                    "Logout Conformation",
                    JOptionPane.YES_NO_OPTION
            );
            if (answer == 0) {
                Main.mainFrame.dispose();
            }
        }
    }
}
