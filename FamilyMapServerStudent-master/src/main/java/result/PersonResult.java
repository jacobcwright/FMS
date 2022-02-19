package result;

import model.Person;

/**
 * Person result class
 */
public class PersonResult extends Response{
    /**
     * Array of person objects
     */
    Person[] persons;

    /**
     * constructor based on success
     * @param persons
     */
    public PersonResult(Person[] persons) {
        this.persons = persons;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }
}
