import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NaviButton extends JButton {
    private NaviButton self;
    NaviButton(ImageIcon icon, String next, JPanel parent){
        super(icon);
        self = this;
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.navigate(next);
                Main.mainWindow.remove(parent);
            }
        });
        setSize(icon.getIconWidth(), icon.getIconHeight());
        parent.add(this);
    }
}
