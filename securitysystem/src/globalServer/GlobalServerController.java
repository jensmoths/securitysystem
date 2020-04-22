package globalServer;

import model.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class GlobalServerController {


    private Clients clients;
    private LocalServers localServers;

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
        private Message message;

        public ClientHandler(Socket socket, ObjectOutputStream oos, ObjectInputStream ois) {
            this.socket = socket;
            this.oos = oos;
            this.ois = ois;
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
                    System.out.println(name + " :" + localServers.get(name));

                    while (true) {
                        try {

                            ois = new ObjectInputStream(socket.getInputStream());
                            requestObject = ois.readObject();
                            System.out.println(requestObject.toString());

                                handleServerRequest(requestObject);

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
                            System.out.println(socket.getInetAddress() + ": " + requestString);
                            ObjectOutputStream localServerOos = localServers.get(name).oos;
                            localServerOos.writeObject(handleClientRequest(requestString));
                            System.out.println(handleClientRequest(requestString).toString());

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

        public void handleServerRequest (Object requestObject) {
            try {
                if (requestObject instanceof String) {
                    clients.get(name).oos.writeObject(requestObject);
                } else if (requestObject instanceof Message) {
                    message = (Message) requestObject;
                    SecurityComponent securityComponent = message.getSecurityComponent();

                    if (securityComponent instanceof MagneticSensor) {
                        System.out.println("You are in magnet sensor");
                        System.out.println(securityComponent.isOpen());

                        if (securityComponent.isOpen()) {
                            clients.get(name).oos.writeObject("Magnetsensorn larmar");

                        } else if (!securityComponent.isOpen()) {
                            clients.get(name).oos.writeObject("Magnetsensorn är aktiv");
                        }
                    }
                    if (securityComponent instanceof FireAlarm) {
                        System.out.println("You are in Firealarm");
                        //System.out.println(securityComponent.isOpen());
                        clients.get(name).oos.writeObject("Brandlarmet har upptäckt rök i byggnaden");
                    }
                    if (securityComponent instanceof ProximitySensor) {
                        System.out.println("You are in Proximity Sensor");
                        //System.out.println(securityComponent.isOpen());

                        clients.get(name).oos.writeObject("Rörelsedetektorn har upptäckt rörelse i byggnaden");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public Message handleClientRequest(String clientRequest) {
            Message messageResponse = new Message();

            switch (clientRequest) {
                    case "on":
                        //localServerOos.writeObject(new MagneticSensor());
                        break;
                    case "off":
                        //localServerOos.writeObject(new );
                        break;
                    case "lock":
                        messageResponse = new Message("", new DoorLock(false));
                        break;
                    case "unlock":
                        messageResponse = new Message("", new DoorLock(true));
                        break;
                }

            return messageResponse;
        }
    }


    public static void main(String[] args) {
        GlobalServerController globalServerController = new GlobalServerController(8081);
    }
}
