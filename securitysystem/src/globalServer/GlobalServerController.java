package globalServer;

import globalServerGUI.MainFrame;

import javax.swing.*;
import javax.mail.MessagingException;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class GlobalServerController implements Observer {
    private GlobalServer globalServer;
    private HashMap<String, Home> homes;
    private MainFrame mainFrame;
    private UserRegister userRegister;

    public GlobalServerController() {

        userRegister = new UserRegister();
        this.userRegister.addObserver(this);
        homes = new HashMap<>();
        mainFrame = new MainFrame(this);
        globalServer = new GlobalServer(8081, homes);
    }

    public User getUser (String userName) {
        return homes.get(userName).getUser();
    }

    public void addHome(String userName, Home home) {
        homes.put(userName, home);
    }

    public void deleteHome(String userName) {
        homes.get(userName).logger.deleteLog();
        homes.remove(userName);
    }

    public String getClientLoggerText(String username) {
        String []  startDateArray = mainFrame.getMainPanel().getStartDate();
        String []  startTimeArray = mainFrame.getMainPanel().getStartTime();
        String []  endDateArray = mainFrame.getMainPanel().getEndDate();
        String []  endTimeArray = mainFrame.getMainPanel().getEndTime();
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

            return homes.get(username).logger.getLog(startDateYear, startDateMonth, startDateDay, startTimeHour,
                    startTimeMin, endDateYear, endDateMonth, endDateDay, endTimeHour, endTimeMin);
        } catch (NullPointerException e) {
                e.printStackTrace();
        }
        return "";
    }

    public UserRegister getUserRegister(){
        return userRegister;
    }

    public void setInfo(String[][] strings){
        mainFrame.getMainPanel().setTableInfo(strings);
    }

    @Override
    public void update(Observable observable, Object o) {
        setInfo((String[][]) o);
    }

    public static void main(String[] args) {
        new GlobalServerController();
    }
}
