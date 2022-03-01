package request;

/**
 * EventID Request Class
 */
public class EventIDRequest {
    /**
     * eventID of desired event
     */
    String eventID;
    String authtoken;

    /**
     * constructor with authtoken
     * @param eventID
     * @param authtoken
     */
    public EventIDRequest(String eventID, String authtoken) {
        this.eventID = eventID;
        this.authtoken = authtoken;
    }

    /**
     * Constructor for eventID
     * @param eventID
     */
    public EventIDRequest(String eventID) {
        this.eventID = eventID;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }
}
