package globalClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class Login extends JFrame implements ActionListener{

    private JLabel user_label, password_label, message;
    private JTextField userName_text;
    private JPasswordField password_text;
    private JButton  submit, cancel;
    private JPanel panel;
    private PasswordAuthentication passwordAuthentication; //TODO LÖSA KRYPTERING. SKAPAR EN REFERENS TILL LÖSENORDET ISTÄLLET FÖR LÖSENORDET


    public void Login() {
        // Username Label
        user_label = new JLabel();
        user_label.setText("User Name :");
        userName_text = new JTextField();
        // Password Label
        password_label = new JLabel();
        password_label.setText("Password :");
        password_text = new JPasswordField();
        // Submit
        submit = new JButton("SUBMIT");
        panel = new JPanel(new GridLayout(3, 1));
        panel.add(user_label);
        panel.add(userName_text);
        panel.add(password_label);
        panel.add(password_text);
        message = new JLabel();
        panel.add(message);
        panel.add(submit);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Adding the listeners to components..
        submit.addActionListener(this);
        add(panel, BorderLayout.CENTER);
        setTitle("Please Login Here !");
        setSize(450, 350);
        setVisible(true);

    }



        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String userName = userName_text.getText();
            String password = password_text.getText();
            if (userName.trim().equals("admin") && password.trim().equals("admin")) {
                message.setText(" Hello " + userName + "");
                new GlobalClientController("localhost");
                dispose();
            } else {
                message.setText(" Invalid user.. ");
            }
        }

        }


