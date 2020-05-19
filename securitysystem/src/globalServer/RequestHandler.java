package globalServer;

import model.*;

import javax.mail.MessagingException;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class RequestHandler {

    private EmailSender emailSender = new EmailSender();
    private Home home;

    public RequestHandler(Home home) {
        this.home = home;
    }

    public void handleServerRequest(Object requestObject, Home home, GlobalServer.ClientHandler handler) {
        if (requestObject instanceof String) {

            home.logger.addToLog((String) requestObject);
            home.sendToAllClients(home.logger);
            home.sendToAllClients(requestObject);

        } else if (requestObject instanceof ImageIcon) {

            home.logger.addToLog("received an image");
            home.sendToAllClients(home.logger);
            home.sendToAllClients(requestObject);
            emailSender.sendPictureMail(home.getUser().getEmail(), "", "Nytt foto från ditt hem", (ImageIcon) requestObject);

        } else if (requestObject instanceof Message) {

            Message message = (Message) requestObject;
            SecurityComponent securityComponent = message.getSecurityComponent();

            if (securityComponent == null) {
                home.setOffline(message.getOfflineSensors());
                home.setOnline(message.getOnlineSensors());
                boolean alarm = message.isAlarmOn();
                home.sendToAllClients(message);


                for (SecurityComponent s : home.getOnline()) {
                    System.out.println("ONLINE SENSOR: " + s.getId());
                }
                for (SecurityComponent s : home.getOffline()) {
                    System.out.println("OFFLINE SENSOR: " + s.getId());
                }
                System.out.println("Alarm status: " + alarm);
            }

            if (securityComponent instanceof MagneticSensor) {
                if (securityComponent.isOpen()) {
                    home.sendToAllClients("Magnetsensorn larmar");
                    home.logger.addToLog("Magnetsensorn larmar");
                    home.sendToAllClients(home.logger);
                    emailSender.sendMail(home.getUser().getEmail(), "SecureHomesMAU", "Hej kära kund!\n\n Magnetsensorn har larmat");

                } else if (!securityComponent.isOpen()) {
                    home.sendToAllClients("Magnetsensorn är aktiv");
                    home.logger.addToLog("Magnetsensorn är aktiv");
                    home.sendToAllClients(home.logger);
                }
            }
            if (securityComponent instanceof FireAlarm) {
                home.sendToAllClients("Brandlarmet har upptäckt rök i byggnaden");
                home.logger.addToLog("Brandlarmet har upptäckt rök i byggnaden");
                try {
                    emailSender.sendMail(home.getUser().getEmail(), "SecureHomesMAU", "Hej kära kund!\n Brandlarmet har utlösts");

                } catch (Exception e) {
                    e.printStackTrace();
                }
                home.sendToAllClients(home.logger);
            }

            if (securityComponent instanceof ProximitySensor) {
                home.sendToAllClients("Rörelsedetektorn har upptäckt rörelse i byggnaden");
                home.logger.addToLog("Rörelsedetektorn har upptäckt rörelse i byggnaden");
                home.sendToAllClients(home.logger);
                emailSender.sendMail(home.getUser().getEmail(), "SecureHomesMAU", "Hej kära kund!\n Rörelsedetektorn har upptäckt rörelse i byggnaden");

            }
        }
    }

    public Object handleClientRequest(Object clientRequest) {
        Object respone = null;

        if (clientRequest instanceof Message) {

            if (((Message) clientRequest).getInfo().equals("ny location")) {
              //  home.logger.addToLog("Ny location");
                //home.sendToAllClients(home.logger);
                return clientRequest;
            }
            if (((Message) clientRequest).getInfo().equals("lock")) {
              //  home.logger.addToLog("Door locked");
              //  home.sendToAllClients(home.logger);
                ((Message) clientRequest).getSecurityComponent().setOpen(false);
                return clientRequest;
            }
            if (((Message) clientRequest).getInfo().equals("unlock")) {
             //   home.logger.addToLog("Door unlocked");
              //  home.sendToAllClients(home.logger);
                ((Message) clientRequest).getSecurityComponent().setOpen(true);
                return clientRequest;
            }
        }


        if (clientRequest instanceof String) {
//                    if (clientRequest.equals("on")) {
//                        //localServerOos.writeObject(new MagneticSensor());
//                    } else if ("off".equals(clientRequest)) {
//                        //localServerOos.writeObject(new);
//                    } else
            if ("lock".equals(clientRequest)) {
                respone = new Message("", new DoorLock(false));
                home.logger.addToLog("Door locked");
                home.sendToAllClients(home.logger);
            } else if ("unlock".equals(clientRequest)) {
                respone = new Message("", new DoorLock(true));
                home.logger.addToLog("Door unlocked");
                home.sendToAllClients(home.logger);
            } else if ("Take photo".equals(clientRequest)) {
                respone = "Take photo";
                home.logger.addToLog("Client wants a photo");
                home.sendToAllClients(home.logger);
            }
        }
        return respone;
    }
}




