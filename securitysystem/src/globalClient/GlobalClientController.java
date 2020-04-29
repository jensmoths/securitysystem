package globalClient;

public class GlobalClientController {
    private GlobalClient globalClient;
    private MainFrame mainFrame;

    public GlobalClientController () {
        globalClient = new GlobalClient("localhost", 8081, this);
        mainFrame = new MainFrame(globalClient);
    }

    public void authenticateUser() {
            System.out.println("show mainpanel");
        mainFrame.disposeLoginPanel();
        mainFrame.showMainPanel();
    }


    public static void main(String[] args) {
        new GlobalClientController();
    }
}
