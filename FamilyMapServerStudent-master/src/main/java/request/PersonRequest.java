package request;

/**
 * Person Request Class
 */
public class PersonRequest {
    /**
     * authtoken -- will not change once set
     */
    private final String authtoken;

    /**
     * constructor for authtoken
     * @param authtoken
     */
    public PersonRequest(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getAuthToken() {
        return authtoken;
    }
}
