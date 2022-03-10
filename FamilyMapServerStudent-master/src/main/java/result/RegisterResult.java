package result;

/**
 * Register Result class
 */
public class RegisterResult extends Response{
    /**
     * authtoken after registering
     */
    String authtoken;

    /**
     * username of new user
     */
    String username;

    /**
     * ID of person created
     */
    String personID;

    /**
     * Constructor for result. Success/Failure dependent.
     * @param authtoken
     * @param username
     * @param personID
     */
    public RegisterResult(String authtoken, String username, String personID) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.success = true;
    }

    /**
     * constructor for failed results
     * @param b
     * @param m
     */
    public RegisterResult(boolean b, String m) {
        success = b;
        message = m;
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
