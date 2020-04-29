package globalServer;

import globalServerGUI.MainFrame;
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

    public void addHome(String userName, Home home) {
        homes.put(userName, home);
    }

    public void deleteHome(String userName) {
        homes.remove(userName);
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
