package model;

public class DoorLock extends SecurityComponent {
    private String id, location;


    public DoorLock(String id, String location, boolean open) {
        super(id, location);
        super.setOpen(open);
    }

    public DoorLock(String id, String location) {
        super(id, location);
        this.id = id;
        this.location = location;

    }

    public DoorLock(boolean open) {
        super("", "");
        super.setOpen(open);
    }

    @Override
    public boolean isOpen() {
        return super.isOpen();
    }

    @Override
    public void setOpen(boolean open) {
        super.setOpen(open);
    }

    @Override
    public String toString() {
        if (super.isOpen()) {
            return getClass().getSimpleName() + "   Id: " + id + "   Location: " + location + " Dörren är : Öppen";
        } else return getClass().getSimpleName() + "   Id: " + id + "   Location: " + location + " Dörren är : Stängd";
    }
}
