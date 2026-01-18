package main.users.developer;

import main.users.user.User;
import main.utils.UserInput;

public class Developer extends User {
    private enum ExpertiseArea {
        FRONTEND,
        BACKEND,
        FULLSTACK,
        DEVOPS,
        DB,
        DESIGN
    }
    private enum SeniorityLevel {
        JUNIOR,
        MID,
        SENIOR
    }

    private String hireDate;
    private ExpertiseArea expertiseArea;
    private SeniorityLevel seniority;

    public Developer(final UserInput userInput) {
        super(userInput);
        this.hireDate = userInput.getHireDate();
        this.expertiseArea = ExpertiseArea.valueOf(userInput.getExpertiseArea().toUpperCase());
        this.seniority = SeniorityLevel.valueOf(userInput.getSeniority().toUpperCase());
    }
}
