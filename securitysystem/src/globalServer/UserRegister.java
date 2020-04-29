package globalServer;



import java.util.*;

public class UserRegister extends Observable {

    private LinkedList<User> users = new LinkedList<>();
    private String[][] userString;

    public void addUser(User user){
        this.users.add(user);
        System.out.println(Arrays.deepToString(user.getUserInfo()));
        getUserInformation();
    }

    public void deleteUser(int index){

        users.remove(index);
        getUserInformation();

    }

    public User getUser(int index) {
        return users.get(index);
    }

    public void getUserInformation(){

        userString = new String[users.size()][7];

        for(int i = 0 ; i<users.size(); i++){
            for (int j=0; j<userString[i].length; j++){
                userString[i][j] = users.get(i).getUserInfo()[j];
            }
        }
        System.out.println("NERREE" + Arrays.deepToString(userString));
        setChanged();
        notifyObservers(userString);
    }




}