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
     * Constructor for person ID
     * @param personID
     */
    public PersonIDRequest(String personID) {
        this.personID = personID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
