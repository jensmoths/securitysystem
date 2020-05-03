package globalClient;

import globalServer.Logger;

public class GlobalClientController {
    private GlobalClient globalClient;
    private MainFrame mainFrame;

    public GlobalClientController () {
        globalClient = new GlobalClient("localhost",8081, this);
        mainFrame = new MainFrame(globalClient);
    }

    public void authenticateUser() {
        mainFrame.disposeLoginPanel();
        mainFrame.showMainPanel();
    }

    public void updateLog(Logger logger) {
        mainFrame.setTextArea(logger.getLog(23, 47, 23, 49));
    }

    public static void main(String[] args) {
        new GlobalClientController();
    }
}
