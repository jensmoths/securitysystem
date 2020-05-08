package localserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class test {
    public static void main(String[] args)  {
        try {
            ServerSocket ss = new ServerSocket(40000);
            Socket socket = ss.accept();
            System.out.println(socket);
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            Object obj;
            while (true) {
                obj = objectInputStream.readObject();
                System.out.println(obj);
                objectOutputStream.writeObject(obj);
                objectOutputStream.flush();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
