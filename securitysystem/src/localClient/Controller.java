package localClient;


import localserver.*;
import localserver.PiServer;
import model.SecurityComponent;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class Controller {
    public boolean alarmOn = false;
    public boolean cameraReady = true;

    MainFrame mainFrame = new MainFrame(this);
    PiServer server = new PiServer(this);

    public Controller() throws IOException, InterruptedException, ParseException {

    }

    void setDoorOpen(boolean b) {
        try {
            server.setDoor(b);
            updateOnlineMK(server.allOnlineSensors);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateOnlineMK(ArrayList<SecurityComponent> MKarray) {
        mainFrame.meny.updateOnlineMK(MKarray);

    }

    public void updateOfflineMK(ArrayList<SecurityComponent> MKarray) {
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
        System.out.println("Startar ny kameratråd" + cameraReady);
        Thread t = new Thread(new CommandLine(this));
        t.start();
    }

    public void pictureTaken(int number)  {
        ImageIcon icon = new ImageIcon("/home/pi/pic/cam" + number + ".jpg");
        System.out.println(icon.getImageLoadStatus());

        if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            try {
                server.globalServer.globalsendPicture(icon);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void soundAlarm(String file) {
        String filepath = "/home/pi/" + file;
        new Thread(new PlaySound(filepath)).start();
    }

    public void setAlarmOn(boolean b) {
        alarmOn = b;

        if (alarmOn) {
            mainFrame.frame.setVisible(true);
            mainFrame.meny.setVisible(false);
        } else {
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
