package model;

public class DoorLock extends SecurityComponent {

    public DoorLock(String id, String location, boolean open) {
        super(id, location);
        super.setOpen(open);
    }

    public DoorLock(String id, String location) {
        super(id, location);
        super.setOpen(false);
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
}
