package globalServer;

import model.*;

import javax.mail.MessagingException;
import javax.swing.*;
import java.util.ArrayList;

public class RequestHandler {

    private EmailSender emailSender = new EmailSender();
    private Home home;

    public RequestHandler(Home home) {
        this.home = home;
    }

    public void handleServerRequest(Object requestObject, Home home) throws MessagingException {

        if (requestObject instanceof String) {
            home.logger.addToLog((String) requestObject);
            home.sendToAllClients(home.logger);
            home.sendToAllClients(requestObject);

        } else if(requestObject instanceof ImageIcon) {
            home.logger.addToLog("received an image");
            home.sendToAllClients(home.logger);
            home.sendToAllClients(requestObject);
        }else if (requestObject instanceof Message) {
            Message message = (Message) requestObject;
            SecurityComponent securityComponent = message.getSecurityComponent();

            if(securityComponent == null){
               ArrayList<SecurityComponent> online = message.getOnlineSensors();
               ArrayList<SecurityComponent> offline = message.getOfflineSensors();
               boolean alarm = message.isAlarmOn();
                home.sendToAllClients(message);

                for (SecurityComponent s: online
                     ) {
                    System.out.println("ONLINE SENSOR: "+s.getId());

                }
                for (SecurityComponent s: offline
                ) {
                    System.out.println("OFFLINE SENSOR: "+s.getId());

                }

                System.out.println("Alarm status: "+alarm);
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

                }catch (Exception e){

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

    public Message handleClientRequest(String clientRequest) {
        Message messageResponse = new Message();

        switch (clientRequest) {
            case "on":
                //localServerOos.writeObject(new MagneticSensor());
                break;
            case "off":
                //localServerOos.writeObject(new );
                break;
            case "lock":
                messageResponse = new Message("", new DoorLock(false));
                home.logger.addToLog("Door locked");
                home.sendToAllClients(home.logger);
                break;
            case "unlock":
                messageResponse = new Message("", new DoorLock(true));
                home.logger.addToLog("Door unlocked");
                home.sendToAllClients(home.logger);
                break;
        }

        return messageResponse;
    }
}

