package globalClient;

import model.SecurityComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
<<<<<<< HEAD:securitysystem/src/globalClient/GlobalClientGui.java
import java.util.ArrayList;
=======
import java.awt.event.WindowEvent;
>>>>>>> origin/Malek4:securitysystem/src/globalClient/MainPanel.java

public class MainPanel extends JPanel {

    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel leftPanelNorth;
    private JPanel leftPanelSouth;

    private JButton btnON;
    private JButton btnOFF;
    private JButton btnLock;
    private JButton btnUnlock;

    private JTextArea textArea;
    private GlobalClient globalClient;
    private JFrame frame;
    private JScrollPane scrollPane;

<<<<<<< HEAD:securitysystem/src/globalClient/GlobalClientGui.java
    private JList testJlist;
    private DefaultListModel testListModel;




    public GlobalClientGui(GlobalClientController globalClientController) {
        this.globalClientController = globalClientController;
=======
    public MainPanel(GlobalClient globalClient) {
        this.globalClient = globalClient;
        frame = new JFrame();
>>>>>>> origin/Malek4:securitysystem/src/globalClient/MainPanel.java
        draw();

    }
    public void setTestJlist(ArrayList<SecurityComponent> rey) {
        testListModel.clear();

        for (SecurityComponent s: rey
             ) {
            String hej =s.getClass().getSimpleName()+" ID: "+ s.getId()+ " Location: "+s.getLocation();
            testListModel.addElement(hej);
        }
        testJlist.setModel(testListModel);
       testJlist.repaint();


    }


    public void draw() {

        ButtonListener buttonListener = new ButtonListener();
        this.setPreferredSize(new Dimension(600, 600));
        this.setLayout(new BorderLayout());

        Dimension btnDimension = new Dimension(200, 20);

        leftPanel = new JPanel();
        rightPanel = new JPanel();
        leftPanelNorth = new JPanel();
        leftPanelSouth = new JPanel();
        textArea = new JTextArea();

        leftPanel.setPreferredSize(new Dimension(280, 580));
        rightPanel.setPreferredSize(new Dimension(280, 580));

        leftPanelSouth.setPreferredSize(new Dimension(260, 285));
        leftPanelNorth.setPreferredSize(new Dimension(260, 285));

        textArea.setEditable(false);
        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(270, 580));

        btnLock = new JButton("Lock door");
        btnUnlock = new JButton("Unlock door");

        btnOFF = new JButton("Off");
        btnON = new JButton("On");

        btnUnlock.setPreferredSize(new Dimension(btnDimension));
        btnLock.setPreferredSize(new Dimension(btnDimension));

        btnOFF.setPreferredSize(new Dimension(btnDimension));
        btnON.setPreferredSize(new Dimension(btnDimension));

<<<<<<< HEAD:securitysystem/src/globalClient/GlobalClientGui.java
        leftPanelNorth.add(btnLock, BorderLayout.CENTER);
        leftPanelNorth.add(btnUnlock,BorderLayout.SOUTH); //TODO ÄNDRAT UNLOCK TILL NORTH ISTÄLLET FÖR SOUTHPANEL

       testJlist = new JList();
       testListModel = new DefaultListModel();
        leftPanelSouth.setBackground(Color.pink);
        leftPanelSouth.add(testJlist);
        leftPanelSouth.add(btnON, BorderLayout.SOUTH);
        leftPanelSouth.add(btnOFF, BorderLayout.SOUTH);


=======
        leftPanelNorth.add(btnLock, BorderLayout.SOUTH);
        leftPanelSouth.add(btnUnlock, BorderLayout.SOUTH);
>>>>>>> origin/Malek4:securitysystem/src/globalClient/MainPanel.java

        //leftPanelNorth.add(btnOFF, BorderLayout.CENTER);
        //leftPanelSouth.add(btnON, BorderLayout.CENTER);

        leftPanel.add(leftPanelNorth, BorderLayout.NORTH);
        leftPanel.add(leftPanelSouth, BorderLayout.SOUTH);

        rightPanel.add(scrollPane);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);

        btnOFF.addActionListener(buttonListener);
        btnON.addActionListener(buttonListener);
        btnLock.addActionListener(buttonListener);
        btnUnlock.addActionListener(buttonListener);

        frame.setSize(new Dimension(610, 650));
        frame.setContentPane(MainPanel.this);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                globalClient.closeSocket();
                //System.exit(0);
            }
        });
    }

    public void setTextArea(String activity) {
        textArea.setText(activity);
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {


            if (e.getSource() == btnON) {
<<<<<<< HEAD:securitysystem/src/globalClient/GlobalClientGui.java
                testJlist.getSelectedIndex();
                globalClientController.send("on");
                leftPanelSouth.setBackground(Color.CYAN);
            } else if (e.getSource() == btnOFF) {
                testJlist.getSelectedIndex();
                globalClientController.send("off");
                leftPanelSouth.setBackground(Color.YELLOW);
            }else if (e.getSource() == btnLock){
                globalClientController.send("lock");
            }else if (e.getSource() == btnUnlock){
                globalClientController.send("unlock");
=======
                globalClient.send("on");
            } else if (e.getSource() == btnOFF) {
                globalClient.send("off");
            } else if (e.getSource() == btnLock) {
                globalClient.send("lock");
            } else if (e.getSource() == btnUnlock) {
                globalClient.send("unlock");
>>>>>>> origin/Malek4:securitysystem/src/globalClient/MainPanel.java

            }
        }
    }
}
