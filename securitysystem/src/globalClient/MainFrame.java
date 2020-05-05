package globalClient;

import javax.swing.*;

public class MainFrame extends JFrame {
    private MainPanel mainPanel;
    private LogInPanel logInPanel;
    private GlobalClient globalClient;

    public MainFrame(GlobalClient globalClient) {
        this.globalClient = globalClient;
        logInPanel = new LogInPanel(globalClient);
    }

    public void showMainPanel() {
        mainPanel = new MainPanel(globalClient);
    }

    public void disposeLoginPanel() {
        logInPanel.disposeLogInPanel();
    }

    public void setTextArea(String text) {
        mainPanel.setTextArea(text);
    }
}
