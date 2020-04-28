package globalServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class GlobalServer {

    private Clients clients;
    private LocalServers localServers;
    //private HashMap<User, Home> registeredHomes = new HashMap<>();

    public GlobalServer(int port, Clients clients, LocalServers localServers) {
        this.clients = clients;
        this.localServers = localServers;
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
                            requestHandler.handleServerRequest(requestObject, clients, name);

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

    }
}
