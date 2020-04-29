package globalServerGUI;

import globalServer.GlobalServerController;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel {
    private RegisterPanel registerPanel;
    private JPanel pnlLeft;
    private JPanel pnlRight;
    private JButton btnRegister;
    private JButton btnDelete;
    private JButton btnUpdate;
    private JScrollPane loggerScrollPane;
    private JTextArea textArea;
    private JTable tblInfo;
    private ButtonListener buttonListener;
    private GlobalServerController globalServerController;
    private DefaultTableModel model;
    private String[] columns;
    private JScrollPane tblScrollPane;

    public MainPanel(GlobalServerController globalServerController) {
        this.globalServerController = globalServerController;
        draw();
    }

    public void draw() {
        buttonListener = new ButtonListener();

        model  = new DefaultTableModel();
        pnlLeft = new JPanel();
        pnlRight = new JPanel();

        btnRegister = new JButton();
        btnDelete = new JButton();
        btnUpdate = new JButton();

        textArea = new JTextArea();
        tblInfo = new JTable();

        loggerScrollPane = new JScrollPane(textArea);
        tblScrollPane = new JScrollPane(tblInfo);

        this.setBackground(new Color(83,86,91));
        pnlLeft.setBackground(new Color(83,86,91));
        pnlRight.setBackground(new Color(83,86,91));

        textArea.setBackground(new Color(83,86,91));
        tblInfo.setBackground(new Color(83,86,91));

        this.setLayout(new BorderLayout());
        pnlLeft.setLayout(new FlowLayout());
        pnlRight.setLayout(new FlowLayout());

        this.setPreferredSize(new Dimension(1250,600));
        pnlLeft.setPreferredSize(new Dimension(800,580));
        pnlRight.setPreferredSize(new Dimension(450,580));
        tblScrollPane.setPreferredSize(new Dimension(750,370));
        loggerScrollPane.setPreferredSize(new Dimension(420,370));

        btnDelete.setText("Delete");
        btnRegister.setText("Register");

        TitledBorder borderContact = new TitledBorder("User Information");
        borderContact.setTitleColor(new Color(255, 123,0, 231));

        TitledBorder borderLogger = new TitledBorder("Logger");
        borderLogger.setTitleColor(new Color(255, 123,0, 231));

        textArea.setEditable(false);

        pnlLeft.setBorder(borderContact);
        pnlRight.setBorder(borderLogger);

        columns = new String[]{"First Name", "Last Name", "Address", "Zip Code", "City", "Username", "Password"};
        tblInfo.setForeground(Color.WHITE);


        model.setColumnIdentifiers(columns);

        tblInfo.setRowHeight(30);
        tblInfo.setModel(model);
        tblInfo.setBackground(new Color(83,86,91));


        btnRegister.setPreferredSize(new Dimension(100,40));
        btnDelete.setPreferredSize(new Dimension(100, 40));

        textArea.setForeground(Color.white);

        loggerScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        pnlLeft.add(tblScrollPane);
        pnlLeft.add(btnRegister);
        pnlLeft.add(btnDelete);

        pnlRight.add(loggerScrollPane);

        add(pnlLeft, BorderLayout.WEST);
        add(pnlRight , BorderLayout.EAST);

        btnRegister.addActionListener(buttonListener);
        btnDelete.addActionListener(buttonListener);
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    public void setTableInfo(String[][] info){

        model = new DefaultTableModel(info,columns);
        tblInfo.setModel(model);
        tblInfo.repaint();

    }

    public int getSelectedRow(){
      return tblInfo.getSelectedRow();
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource() == btnRegister) {
                registerPanel = new RegisterPanel(globalServerController);
            } else if (actionEvent.getSource() == btnDelete) {
                String username = globalServerController.getUserRegister().getUser(getSelectedRow()).getUserName();
                globalServerController.deleteHome(username);
                globalServerController.getUserRegister().deleteUser(getSelectedRow());
            }
        }
    }

    public RegisterPanel getRegisterPanel() {
        return registerPanel;
    }


}

