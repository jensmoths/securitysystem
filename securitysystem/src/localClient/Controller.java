package localClient;


import localserver.MicroClients;
import localserver.PiServer;
import model.FingerprintSensor;
import model.SecurityComponent;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class Controller {
    boolean alarmOn = false;

    MainFrame mainFrame = new MainFrame(this);
    PiServer server = new PiServer(this);



    public Controller() throws IOException, InterruptedException, ParseException {

    }

    void setDoorOpen(boolean b) {
        server.setDoor(b);
    }
    public void updateMK(ArrayList<SecurityComponent> MKarray){
        mainFrame.meny.updateStatusMK(MKarray);

    }
    public void sendToMK(char c, int id) throws IOException {
        System.out.println("CONTROLLER SEND TO MK");
        server.sendToFinger(c, id);

            }

    public void connectToGlobal() {
        new Thread(server.globalServer).start();
    }



    void setAlarmOn(boolean b) {
        alarmOn = b;
    }

    public static void main(String[] args) throws IOException, InterruptedException, ParseException {
        new Controller();
    }

    public void setOnline(boolean b) {
        mainFrame.meny.btnGoOnline.setEnabled(b);
    }
}
