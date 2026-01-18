package main.users.manager;

import main.users.user.User;
import main.utils.UserInput;

import java.util.List;

public class Manager extends User {
    private String hireDate;
    private List<String> subordinates;

    public Manager(UserInput userInput) {
        super(userInput);
        this.hireDate = userInput.getHireDate();
        this.subordinates = userInput.getSubordinates();
    }
}
