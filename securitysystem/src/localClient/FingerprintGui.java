package localClient;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;

public class FingerprintGui extends JFrame implements ActionListener {
    private MainFrame mainFrame;
    private JFrame fingerFrame;
    private JButton add, delete, empty;
    private JPanel panel;
    private JFormattedTextField addText;
    private JFormattedTextField deleteText;
    private JList list;
    private MaskFormatter mask;

    public FingerprintGui(MainFrame mainFrame) throws ParseException {
        this.mainFrame = mainFrame;

        fingerFrame = new JFrame();
        setTitle("Fingerprint Scanner");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(600, 600);
        draw();

    }
    public void draw() throws ParseException {
        //mask = new MaskFormatter("###");
        panel = new JPanel(new GridLayout(6,6));
        addText = new JFormattedTextField(1);
        deleteText = new JFormattedTextField(1);
        list = new JList();
        //mask.setMask("###");
        add = new JButton("Add");
        delete = new JButton("Delete");
        empty = new JButton("Clear");
        add.addActionListener(this::actionPerformed);
        delete.addActionListener(this::actionPerformed);
        empty.addActionListener(this::actionPerformed);
        addText.setBorder(BorderFactory.createTitledBorder("Add"));
        deleteText.setBorder(BorderFactory.createTitledBorder("Delete"));

        this.add(panel);
        panel.add(addText);
        panel.add(deleteText);
        panel.add(add);
        panel.add(delete);
        panel.add(empty);


    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() ==add){
            try {

                int i = (int) addText.getValue();
                System.out.println(i);
                mainFrame.controller.sendToMK('a',i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (actionEvent.getSource() == delete){
            try {
                int i = Integer.parseInt((String)deleteText.getText());
                mainFrame.controller.sendToMK('d',i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (actionEvent.getSource() == empty){
            try {
                mainFrame.controller.sendToMK('e',0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

