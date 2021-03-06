package result;

import model.Event;

/**
 * EventID Result Class
 */
public class EventIDResult extends Response{
    /**
     * username associated with event
     */
    String associatedUsername;

    /**
     * eventID for event
     */
    String eventID;

    /**
     * person ID of person in event
     */
    String personID;

    /**
     * latitude of event
     */
    float latitude;

    /**
     * longitude of event
     */
    float longitude;

    /**
     * country of event
     */
    String country;

    /**
     * city of event
     */
    String city;

    /**
     * type of event
     */
    String eventType;

    /**
     * year event occurred
     */
    int year;

    /**
     * Constructor based on success
     * @param associatedUsername
     * @param eventID
     * @param personID
     * @param latitude
     * @param longitude
     * @param country
     * @param city
     * @param eventType
     * @param year
     */
    public EventIDResult(Boolean success, String associatedUsername, String eventID, String personID, float latitude, float longitude,
                         String country, String city, String eventType, int year) {
        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
        this.success = true;
    }

    /**
     * constructor for when passed an event
     * @param e
     */
    public EventIDResult(Event e){
        this.associatedUsername = e.getUsername();
        this.eventID = e.getEventID();
        this.personID = e.getPersonID();
        this.latitude = (float) e.getLatitude();
        this.longitude = (float) e.getLongitude();
        this.country = e.getCountry();
        this.city = e.getCity();
        this.eventType = e.getEventType();
        this.year = e.getYear();
        success = true;
    }

    /**
     * super constructor
     * @param b
     * @param message
     */
    public EventIDResult(boolean b, String message) {
        super(b, message);
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getEventType() {
        return eventType;
    }

    public int getYear() {
        return year;
    }
}
