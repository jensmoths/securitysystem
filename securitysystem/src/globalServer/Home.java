package globalServer;

import java.util.ArrayList;

public class Home {
    String userName;
    GlobalServer.ClientHandler localServer;
    ArrayList<GlobalServer.ClientHandler> globalClients;

    public Home(String userName, GlobalServer.ClientHandler localServer) {
        globalClients = new ArrayList<>();
    }


}
