package globalServer;

import java.util.HashMap;

public class Clients {

    private HashMap<String, GlobalServer.ClientHandler> clients = new HashMap<>();



    public synchronized void put(String name, GlobalServer.ClientHandler handler) {
        clients.put(name, handler);
    }


    public GlobalServer.ClientHandler get(String name) {
        return clients.get(name);
    }

    public int getSize(){
        return clients.size();
    }


    /*public synchronized LinkedList<User> getOnlineList() {
        LinkedList<User> linkedList = new LinkedList<>();
        ServerClient serverClient;
        for (String key : clients.keySet()) {
            serverClient = clients.get(key);
            if (serverClient.isLoggedIn()){
                linkedList.add(serverClient.getUser());
            }
        }
        return linkedList;
    }
     */
}
