package globalServerGUI;

import globalServer.GlobalServer;
import globalServer.GlobalServerController;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel {
    private RegisterPanel registerPanel;
    private JPanel pnlLeft;
    private JPanel pnlRight;
    private JButton btnRegister;
    private JButton btnDelete;
    private JScrollPane scrollPane;
    private JTextArea textArea;
    private JList tblInfo;
    private ButtonListener buttonListener;
    private GlobalServerController globalServerController;

    public MainPanel(GlobalServerController globalServerController) {
        this.globalServerController = globalServerController;
        draw();
    }

    public void draw() {
        buttonListener = new ButtonListener();

        pnlLeft = new JPanel();
        pnlRight = new JPanel();

        btnRegister = new JButton();
        btnDelete = new JButton();

        textArea = new JTextArea();
        tblInfo = new JList();

        this.setBackground(new Color(83, 86, 91));
        pnlLeft.setBackground(new Color(83, 86, 91));
        pnlRight.setBackground(new Color(83, 86, 91));

        textArea.setBackground(new Color(83, 86, 91));
        tblInfo.setBackground(new Color(83, 86, 91));

        this.setLayout(new BorderLayout());
        pnlLeft.setLayout(new FlowLayout());
        pnlRight.setLayout(new FlowLayout());

        this.setPreferredSize(new Dimension(810, 810));
        pnlLeft.setPreferredSize(new Dimension(400, 400));
        pnlRight.setPreferredSize(new Dimension(400, 400));
        textArea.setSize(new Dimension(380, 380));

        btnDelete.setText("Delete");
        btnRegister.setText("Register");

        Border borderContact = BorderFactory.createTitledBorder("Contact Information");
        Border borderLogger = BorderFactory.createTitledBorder("Logger");

        textArea.setEditable(false);

        pnlLeft.setBorder(borderContact);
        pnlRight.setBorder(borderLogger);

        tblInfo.setPreferredSize(new Dimension(380, 370));
        btnRegister.setPreferredSize(new Dimension(100, 40));
        btnDelete.setPreferredSize(new Dimension(100, 40));

        textArea.setPreferredSize(new Dimension(380, 400));

        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        pnlLeft.add(tblInfo);
        pnlLeft.add(btnRegister);
        pnlLeft.add(btnDelete);

        pnlRight.add(scrollPane);

        add(pnlLeft, BorderLayout.WEST);
        add(pnlRight, BorderLayout.EAST);

        btnRegister.addActionListener(buttonListener);
        btnDelete.addActionListener(buttonListener);

    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource() == btnRegister) {
                registerPanel = new RegisterPanel(globalServerController);
            } else if (actionEvent.getSource() == btnDelete) {
                System.out.println("Delete this mf");
            }
        }
    }

    public RegisterPanel getRegisterPanel() {
        return registerPanel;
    }
/*
    public static void main(String[] args) {
        new MainFrame();
    }

 */
}

