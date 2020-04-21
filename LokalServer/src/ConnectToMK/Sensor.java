//package ConnectToMK;
//
//import java.io.Serializable;
//
//public class Sensor implements Serializable {
//
//    private String id;
//    private String location;
//    private String type;
//    private boolean isOn;
//    static final long serialVersionUid = 0L;
//
//
//    public Sensor (String id, String location, String type, boolean isOn){
//        this.id = id;
//        this.location = location;
//        this.isOn = isOn;
//        this.type = type;
//    }
//    public Sensor(String id){
//        this.id=id;
//    }
//    public int hashCode(){
//        return String.valueOf(id).hashCode();
//    }
//
//    public synchronized PiServer.ClientHomeHandler get(Sensor id){
//        return null;
//    }
//
//    public String getId(){
//        return id;
//    }
//
//    public String getType(){
//        return type;
//    }
//    public String getLocation(){
//        return location;
//    }
//
//    public boolean equals(Object obj) {
//        {
//            if (obj != null && obj instanceof Sensor) return id.equals(((Sensor) obj).getId());
//            return false;
//        }
//    }
//    public String toString() {
//        return id;
//    }
//
//
//}
