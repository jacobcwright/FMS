package request;

/**
 * Person Request Class
 */
public class PersonRequest {
    private final String authtoken;

    public PersonRequest(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getAuthToken() {
        return authtoken;
    }
}
