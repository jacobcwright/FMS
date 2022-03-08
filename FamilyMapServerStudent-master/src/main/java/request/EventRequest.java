package request;

/**
 * Event request class
 */
public class EventRequest {
    private final String authToken;

    public EventRequest(String a) {
        authToken = a;
    }

    public String getAuthToken() {
        return authToken;
    }
}
