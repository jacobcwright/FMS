package result;

import model.Event;

import java.util.ArrayList;

/**
 * Event Result Class
 */
public class EventResult extends Response{
    /**
     * Array of event objects
     */
    ArrayList<Event> data;

    /**
     * Constructor assuming success
     * @param e
     */
    public EventResult(ArrayList<Event> e){
        data = e;
        success = true;
    }

    public EventResult(boolean b, String s) {
        this.success = b;
        this.message = s;
    }

    public ArrayList<Event> getEvents() {
        return data;
    }

    public void setEvents(ArrayList<Event> events) {
        this.data = events;
    }
}
