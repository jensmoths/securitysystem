package globalClient;

import javax.swing.*;


public class MainFrame extends JFrame {
    private MainPanel mainPanel;
    private LogInPanel logInPanel;
    private GlobalClient globalClient;
    private GlobalClientController clientController;

    public MainFrame(GlobalClientController clientController) {
//        this.globalClient = globalClient;
        this.clientController = clientController;
        logInPanel = new LogInPanel(clientController);
    }

    public void showMainPanel() {
        mainPanel = new MainPanel(clientController);
    }

    public void disposeLoginPanel() {
        logInPanel.disposeLogInPanel();
    }

    public void setTextArea(String text) {
        mainPanel.setTaLogger(text);
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public void showImage(ImageIcon imageIcon) {
        mainPanel.showImage(imageIcon);
    }
}
