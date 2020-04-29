package localClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class FingerprintGui extends JFrame implements ActionListener {
    private MainFrame mainFrame;
    private JFrame fingerFrame;
    private JButton add, delete, empty;
    private JPanel panel;
    private JTextArea addText, deleteText, clearText;
    private JList list;

    public FingerprintGui(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        fingerFrame = new JFrame();
        setTitle("Fingerprint Scanner");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(600, 600);
        draw();

    }
    public void draw(){
        panel = new JPanel(new GridLayout(6,6));
            addText = new JTextArea();
            deleteText = new JTextArea();
            clearText = new JTextArea();
            list = new JList();
                add = new JButton("Add");
                delete = new JButton("Delete");
                empty = new JButton("Clear");
                add.addActionListener(this::actionPerformed);
                delete.addActionListener(this::actionPerformed);
                empty.addActionListener(this::actionPerformed);
                addText.setBorder(BorderFactory.createTitledBorder("Add"));
        deleteText.setBorder(BorderFactory.createTitledBorder("Delete"));
        clearText.setBorder(BorderFactory.createTitledBorder("Clear"));

            this.add(panel);
                panel.add(addText);
                panel.add(deleteText);
                panel.add(clearText);
                panel.add(add);
                panel.add(delete);
                panel.add(empty);


    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == add){
            try {
                mainFrame.controller.sendToMK('a');
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (actionEvent.getSource() == delete){
            try {
                mainFrame.controller.sendToMK('d');
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (actionEvent.getSource() == empty){
            try {
                mainFrame.controller.sendToMK('e');
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
