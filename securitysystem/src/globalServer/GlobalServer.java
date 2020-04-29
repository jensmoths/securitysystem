package globalServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class GlobalServer {
    private HashMap<String, Home> homes;

    public GlobalServer(int port, HashMap<String, Home> homes) {
        this.homes = homes;
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
        private String username;
        private String password;
        private RequestHandler requestHandler;

        public ClientHandler(Socket socket, ObjectOutputStream oos, ObjectInputStream ois) {
            this.socket = socket;
            this.oos = oos;
            this.ois = ois;
            requestHandler = new RequestHandler();
        }

        public String getServerOrClient() {
            return serverOrClient;
        }

        @Override
        public void run() {
            try {
                serverOrClient = (String) ois.readObject();
                username = (String) ois.readObject();
                password = (String) ois.readObject();
                System.out.println(serverOrClient);

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            do {
                try {
                    if (homes.containsKey(username)) {
                        break;
                    } else {
                        System.out.println("failed login");
                        oos.writeObject("user unauthenticated");
                        username = (String) ois.readObject();
                        password = (String) ois.readObject();
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } while (!homes.containsKey(username) );

            if (homes.containsKey(username)) {
                if (password.equals(homes.get(username).getUser().getPassword()) & socket != null) {
                    System.out.println("in if");
                    Home home = homes.get(username);
                    home.setClientHandler(this);

                    try {
                        oos.writeObject("user authenticated");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    switch (serverOrClient) {

                        case "server":

                            while (true) {
                                try {
                                    ois = new ObjectInputStream(socket.getInputStream());
                                    requestObject = ois.readObject();
                                    System.out.println(requestObject.toString());
                                    requestHandler.handleServerRequest(requestObject, home);

                                } catch (IOException | ClassNotFoundException e) {
                                    e.printStackTrace();
                                    System.out.println(socket.getInetAddress() + " has disconnected");
                                    try {
                                        if (socket != null) {
                                            home.setLocalServer(null);
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
                            ObjectOutputStream localServerOos;
                            ClientHandler localServer = home.getLocalServer();

                            while (true) {
                                try {
                                    requestObject = ois.readObject();
                                    String requestString = requestObject.toString();

                                    if (localServer != null) {
                                        localServerOos = home.getLocalServer().getOos();
                                        localServerOos.writeObject(requestHandler.handleClientRequest(requestString));
                                    } else {
                                        home.getGlobalClient(this).oos.writeObject("local server offline"); //sending a string when a local server is offline
                                    }

                                } catch (IOException | ClassNotFoundException e) {
                                    System.out.println(socket.getInetAddress() + " has disconnected");
                                    try {
                                        if (socket != null) {
                                            home.removeGlobalClient(this);
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
                } else {
                    System.out.println("disconnected");
                    try {
                        oos.writeObject("user unauthenticated");
                        System.out.println("failed login");
                        //socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                try {
                    System.out.println("failed login");
                    oos.writeObject("user unauthenticated");
                    //socket.close();
                    //System.out.println("Unexpected disconnection");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        public ObjectOutputStream getOos() {
            return oos;
        }

    }
}

