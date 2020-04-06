package laboration8;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class test {
    public static void main(String[] args) {
        {

            try {
                ServerSocket ss = new ServerSocket(3000);
                Socket socket = ss.accept();

                System.out.println(socket.getInetAddress());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                System.out.println(bufferedReader.readLine());

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                writer.write("Hello from server");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
