package result;

/**
 * Person ID Result
 */
public class PersonIDResult extends Response{
    /**
     * username associated with person
     */
    String associatedUsername;

    /**
     * person id of person
     */
    String personID;

    /**
     * first name of person
     */
    String firstName;

    /**
     * last name of person
     */
    String lastName;

    /**
     * gender of person, "m" or "f"
     */
    String gender;

    /**
     * father id of person
     */
    String fatherID;

    /**
     * mother id of person
     */
    String motherID;

    /**
     * spouse id of person
     */
    String spouseID;

    /**
     * Constructor based on success
     * @param associatedUsername
     * @param personID
     * @param firstName
     * @param lastName
     * @param gender
     * @param fatherID
     * @param motherID
     * @param spouseID
     */
    public PersonIDResult(String associatedUsername, String personID, String firstName, String lastName, String gender,
                          String fatherID, String motherID, String spouseID) {
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
        this.success = true;
    }
}
