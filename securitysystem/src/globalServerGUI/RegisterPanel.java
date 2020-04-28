package globalServerGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.miginfocom.swing.MigLayout;

public class RegisterPanel extends JPanel {
    private JTextField tfFirstName, tfSurName, tfStreet, tfZipCode, tfCity, tfCountry;
    private JLabel lblFirstName, lblSurName, lblStreet, lblZipCode, lblCity, lblCountry;
    private JButton btnRegister;


    public RegisterPanel() {
        this.setLayout(new MigLayout());
        setPreferredSize(new Dimension(250, 200));
        setBackground(new Color(83, 86, 91));

        btnRegister = new JButton("Register Client");
        tfStreet = new JTextField();
        tfCity = new JTextField();
        tfCountry = new JTextField();
        tfFirstName = new JTextField();
        tfSurName = new JTextField();
        tfZipCode = new JTextField();

        lblStreet = new JLabel("Street:");
        lblCity = new JLabel("City:");
        lblCountry = new JLabel("Country:");
        lblFirstName = new JLabel("First name:");
        lblSurName = new JLabel("Surname:");
        lblZipCode = new JLabel("Zip code:");
        draw();

        JFrame frame = new JFrame();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/3-frame.getSize().width/3, dim.height/3-frame.getSize().height/3);
        frame.setSize(new Dimension(350, 150));
        frame.setContentPane(this);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setTitle("Registration");
    }

    public void draw() {
        btnRegister.addActionListener(new ButtonListener());
        tfFirstName.setPreferredSize(new Dimension(100, 20));
        tfSurName.setPreferredSize(new Dimension(100, 20));

        lblCity.setForeground(Color.white);
        lblCountry.setForeground(Color.white);
        lblFirstName.setForeground(Color.white);
        lblStreet.setForeground(Color.white);
        lblSurName.setForeground(Color.white);
        lblZipCode.setForeground(Color.white);
        add(lblFirstName);
        add(tfFirstName);
        add(lblSurName, "gap unrelated");
        add(tfSurName, "wrap");
        add(lblStreet);
        add(tfStreet, "span, grow");
        add(lblCity);
        add(tfCity, "grow");
        add(lblZipCode, "gap unrelated");
        add(tfZipCode, "span, grow");
        add(btnRegister, "span 2, grow,  wrap");
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (tfFirstName.getText().isEmpty() | tfSurName.getText().isEmpty() | tfStreet.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Fill in all the fields correctly!");
            } else {
                // TODO: 28-Apr-20 Create a new Home instance with the auto generated inparameters and put that instance in ServerController
            }
        }
    }
}
