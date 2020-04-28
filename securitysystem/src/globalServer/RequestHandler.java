package globalServer;

import globalClient.GlobalClientGui;
import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class RequestHandler {

    public void handleServerRequest(Object requestObject, Clients clients, String name) {
        try {

            if (requestObject instanceof String) {
                clients.get(name).getOos().writeObject(requestObject);
            } else if (requestObject instanceof Message) {
                Message message = (Message) requestObject;
                SecurityComponent securityComponent = message.getSecurityComponent();

                if (securityComponent instanceof MagneticSensor) {
                    System.out.println("You are in magnet sensor");
                    System.out.println(securityComponent.isOpen());

                    if (securityComponent.isOpen()) {
                        clients.get(name).getOos().writeObject("Magnetsensorn larmar");

                    } else if (!securityComponent.isOpen()) {
                        clients.get(name).getOos().writeObject("Magnetsensorn är aktiv");
                    }
                }
                if (securityComponent instanceof FireAlarm) {
                    System.out.println("You are in Firealarm");
                    //System.out.println(securityComponent.isOpen());
                    clients.get(name).getOos().writeObject("Brandlarmet har upptäckt rök i byggnaden");
                }
                if (securityComponent instanceof ProximitySensor) {
                    System.out.println("You are in Proximity Sensor");
                    //System.out.println(securityComponent.isOpen());

                    clients.get(name).getOos().writeObject("Rörelsedetektorn har upptäckt rörelse i byggnaden");
                }
            }
            if(requestObject instanceof ArrayList){   //TODO ÄNDRAT HÄR
                ArrayList<SecurityComponent> onlineMK = (ArrayList<SecurityComponent>) requestObject;
                clients.get(name).getOos().writeObject(onlineMK);





            }
        } catch (IOException e) {
            e.printStackTrace();
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

