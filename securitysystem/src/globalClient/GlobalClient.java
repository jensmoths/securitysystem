package globalClient;

import globalServer.Logger;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GlobalClient {

    private Socket socket;
    private String ip;
    private int port;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private GlobalClientController globalClientController;

    public GlobalClient(String ip, int port, GlobalClientController globalClientController) {
        this.ip = ip;
        this.port = port;
        this.globalClientController = globalClientController;
        connect();
    }


    public void connect() {

        try {
            socket = new Socket(ip, port);
            System.out.println("You're connected");

            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            new Receiver().start();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Connection faulty in client");
        }
    }

    public void closeSocket() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void send(String string) {
        try {
            oos.writeObject(string);
            oos.flush();
            System.out.println("You have sent: " + string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Receiver extends Thread {

        @Override
        public void run() {
            try {
                oos.writeObject("globalClient");
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (true) {
                try {
                    Object objectRead = ois.readObject();

                    if (objectRead instanceof String) {
                        System.out.println("You have received: " + objectRead);
                        if (objectRead.equals("user authenticated")) {
                            globalClientController.authenticateUser();
                        } else if (objectRead.equals("user unauthenticated")) {
                            JOptionPane.showMessageDialog(null, "username or password are incorrect");
                        }
                    } else if (objectRead instanceof Logger) {
                        globalClientController.setLogger((Logger) objectRead);
                    } else if (objectRead instanceof ImageIcon) {
                        globalClientController.showImage((ImageIcon) objectRead);
                    }

                } catch (IOException | ClassNotFoundException e) {
                    break;
                }
            }
        }
    }
}




