package main.users;

import main.users.developer.DeveloperFactory;
import main.users.manager.ManagerFactory;
import main.users.reporter.ReporterFactory;
import main.users.user.UserFactory;

import java.util.HashMap;
import java.util.Map;

public class UserFactoryChoice {
    private static final Map<String, UserFactory> factories = new HashMap<>();

    static {
        factories.put("developer", new DeveloperFactory());
        factories.put("manager", new ManagerFactory());
        factories.put("reporter", new ReporterFactory());
    }

    public static UserFactory getFactory(String userType) {
        return factories.get(userType.toLowerCase());
    }
}
