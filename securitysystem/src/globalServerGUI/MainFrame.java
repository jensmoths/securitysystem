package globalServerGUI;

import javax.swing.*;
import java.awt.*;

public class MainFrame {
    private JFrame frame = new JFrame();
    private RegisterPanel registerPanel = new RegisterPanel();

    public MainFrame () {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                frame.setLocation(dim.width/3-frame.getSize().width/3, dim.height/3-frame.getSize().height/3);
                frame.setSize(new Dimension(350, 150));
                frame.setContentPane(registerPanel);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setTitle("Registration");
            }
        });
    }

    public RegisterPanel getRegisterPanel() {
        return registerPanel;
    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
    }
}
