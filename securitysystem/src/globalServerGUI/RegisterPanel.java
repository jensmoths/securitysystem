package globalServerGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import globalServer.GlobalServerController;
import globalServer.Home;
import globalServer.User;
import globalServer.UserRegister;
import net.miginfocom.swing.MigLayout;

public class RegisterPanel extends JPanel {
    private JTextField tfFirstName, tfSurName, tfStreet, tfZipCode, tfCity;
    private JLabel lblFirstName, lblSurName, lblStreet, lblZipCode, lblCity, lblCountry;
    private JButton btnRegister;
    private GlobalServerController globalServerController;
    private JFrame frame;
    private UserRegister register;

    public RegisterPanel(GlobalServerController globalServerController) {
        this.globalServerController = globalServerController;
        register = new UserRegister();
        frame = new JFrame();
        this.setLayout(new MigLayout());
        setPreferredSize(new Dimension(250, 200));
        setBackground(new Color(83, 86, 91));

        btnRegister = new JButton("Register Client");
        tfStreet = new JTextField();
        tfCity = new JTextField();
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

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 3 - frame.getSize().width / 3, dim.height / 3 - frame.getSize().height / 3);
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
            // TODO: 29-Apr-20 Avkommentera alla kommentaren nedan för slutversion
            /*
            if ((tfFirstName.getText().isEmpty() | tfFirstName.getText().length() < 2)
                    | (tfSurName.getText().isEmpty() | tfSurName.getText().length() < 2)
                    | (tfStreet.getText().isEmpty() | tfStreet.getText().length() < 3)) {
                JOptionPane.showMessageDialog(null, "Fill in all the fields correctly!");
            } else {*/
                /*
                User user = new User(tfFirstName.getText(), tfSurName.getText(),
                                     tfStreet.getText(), tfZipCode.getText(), tfCity.getText());*/
                User user = new User("Malek", "Abdul Sater", "Sörbäcksgatan 4", "21625", "Malmö");
                //user.generateLogInDetails();
                user.setUserName("admin");
                user.setPassword("password");
                globalServerController.getUserRegister().addUser(user);
                globalServerController.addHome(user.getUserName(), new Home(user));
                System.out.println("created and added home");
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        RegisterPanel.this.frame.dispose();
                    }
                });
            /*}*/
        }
    }
}
