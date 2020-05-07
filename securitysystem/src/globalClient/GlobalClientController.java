package globalClient;

import globalServer.Logger;
import model.SecurityComponent;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class GlobalClientController {
    private GlobalClient globalClient;
    private MainFrame mainFrame;
    private Logger logger;

    public GlobalClientController() {
        globalClient = new GlobalClient("localhost", 8081, this);
        mainFrame = new MainFrame(this);
    }

    public void authenticateUser() {
        mainFrame.disposeLoginPanel();
        mainFrame.showMainPanel();
    }

    public void send(String string) {
        globalClient.send(string);
    }

    public void closeSocket() {
        globalClient.closeSocket();
    }

    // TODO: 05-May-20 Add code to make it possible for client to typ in own time
    public void updateLog(Logger logger) {
        mainFrame.setTextArea(logger.getLog(2020, 5, 5, 1, 6,
                2021, 5, 5, 1, 44));
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public String getClientLoggerText() {
        String[] startDateArray = mainFrame.getMainPanel().getStartDate();
        String[] startTimeArray = mainFrame.getMainPanel().getStartTime();
        String[] endDateArray = mainFrame.getMainPanel().getEndDate();
        String[] endTimeArray = mainFrame.getMainPanel().getEndTime();
        try {
            int startDateYear = Integer.parseInt(startDateArray[0]);
            int startDateMonth = Integer.parseInt(startDateArray[1]);
            int startDateDay = Integer.parseInt(startDateArray[2]);
            int startTimeHour = Integer.parseInt(startTimeArray[0]);
            int startTimeMin = Integer.parseInt(startTimeArray[1]);
            int endDateYear = Integer.parseInt(endDateArray[0]);
            int endDateMonth = Integer.parseInt(endDateArray[1]);
            int endDateDay = Integer.parseInt(endDateArray[2]);
            int endTimeHour = Integer.parseInt(endTimeArray[0]);
            int endTimeMin = Integer.parseInt(endTimeArray[1]);

            return logger.getLog(startDateYear, startDateMonth, startDateDay, startTimeHour,
                    startTimeMin, endDateYear, endDateMonth, endDateDay, endTimeHour, endTimeMin);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void showImage(ImageIcon imageIcon) {
        mainFrame.showImage(imageIcon);
    }

    public void setOnlinesensor(ArrayList<SecurityComponent> rey){
        mainFrame.getMainPanel().setOnlineSensor(rey);
    }
    public void setOfflinesensor(ArrayList<SecurityComponent> rey){
        mainFrame.getMainPanel().setOfflineSensor(rey);
    }
    public void clearList(){
        mainFrame.getMainPanel().clearList();
    }

    public static void main(String[] args) {
        new GlobalClientController();
    }
}
