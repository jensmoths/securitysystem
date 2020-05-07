package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
    boolean alarmOn;
    ArrayList<SecurityComponent> onlineSensors;
    ArrayList<SecurityComponent> offlineSensors;
    String info;
    SecurityComponent securityComponent;

    public Message(boolean alarmOn, ArrayList<SecurityComponent> onlineSensors, ArrayList<SecurityComponent> offlineSensors) {
        this.alarmOn = alarmOn;
        this.onlineSensors = onlineSensors;
        this.offlineSensors = offlineSensors;
    }

    public Message(boolean alarmOn, ArrayList<SecurityComponent> onlineSensors, ArrayList<SecurityComponent> offlineSensors, SecurityComponent securityComponent) {
        this.alarmOn = alarmOn;
        this.onlineSensors = onlineSensors;
        this.offlineSensors = offlineSensors;
        this.securityComponent = securityComponent;
    }

    public Message() {
        info = "";
    }

    public Message(String info) {
        this.info = info;
    }

    public Message(String info, SecurityComponent securityComponent) {
        this.info = info;
        this.securityComponent = securityComponent;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public SecurityComponent getSecurityComponent() {
        return securityComponent;
    }

    @Override
    public String toString() {
        return String.format("The ID is %s, in the %s", securityComponent.getId(), securityComponent.getLocation());
    }
}
