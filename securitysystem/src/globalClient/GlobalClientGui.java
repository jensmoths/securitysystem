package globalClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GlobalClientGui extends JPanel {

    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel leftPanelNorth;
    private JPanel leftPanelSouth;

    private JButton btnON;
    private JButton btnOFF;
    private JButton btnLock;
    private JButton btnUnlock;

    private JTextArea textArea;
    private GlobalClientController globalClientController;
    private String name;

    private JScrollPane scrollPane;

    public GlobalClientGui(GlobalClientController globalClientController) {
        this.globalClientController = globalClientController;
        draw();
    }

    public void draw() {
        ButtonListener buttonListener = new ButtonListener();
        this.setPreferredSize(new Dimension(600, 600));
        this.setLayout(new BorderLayout());

        Dimension btnDimension = new Dimension(200,20);

        name = "Ammar";
        globalClientController.send(name);

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

        leftPanelNorth.add(btnLock, BorderLayout.SOUTH);
        leftPanelSouth.add(btnUnlock,BorderLayout.SOUTH);

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
    }

    public void setTextArea(String activity) {
        textArea.setText(activity);
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {


            if (e.getSource() == btnON) {
                globalClientController.send("on");
            } else if (e.getSource() == btnOFF) {
                globalClientController.send("off");
            }else if (e.getSource() == btnLock){
                globalClientController.send("lock");
            }else if (e.getSource() == btnUnlock){
                globalClientController.send("unlock");

            }
        }
    }
}
