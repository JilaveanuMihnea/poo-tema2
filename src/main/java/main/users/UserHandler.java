package main.users;

import main.users.user.User;
import main.utils.UserInput;

import java.util.ArrayList;
import java.util.List;

public class UserHandler {
    private static UserHandler instance;
    List<User> users;

    private UserHandler() {
    }

    public static UserHandler getInstance() {
        if (instance == null) {
            instance = new UserHandler();
        }
        return instance;
    }

    public void loadUsers(List<UserInput> usersList) {
        users = new ArrayList<>();
        for (UserInput userInput : usersList) {
            User user = UserFactoryChoice.getFactory(userInput.getRole()).createUser(userInput);
            users.add(user);
        }
    }

    public boolean userExists(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
