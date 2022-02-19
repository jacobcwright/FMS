package result;

/**
 * Login Result Class
 */
public class LoginResult extends Response{
    /**
     * returned authtoken from login
     */
    String authtoken;

    /**
     * username of user login
     */
    String username;

    /**
     * personID of person associated to user login
     */
    String personID;

    /**
     * Constructor dependent on success
     * @param authtoken
     * @param username
     * @param personID
     */
    public LoginResult(String authtoken, String username, String personID) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
