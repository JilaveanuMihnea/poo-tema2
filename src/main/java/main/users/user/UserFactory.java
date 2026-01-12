package main.users.user;

import main.utils.UserInput;

public abstract class UserFactory {
    public abstract User createUser(UserInput userInput);
}
