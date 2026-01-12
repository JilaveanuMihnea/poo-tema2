package main.users.manager;

import main.users.user.UserFactory;
import main.utils.UserInput;

public class ManagerFactory extends UserFactory {
    @Override
    public Manager createUser(final UserInput userInput) {
        return new Manager(userInput);
    }
}
