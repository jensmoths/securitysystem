package globalClient;

import model.SecurityComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private GlobalClientGui globalClientGui;
    private GlobalClientController globalClientController;

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
    }
    public void setTextArea(String text) {
        globalClientGui.setTextArea(text);
    }
    public void setTest(ArrayList<SecurityComponent> rey){
             globalClientGui.setTestJlist(rey);
       }
    }
//}
