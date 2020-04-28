package globalServer;

public class GlobalServerController {
    private Clients clients;
    private LocalServers localServers;
    private GlobalServer globalServer;

    public GlobalServerController() {
        clients = new Clients();
        localServers = new LocalServers();
        globalServer = new GlobalServer(8081, clients, localServers);


    }


}
