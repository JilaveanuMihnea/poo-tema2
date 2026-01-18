package main.users.developer;

import main.users.user.UserFactory;
import main.utils.UserInput;

public class DeveloperFactory extends UserFactory {
    @Override
    public Developer createUser(final UserInput userInput) {
        return new Developer(userInput);
    }
}
