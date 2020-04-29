package globalClient;

public class GlobalClientController {
    private GlobalClient globalClient;
    private MainFrame mainFrame;

    public GlobalClientController () {
        globalClient = new GlobalClient("localhost", 8081);
        mainFrame = new MainFrame(globalClient);
    }

    public static void main(String[] args) {
        new GlobalClientController();
    }
}
