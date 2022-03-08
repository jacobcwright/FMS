package request;

/**
 * Person ID Request Class
 */
public class PersonIDRequest {
    /**
     * Person ID to get person
     */
    String personID;

    /**
     * Authtoken from header
     */
    String authtoken;

    /**
     * Constructor for person ID
     * @param personID
     */
    public PersonIDRequest(String personID, String authtoken) {
        this.personID = personID;
        this.authtoken = authtoken;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAuthtoken() {
        return authtoken;
    }
}
