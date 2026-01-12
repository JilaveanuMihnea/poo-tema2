package main.users.user;

import lombok.Getter;
import main.utils.UserInput;

@Getter
public abstract class User {
    private enum Role {
        REPORTER,
        DEVELOPER,
        MANAGER
    }

    private String username;
    private String email;
    private Role role;

    public User(UserInput userInput) {
        this.username = userInput.getUsername();
        this.email = userInput.getEmail();
        this.role = Role.valueOf(userInput.getRole().toUpperCase());
    }

    public boolean isManager() {
        return this.role == Role.MANAGER;
    }

    public boolean isDeveloper() {
        return this.role == Role.DEVELOPER;
    }

    public boolean isReporter() {
        return this.role == Role.REPORTER;
    }
}