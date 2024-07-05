import javax.swing.*;
import java.awt.*;

public class Resources {
    public static String resources = "E:\\Vimal\\DBMSProject\\Assets\\"; //Folder with all the multimedia assets

    //Initializing ImageIcon for each image resource(Can be automated in near future)
    public static Image backImage = new ImageIcon(resources + "back.png").getImage();
    public static Image logout = new ImageIcon(resources + "logoutXLNT.png").getImage();
    public static Image logoutXL = new ImageIcon(resources + "logoutXL.png").getImage();
    public static Image resetButton = new ImageIcon(resources + "reset.png").getImage();
    public static Image run = new ImageIcon(resources + "run.png").getImage();
    public static Image setting = new ImageIcon(resources + "SettingsF.png").getImage();

    public static Image queryImg = new ImageIcon(resources + "SQLCQuery.png").getImage();
    public static Image managePpl = new ImageIcon(resources + "ManagePeople.png").getImage();
    public static Image addPpl = new ImageIcon(resources + "AddPeople.png").getImage();
    public static Image removePpl = new ImageIcon(resources + "RemovePeople.png").getImage();
    public static Image modPpl = new ImageIcon(resources + "ModifyPeople.png").getImage();
    public static Image DBCVImg = new ImageIcon(resources + "DBCView.png").getImage();

    public static Image agentsImg = new ImageIcon(resources + "Agents.png").getImage();
    public static Image addAgentImg = new ImageIcon(resources + "AddAgent.png").getImage();
    public static Image viewAgentImg = new ImageIcon(resources + "ViewAgent.png").getImage();
    public static Image removeAgentImg = new ImageIcon(resources + "RemoveAgent.png").getImage();
    public static Image statsImg = new ImageIcon(resources + "Stats.png").getImage();

    public static Image recordSaleImg = new ImageIcon(resources + "RecordSale.png").getImage();
    public static Image newPropertyImg = new ImageIcon(resources + "NewProperty.png").getImage();

    public static ImageIcon backImageIcon = new ImageIcon(backImage.getScaledInstance(30, 30,
            Image.SCALE_SMOOTH));
    public static ImageIcon logoutIcon = new ImageIcon(logout.getScaledInstance(30, 30,
            Image.SCALE_SMOOTH));
    public static ImageIcon logoutXLIcon = new ImageIcon(logoutXL.getScaledInstance(250, 200,
            Image.SCALE_SMOOTH));
    public static ImageIcon resetButtonIcon = new ImageIcon(resetButton.getScaledInstance(30, 30,
            Image.SCALE_SMOOTH));
    public static ImageIcon runButton = new ImageIcon(run.getScaledInstance(30, 30,
            Image.SCALE_SMOOTH));
    public static ImageIcon settingsIcon = new ImageIcon(setting.getScaledInstance(30, 30,
            Image.SCALE_SMOOTH));

    public static ImageIcon queryImgIcon = new ImageIcon(queryImg.getScaledInstance(250, 200,
            Image.SCALE_SMOOTH));
    public static ImageIcon managePplIcon = new ImageIcon(managePpl.getScaledInstance(250, 200,
            Image.SCALE_SMOOTH));
    public static ImageIcon addPplIcon = new ImageIcon(addPpl.getScaledInstance(250, 200,
            Image.SCALE_SMOOTH));
    public static ImageIcon removePplIcon = new ImageIcon(removePpl.getScaledInstance(250, 200,
            Image.SCALE_SMOOTH));
    public static ImageIcon modPplIcon = new ImageIcon(modPpl.getScaledInstance(250, 200,
            Image.SCALE_SMOOTH));
    public static ImageIcon DBCVIcon = new ImageIcon(DBCVImg.getScaledInstance(250, 200,
            Image.SCALE_SMOOTH));

    public static ImageIcon agentsIcon = new ImageIcon(agentsImg.getScaledInstance(200, 160,
            Image.SCALE_SMOOTH));
    public static ImageIcon addAgentIcon = new ImageIcon(addAgentImg.getScaledInstance(200, 160,
            Image.SCALE_SMOOTH));
    public static ImageIcon viewAgentsIcon = new ImageIcon(viewAgentImg.getScaledInstance(200, 160,
            Image.SCALE_SMOOTH));
    public static ImageIcon removeAgentsIcon = new ImageIcon(removeAgentImg.getScaledInstance(200, 160,
            Image.SCALE_SMOOTH));
    public static ImageIcon statsIcon = new ImageIcon(statsImg.getScaledInstance(200, 160,
            Image.SCALE_SMOOTH));

    public static ImageIcon recordSaleIcon = new ImageIcon(recordSaleImg.getScaledInstance(250, 200,
            Image.SCALE_SMOOTH));
    public static ImageIcon newPropertyIcon = new ImageIcon(newPropertyImg.getScaledInstance(250, 200,
            Image.SCALE_SMOOTH));
}
