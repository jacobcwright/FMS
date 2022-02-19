package request;

/**
 * Login Request Class
 */
public class LoginRequest {
    /**
     * username for login
     */
    String username;

    /**
     * password for login
     */
    String password;

    /**
     * Constructor for login request
     * @param username
     * @param password
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
