package globalClient;

import model.SecurityComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

    private JList testJlist;
    private DefaultListModel testListModel;




    public GlobalClientGui(GlobalClientController globalClientController) {
        this.globalClientController = globalClientController;
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

        leftPanelNorth.add(btnLock, BorderLayout.CENTER);
        leftPanelNorth.add(btnUnlock,BorderLayout.SOUTH); //TODO ÄNDRAT UNLOCK TILL NORTH ISTÄLLET FÖR SOUTHPANEL

       testJlist = new JList();
       testListModel = new DefaultListModel();
        leftPanelSouth.setBackground(Color.pink);
        leftPanelSouth.add(testJlist);
        leftPanelSouth.add(btnON, BorderLayout.SOUTH);
        leftPanelSouth.add(btnOFF, BorderLayout.SOUTH);



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

            }
        }
    }
}
