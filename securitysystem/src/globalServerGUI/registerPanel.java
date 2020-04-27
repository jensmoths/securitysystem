package globalServerGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class registerPanel extends JPanel {
    private JTextField tfFirstName, tfSurName, tfAddress, tfZipCode, tfCity, tfCountry;
    private JLabel lblFirstName, lblSurName, lblAddress, lblZipCode, lblCity, lblCountry;
    private JButton btnRegister;
    private Dimension lblDimension;
    private Dimension tfDimension;
    private JPanel pnlName;
    private JPanel pnlAddress;

    public registerPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 200));
        btnRegister = new JButton("Register Client");
        pnlName = new JPanel();
        pnlAddress = new JPanel();
        pnlName.setPreferredSize(new Dimension(380, 30));
        pnlName.setBackground(Color.ORANGE);

        tfAddress = new JTextField();
        tfCity = new JTextField();
        tfCountry = new JTextField();
        tfFirstName = new JTextField();
        tfSurName = new JTextField();
        tfZipCode = new JTextField();

        lblAddress = new JLabel("Address:");
        lblCity = new JLabel("City:");
        lblCountry = new JLabel("Country:");
        lblFirstName = new JLabel("First name:");
        lblSurName = new JLabel("Surname:");
        lblZipCode = new JLabel("Zip code:");

        lblDimension = new Dimension(30, 25);
        tfDimension = new Dimension(60, 25);

        draw();
        add(BorderLayout.NORTH, pnlName);
        add(BorderLayout.CENTER, pnlAddress);
    }

    public void draw() {
        btnRegister.addActionListener(new ButtonListener());
        lblFirstName.setSize(lblDimension);
        lblSurName.setSize(lblDimension);
        tfFirstName.setSize(tfDimension);
        tfSurName.setSize(tfDimension);

        pnlName.add(lblFirstName);
        pnlName.add(tfFirstName);
        pnlName.add(lblSurName);
        pnlName.add(tfSurName);
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, new registerPanel());
    }
}
