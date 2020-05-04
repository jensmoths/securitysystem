package globalServer;

import java.io.IOException;
import java.util.LinkedList;

public class Home {
    User user;
    GlobalServer.ClientHandler localServer;
    LinkedList<GlobalServer.ClientHandler> globalClients;
    Logger logger;

    public Home(User user) {
        globalClients = new LinkedList<>();
        logger = new Logger();
        logger.createLogger(user.getUserName());
        this.user = user;
    }

    public void addGlobalClient(GlobalServer.ClientHandler globalClient) {
        globalClients.add(globalClient);
    }

    public void removeGlobalClient(GlobalServer.ClientHandler globalClient) {
        globalClients.remove(globalClient);
    }

    public void setLocalServer(GlobalServer.ClientHandler localServer) {
        this.localServer = localServer;
    }

    public void sendToAllClients(Object object) {
        GlobalServer.ClientHandler client;
        if (globalClients.size() > 0) {
            try {
                for (int i = 0; i < globalClients.size(); i++) {
                    client = globalClients.get(i);
                    client.getOos().writeObject(object);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Someone i trying to send to non-existing Global Client");
        }
    }

    public GlobalServer.ClientHandler getLocalServer() {
        return localServer;
    }

    public void setClientHandler(GlobalServer.ClientHandler clientHandler) {
        if (clientHandler.getServerOrClient().equals("server")) {
            localServer = clientHandler;
        } else {
            globalClients.add(clientHandler);
        }
    }

    public User getUser() {
        return user;
    }

    public GlobalServer.ClientHandler getGlobalClient(GlobalServer.ClientHandler clientHandler) {
        return globalClients.get(globalClients.indexOf(clientHandler));
    }

}
