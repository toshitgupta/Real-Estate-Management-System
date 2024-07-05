import java.io.*;
import java.util.*;

public class User implements Serializable {
    public String username;
    String passwd; //Will store password as SHA-256 in future instead of strings

    //type of user, which can be set to the below 3 constants
    int type;
    public static final int ADMIN = 0;
    public static final int AGENT = 1;
    public static final int OFFICE = 2;
    private static final int SUPER_USER = 255;

    User(String username, String passwd, int type) {
        //Initializes the values and adds the instance to userList
        this.username = username;
        this.passwd = passwd;
        this.type = type;
    }
}


class UserManager{
    private final ArrayList<User> userList = new ArrayList<>();
    UserManager(){
        readUsers();
        displayUsers();
        if(find("super000") == -1) {
            addUser("super000", "qwerty", 255);
            saveUsers();
        }
    }
    public int saveUsers(){
        try {
            //Creates OutputStreams and writes every user object in userList into user.bin
            //new File("user.bin").delete();
            FileOutputStream file = new FileOutputStream("user.bin");
            ObjectOutputStream objectOut = new ObjectOutputStream(file);
            for(User user: userList){
                objectOut.writeObject(user);
            }
            objectOut.flush();
            objectOut.close();
            file.close();
            return 0; //Returns 0 to indicate successful save. Inspired by C and UNIX error codes
        } catch (FileNotFoundException e) {
            return 1; //Indicates FOF
        } catch (IOException e) {
            return 2; //Indicates IO
        }
    }

    public int readUsers(){
        try {
            //Creates InputStreams and reads objects from user.bin into userList.
            FileInputStream file = new FileInputStream("user.bin");
            ObjectInputStream ois = new ObjectInputStream(file);
            userList.clear(); //Cleared to prevent duplicates
            User usr = (User) ois.readObject();
            while (usr != null){
                userList.add(usr);
                usr = (User) ois.readObject();
            }
            return 0; //Similar to saveUsers()
        }catch (EOFException e){
            return 0; //EOFException thrown when completely reading the file, indicates successful read
        }
        //Below 3 catch blocks have similar error codes to saveUsers()
        catch (FileNotFoundException e) {
            return 1;
        } catch (IOException e) {
            return 2;
        } catch (ClassNotFoundException e) {
            return 3;
        }
    }

    public void displayUsers(){
        //For debugging
        for(User usr:userList){
            System.out.println(usr.username + " :: " + usr.type);
        }
    }

    public void clearUsers(){
        //Clears userList, not really needed but might come in handy during debugging
        userList.clear();
    }

    public static void main(String[] args) {
        //Main in general will be run during programming phase to populate users and shouldn't be run elsewhere.
        //Adding an initial list of users and saving their details.
    }

    private void test(){
        //Testing whether readUsers() works as intended or not
        readUsers();
        displayUsers();
    }

    public int find(String username){
        int i = 0;
        for(User u:userList){
            if (u.username.equals(username)){
                return i;
            }
            i++;
        }
        return -1;
    }

    public int find(String username, String passwd){
        for(User u:userList){
            if (u.username.equals(username) && u.passwd.equals(passwd)){
                return 0;
            }
        }
        return 10;
    }

    private User getUser(String username, String passwd){
        for(User u:userList){
            if (u.username.equals(username) && u.passwd.equals(passwd)){
                return u;
            }
        }
        return null;
    }

    public int addUser(String username, String passwd, int type){
        if(find(username) == -1) {
            userList.add(new User(username, passwd, type));
            return 0;
        }
        return 10;
    }

    public int removeUser(String username){
        int index = find(username);
        if(index != -1){
            userList.remove(index);
            return 0;
        }
        return 10;
    }

    public String[] getDetails(String username){
        int i = find(username);
        if(i != -1){
            User u = userList.get(i);
            if(u.type == 0)
                return new String[]{username, "Admin"};
            else if (u.type == 1)
                return new String[]{username, "Agent"};
            else if (u.type == 2)
                return new String[]{username, "Office"};
            else if (u.type == 255)
                return new String[]{username, "Super User"};
        }
        return new String[]{};
    }

    public int modify(String username, int newRole){
        int i = find(username);
        if(i != -1){
            User u = userList.get(i);
            u.type = newRole;
            return 0;
        }
        return 10;
    }
}
