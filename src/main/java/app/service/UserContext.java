package app.service;

public class UserContext {
    private static String firstName;
    private static String lastName;
    private static String email;

    private static String sesionId;


    public static String getFirstName() {
        return firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static String getEmail() {
        return email;
    }

    public static String getSesionId() {
        return sesionId;
    }

    public static void setLastName(String lastName) {
        UserContext.lastName = lastName;
    }

    public static void setEmail(String email) {
        UserContext.email = email;
    }

    public static void setSesionId(String sesionId) {
        UserContext.sesionId = sesionId;
    }

    public static void setFirstName(String firstName) {
        UserContext.firstName = firstName;
    }
}
