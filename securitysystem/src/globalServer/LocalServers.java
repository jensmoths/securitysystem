package globalServer;

import java.util.HashMap;

public class LocalServers {

    private HashMap<String, GlobalServerController.ClientHandler> localServers = new HashMap<>();

    public synchronized void put(String name, GlobalServerController.ClientHandler handler) {
        localServers.put(name, handler);
    }


    public GlobalServerController.ClientHandler get(String name) {
        return localServers.get(name);
    }

}
