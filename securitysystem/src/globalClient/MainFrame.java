package globalClient;

import model.SecurityComponent;

import javax.swing.*;
<<<<<<< HEAD
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
=======
>>>>>>> origin/Malek4

public class MainFrame extends JFrame {
    private MainPanel mainPanel;
    private LogInPanel logInPanel;
    private GlobalClient globalClient;

<<<<<<< HEAD
    public MainFrame(GlobalClientController globalClientController) {
        this.globalClientController = globalClientController;
        globalClientGui = new GlobalClientGui(globalClientController);
        setupMainFrame();
    }

    public void setupMainFrame() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override

            public void run() {

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
=======

    public MainFrame(GlobalClient globalClient) {
        this.globalClient = globalClient;
        logInPanel = new LogInPanel(globalClient);
    }

    public void showMainPanel() {
        mainPanel = new MainPanel(globalClient);
    }

    public void disposeLoginPanel() {
        logInPanel.disposeLogInPanel();
>>>>>>> origin/Malek4
    }

    public void setTextArea(String text) {
        mainPanel.setTextArea(text);
    }
    public void setTest(ArrayList<SecurityComponent> rey){
             globalClientGui.setTestJlist(rey);
       }
    }
//}
