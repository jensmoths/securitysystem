package globalClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
    private GlobalClientGui globalClientGui;
    private GlobalClientController globalClientController;

    public MainFrame(GlobalClientController globalClientController) {
        this.globalClientController = globalClientController;
        setupMainFrame();
    }

    public void setupMainFrame() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                globalClientGui = new GlobalClientGui(globalClientController);
                setSize(new Dimension(610, 650));
                setContentPane(globalClientGui);
                setVisible(true);
                setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            }
        });
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                globalClientController.closeSocket();
                //System.exit(0);
            }
        });
    }
    public void setTextArea(String text) {
        globalClientGui.setTextArea(text);
    }
}
