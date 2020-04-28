package localserver;

import model.*;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;

public class PiServer extends Thread implements Serializable {
    private HashMap<SecurityComponent, ClientHandler> map = new HashMap<>();
    private HashMap<String, SecurityComponent> globalMap = new HashMap<>();
    ArrayList<SecurityComponent> firesensors = new ArrayList<SecurityComponent>();
    ArrayList<SecurityComponent> magnetSensors = new ArrayList<SecurityComponent>();
    ArrayList<SecurityComponent> proximitySensors = new ArrayList<SecurityComponent>();
    ArrayList<SecurityComponent> doorSensors = new ArrayList<SecurityComponent>();
    ArrayList<SecurityComponent> allOnlineSensors = new ArrayList<>();
    GlobalServer globalServer;


    PiServer() throws IOException, InterruptedException {
        new StartServer(Integer.parseInt(JOptionPane.showInputDialog(null, "Välj port"))).start();
       // globalServer = new GlobalServer();
       // new Thread(globalServer).start();

    }


    class StartServer extends Thread {

        private int port;
        SecurityComponent sensor;
        private String location;

        StartServer(int port) {
            this.port = port;

        }

        @Override
        public void run() {
            try {
                Socket socket;
                ServerSocket ss = new ServerSocket(port);

                while (true) {
                    socket = ss.accept();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    String who = bufferedReader.readLine();
                    //   bufferedWriter.write(ConsoleColors.RED+"VÄLKOMNMEN TILL SERVERN");
                    //  bufferedWriter.flush();
                    System.out.println("CON " + "ID: " + who);

                    String[] split = who.split("\\|");
                    String id = split[0];
                    String type = split[1];
                    if (split.length > 2) {
                        location = split[2];
                    } else location = "default";

                    switch (type) {
                        case "firealarm":
                            sensor = new FireAlarm(id, location);
                            firesensors.add(sensor);


                            break;
                        case "magnet":
                            sensor = new MagneticSensor(id, location);
                            magnetSensors.add(sensor);

                            break;

                        case "door":
                            sensor = new DoorLock(id, location);
                            doorSensors.add(sensor);


                            break;
                        case "proximity":
                            sensor = new ProximitySensor(id, location);
                            proximitySensors.add(sensor);


                    }
                    allOnlineSensors.add(sensor); //TODO NYTT KOPPLA FRÅN SENSOR


                    System.out.println("IP :" + socket.getInetAddress() + " Sensortype: " + sensor.getClass().getSimpleName() + " Location: " + sensor.getLocation() + " SensorID: " + sensor.getId());

                    if (!map.containsKey(sensor)) {
                        ClientHandler ch = new ClientHandler(socket, sensor);
                        map.putIfAbsent(sensor, ch);
                    } else {
                        String oldLocation = map.get(sensor).sensor.getLocation();
                        System.out.println(oldLocation);
                        sensor.setLocation(oldLocation);                                                                        // RAD 90
                        map.get(sensor).interrupt();
                        ClientHandler ch = new ClientHandler(socket, sensor);
                        map.replace(sensor, ch);
                        System.out.println("EN RECONNECT HAR SKETT SORTA: " + "FÅR DEN SIN GAMLA LOCATION: " + sensor.getLocation()); //BORDE INTE GETLOCATION VARA BASERA PÅ RAD 90??__??
                    }
                    globalMap.put(sensor.getId(), sensor); //TODO NYTT
                    globalServer.UpdateGlobal(globalMap);
                    System.out.println("MAPSIZE: " +map.size());
                    System.out.println("GLOBALMAPSIZE: "+ globalMap.size());


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class ClientHandler extends Thread {
        private BufferedReader bufferedReader;
        private BufferedWriter bufferedWriter;
        private Socket socket;
        private SecurityComponent sensor;
        private long lastRead;
        private int HeartbeatIntervall = 5;

        ClientHandler(Socket socket, SecurityComponent securityComponent) throws IOException {
            this.socket = socket;
            this.sensor = securityComponent;
            socket.setTcpNoDelay(true);

            socket.setSoTimeout(2000);

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            start();
        }

        @Override
        public void run() {
            while (!interrupted()) {
                try {
                    String stringMessage = bufferedReader.readLine();
                    if (stringMessage == null) continue;
                    lastRead = System.currentTimeMillis();
                    if (stringMessage == "hearthbeat") continue;

                    String[] split = stringMessage.split("\\|");
                    String state = split[0]; //, location = split[1], type = split[2];
                    boolean booleanState = state.equals("on");
                    sensor.setOpen(booleanState);
                    Message message = new Message("", sensor);


                    if (message.getSecurityComponent() instanceof MagneticSensor) {
                        for (SecurityComponent s : map.keySet()) {
                            if (s instanceof MagneticSensor) {
                                if (message.getSecurityComponent().isOpen()) {
                                    map.get(s).sendMessage('o');
                                    globalServer.GlobalsendMessage(message);

                                } else {
                                    map.get(s).sendMessage('c');
                                    globalServer.GlobalsendMessage(message);
                                }
                            }
                        }
                    }
                    if (message.getSecurityComponent() instanceof ProximitySensor) {
                        for (SecurityComponent s : map.keySet()) {
                            if (s instanceof MagneticSensor) {
                                if (message.getSecurityComponent().isOpen()) {
                                    map.get(s).sendMessage('o');
                                    globalServer.GlobalsendMessage(message);

                                } else {
                                    map.get(s).sendMessage('c');
                                    globalServer.GlobalsendMessage(message);
                                }
                            }
                        }
                    }
                    if (message.getSecurityComponent() instanceof FireAlarm) {
                        globalServer.GlobalsendMessage(message);
                        for (SecurityComponent s : map.keySet()) {
                            if (s instanceof MagneticSensor) {
                                map.get(s).sendMessage('c');
                            }
                        }
                    }

                    System.out.println("CH " + "IP: " + socket.getInetAddress() + " ID: " + sensor.getId() + " TYPE: " + sensor.getClass().getSimpleName() + " " + message.getSecurityComponent().isOpen());


                } catch (SocketTimeoutException e) {
                    if ((HeartbeatIntervall > 0) && ((System.currentTimeMillis() - lastRead) > HeartbeatIntervall)) {
                        e.printStackTrace();
                        allOnlineSensors.remove(sensor);//TODO NYTT KOPPLA FRÅN SENSOR
                        globalMap.remove(sensor.getId());
                        System.out.println(sensor.getClass().getSimpleName()+" "+ socket.getInetAddress()+ " Har kopplats bort via HEARTHBEAT YEYEYEYE");
                       //UPDATE ONLINE METOD. SKICKA INFO TILL GLOBAL SERVER ATT EN MK HAR DISCONNECTAT
                        try {
                            globalServer.UpdateGlobal(globalMap);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    }


                } catch (IOException e) {
                }
            }
        }
        public void sendMessage(char msg) throws IOException {
            bufferedWriter.write(msg);
            bufferedWriter.flush();
            System.out.println("DET HÄR: " + msg + " HAR SKICKATS TILL MIKROKONTROLLER");


        }
    }


    class GlobalServer implements Runnable {
        Socket socket;
        ObjectOutputStream oos;
        ObjectInputStream ois;

        public void connect(String ip, int port) throws IOException {
            String clientType = "server";
            socket = new Socket(ip, port);
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());

            oos.writeObject(clientType);
            oos.writeObject("Ammar");
            oos.writeObject(allOnlineSensors); //TODO NYTT KOPPLA FRÅN SENSOR

            oos.flush();
        }


        public void GlobalsendMessage(Message msg) throws IOException {
            oos = new ObjectOutputStream(socket.getOutputStream());  //FUNGERADE INTE ATT LÄSA OBJEKTETS BOOLEAN OM VI INTE GJORDE NYA STREAMS VARJE GÅNG VI SKICKADE
            oos.writeObject(msg);
            oos.flush();

            System.out.println("sent message " + msg.getInfo() + "to GlobalServer: " + socket.toString());
        }
        public void UpdateGlobal (HashMap msg) throws IOException { //TODO EJ TESTAD METOD
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(msg);
            oos.flush();
        }

        public void ShutdownSensor(Message msg) throws IOException {  //TODO EJ TESTAD METOD

          //  map.get(msg.getSecurityComponent()).socket.close();
            map.get(msg.getSecurityComponent()).sendMessage('k');
        }


        @Override
        public void run() {
            try {
                connect("109.228.172.110", 8081);
                System.out.println("connected to server");
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (true) {

                try {
                    Message msg = (Message) ois.readObject();

                    if (msg.getInfo() == "shutdown") {
                        ShutdownSensor(msg);
                    }

                    if (msg.getSecurityComponent() instanceof DoorLock) {
                        for (SecurityComponent s : map.keySet()) {
                            if (s instanceof MagneticSensor) {
                                if (msg.getSecurityComponent().isOpen()) {
                                    map.get(s).sendMessage('o');

                                } else map.get(s).sendMessage('c');


                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }


            }
        }
    }
}

