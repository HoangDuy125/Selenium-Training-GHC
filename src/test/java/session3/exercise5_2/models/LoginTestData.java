package session3.exercise5_2.models;

public class LoginTestData {
    private final String username;
    private final String password;
    private final boolean expectedSuccess;

    public LoginTestData(String username, String password, boolean expectedSuccess) {
        this.username = username;
        this.password = password;
        this.expectedSuccess = expectedSuccess;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isExpectedSuccess() {
        return expectedSuccess;
    }

    public static boolean parseExpectedResult(String value) {
        if (value == null) {
            return false;
        }
        String normalized = value.trim();
        return "SUCCESS".equalsIgnoreCase(normalized) || "true".equalsIgnoreCase(normalized);
    }
}
