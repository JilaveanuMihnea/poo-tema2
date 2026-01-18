package main.users.reporter;

import main.users.user.UserFactory;

public class ReporterFactory extends UserFactory {
    @Override
    public Reporter createUser(final main.utils.UserInput userInput) {
        return new Reporter(userInput);
    }
}
