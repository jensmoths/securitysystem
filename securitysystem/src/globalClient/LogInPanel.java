package globalClient;


import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogInPanel extends JPanel {

    private JTextField tfUsername, tfPassword;
    private JLabel lblUsername, lblPassword;
    private JButton btnLogIn;
    private JFrame frame;
    private GlobalClient globalClient;

    public LogInPanel(GlobalClient globalClient) {
        this.globalClient = globalClient;
        frame = new JFrame();
        this.setLayout(new MigLayout());
        setBackground(new Color(83, 86, 91));

        btnLogIn = new JButton("Log In");
        lblUsername = new JLabel("Username:");
        lblPassword = new JLabel("Password:");
        tfPassword = new JTextField();
        tfUsername = new JTextField();

        draw();

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(new Dimension(220, 130));
        frame.setLocation(dim.width / 3 - frame.getSize().width / 3, dim.height / 3 - frame.getSize().height / 3);
        frame.setContentPane(this);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setTitle("LogIn");
    }

    public void draw() {
        btnLogIn.addActionListener(new ButtonListener());
        tfUsername.setPreferredSize(new Dimension(120, 20));
        lblUsername.setForeground(Color.white);
        lblPassword.setForeground(Color.white);
        add(lblUsername);
        add(tfUsername, "span");
        add(lblPassword);
        add(tfPassword, "span, grow");
        add(btnLogIn, "span 2, grow,  wrap");
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnLogIn) {
                if (!tfUsername.getText().equals("") | !tfPassword.getText().equals("")) {
                    globalClient.send(tfUsername.getText());
                    globalClient.send(tfPassword.getText());
                } else {
                    JOptionPane.showMessageDialog(null, "Fill in all the fields correctly!");
                }
            }
        }
    }
}
