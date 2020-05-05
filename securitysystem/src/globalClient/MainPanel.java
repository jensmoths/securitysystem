package globalClient;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class MainPanel extends JPanel {

    private JPanel leftPanel;
    private JPanel centerPanel;
    private JPanel rightPanel;
    private JPanel leftPanelNorth;
    private JPanel leftPanelSouth;
    private JPanel centerPanelNorth;
    private JPanel centerPanelSouth;

    private JButton btnON;
    private JButton btnOFF;
    private JButton btnLock;
    private JButton btnUnlock;

    private JTextArea taLogger;
    private JTextArea taOnline;
    private JTextArea taOffline;

    private GlobalClient globalClient;

    private JFrame frame;

    private JScrollPane scrollPaneLogger;
    private JScrollPane scrollPaneOnline;
    private JScrollPane scrollPaneOffline;

    public MainPanel(GlobalClient globalClient) {
        this.globalClient = globalClient;
        frame = new JFrame();
        draw();
    }

    public void draw() {
        ButtonListener buttonListener = new ButtonListener();
        this.setPreferredSize(new Dimension(1200, 630));
        this.setLayout(new BorderLayout());

        Dimension btnDimension = new Dimension(200, 20);

        leftPanel = new JPanel();
        centerPanel = new JPanel();
        rightPanel = new JPanel();
        leftPanelNorth = new JPanel();
        leftPanelSouth = new JPanel();
        centerPanelNorth = new JPanel();
        centerPanelSouth = new JPanel();

        taLogger = new JTextArea();
        taOffline = new JTextArea();
        taOnline = new JTextArea();

        leftPanel.setPreferredSize(new Dimension(280, 600));
        centerPanel.setPreferredSize(new Dimension(430,600));
        rightPanel.setPreferredSize(new Dimension(400, 600));

        leftPanelSouth.setPreferredSize(new Dimension(260, 285));
        leftPanelNorth.setPreferredSize(new Dimension(260, 285));

        centerPanelSouth.setPreferredSize(new Dimension(420, 300));
        centerPanelNorth.setPreferredSize(new Dimension(420, 300));

        taLogger.setEditable(false);
        taOffline.setEditable(false);
        taOnline.setEditable(false);

        scrollPaneLogger = new JScrollPane(taLogger);
        scrollPaneOffline = new JScrollPane(taOffline);
        scrollPaneOnline = new JScrollPane(taOnline);

        scrollPaneLogger.setPreferredSize(new Dimension(380, 580));
        scrollPaneOnline.setPreferredSize(new Dimension(380,260));
        scrollPaneOffline.setPreferredSize(new Dimension(380,260));

        btnLock = new JButton("Lock door");
        btnUnlock = new JButton("Unlock door");

        btnOFF = new JButton("Off");
        btnON = new JButton("On");

        btnUnlock.setPreferredSize(new Dimension(btnDimension));
        btnLock.setPreferredSize(new Dimension(btnDimension));

        btnOFF.setPreferredSize(new Dimension(btnDimension));
        btnON.setPreferredSize(new Dimension(btnDimension));

        centerPanelNorth.add(scrollPaneOnline);
        centerPanelSouth.add(scrollPaneOffline);

        leftPanelSouth.add(btnLock, BorderLayout.SOUTH);
        leftPanelSouth.add(btnUnlock, BorderLayout.SOUTH);

        leftPanelNorth.add(btnOFF, BorderLayout.CENTER);
        leftPanelNorth.add(btnON, BorderLayout.CENTER);

        leftPanel.add(leftPanelNorth, BorderLayout.NORTH);
        leftPanel.add(leftPanelSouth, BorderLayout.SOUTH);

        centerPanel.add(centerPanelNorth, BorderLayout.NORTH);
        centerPanel.add(centerPanelSouth, BorderLayout.SOUTH);

        TitledBorder loggerBorder = new TitledBorder("Logger");
        TitledBorder offlineBorder = new TitledBorder("Offline Components");
        TitledBorder onlineBorder = new TitledBorder("Online Components");
        TitledBorder onOffBorder = new TitledBorder("Turn On/Off");
        TitledBorder doorBorder = new TitledBorder("Door");

        loggerBorder.setTitleColor(new Color(62, 134, 160));
        onlineBorder.setTitleColor(new Color(62, 134, 160));
        offlineBorder.setTitleColor(new Color(62, 134, 160));
        onOffBorder.setTitleColor(new Color(62, 134, 160));
        doorBorder.setTitleColor(new Color(62, 134, 160));

        centerPanelNorth.setBorder(onlineBorder);
        centerPanelSouth.setBorder(offlineBorder);

        leftPanelNorth.setBorder(onOffBorder);
        leftPanelSouth.setBorder(doorBorder);

        rightPanel.setBorder(loggerBorder);

        rightPanel.add(scrollPaneLogger);

        leftPanel.setBackground(new Color(60, 63, 65));
        leftPanelSouth.setBackground(new Color(60, 63, 65));
        leftPanelNorth.setBackground(new Color(60, 63, 65));

        centerPanelSouth.setBackground(new Color(60, 63, 65));
        centerPanelNorth.setBackground(new Color(60, 63, 65));
        centerPanel.setBackground(new Color(60, 63, 65));

        taOnline.setBackground(new Color(43, 43, 43));
        taOffline.setBackground(new Color(43, 43, 43));
        taLogger.setBackground(new Color(43, 43, 43));

        rightPanel.setBackground(new Color(60, 63, 65));

        btnOFF.setBackground(new Color(43, 43, 43));
        btnON.setBackground(new Color(43, 43, 43));

        btnLock.setBackground(new Color(43, 43, 43));
        btnUnlock.setBackground(new Color(43, 43, 43));

        btnON.setForeground(Color.white);
        btnOFF.setForeground(Color.white);
        btnUnlock.setForeground(Color.white);
        btnLock.setForeground(Color.white);
        
        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        btnOFF.addActionListener(buttonListener);
        btnON.addActionListener(buttonListener);
        btnLock.addActionListener(buttonListener);
        btnUnlock.addActionListener(buttonListener);

        frame.setSize(new Dimension(1250, 650));
        frame.setContentPane(MainPanel.this);
        frame.setBackground(new Color(83,86,91));
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
    // TODO: 30-Apr-20 add this code to GUI for microcontroller gui
//    public void setTestJlist(ArrayList<SecurityComponent> rey) {
//        testListModel.clear();
//
//        for (SecurityComponent s: rey
//        ) {
//            String hej = s.getClass().getSimpleName()+" ID: "+ s.getId()+ " Location: "+s.getLocation();
//            testListModel.addElement(hej);
//        }
//        testJlist.setModel(testListModel);
//        testJlist.repaint();
//
//
//    }


    public void setTaLogger(String text) {
        taLogger.setText(text);
    }


    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == btnON) {
                globalClient.send("on");
            } else if (e.getSource() == btnOFF) {
                globalClient.send("off");
            } else if (e.getSource() == btnLock) {
                globalClient.send("lock");
            } else if (e.getSource() == btnUnlock) {
                globalClient.send("unlock");

            }
        }
    }
}
