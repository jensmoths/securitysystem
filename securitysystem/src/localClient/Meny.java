package localClient;


import net.miginfocom.swing.MigLayout;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Meny extends JFrame {

    JButton btnOpenDoor = new JButton("Lås upp");
    JButton btnCloseDoor = new JButton("Lås");
    JButton btnAlarmOn = new JButton("Larma");
    JButton btnChangeCode = new JButton("Ändra kod");

    JList tfonlineMK = new JList();
    JList tfofflineMK = new JList();
    private VirtualNumPad virtualNumPad;
    private ChangeCode changeCode;


    public Meny (VirtualNumPad virtualNumPad, ChangeCode changeCode){
        this.virtualNumPad = virtualNumPad;
        this.changeCode = changeCode;

        this.setLayout(new MigLayout());
        setSize(400,600);
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

        tfofflineMK.setPreferredSize(new Dimension(400,400));
        tfonlineMK.setPreferredSize(new Dimension(400, 400));

        btnOpenDoor.setPreferredSize(new Dimension(90,90));
        btnCloseDoor.setPreferredSize(new Dimension(90,90));
        btnAlarmOn.setPreferredSize(new Dimension(90,90));
        btnChangeCode.setPreferredSize(new Dimension(90,90));

        add(tfonlineMK,"span");
        add(tfofflineMK,"wrap, span");
        add(btnOpenDoor);
        add(btnCloseDoor);
        add(btnAlarmOn);
        add(btnChangeCode);

        tfofflineMK.setBorder(BorderFactory.createTitledBorder("Offline"));
        tfonlineMK.setBorder(BorderFactory.createTitledBorder("Online"));


    }


    private class ButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == btnOpenDoor){
                System.out.println("Dörr öppen");
            }
            if (e.getSource() == btnCloseDoor){
                System.out.println("Dörr stängd");
            }
            if(e.getSource() == btnAlarmOn){
                System.out.println("Larmet är på");
            }
            if (e.getSource() == btnChangeCode){
                //JFrame ChangeCode = new JFrame();
                //ChangeCode.setSize(new Dimension(320, 420));
                changeCode.setVisible(true);
                //ChangeCode.setTitle("Ändra kod");
                //ChangeCode.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            }

        }
    }

}
