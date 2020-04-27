package model;

public class ProximitySensor extends Sensor {

    public ProximitySensor(String id, String location, boolean active) {
        super(id, location, active);
    }

    public ProximitySensor(String id, String location) {
        super(id, location, true);
    }

    public ProximitySensor() {
        super("", "", true);
    }

}
