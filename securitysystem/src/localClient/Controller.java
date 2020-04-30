package localClient;

import localserver.MicroClients;
import localserver.PiServer;
import model.FingerprintSensor;
import model.SecurityComponent;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    boolean alarmOn = false;

    MainFrame mainFrame = new MainFrame(this);
    PiServer server = new PiServer(this);



    public Controller() throws IOException, InterruptedException {

    }

    void setDoorOpen(boolean b) {
        server.setDoor(b);
    }
    public void updateMK(ArrayList<SecurityComponent> MKarray){
        mainFrame.meny.updateStatusMK(MKarray);

    }
    public void sendToMK(char c) throws IOException {
        server.sendToFinger(c);

            }



    void setAlarmOn(boolean b) {
        alarmOn = b;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new Controller();
    }
}
