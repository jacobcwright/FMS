package request;

/**
 * EventID Request Class
 */
public class EventIDRequest {
    /**
     * eventID of desired event
     */
    String eventID;

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
}
