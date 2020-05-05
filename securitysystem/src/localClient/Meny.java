package localClient;


import model.FireAlarm;
import model.MagneticSensor;
import model.ProximitySensor;
import model.SecurityComponent;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Meny extends JFrame {


    JButton btnOpenDoor = new JButton("Lås upp");
    JButton btnCloseDoor = new JButton("Lås");
    JButton btnAlarmOn = new JButton("Larma");
    JButton btnChangeCode = new JButton("Ändra kod");
    JButton btnFingerprint = new JButton("Fingeravläsare");
    JButton btnGoOnline = new JButton("Go online");

    JList tfonlineMK = new JList();
    JList tfofflineMK = new JList();
    DefaultListModel OnlineListModel = new DefaultListModel();
    DefaultListModel OfflineListModel = new DefaultListModel();
    private MainFrame mainFrame;
    private ChangeCode changeCode;
    private FingerprintGui fingerprintGui;



    public Meny (MainFrame mainFrame, ChangeCode changeCode, FingerprintGui fingerprintGui, GoOnline goOnline){
        this.mainFrame = mainFrame;
        this.changeCode = changeCode;
        this.fingerprintGui = fingerprintGui;

        this.setLayout(new MigLayout());
        setSize(600,1000);
        setBackground(new Color(83, 86, 91));
        setVisible(true);
        setLocation(500,200);

        DrawMeny();

    }

    public void DrawMeny(){

        btnOpenDoor.addActionListener(new ButtonListener());
        btnCloseDoor.addActionListener(new ButtonListener());
        btnAlarmOn.addActionListener(new ButtonListener());
        btnChangeCode.addActionListener(new ButtonListener());
        btnFingerprint.addActionListener(new ButtonListener());
        btnGoOnline.addActionListener(new ButtonListener());

        tfofflineMK.setPreferredSize(new Dimension(400,400));
        tfonlineMK.setPreferredSize(new Dimension(400, 400));

        btnOpenDoor.setPreferredSize(new Dimension(90,90));
        btnCloseDoor.setPreferredSize(new Dimension(90,90));
        btnAlarmOn.setPreferredSize(new Dimension(90,90));
        btnChangeCode.setPreferredSize(new Dimension(90,90));
        btnFingerprint.setPreferredSize(new Dimension(90,90));
        btnGoOnline.setPreferredSize(new Dimension(90,90));

        add(tfonlineMK,"span");
        add(tfofflineMK,"wrap, span");
        add(btnOpenDoor);
        add(btnCloseDoor);
        add(btnAlarmOn);
        add(btnChangeCode);
        add(btnFingerprint);
        add(btnGoOnline);

        tfofflineMK.setBorder(BorderFactory.createTitledBorder("Offline"));
        tfonlineMK.setBorder(BorderFactory.createTitledBorder("Online"));


    }

    public void updateOnlineMK(ArrayList<SecurityComponent> sensors) {
        OnlineListModel.clear();
        String status = "";

        for (SecurityComponent s: sensors) {

                if (s instanceof MagneticSensor) {
                    if(s.isOpen()) {
                    status = " Dörren är: Öppen";

                } else status = " Dörren är: Stängd";
            }
           if(s instanceof FireAlarm){
               if(s.isOpen()) {
               status = " Det brinner";
           } else status = "";
           }

           if(s instanceof ProximitySensor){   //TODO TESTA DET HÄR, DEN BLIR ALDRIG BOOLEAN FALSE???
               if(s.isOpen()){
                   status = " Rörelse upptäckt";
               }else status="";
           }
            String onlineMK;
                onlineMK = s.getClass().getSimpleName() + " ID: " + s.getId() + " Location: " + s.getLocation() + status;


            OnlineListModel.addElement(onlineMK);
        }
        tfonlineMK.setModel(OnlineListModel);
        tfonlineMK.repaint();


    }
    public void updateOfflineMK(ArrayList<SecurityComponent> sensor){
        OfflineListModel.clear();


        for (SecurityComponent s: sensor){
            String offlineMK =s.getClass().getSimpleName()+" ID: "+ s.getId()+ " Location: "+s.getLocation();
            OfflineListModel.addElement(offlineMK);
        }
        tfofflineMK.setModel(OfflineListModel);
        tfofflineMK.repaint();
    }




    private class ButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == btnOpenDoor){
                System.out.println("Dörr öppen");
                mainFrame.controller.setDoorOpen(true);
            }
            if (e.getSource() == btnCloseDoor){
                System.out.println("Dörr stängd");
                mainFrame.controller.setDoorOpen(false);
            }
            if(e.getSource() == btnAlarmOn){
                System.out.println("Larmet är på");
                mainFrame.controller.setAlarmOn(true);
            }
            if (e.getSource() == btnChangeCode){
                mainFrame.cc = new ChangeCode(mainFrame);
                //JFrame ChangeCode = new JFrame();
                //ChangeCode.setSize(new Dimension(320, 420));
                //ChangeCode.setTitle("Ändra kod");
                //ChangeCode.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            }
            if(e.getSource() == btnFingerprint){
                fingerprintGui.setVisible(true);
            }
            if(e.getSource() == btnGoOnline)
            {
                mainFrame.controller.connectToGlobal();
            }

        }
    }
}

