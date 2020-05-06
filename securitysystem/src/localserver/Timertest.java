package localserver;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.awt.event.*;
import java.util.TimerTask;


public class Timertest extends JFrame {

    int starttime = 10;
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
  //  JTextArea area = new JTextArea();
    JTextField area = new JTextField();
    Font font1 = new Font("SansSerif", Font.BOLD, 64);






public void start(){
    frame = new JFrame();
    frame.setSize(new Dimension(320, 420));
    frame.setExtendedState(Frame.MAXIMIZED_BOTH);
    //frame.setUndecorated(true);
    frame.setContentPane(panel);
    frame.setTitle("Numpad");
    frame.setVisible(true);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    //frame.add(panel);
    area.setPreferredSize(new Dimension(300,300));
    area.setFont(font1);
    area.setEditable(false);
    panel.add(area);

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            if (starttime >= 0){
                System.out.println(starttime);
                area.setText(String.valueOf(starttime));

                starttime--;

            }
            if (starttime == -1){
                System.out.println("Färdig");
               area.setText("Done");
                timer.cancel();
                frame.dispose();
            }

        }
    };
timer.schedule(task,0,1000);
}

    public static void main(String[] args) {
        Timertest time = new Timertest();
        time.start();


}
}
