package app.service;

import app.views.UserPanel;
import org.apache.catalina.User;

import java.util.HashMap;
import java.util.Map;

public abstract class SessionManager {

    private static final Map<String, Map<String, String>> loggedInUsers = new HashMap<>();
    UserPanel userPanel = new UserPanel();

    public static void loginUser(String sessionId, String firstName, String lastName, String email) {
        Map<String, String> userAttributes = new HashMap<>();
        if(sessionId != null && firstName != null && lastName != null && email != null){
            userAttributes.put("firstName", firstName);
            userAttributes.put("lastName", lastName);
            userAttributes.put("email", email);

            userPanel.sesionUserData(firstName, lastName, email);
            loggedInUsers.put(sessionId, userAttributes);

            System.out.println(loggedInUsers);
        }


    }

    public static void logoutUser(String sessionId) {
        loggedInUsers.remove(sessionId);
    }
}
