package globalServer;

import java.util.HashMap;

public class LocalServers {

    private HashMap<String, GlobalServer.ClientHandler> localServers = new HashMap<>();

    public synchronized void put(String name, GlobalServer.ClientHandler handler) {
        localServers.put(name, handler);
    }


    public GlobalServer.ClientHandler get(String name) {
        return localServers.get(name);
    }

}
