package localClient;

import localserver.PiServer;

import java.io.IOException;

public class Controller {
    boolean alarmOn = false;

    MainFrame mainFrame = new MainFrame(this);
    PiServer server = new PiServer(this);

    public Controller() throws IOException, InterruptedException {

    }

    void setDoorOpen(boolean b) {
        server.setDoor(b);
    }

    void setAlarmOn(boolean b) {
        alarmOn = b;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new Controller();
    }
}
