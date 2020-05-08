package com.example.myapplication;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;

public class ClientHandler extends Thread {
    private final MainActivity mainActivity;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public ClientHandler(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void run() {
        try {
            socket = new Socket("109.228.172.110", 8081);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            send("globalClient");
            System.out.println("You're connected");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Connection faulty in client");
        }

        while (true) {
            try {
                Object objectRead = ois.readObject();
                mainActivity.showMessage(objectRead);

                System.out.println("You have received: " + objectRead);
            } catch (IOException | ClassNotFoundException e) {
                break;
            }
        }
    }

    public void send(String username, String password) {
        new Send().execute(username, password);
    }

    public void send(String msg) {
        new Send().execute(msg);
    }

    class Send extends AsyncTask<String, Void, Object> {
        @Override
        protected Object doInBackground(String... strings) {
            try {
                for (String s : strings) {
                    oos.writeObject(s);
                    oos.flush();
                    System.out.println("You have sent: " + s);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }
}

