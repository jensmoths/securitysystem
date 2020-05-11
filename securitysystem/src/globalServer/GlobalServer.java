package globalServer;

import model.Buffer;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
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
                start();
            }
        }
    }

    public class ClientHandler extends Thread {

        private Socket socket;
        private ObjectInputStream ois;
        private ObjectOutputStream oos;
        private String serverOrClient;
        private String username;
        private String password;
        private RequestHandler requestHandler;
        private Home home;
        private boolean authenticated = false;

        public ClientHandler(Socket socket, ObjectOutputStream oos, ObjectInputStream ois) {
            this.socket = socket;
            this.oos = oos;
            this.ois = ois;

            try {
                serverOrClient = (String) ois.readObject();
            } catch (IOException | ClassNotFoundException i) {
                i.printStackTrace();
            }
        }

        public void authenticateUser() {

            while (true) {
                try {
                    username = (String) ois.readObject();
                    password = (String) ois.readObject();

                    if (homes.containsKey(username)) {

                        if (password.equals(homes.get(username).getUser().getPassword())) {
                            authenticated = true;
                            home = homes.get(username);
                            home.setClientHandler(this);
                            requestHandler = new RequestHandler(home);
                            oos.writeObject("user authenticated");
                            home.logger.addToLog(socket.getInetAddress() + "has logged in");
                            home.sendToAllClients(home.logger);
                            break;
                        }
                    }
                    System.out.println("failed login");
                    oos.writeObject("user unauthenticated");

                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("unexpected disconnect");
                    e.printStackTrace();
                    break;
                }
            }
        }

        @Override
        public void run() {
            authenticateUser();

            if (authenticated) {
                Object requestObject;
                switch (serverOrClient) {

                    case "server":
                        while (true) {
                            try {
                                ois = new ObjectInputStream(socket.getInputStream());
                                requestObject = ois.readObject();
                                requestHandler.handleServerRequest(requestObject, home, ClientHandler.this);

                            } catch (IOException | ClassNotFoundException | MessagingException e) {
                                System.out.println(socket.getInetAddress() + " has disconnected (local server)");
                                try {
                                    home.sendToAllClients("local server offline");
                                    home.setLocalServer(null);
                                    if (socket != null) {
                                        socket.close();
                                    }
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                    break;
                                }
                                break;
                            }
                        }
                        break;

                    case "globalClient":
                        ObjectOutputStream localServerOos;

                        if (!home.getObjectBuffer().objectListIsEmpty()) {
                            try {
                                Buffer<Object> buffer = home.getObjectBuffer();
                                for (int i = 0; i < buffer.getBufferSize(); i++) {
                                    Object object = buffer.getObjects(i);
                                    requestHandler.handleServerRequest(object, home, this);
                                }
                                buffer.clearObjectBuffer();
                            } catch (MessagingException e) {
                                e.printStackTrace();
                            }
                        }

                        while (true) {
                            try {
                                requestObject = ois.readObject();

                                if (home.localServer != null) {
                                    localServerOos = home.getLocalServer().getOos();
                                    localServerOos.writeObject(requestHandler.handleClientRequest(requestObject));
                                } else {
                                    home.getGlobalClient(this).oos.writeObject("local server offline");
                                }

                            } catch (IOException | ClassNotFoundException e) {
                                System.out.println(socket.getInetAddress() + " has disconnected (global client)");

                                try {
                                    home.logger.addToLog(socket.getInetAddress() + "has logged out (global client)");
                                    home.removeGlobalClient(this);
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
        }

        public ObjectOutputStream getOos() {
            return oos;
        }

        public String getServerOrClient() {
            return serverOrClient;
        }
    }
}

