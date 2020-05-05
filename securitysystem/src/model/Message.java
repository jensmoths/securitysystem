package model;

import java.io.Serializable;

public class Message implements Serializable {

    String info;
    SecurityComponent securityComponent;

    public Message (){
        info = "";
    }

    public Message (String info){
        this.info = info;
    }

    public Message(String info, SecurityComponent securityComponent){
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
