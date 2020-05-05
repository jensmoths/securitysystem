package globalServer;

import globalServerGUI.MainFrame;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class GlobalServerController implements Observer {
    private GlobalServer globalServer;
    private HashMap<String, Home> homes;
    private MainFrame mainFrame;
    private UserRegister userRegister;
    private EmailSender emailSender;

    public GlobalServerController() {

        emailSender = new EmailSender();
        userRegister = new UserRegister();
        this.userRegister.addObserver(this);
        homes = new HashMap<>();
        mainFrame = new MainFrame(this);
        globalServer = new GlobalServer(8081, homes);
    }

    public void addHome(String userName, Home home) {
        homes.put(userName, home);
    }

    public void sendEmail(String recipient, String subject, String information) throws MessagingException {

        emailSender.sendMail(recipient,subject,information);

    }

    public User getUser(String userName){

        return homes.get(userName).getUser();

    }

    public void deleteHome(String userName) {
        homes.get(userName).logger.deleteLog();
        homes.remove(userName);
    }

    public Logger getClientLogger(String username) {
        return homes.get(username).logger;
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
