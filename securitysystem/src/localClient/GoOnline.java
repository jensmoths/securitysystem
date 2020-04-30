package localClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GoOnline extends JFrame implements ActionListener {

    private JLabel username, password;
    private JTextField user;
    private JList list;
    private DefaultListModel defaultListModel;
    private JButton ok, cancel;
    private JPanel panel;
    private MainFrame mainFrame;
    private JFrame frame;
    public GoOnline(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        frame = new JFrame();
        setTitle("Go online");
       setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(500, 500);

        draw();
    }


    public void draw() {
        ArrayList<String> rey = new ArrayList<String>();
        rey.add("Karl");
        rey.add("Olof");
        rey.add("Per");
        rey.add("Jens");
        rey.add("Ammar");
        rey.add("Malek");


        defaultListModel = new DefaultListModel();
        list = new JList();

        rey.forEach((n) -> defaultListModel.addElement(n));
        list.setModel(defaultListModel);
     panel = new JPanel(new GridLayout(3,2));
        user = new JTextField();

        username = new JLabel("Username:");
        password = new JLabel("FAN VA NICE:");
        ok = new JButton("OK");
        cancel = new JButton("Cancel");
        ok.setPreferredSize(new Dimension(90,90));
        cancel.setPreferredSize(new Dimension(90,90));

        username.setPreferredSize(new Dimension(90,90));
        password.setPreferredSize(new Dimension(90,90));
        user.setPreferredSize(new Dimension(90,90));

        this.add(panel);
        panel.add(username);
        panel.add(list);
        panel.add(password);
        panel.add(user);

        panel.add(ok);
        panel.add(cancel);
        ok.addActionListener(this::actionPerformed);
        cancel.addActionListener(this::actionPerformed);

    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == ok){
            mainFrame.controller.connectToGlobal();
            JOptionPane.showMessageDialog(null, "Du har loggat in: " + user.getText());

        }

        if(actionEvent.getSource()== cancel){
            setVisible(false);

        }

    }
}