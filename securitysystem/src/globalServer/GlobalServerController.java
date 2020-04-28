package globalServer;

import globalServerGUI.MainFrame;

import java.util.HashMap;

public class GlobalServerController {
    private Clients clients;
    private LocalServers localServers;
    private GlobalServer globalServer;
    private HashMap<String, Home> homes = new HashMap<>();

    public GlobalServerController() {
        clients = new Clients();
        localServers = new LocalServers();
        globalServer = new GlobalServer(8081, clients, localServers);


    }


}
