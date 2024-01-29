package app.service;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
        private static final Map<String, String> loggedInUsers = new HashMap<>();

        public static void loginUser(String sessionId, String userName) {
            loggedInUsers.put(sessionId, userName);
        }

        public static void logoutUser(String sessionId) {
            loggedInUsers.remove(sessionId);
        }

        public static String getLoggedInUserName(String sessionId) {
            return loggedInUsers.get(sessionId);
        }
}
