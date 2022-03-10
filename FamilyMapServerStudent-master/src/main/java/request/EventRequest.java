package request;

/**
 * Event request class
 */
public class EventRequest {
    /**
     * authtoken -- will not change
     */
    private final String authToken;

    /**
     * constructor for authtoken
     * @param a
     */
    public EventRequest(String a) {
        authToken = a;
    }

    public String getAuthToken() {
        return authToken;
    }
}
