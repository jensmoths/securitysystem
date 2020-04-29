package globalServer;

import model.*;

import java.io.IOException;

public class RequestHandler {

    public void handleServerRequest(Object requestObject, Home home) {

        if (requestObject instanceof String) {
            home.sendToAllClients(requestObject);
        } else if (requestObject instanceof Message) {
            Message message = (Message) requestObject;
            SecurityComponent securityComponent = message.getSecurityComponent();

            if (securityComponent instanceof MagneticSensor) {
                System.out.println("You are in magnet sensor");
                System.out.println(securityComponent.isOpen());

                if (securityComponent.isOpen()) {
                    home.sendToAllClients("Magnetsensorn larmar");

                } else if (!securityComponent.isOpen()) {
                    home.sendToAllClients("Magnetsensorn är aktiv");
                }
            }
            if (securityComponent instanceof FireAlarm) {
                System.out.println("You are in Firealarm");
                home.sendToAllClients("Brandlarmet har upptäckt rök i byggnaden");
            }
            if (securityComponent instanceof ProximitySensor) {
                System.out.println("You are in Proximity Sensor");
                home.sendToAllClients("Rörelsedetektorn har upptäckt rörelse i byggnaden");
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
                break;
            case "unlock":
                messageResponse = new Message("", new DoorLock(true));
                break;
        }

        return messageResponse;
    }
}

