package model;

import java.io.Serializable;

public class SecurityComponent implements Serializable {
    private String id;
    private String location;
    private boolean open;

    public SecurityComponent(String id, String location) {
        this.id = id;
        this.location = location;
    }

    public SecurityComponent() {
        id = "";
        location = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean equals(Object obj) {
        if (obj != null) return id.equals(((SecurityComponent) obj).getId());
        return false;
    }

    public int hashCode() {
        return String.valueOf(id).hashCode();
    }

    public String toString() {
        return id;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
