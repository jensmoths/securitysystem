package globalServer;

import globalServerGUI.MainFrame;

import java.util.HashMap;

public class GlobalServerController {
    private GlobalServer globalServer;
    private HashMap<String, Home> homes;
    private MainFrame mainFrame;

    public GlobalServerController() {
        homes = new HashMap<>();
        mainFrame = new MainFrame(this);
        globalServer = new GlobalServer(8081, homes);
    }

    public void addHome(String userName, Home home) {
        homes.put(userName, home);
    }

    public static void main(String[] args) {
        new GlobalServerController();
    }
}
