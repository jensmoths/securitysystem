package localClient;


import localserver.*;
import localserver.PiServer;
import model.Message;
import model.SecurityComponent;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;

public class Controller {
    public static boolean alarmOn = false;
    public boolean cameraReady = true;

    MainFrame mainFrame = new MainFrame(this);
    PiServer server = new PiServer(this);

    public Controller() throws IOException, InterruptedException, ParseException {

    }

   public void setDoorOpen(boolean b) {
        try {
            server.setDoor(b);
            updateSensors();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateSensors() {
        mainFrame.meny.updateOnlineMK(server.allOnlineSensors);
        mainFrame.meny.updateOfflineMK(server.allOfflineSensors);
        server.globalServer.updateGlobal();
    }

    public void sendToMK(char c, int id) throws IOException {
       // System.out.println("CONTROLLER SEND TO MK");
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

    public void pictureTaken(int number) {
        ImageIcon icon = new ImageIcon("/home/pi/data/photo/cam" + number + ".jpg");
        System.out.println(icon.getImageLoadStatus());

        if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            try {
                server.globalServer.globalsendPicture(icon);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void alarmOnDelay(String soundFile, Message message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (alarmOn) {
                    server.globalServer.globalsendMessage(message);
                    soundAlarm(soundFile);
                }
            }
        }).start();
    }

    public void soundAlarm(String file) {
        int rnd = new Random().nextInt(7) + 1;
        String filepath = "data/voices/" + file + "/" + rnd + ".wav";
        new Thread(new PlaySound(filepath)).start();
    }

    public void setAlarmOn(boolean alarmOn) {
        if (alarmOn) {
            JDialog jd = new JDialog(mainFrame.numpad, "");
            JOptionPane op = new JOptionPane("Larmar om 10 sekunder", JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{});
            jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jd.setUndecorated(true);
            jd.setLayout(new BorderLayout());
            jd.add(op);
            jd.pack();
            jd.setLocationRelativeTo(null);


            mainFrame.numpad.setVisible(true);
            mainFrame.meny.setVisible(false);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 9; i >= 0; i--) {
                            Thread.sleep(1000);
                            jd.add(new JOptionPane("Larmar om " + i + " sekunder", JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}));
                            jd.pack();
                        }
                        jd.dispose();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Controller.alarmOn = true;
                }
            }).start();
            jd.setVisible(true);
        } else {
            mainFrame.numpad.setVisible(false);
            mainFrame.meny.setVisible(true);
            Controller.alarmOn = false;
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, ParseException {
        new Controller();
    }

    public void setOnlineButton(boolean b) {
        mainFrame.meny.btnGoOnline.setEnabled(b);
    }

    public int getFingerAmount() throws IOException {
        server.sendToFinger('g', 0);
        return 0;
    }
}
