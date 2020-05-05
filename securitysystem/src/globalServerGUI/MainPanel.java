package globalServerGUI;

import globalServer.GlobalServerController;
import globalServer.User;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainPanel extends JPanel {
    private RegisterPanel registerPanel;
    private JPanel pnlLeft;
    private JPanel pnlRight;
    private JButton btnRegister;
    private JButton btnDelete;
    private JTextField tfSearch;
    private JLabel lblSearch;
    private JLabel lblEmpty;
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

        tfSearch = new JTextField();

        lblSearch = new JLabel();
        lblEmpty = new JLabel();

        textArea = new JTextArea();
        tblInfo = new JTable();

        loggerScrollPane = new JScrollPane(textArea);
        tblScrollPane = new JScrollPane(tblInfo);

        this.setBackground(new Color(60, 63, 65));
        pnlLeft.setBackground(new Color(60, 63, 65));
        pnlRight.setBackground(new Color(60, 63, 65));

        textArea.setBackground(new Color(43,43,43));
        tblScrollPane.getViewport().setBackground(new Color(43,43,43));

        this.setLayout(new BorderLayout());
        pnlLeft.setLayout(new FlowLayout());
        pnlRight.setLayout(new FlowLayout());

        this.setPreferredSize(new Dimension(1250,600));
        pnlLeft.setPreferredSize(new Dimension(800,580));
        pnlRight.setPreferredSize(new Dimension(450,580));
        tblScrollPane.setPreferredSize(new Dimension(750,370));
        loggerScrollPane.setPreferredSize(new Dimension(420,370));
        lblEmpty.setPreferredSize(new Dimension(290,40));
        tfSearch.setPreferredSize(new Dimension(200,20));

        lblSearch.setText("Search:");
        btnDelete.setText("Delete");
        btnRegister.setText("Register");

        TitledBorder borderContact = new TitledBorder("User Information");
        borderContact.setTitleColor(new Color(62, 134, 160));

        TitledBorder borderLogger = new TitledBorder("Logger");
        borderLogger.setTitleColor(new Color(62, 134, 160));

        textArea.setEditable(false);

        pnlLeft.setBorder(borderContact);
        pnlRight.setBorder(borderLogger);

        columns = new String[]{"First Name", "Last Name", "Address", "Zip Code", "City", "Username", "Password", "Email"};
        tblInfo.setForeground(Color.WHITE);
        model.setColumnIdentifiers(columns);

        tblInfo.setRowHeight(30);
        tblInfo.setModel(model);

        btnRegister.setPreferredSize(new Dimension(100,40));
        btnDelete.setPreferredSize(new Dimension(100, 40));

        btnRegister.setBackground(new Color(43,43,43));
        btnDelete.setBackground(new Color(43,43,43));

        btnRegister.setForeground(Color.white);
        btnDelete.setForeground(Color.white);

        lblSearch.setForeground(Color.white);
        textArea.setForeground(Color.white);

        loggerScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        pnlLeft.add(tblScrollPane);
        pnlLeft.add(btnRegister);
        pnlLeft.add(btnDelete);
        pnlLeft.add(lblEmpty);
        pnlLeft.add(lblSearch);
        pnlLeft.add(tfSearch);

        pnlRight.add(loggerScrollPane);

        add(pnlLeft, BorderLayout.WEST);
        add(pnlRight , BorderLayout.EAST);

        btnRegister.addActionListener(buttonListener);
        btnDelete.addActionListener(buttonListener);
        tfSearch.addKeyListener(new KeyReleasedListener());
    }

    // TODO: 05-May-20 add code to make it possible for server to see logs for individual homes
    //  Do this by fixing the lowest to do
    // TODO: 05-May-20 You need to have a search bar exactly like with the client. This means you just access
    //  their logger from Home and getLog applying filter though this gui
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

    public void searchTable(){

        DefaultTableModel tableModel = (DefaultTableModel) tblInfo.getModel();
        String search = tfSearch.getText().toLowerCase();
        TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(tableModel);
        tblInfo.setRowSorter(rowSorter);
        rowSorter.setRowFilter(RowFilter.regexFilter(search));

    }


    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource() == btnRegister) {
                // TODO: 05-May-20 Make it so that you can't register more than one user of the
                //  same info. OR you can just inplement a system where the username is added by an index if its the same.
                registerPanel = new RegisterPanel(globalServerController);
            } else if (actionEvent.getSource() == btnDelete) {
                // TODO: 05-May-20 make a way to get the username of a user from the table
                // TODO: 05-May-20 Then change the way you delete users by using object and not index in User Register
                String username = globalServerController.getUserRegister().getUser(getSelectedRow()).getUserName();
                User user = globalServerController.getUser(username);
                globalServerController.getUserRegister().deleteUser(user);
                globalServerController.deleteHome(username);
            }
        }
    }

    private class KeyReleasedListener implements KeyListener {


        @Override
        public void keyTyped(KeyEvent keyEvent) {

        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            searchTable();
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {
            searchTable();
        }
    }

    public RegisterPanel getRegisterPanel() {
        return registerPanel;
    }


}

