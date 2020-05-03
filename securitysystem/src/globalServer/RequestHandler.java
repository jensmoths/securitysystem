package globalServer;

import model.*;

import java.io.IOException;

public class RequestHandler {

    private EmailSender emailSender = new EmailSender();
    private Home home;

    public RequestHandler(Home home) {
        this.home = home;
    }

    public void handleServerRequest(Object requestObject, Home home) {

        if (requestObject instanceof String) {
            home.logger.addToLogger((String) requestObject);
            home.sendToAllClients(home.logger);
            home.sendToAllClients(requestObject);
        } else if (requestObject instanceof Message) {
            Message message = (Message) requestObject;
            SecurityComponent securityComponent = message.getSecurityComponent();

            if (securityComponent instanceof MagneticSensor) {

                if (securityComponent.isOpen()) {
                    home.sendToAllClients("Magnetsensorn larmar");
                    home.logger.addToLogger("Magnetsensorn larmar");
                    home.sendToAllClients(home.logger);

                } else if (!securityComponent.isOpen()) {
                    home.sendToAllClients("Magnetsensorn är aktiv");
                    home.logger.addToLogger("Magnetsensorn är aktiv");
                    home.sendToAllClients(home.logger);

                }
            }
            if (securityComponent instanceof FireAlarm) {
                home.sendToAllClients("Brandlarmet har upptäckt rök i byggnaden");
                home.logger.addToLogger("Brandlarmet har upptäckt rök i byggnaden");
                home.sendToAllClients(home.logger);

            }

            if (securityComponent instanceof ProximitySensor) {
                home.sendToAllClients("Rörelsedetektorn har upptäckt rörelse i byggnaden");
                home.logger.addToLogger("Rörelsedetektorn har upptäckt rörelse i byggnaden");
                home.sendToAllClients(home.logger);

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
                home.logger.addToLogger("Door locked");
                home.sendToAllClients(home.logger);
                break;
            case "unlock":
                messageResponse = new Message("", new DoorLock(true));
                home.logger.addToLogger("Door unlocked");
                home.sendToAllClients(home.logger);
                break;
        }

        return messageResponse;
    }
}

