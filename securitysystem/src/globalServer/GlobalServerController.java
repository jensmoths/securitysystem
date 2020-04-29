package globalServer;

import globalServerGUI.MainFrame;

<<<<<<< HEAD
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GlobalServerController {


    private Clients clients;
    private LocalServers localServers;
    private ArrayList<SecurityComponent> rey;


    public GlobalServerController(int port) {
        clients = new Clients();
        localServers = new LocalServers();
        new Connection(port).start();

    }

    private class Connection extends Thread {

        private int port;

        public Connection(int port) {
            this.port = port;
        }

        @Override
        public void run() {

            try (ServerSocket serverSocket = new ServerSocket(port)) {

                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println(socket.getInetAddress() + " has connected");
                    ClientHandler clientHandler = new ClientHandler(socket, new ObjectOutputStream(socket.getOutputStream()),
                            new ObjectInputStream(socket.getInputStream()));
                    clientHandler.start();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class ClientHandler extends Thread {

        private Socket socket;
        private ObjectInputStream ois;
        private ObjectOutputStream oos;
        private Object requestObject;
        private String serverOrClient;
        private String name;
        private RequestHandler requestHandler;


        public ClientHandler(Socket socket, ObjectOutputStream oos, ObjectInputStream ois) {
            this.socket = socket;
            this.oos = oos;
            this.ois = ois;
            requestHandler = new RequestHandler();
        }

        @Override
        public void run() {
            try {
                serverOrClient = (String) ois.readObject();
                name = (String) ois.readObject();
                System.out.println(serverOrClient);

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            switch (serverOrClient) {

                case "server":
                    localServers.put(name, this);
                    while (true) {
                        try {
                            ois = new ObjectInputStream(socket.getInputStream());
                            requestObject = ois.readObject();
                            System.out.println(requestObject.toString());
                               if(requestObject instanceof ArrayList){
                                   rey = (ArrayList<SecurityComponent>) requestObject;
                                   System.out.println("Sparade en rey");
                               }
                                    if(clients.getSize() > 0) {
                                        System.out.println("Skickade till klient");
                                        requestHandler.handleServerRequest(requestObject, clients, name);
                                    }
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                            System.out.println(socket.getInetAddress() + " has disconnected");
                            try {
                                if (socket != null) {
                                    socket.close();
                                }
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            break;
                        }
                    }
                    break;

                case "globalClient":
                    clients.put(name, this);
                    System.out.println(name + " :" + clients.get(name));
                    try {

                        clients.get(name).getOos().writeObject(rey);
                        System.out.println("skickade en rey" + rey);
                        clients.get(name).getOos().flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    while (true) {
                        try {
                            requestObject = ois.readObject();
                            String requestString = requestObject.toString();
                            ObjectOutputStream localServerOos = localServers.get(name).oos;
                            localServerOos.writeObject(requestHandler.handleClientRequest(requestString));

                        } catch (IOException | ClassNotFoundException e) {
                            System.out.println(socket.getInetAddress() + " has disconnected");
                            try {
                                if (socket != null) {
                                    socket.close();
                                }
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            break;
                        }
                    }
                    break;
            }
        }


        public ObjectOutputStream getOos() {
            return oos;
        }

=======
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
>>>>>>> origin/Malek4
    }

    public static void main(String[] args) {
        new GlobalServerController();
    }
}
