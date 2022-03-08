package result;

import model.Person;

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

    public PersonIDResult(boolean b, String m) {
        this.success = b;
        this.message = m;
    }

    public PersonIDResult(Person found) {
        this.associatedUsername = found.getAssociatedUsername();
        this.personID = found.getPersonID();
        this.firstName = found.getFirstName();
        this.lastName = found.getLastName();
        this.gender = found.getGender();
        this.fatherID = found.getFatherID();
        this.motherID = found.getMotherID();
        this.spouseID = found.getSpouseID();
        this.success = true;
    }
}
