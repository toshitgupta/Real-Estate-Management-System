import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import javax.swing.*;
import java.awt.*;

//Main Class... also the window of the application
public class Main extends JFrame{
    public static JPanel mainWindow; //Stores the main window panel
    private static CardLayout c; //CardLayout of mainWindow, required to switch panels
    public static JFrame mainFrame; //The instance of this class, necessary for logout button
    public static UserManager usrManager = new UserManager();

    public static String userName = "super000";
    Main(){
        super("Project");

        //Initializing c and mainFrame. Also adding all the application panels to mainFrame with their names
        c = new CardLayout();
        mainFrame = this;
        mainWindow = new JPanel(c);
        mainWindow.add(new Admin_Main(), "AdminMain");
        mainWindow.add(new AdminQuery(), "AdminQuery");
        mainWindow.add(new DatabaseViewer(), "DatabaseViewer");
        mainWindow.add(new Office_Interface_Main(), "OfficeMain");
        mainWindow.add(new AgentMainMenu(1).f, "AgentMain");
        mainWindow.add(new SignIn(), "SignIn");
        //Setting Panel and Window Dimensions(default 640x480, 4:3 looks nicer for smaller windows)
        mainWindow.setBounds(0, 0, 640, 480);
        add(mainWindow);
        setSize(640, 480);

        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        //Setting Theme and creating an instance of Main() to launch the program
        FlatMacDarkLaf.setup();
        new Main();
        //Main.c.show(mainWindow, "AdminMain");
        Main.c.show(mainWindow, "SignIn");
    }

    public static void navigate(String nextPage){
        //Switching to the page(panel) specified in the argument
        c.show(mainWindow, nextPage);
    }
}
