package globalClient;

import javax.swing.*;

public class MainFrame extends JFrame {
    private MainPanel mainPanel;
    private LogInPanel logInPanel;
    private GlobalClient globalClient;

    public MainFrame(GlobalClient globalClient) {
        this.globalClient = globalClient;
        mainPanel = new MainPanel(globalClient);
        logInPanel = new LogInPanel(globalClient);
        setupMainFrame();
    }

    public void setupMainFrame() {

    }

    public void setTextArea(String text) {
        mainPanel.setTextArea(text);
    }
}
