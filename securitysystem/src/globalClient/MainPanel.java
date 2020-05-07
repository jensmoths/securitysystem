package globalClient;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainPanel extends JPanel {

    private JPanel leftPanel;
    private JPanel centerPanel;
    private JPanel rightPanel;
    private JPanel leftPanelNorth;
    private JPanel leftPanelSouth;
    private JPanel centerPanelNorth;
    private JPanel centerPanelSouth;
    private JPanel pnlDateTime = new JPanel();

    private JButton btnON;
    private JButton btnOFF;
    private JButton btnLock;
    private JButton btnUnlock;

    private JTextArea taLogger;
    private JTextArea taOnline;
    private JTextArea taOffline;

    private GlobalClientController globalClientController;

    private JFrame frame;

    private JScrollPane scrollPaneLogger;
    private JScrollPane scrollPaneOnline;
    private JScrollPane scrollPaneOffline;

    private JButton btnGetLog;
    private JTextField tfStartDate;
    private JTextField tfEndDate;
    private JTextField tfStartTime;
    private JTextField tfEndTime;
    private ButtonListener buttonListener = new ButtonListener();

    public MainPanel(GlobalClientController globalClientController) {
        this.globalClientController = globalClientController;
        frame = new JFrame();
        draw();
    }

    public void drawPnlDateTime() {
        Dimension tfDimension = new Dimension(125, 35);
        btnGetLog = new JButton("Filter");
        btnGetLog.addActionListener(buttonListener);
        pnlDateTime.setPreferredSize(new Dimension(600, 45));
        tfStartDate = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
        tfStartTime = new JTextField(new SimpleDateFormat("HH-mm").format(Calendar.getInstance().getTime()));
        tfEndDate = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
        tfEndTime = new JTextField(new SimpleDateFormat("HH-mm").format(Calendar.getInstance().getTime()));

        tfStartDate.setPreferredSize(tfDimension);
        tfStartTime.setPreferredSize(tfDimension);
        tfEndDate.setPreferredSize(tfDimension);
        tfEndTime.setPreferredSize(tfDimension);
        btnGetLog.setPreferredSize(new Dimension(70, 35));

        tfStartDate.setBorder(BorderFactory.createTitledBorder("From: yyyy-mm-dd"));
        tfStartTime.setBorder(BorderFactory.createTitledBorder("From: HH-mm"));
        tfEndDate.setBorder(BorderFactory.createTitledBorder("To: yyyy-mm-dd"));
        tfEndTime.setBorder(BorderFactory.createTitledBorder("To: HH-mm"));

        pnlDateTime.add(tfStartDate);
        pnlDateTime.add(tfStartTime);
        pnlDateTime.add(tfEndDate);
        pnlDateTime.add(tfEndTime);
        pnlDateTime.add(btnGetLog);

        rightPanel.add(BorderLayout.CENTER, pnlDateTime);
    }

    public void draw() {

        this.setPreferredSize(new Dimension(1435, 630));
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(60, 63, 65));
        LineBorder border = new LineBorder(new Color(184, 207, 229));
        this.setBorder(border);

        Dimension btnDimension = new Dimension(200, 20);

        leftPanel = new JPanel();
        centerPanel = new JPanel();
        rightPanel = new JPanel();
        leftPanelNorth = new JPanel();
        leftPanelSouth = new JPanel();
        centerPanelNorth = new JPanel();
        centerPanelSouth = new JPanel();
        drawPnlDateTime();
        taLogger = new JTextArea();
        taOffline = new JTextArea();
        taOnline = new JTextArea();

        taOnline.setForeground(Color.white);
        taOffline.setForeground(Color.white);
        taLogger.setForeground(Color.white);

        leftPanel.setPreferredSize(new Dimension(280, 600));
        centerPanel.setPreferredSize(new Dimension(430, 600));
        rightPanel.setPreferredSize(new Dimension(630, 600));

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

        scrollPaneLogger.setPreferredSize(new Dimension(600, 530));
        scrollPaneOnline.setPreferredSize(new Dimension(380, 260));
        scrollPaneOffline.setPreferredSize(new Dimension(380, 260));

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

        frame.setSize(new Dimension(1500, 650));
        frame.setContentPane(MainPanel.this);
        frame.setBackground(new Color(83, 86, 91));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                globalClientController.closeSocket();
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
    public String[] getStartDate() {
        String temp = tfStartDate.getText();
        if (temp.matches("\\d{4}-\\d{2}-\\d{2}")) {
            String[] startDate = tfStartDate.getText().split("-");
            return startDate;
        } else {
            JOptionPane.showMessageDialog(null, "Use the correct format!");
            return null;
        }
    }

    public String[] getStartTime() {
        String temp = tfStartTime.getText();
        if (temp.matches("\\d{2}-\\d{2}")) {
            String[] startTime = tfStartTime.getText().split("-");
            return startTime;
        } else {
            JOptionPane.showMessageDialog(null, "Use the correct format!");
            return null;
        }
//        String[] startTime = tfStartTime.getText().split("-");
//        return startTime;
    }

    public String[] getEndDate() {
        String temp = tfEndDate.getText();
        if (temp.matches("\\d{4}-\\d{2}-\\d{2}")) {
            String[] endDate = tfEndDate.getText().split("-");
            return endDate;
        } else {
            JOptionPane.showMessageDialog(null, "Use the correct format!");
            return null;
        }
    }

    public String[] getEndTime() {
        String temp = tfEndTime.getText();
        if (temp.matches("\\d{2}-\\d{2}")) {
            String[] endTime = tfEndTime.getText().split("-");
            return endTime;
        } else {
            JOptionPane.showMessageDialog(null, "Use the correct format!");
            return null;
        }
    }
    public void showImage(ImageIcon imageIcon) {
        JOptionPane.showMessageDialog(null, imageIcon);

    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == btnON) {
                globalClientController.send("on");
            } else if (e.getSource() == btnOFF) {
                globalClientController.send("off");
            } else if (e.getSource() == btnLock) {
                globalClientController.send("lock");
            } else if (e.getSource() == btnUnlock) {
                globalClientController.send("unlock");

            }else if (e.getSource() == btnGetLog) {

                setTaLogger(globalClientController.getClientLoggerText());
            }
        }
    }
}
