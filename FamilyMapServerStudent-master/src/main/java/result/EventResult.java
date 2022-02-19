package result;

import model.Event;

/**
 * Event Result Class
 */
public class EventResult extends Response{
    /**
     * Array of event objects
     */
    Event[] events;

    /**
     * Constructor assuming success
     * @param e
     */
    public EventResult(Event[] e){
        events = e;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
