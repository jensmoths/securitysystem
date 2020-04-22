package model;

public class MagneticSensor extends Sensor {

    public MagneticSensor(String id, String location, boolean active) {
        super(id, location, active);
    }
    public MagneticSensor(String id, String location) {
        super(id, location);
    }
    public MagneticSensor() {
        super("", "", false);
    }
    public MagneticSensor(boolean open) {
        super("", "", false);
        super.setOpen(open);
    }

    public boolean isOpen() {
        return super.isOpen();
    }

    @Override
    public void setOpen(boolean open) {
        super.setOpen(open);
    }
}
