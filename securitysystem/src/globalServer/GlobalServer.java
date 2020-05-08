package globalServer;

import model.Buffer;

import javax.mail.MessagingException;
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

        public ClientHandler(Socket socket, ObjectOutputStream oos, ObjectInputStream ois) {
            this.socket = socket;
            this.oos = oos;
            this.ois = ois;
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
                        if (password.equals(homes.get(username).getUser().getPassword()) & socket != null) {
                            break;
                        }
                    } else {
                        System.out.println("failed login");
                        oos.writeObject("user unauthenticated");
                        username = (String) ois.readObject();
                        password = (String) ois.readObject();
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } while (!homes.containsKey(username));

            if (homes.containsKey(username)) {
                if (password.equals(homes.get(username).getUser().getPassword()) & socket != null) {
                    Home home = homes.get(username);
                    home.setClientHandler(this);
                    RequestHandler requestHandler = new RequestHandler(home);

                    try {
                        oos.writeObject("user authenticated");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    home.logger.addToLog(socket.getInetAddress() + "has logged in");
                    home.sendToAllClients(home.logger);

                    Object requestObject;
                    switch (serverOrClient) {

                        case "server":

                            while (true) {
                                try {
                                    ois = new ObjectInputStream(socket.getInputStream());
                                    requestObject = ois.readObject();
                                   // System.out.println(requestObject.toString());
                                    requestHandler.handleServerRequest(requestObject, home, ClientHandler.this);

                                } catch (IOException | ClassNotFoundException | MessagingException e) {
                                    System.out.println(socket.getInetAddress() + " has disconnected");
                                    try {
                                        home.sendToAllClients("local server offline");
                                        home.setLocalServer(null);
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
                            ObjectOutputStream localServerOos;

                            if (!home.getObjectBuffer().objectListIsEmpty()) {

                                    try {

                                        Buffer<Object> buffer = home.getObjectBuffer();

                                        for (int i=0; i<buffer.getBufferSize(); i++){

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
                                    String requestString = requestObject.toString();

                                    if (home.localServer != null) {
                                        localServerOos = home.getLocalServer().getOos();
                                        localServerOos.writeObject(requestHandler.handleClientRequest(requestString));
                                    } else {
                                        home.getGlobalClient(this).oos.writeObject("local server offline"); //sending a string when a local server is offline
                                    }

                                } catch (IOException | ClassNotFoundException e) {
                                    System.out.println(socket.getInetAddress() + " has disconnected");

                                    try {
                                        home.logger.addToLog(socket.getInetAddress() + "has logged out");
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

