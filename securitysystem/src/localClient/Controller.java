package localClient;


import localserver.CommandLine;
import localserver.PiServer;
import model.SecurityComponent;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class Controller {
   public boolean alarmOn = false;

    MainFrame mainFrame = new MainFrame(this);
    PiServer server = new PiServer(this);





    public Controller() throws IOException, InterruptedException, ParseException {

    }

    void setDoorOpen(boolean b)  {
        try {
            server.setDoor(b);
            updateOnlineMK(server.allOnlineSensors);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void updateOnlineMK(ArrayList<SecurityComponent> MKarray){
        mainFrame.meny.updateOnlineMK(MKarray);

    }

    public void updateOfflineMK(ArrayList<SecurityComponent> MKarray){
        mainFrame.meny.updateOfflineMK(MKarray);
    }
    public void sendToMK(char c, int id) throws IOException {
        System.out.println("CONTROLLER SEND TO MK");
        server.sendToFinger(c, id);

            }

    public void connectToGlobal() {
        new Thread(server.globalServer).start();
    }

    public void takePicture() {
        new Thread(new CommandLine()).start();
    }

    public void soundAlarm(){

    }



    void setAlarmOn(boolean b) {
        alarmOn = b;

        if(alarmOn) {
            mainFrame.frame.setVisible(true);
            mainFrame.meny.setVisible(false);
        }
       else {
            mainFrame.frame.setVisible(false);
            mainFrame.meny.setVisible(true);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, ParseException {
        new Controller();
    }

    public void setOnline(boolean b) {
        mainFrame.meny.btnGoOnline.setEnabled(b);
    }
}
