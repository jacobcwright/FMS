package model;

import java.util.Objects;

/**
 * Person Model Class. Models data from person table.
 */
public class Person {
    /**
     * Unique identifier for this person
     * string
     */
    String personID;

    /**
     * Username of user to which this person belongs
     * string
     */
    String associatedUsername;

    /**
     * Person’s first name
     * string
     */
    String firstName;

    /**
     * Person’s last name
     * string
     */
    String lastName;

    /**
     * Person’s gender
     * string: "f" or "m"
     */
    String gender;

    /**
     * Person ID of person’s father
     * string, may be null
     */
    String fatherID;

    /**
     * Person ID of person’s mother
     * string, may be null
     */
    String motherID;

    /**
     * Person ID of person’s spouse
     * string, may be null
     */
    String spouseID;

    /**
     * Person Constructor with params.
     * @param personID
     * @param associatedUsername
     * @param firstName
     * @param lastName
     * @param gender
     * @param fatherID
     * @param motherID
     * @param spouseID
     */
    public Person(String personID, String associatedUsername, String firstName, String lastName,
                  String gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    @Override
    public boolean equals(Object obj) {
        Person person = (Person) obj;
        if(!person.getPersonID().equals(this.getPersonID())){
            return false;
        }
        if(!person.associatedUsername.equals(this.associatedUsername)){
            return false;
        }
        if(!person.firstName.equals(this.firstName)){
            return false;
        }
        if(!person.lastName.equals(this.lastName)){
            return false;
        }
        if(!person.gender.equals(this.gender)){
            return false;
        }
        if(!Objects.equals(person.fatherID, this.fatherID)){
            return false;
        }
        if(!Objects.equals(person.motherID, this.motherID)){
            return false;
        }
        if(!Objects.equals(person.spouseID, this.spouseID)){
            return false;
        }

        return true;
    }
}
