package globalClient;

import model.Message;
import model.SecurityComponent;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.PasswordAuthentication;
import java.net.Socket;
import java.util.ArrayList;

public class GlobalClientController {

    private Socket socket;
    private String ip;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private MainFrame mainFrame;

    public GlobalClientController(String ip) {
        this.ip = ip;
        connect();
        mainFrame = new MainFrame(this);
       new Receiver().start();
    }

    public void connect() {

        try {
            socket = new Socket(ip, 8081);
            System.out.println("You're connected");

            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            oos.writeObject("globalClient");


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
        String logger = "";

        @Override
        public void run() {
            while (true) {
                try {
                    Object object = ois.readObject();
                    if(object instanceof String) {
                        String objectRead = (String) object;
                        logger += objectRead + "\n";
                        System.out.println("You have received: " + objectRead);
                        mainFrame.setTextArea(logger);
                    }
                    if(object instanceof ArrayList){

                        mainFrame.setTest((ArrayList<SecurityComponent>) object);
                    }


                } catch (IOException | ClassNotFoundException e) {
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
       Login go = new Login();
       go.Login();
       // new GlobalClientController("localhost");
    }
}




