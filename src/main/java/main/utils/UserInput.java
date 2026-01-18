package main.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserInput {
    private String username;
    private String email;
    private String role;
    private String hireDate;
    private String expertiseArea;
    private String seniority;
    private List<String> subordinates;
}
